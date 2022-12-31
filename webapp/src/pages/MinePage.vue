<template>
    <el-container style="height: 100%;display: flex;flex-direction: column;">
        <el-header style="font-weight: bold;">地址管理</el-header>
        <div class="addressContainer">
            <el-table style="width: 100%;" :data="addresses" :border="true">
                <el-table-column prop="district" label="区域"></el-table-column>
                <el-table-column prop="detail" label="详细地址"></el-table-column>
                <el-table-column>
                    <template #default="scope">
                        <el-button type="danger" @click="deleteAddress(scope.row)">删除地址</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="mapContainer">
            <div class="bmap" id="container"></div>
            <div class="operationContainer">
                <div class="input">
                    <text style="width:100px;">经度</text>
                    <el-input v-model="target.point.lng"></el-input>
                </div>
                <div class="input">
                    <text style="width:100px;">纬度</text>
                    <el-input v-model="target.point.lat"></el-input>
                </div>
                <div class="input">
                    <text style="width:100px;">区域</text>
                    <el-input v-model="target.district"></el-input>
                </div>
                <div class="input">
                    <text style="width:100px;">详细地址</text>
                    <el-input v-model="target.detail" type="textarea"></el-input>
                </div>
                <el-button @click="addAddress" type="primary">增加地址</el-button>
            </div>
        </div>
    </el-container>
</template>

<script>
import { useRouter } from "vue-router";
import BMap from "BMap";
import { ElMessageBox } from "element-plus";
export default {
    name: "MinePage",
    created() {
        const router = useRouter()
        this.username = router.currentRoute.value.query.username
        this.getLocation()
        this.refreshAddress()
    },
    data() {
        return {
            username: "",
            currentLocation: "",
            addresses: [],
            target: {
                point: {},
                district: "",
                detail: ""
            }
        }
    },
    methods: {
        getLocation() {
            const getLocation = new BMap.Geolocation();
            getLocation.getCurrentPosition((position) => {
                this.currentLocation = position
                this.target.point = position.point
                this.target.district = position.address.province + position.address.city + position.address.district
                this.initMap()
            }, () => {
                ElMessageBox({
                    message: "定位失败",
                    type: "error",
                    center: true
                })
            }, { provider: 'baidu' });
        },

        initMap() {
            var map = new BMap.Map("container");
            map.centerAndZoom(this.currentLocation.point, 12);
            let that = this
            map.addEventListener('click', function (e) {
                that.target.point = e.point
                let gc = new BMap.Geocoder()
                gc.getLocation(e.point, function (rs) {
                    that.target.district = rs.address
                })
            })
        },
        refreshAddress() {
            this.$axios("/address/getByUser?username=" + this.username).then(res => {
                this.addresses = res.data
            })
        },
        addAddress() {
            this.$axios.post("/address/add?username=" + this.username, this.target).then(res => {
                console.log(res)
                this.$message({
                    message: "添加地址成功",
                    type: "success",
                    center: true
                })
                this.refreshAddress()
            })
        },
        deleteAddress(item) {
            ElMessageBox.confirm("确定删除？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(res => {
                if (res == "confirm") {
                    this.$axios.delete("/address/" + item.id, { data: "" }).then(res => {
                        console.log(res)
                        this.$message({
                            message: "商品删除成功",
                            type: "success",
                            center: true
                        })
                        this.refreshAddress()
                    })
                }
            })
        }
    }
}
</script>
<style scoped>
.bmap {
    width: 60%;
    height: 100%;
    border: 1px solid #000;
}

.addressContainer {
    height: 40%;
    width: 100%;
}

.mapContainer {
    height: 60%;
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.operationContainer {
    align-items: center;
    display: flex;
    flex-direction: column;
    padding: 10px;

}

.input {
    display: flex;
    align-items: center;
    margin-right: 20px;
    margin-bottom: 20px;
}
</style>