<template>
    <el-container class="productContainer">
        <img :src="('http://localhost:8081/image/' + product.picturePath)" style="height: 250px; width: fit-content;" />
        <div style="width: 300px; height: 100%; display: flex;flex-direction: column; margin-left: 20px;">
            <text class="priceText">￥{{ product.price }}</text>
            <div class="nameBox">
                <text class="label">商品名称:</text>
                <text class="nameText">{{ product.name }}</text>
            </div>
            <div class="labelBox">
                <el-tag v-for="item in product.labels" :key="item" style="margin-right: 10px;">
                    {{ this.labelMap[item] }}
                </el-tag>
            </div>
            <div class="descriptionBox">
                <text class="label">商品简介:</text>
                <text class="descriptionText">{{ product.description }}</text>
            </div>
        </div>
    </el-container>
    <el-container class="operationContainer">
        <div class="operationBox">
            <div style="height: 80%;">
                <text class="descriptionText">现有余量{{ product.quantity }}件</text>
                <div class="quantityChooser">
                    <el-input :min="1" :max="product.quantity" type="number" v-model="quantity"
                        style="width: 80px;"></el-input>
                    <text class="priceText">￥{{ (quantity * product.price).toFixed(2) }}</text>
                </div>
                <el-select v-model="selectedAddress" @change="changeAddress">
                    <el-option v-for="item in addresses" :key="item.id" :label="item.district + item.detail"
                        :value="item"></el-option>
                </el-select>
                <el-input placeholder="备注" v-model="remark" style="width: 150px; margin-left: 20px;"></el-input>
            </div>
            <div>
                <el-button class="btn" style="background-color: orange;" @click="addToCart">加入购物车</el-button>
                <el-button class="btn" style="background-color:chocolate;" @click="payBtnClick">立即购买</el-button>
            </div>
        </div>
        <div v-html="alipay" ref="alipay"></div>
    </el-container>

</template>

<script>
import { useRouter } from "vue-router";
export default {
    name: "DetailedProductPage",
    created() {
        const router = useRouter()
        this.username = router.currentRoute.value.query.username
        let id = router.currentRoute.value.query.product
        this.$axios("/product/" + id).then(res => {
            this.product = res.data
        })
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
            labelMap: {
                "All": "所有商品",
                "Cloth": "服饰衣物",
                "Food": "食物饮品",
                "Daily": "日常生活"
            },
            product: {},
            quantity: 1,
            remark: "",
            alipay: ""
        }
    },
    methods: {
        changeAddress(val) {
            console.log(val)
        },
        addToCart() {
            this.$axios.post("/order/addCart", {
                customerName: this.username,
                storeId: this.product.storeId,
                remark: this.remark,
                items: [
                    {
                        product: this.product,
                        count: this.quantity
                    }
                ]
            }).then(res => {
                console.log(res)
                this.$message({
                    message: "商品在购物车等你喔",
                    type: "success",
                    center: true
                })
            })
        },
        payBtnClick() {
            this.$axios.post("/order/payOrder", {
                customerName: this.username,
                storeId: this.product.storeId,
                remark: this.remark,
                address: this.selectedAddress,
                items: [
                    {
                        product: this.product,
                        count: this.quantity
                    }
                ]
            }).then((res) => {
                if (res.data == 0) {
                    this.$axios.post("/pay/ali", {
                        "subject": this.product.name,
                        "total_amount": this.product.price * this.quantity,
                        "body": this.product.name
                    }).then(res => {
                        this.alipay = res.data
                        this.$nextTick(() => {
                            this.$refs.alipay.children[0].submit()
                        })
                    })
                } else {
                    this.$message({
                        message: "支付失败",
                        type: "error",
                        center: true
                    })
                }

            })
        }
    }
}
</script>
<style scoped>
.productContainer {
    height: 70%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.operationContainer {
    height: 30%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.operationBox {
    height: 100%;
    background-color: #f0f0f0;
    width: 570px;
    border-radius: 10px;
    padding: 10px;
    margin-bottom: 20px;
}

.priceText {
    display: flex;
    height: 20%;
    color: rgb(255, 115, 0);
    font-size: 30px;
    text-align: left;
    align-items: center !important;
}

.label {
    color: black;
    font-size: medium;
    margin: 10px;
    text-align: left;
}

.nameBox {
    height: 20%;
    padding: 10px;
    display: flex;
    justify-content: left;
    align-items: center;
}

.nameText {
    color: grey;
    font-size: large;
}

.labelBox {
    height: 10%;
    padding: 10px;
    display: flex;
    justify-content: left;
}

.descriptionBox {
    height: 40%;
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-items: left;
}

.descriptionText {
    font-size: medium;
    color: grey;
    text-align: left;
    word-wrap: break-word;
}

.quantityChooser {
    height: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.btn {
    width: 150px;
    color: white;
}
</style>