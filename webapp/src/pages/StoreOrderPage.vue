<template>
    <el-container>
        <el-header class="header">
            <div :class="item.id == currentStore.id ? 'selectedStoreItem' : 'storeItem'" v-for="item in stores"
                :key="item" @click="changeStore(item)">
                {{ item.name }}
            </div>
        </el-header>
        <el-main>
            <el-table :data="currentStore.orders" :border="true" style="width: 100%;">
                <el-table-column prop="id" label="订单编号"></el-table-column>
                <el-table-column prop="time" label="下单时间" :formatter="formatDate"></el-table-column>
                <el-table-column label="状态">
                    <template #default="scope">
                        <el-tag type="info" v-if="scope.row.status == 'Paid'">未配送</el-tag>
                        <el-tag v-if="scope.row.status == 'Finished'">配送完成</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="详情" :width="100">
                    <template #default="scope">
                        <el-button type="primary" @click="showDetail(scope.row)">详情</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="配送" :width="100">
                    <template #default="scope">
                        <el-button type="success" @click="finishOrder(scope.row)"
                            :disabled="scope.row.status == 'Finished'">送达</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog v-model="showOrder" class="detailContainer">
                <div class="infoContainer">
                    <text class="detailLabel">订单编号：</text>
                    <text class="detailContent">{{ detailOrder.id }}</text>
                </div>
                <div class="infoContainer">
                    <text class="detailLabel">顾客名称：</text>
                    <text class="detailContent">{{ detailOrder.customerName }}</text>
                </div>
                <div class="infoContainer">
                    <text class="detailLabel">下单时间：</text>
                    <text class="detailContent">{{ new Date(detailOrder.time).toLocaleString() }}</text>
                </div>
                <div class="infoContainer">
                    <text class="detailLabel">货物名称：</text>
                    <text class="detailContent">{{ detailOrder.items[0].product.name }}</text>
                </div>
                <div class="infoContainer">
                    <text class="detailLabel">货物数量：</text>
                    <text class="detailContent">{{ detailOrder.items[0].count }}</text>
                </div>
                <div class="infoContainer">
                    <text class="detailLabel">顾客备注：</text>
                    <text class="detailContent" v-if="detailOrder.remark">{{ detailOrder.remark }}</text>
                </div>
                <div class="infoContainer">
                    <text class="detailLabel">详细地址：</text>
                    <text class="detailContent" v-if="detailOrder.address">{{ detailOrder.address.district +
                            detailOrder.address.detail
                    }}</text>
                </div>
            </el-dialog>
        </el-main>
    </el-container>
</template>
<script>
import { ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";
export default {
    name: "StoreOrderPage",
    data() {
        return {
            username: "",
            stores: [],
            currentStore: {},
            showOrder: false,
            detailOrder: {}
        }
    },
    methods: {
        changeStore(store) {
            this.currentStore = store
        },
        refreshCurrentStore() {
            this.$axios("/store/id/" + this.currentStore.id).then(res => {
                let index = this.stores.indexOf(this.currentStore)
                this.currentStore = res.data
                this.stores[index] = this.currentStore
            })
        },
        formatDate(row, column, cellValue, index) {
            console.log(row)
            console.log(column)
            console.log(index)
            var s = new Date(cellValue).toLocaleString();
            return s;
        },
        showDetail(item) {
            this.showOrder = true
            this.detailOrder = item
        },
        finishOrder(item) {
            ElMessageBox.confirm("确定已经把商品送达？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(res => {
                if (res == "confirm") {
                    this.$axios.put("/order/finishOrder/" + item.id, { data: "" }).then(res => {
                        console.log(res)
                        this.$message({
                            type: "success",
                            message: "成功送达订单",
                            center: true
                        })
                        this.refreshCurrentStore()
                    })
                }
            })
        }
    },
    created() {
        const router = useRouter()
        this.username = router.currentRoute.value.query.username
        this.$axios("/store/owner?name=" + this.username).then(res => {
            this.stores = res.data
            if (this.stores.length > 0) this.currentStore = this.stores[0]
        })
    }
}
</script>

<style scoped>
.header {
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
}

.storeItem {
    margin-right: 10px;
    padding: 5px;
    display: flex;
    align-items: center;
    border: 2px solid #313743;
    border-radius: 20%;
    text-align: center;
    color: #313743;
    cursor: pointer;
    border: 2px solid #313743;
}

.selectedStoreItem {
    margin-right: 10px;
    padding: 5px;
    display: flex;
    align-items: center;
    background-color: #313743;
    border-radius: 20%;
    text-align: center;
    color: white;
    cursor: pointer;
    border: 2px solid #313743;
}

.detailContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
}

.infoContainer {
    width: 80%;
    display: flex;
    align-items: center;
    margin: 20px;
}

.detailLabel {
    width: 30%;
    font-size: medium;
    color: black;
}

.detailContent {
    width: 70%;
    font-size: medium;
    color: grey;
    text-align: left;
    border-bottom: 2px solid;
}
</style>