<template>
    <el-container>
        <el-header class="header">
            <div :class="item==currentStatus?'selectedStatusItem':'statusItem'" v-for="item in Object.keys(statusMap)" :key="item" @click="changeStatus(item)">
                    {{ statusMap[item] }}
            </div>
        </el-header>
        <el-main>
            <el-table
            style="width: 100%;"
            :data="showedOrders"
            :border="true"
            >
                <el-table-column prop="id" label="订单编号" :width="250"/>
                <el-table-column prop="items[0].product.name"  label="货物名称" :width="200"/>
                <el-table-column prop="items[0].count"  label="数量" :width="100"/>
                <el-table-column label="金额" :width="150">
                    <template #default="scope">
                        ￥{{(scope.row.items[0].count * scope.row.items[0].product.price).toFixed(2)  }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button type="primary" size="small" @click="detailBtnClick(scope.row.items[0].product)" >查看商品</el-button>
                        <el-button v-if="currentStatus=='Paid'" type="danger" size="small" @click="cancelBtnClick(scope.row)">取消订单</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
    </el-container>
</template>
<script>
import { useRouter } from "vue-router";
import { ElMessageBox } from 'element-plus';
    export default{
        name:"UserOrderPage",
        created(){
            const router = useRouter()
            this.username = router.currentRoute.value.query.username
            this.getOrders()   
        },
        data(){
            return{
                username:"",
                statusMap:{
                    "Paid":"已支付",
                    "Finished":"已完成",
                    "Cancelled":"被取消"
                },
                orders:[],
                currentStatus:"Paid",
                showedOrders:[]
            }
        },
        methods:{
            getOrders(){
                this.$axios("/order/customer?username="+this.username).then(res=>{
                this.orders = res.data
                this.getShowedOrders()
            })  
            },
            changeStatus(status){
                this.currentStatus =status
                this.getShowedOrders()
            },
            getShowedOrders(){
                this.showedOrders = this.orders.filter(o =>o.status == this.currentStatus)
            },
            detailBtnClick(item){
                this.$router.push({
                    path:"/MainPage/"+this.username+"/DetailedProduct",
                    query:{
                        username:this.username,
                        product:item.id
                    }
                })
            },
            cancelBtnClick(item){
                ElMessageBox.confirm("确定取消订单？","提示",{
                    confirmButtonText:"确定",
                    cancelButtonText:"取消"
                }).then(res=>{
                    if(res == "confirm"){
                        this.$axios.put("/order/cancel?id="+item.id,{data:""}).then(res=>{
                            console.log(res)
                            this.$message({
                                message:"订单取消成功",
                                type:"success",
                                center:true
                            })
                            this.getOrders()
                        })
                    }
                })

            }
        }

    }
</script>

<style scoped>
.header{
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
}
.selectedStatusItem{
    margin-right: 10px;
    padding: 5px;
    display: flex;
    align-items: center;
    background-color:  #313743;
    border-radius: 20%;
    text-align: center;
    color: white;
    cursor: pointer;
    border: 2px solid #313743;
}
.statusItem{
    margin-right: 10px;
    padding: 5px;
    display: flex;
    align-items: center;
    border: 2px solid #313743;
    border-radius: 20%;
    text-align: center;
    color: #313743;
    cursor: pointer;
}
</style>