<template>
    <el-container>

        <el-aside :width="showAdd ? '300px' : '60px'" class="addProduct">
            <ProductDetail @refresh="refreshAdd" :store="currentStore.id" :new-product="true" v-if="showAdd">
            </ProductDetail>
            <div class="addBar" @click="toggleAdd">{{ showAdd ? '收起' : '添加商品' }}</div>
        </el-aside>
        <el-main>
            <el-header class="header">
                <div :class="item.id == currentStore.id ? 'selectedStoreItem' : 'storeItem'" v-for="item in stores"
                    :key="item" @click="changeStore(item)">
                    {{ item.name }}
                </div>
                <div class="storeItem" style="margin-left: 20px;" v-if="addStoreBtnVisible"
                    @click="this.addStoreBtnVisible = false">新建店铺</div>
                <el-input style="width: 100px; margin-left: 20px;" v-else v-model="addStoreName"
                    v-on:keyup.enter="addStore" @blur="this.addStoreBtnVisible = true"></el-input>
            </el-header>
            <el-table class="productTable" :data="currentStore.products" :border="true">
                <el-table-column prop="name" label="商品名称" />
                <el-table-column prop="price" label="单价" />
                <el-table-column prop="quantity" label="数量" />
                <el-table-column label="操作" width="200" key="slot">
                    <template #default="scope">
                        <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button> -->
                        <el-button type="primary" size="small" @click="editBtnClick(scope.row)">编辑</el-button>
                        <el-button type="danger" size="small" @click="deleteBtnClick(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog v-model="showEdit">
                <ProductDetail :store="currentStore.id" :new-product="false" :target="detailedProduct"
                    @refresh="refreshEdit"></ProductDetail>
            </el-dialog>
        </el-main>
    </el-container>
</template>

<script>
import ProductDetail from '../components/ProductDetail.vue';
import { useRouter } from "vue-router";
import { ElMessageBox } from 'element-plus';
export default {
    name: "MyStorePage",
    components: {
        ProductDetail
    },
    data() {
        return {
            addStoreBtnVisible: true,
            addStoreName: "",
            username: "",
            showAdd: false,
            showEdit: false,
            stores: [],
            currentStore: {},
            detailedProduct: {}
        }
    },
    methods: {
        toggleAdd() {
            this.showAdd = !this.showAdd
        },
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
        refreshAdd() {
            this.showAdd = false
            this.refreshCurrentStore()
        },
        refreshEdit() {
            this.showEdit = false
            this.refreshCurrentStore()
        },
        deleteBtnClick(item) {
            ElMessageBox.confirm("确定删除？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(res => {
                if (res == "confirm") {
                    this.$axios.delete("/image/deleteImg?name=" + item.picturePath, { data: "" })
                    this.$axios.delete("/product/deleteProduct/" + item.id, { data: "" }).then(res => {
                        if (res.data == 0) {
                            this.$message({
                                message: "商品删除成功",
                                type: "success",
                                center: true
                            })
                            this.refreshCurrentStore()
                        }

                    })
                }
            })
        },
        editBtnClick(item) {
            this.detailedProduct = item
            this.showEdit = true
        },
        addStore() {
            if (this.addStoreName == "") {
                this.addStoreBtnVisible = true
            }
            this.$axios.post("/store/addStore?owner=" + this.username + "&name=" + this.addStoreName, { data: "" }).then(res => {
                if (res.data == 0) {
                    this.$message({
                        message: "新建店铺成功",
                        type: "success",
                        center: true
                    })
                    this.$axios("/store/owner?name=" + this.username).then(res => {
                        this.stores = res.data
                        if (this.stores.length > 0) this.currentStore = this.stores[0]
                    })
                    this.addStoreName = ""
                    this.addStoreBtnVisible = true
                } else {
                    this.$message({
                        message: "新建店铺失败",
                        type: "error",
                        center: true
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

.addProduct {
    display: flex;
    flex-direction: row;
}

.addBar {
    height: 100px;
    background-color: #313743;
    color: white;
    padding: 1px;
    border-radius: 0 5px 5px 0;
    cursor: pointer;
    writing-mode: horizontal-tb;
    writing-mode: tb-rl;
}

.productTable {
    width: 100%;
}
</style>