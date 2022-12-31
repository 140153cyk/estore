<template>
    <el-container class="container">
        <div>
            <el-table style="width: 100%;" :data="orders" :border="true" @selection-change="changeSelected">
                <el-table-column type="selection"></el-table-column>
                <el-table-column prop="items[0].product.name" label="货物名称" :width="150" />
                <el-table-column prop="items[0].count" label="数量" :width="100" />
                <el-table-column label="金额">
                    <template #default="scope">
                        ￥{{ (scope.row.items[0].count * scope.row.items[0].product.price).toFixed(2) }}
                    </template>
                </el-table-column>
                <el-table-column label="状态" :width="150">
                    <template #default="scope">
                        <el-tag type="danger"
                            v-if="scope.row.items[0].product == undefined || scope.row.items[0].product.quantity == 0">不可购买</el-tag>
                        <el-tag type="warning"
                            v-else-if="scope.row.items[0].count > scope.row.items[0].product.quantity">存货不足</el-tag>
                        <el-tag v-else>有效</el-tag>
                    </template>
                </el-table-column>
                <el-table-column :width="200">
                    <template #default="scope">
                        <el-button type="primary" size="small"
                            @click="detailBtnClick(scope.row.items[0].product)">查看商品</el-button>
                        <el-button type="danger" size="small" @click="removeBtnClick(scope.row)">移出购物车</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="operationContainer">

            <el-select v-model="selectedAddress" @change="changeAddress" style="width: 60%;">
                <el-option v-for="item in addresses" :key="item.id" :label="item.district + item.detail"
                    :value="item"></el-option>
            </el-select>
            <div
                style="display: flex;flex-direction: row;justify-content: space-around;align-items: center;width: 100%; margin-top: 20px;">
                <text class="priceText">￥{{ totalAmount.toFixed(2) }}</text>
                <el-button class="btn" style="background-color:chocolate;" @click="payBtnClick">立即购买</el-button>
            </div>

        </div>
    </el-container>
</template>
<script>
import { useRouter } from "vue-router";
import { ElMessageBox } from 'element-plus';
export default {
    name: "ShoppingCartPage",
    created() {
        const router = useRouter()
        this.username = router.currentRoute.value.query.username
        this.refreshOrders()
        this.$axios("/address/getByUser?username=" + this.username).then(res => {
            this.addresses = res.data
            this.selectedAddress = res.data[0]
        })
    },
    data() {
        return {
            addresses: [],
            selectedAddress: {},
            username: "",
            orders: [],
            selectedOrders: [],
            totalAmount: 0
        }
    },
    methods: {
        refreshOrders() {
            this.$axios("/order/cart?username=" + this.username).then(res => {
                this.orders = res.data
            })
        },
        detailBtnClick(item) {
            this.$router.push({
                path: "/MainPage/" + this.username + "/DetailedProduct",
                query: {
                    username: this.username,
                    product: item.id
                }
            })
        },
        removeBtnClick(item) {
            ElMessageBox.confirm("确定删除？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(res => {
                if (res == "confirm") {
                    this.$axios.delete("/order/" + item.id, { data: "" }).then(res => {
                        console.log(res)
                        this.$message({
                            message: "成功移出购物车",
                            center: true,
                            type: "success"
                        })
                        this.refreshOrders()
                    })
                }
            })
        },
        changeSelected(val) {
            this.selectedOrders = val
            let tempSum = 0
            this.selectedOrders.forEach(o => {
                tempSum += o.items[0].count * o.items[0].product.price
            })
            this.totalAmount = tempSum
        },
        payBtnClick() {
            this.selectedOrders.forEach(o => {
                o.address = this.selectedAddress
                this.$axios.put("/order/payForCart", o).then(res => {
                    if (res.data == 0) {
                        this.$message({
                            message: "支付成功！",
                            type: "success",
                            center: true
                        })
                        this.refreshOrders()
                    } else {
                        this.$message({
                            message: "支付失败",
                            type: "error",
                            center: true
                        })
                    }
                })
            })
        }

    }
}
</script>

<style scoped>
.container {
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.operationContainer {
    align-items: center;
    width: 75%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    margin: 10px;
    border-radius: 2px;
    position: fixed;
    bottom: 0;
    height: 100px;
    background-color: #f0f0f0;
}

.priceText {
    display: flex;
    height: 20%;
    color: rgb(255, 115, 0);
    font-size: 30px;
    text-align: left;
    align-items: center !important;
}

.btn {
    height: 50px;
    font-size: large;
    width: 150px;
    color: white;
}
</style>