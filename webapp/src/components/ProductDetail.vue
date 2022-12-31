<template>
    <el-container class="container">
        <el-input class="input" placeholder="商品名称" v-model="details.name" clearable/>
        <el-input class="input" placeholder="商品单价" v-model="details.price" clearable  oninput="value=value.replace(/[^0-9.]/g,'')"/>
        <el-input class="input" placeholder="商品数量" v-model="details.quantity" type="number"/>


            <el-checkbox-group v-model="details.labels" class="checkboxgroup">
                <el-checkbox v-for="item in checkBoxItems" :key="item" :label="item">{{labelMap[item]}}</el-checkbox>
            </el-checkbox-group>

        <el-input class="input" placeholder="商品描述" type="textarea" v-model="details.description" clearable/>
        <el-upload
        :class="[containImage? 'hiddenBtn' : '']" 
        action="#"
        list-type="picture-card"
        limit=1
        accept="image/jpeg,image/gif,image/png,image/jpg"
        :on-change="imageChange"
        :auto-upload="false"
        :on-remove="removeImage"
        :on-preview="previewImage"
        style="width: 100%;"
        v-if="newProduct">
            <Plus></Plus>
        </el-upload>
        <el-dialog v-model="dialogVisible">
            <img :src="dialogImageUrl" alt="" style="width:100%;" />
        </el-dialog>
        <el-button v-if="newProduct" class="submitBtn" @click="upload">添加商品</el-button>
        <el-button v-else class="submitBtn" @click="update">修改商品</el-button>
    </el-container>

</template>

<script>
import { Plus } from '@element-plus/icons-vue';

    export default{
    name: "ProductDetail",
    components: { Plus },
    props:{
        store:String,
        newProduct:Boolean,
        target:Object
    },
    setup(props,context){
        const refresh = ()=>{
            context.emit("refresh","")
        }
        return {refresh}
    },
    watch:{
        target:{
            handler(){
                if(!this.newProduct)this.details = this.target
            },
            deep:true,
            immediate: true
        }
    },
    data(){
        return{
            dialogVisible:false,
            dialogImageUrl:"",
            containImage:false,
            uploadForm:new FormData(),
            checkBoxItems:[],
            labelMap: {
                "All": "所有商品",
                "Cloth": "服饰衣物",
                "Food": "食物饮品",
                "Daily": "日常生活"
            },
            details:{
                name:"",
                price:"",
                quantity:"",
                labels:[],
                description:"",
                picturePath:""
            }

        }
    },
    created(){
        this.$axios("/product/getLabels").then(res => {
            this.checkBoxItems = res.data;
            this.checkBoxItems.splice(this.checkBoxItems.indexOf("All"),1)
        });
    },
    methods:{
        imageChange(file){
            this.uploadForm.delete("image")
            this.uploadForm.append('image', file.raw);
            this.containImage = true;
        },
        removeImage(){
            this.containImage = false;
        },
        previewImage(file){
            this.dialogVisible = true;
            this.dialogImageUrl = file.url
        },
        uploadImage(){
            return this.$axios.post("/image/uploadImg",this.uploadForm)
        },
        upload(){
            if(this.containImage){
                this.uploadImage().then(res=>{
                    if(res.data=="上传失败"){
                        this.$message({
                            message:"图片上传失败",
                            type:"error",
                            center:true
                        })
                        return
                    }
                    this.$message({
                        message:"图片上传成功",
                        type:"success",
                        center:true
                    })
                    this.details.picturePath = res.data
                    this.addProduct()
                })
            }else{
                this.details.picturePath ="default.png"
                this.addProduct()
            }
        },
        addProduct(){
            this.$axios.post("/product/addProduct?store="+this.store,this.details).then(res=>{
                if(res.data == 0){
                    this.$message({
                        message:"商品上传成功",
                        type:"success",
                        center:true
                    })
                    this.refresh()
                }else{
                    this.$message({
                        message:"商品上传失败",
                        type:"error",
                        center:true
                    })
                }

            })
        },
        update(){
            this.$axios.put("/product/updateProduct",this.details).then(res=>{
                if(res.data == 0){
                    this.$message({
                        message:"修改成功",
                        type:"success",
                        center:true
                    })
                    this.refresh()
                }else{
                    this.$message({
                        message:"修改失败",
                        type:"error",
                        center:true
                    })
                }
            })
        }
    }
}
</script>

<style scoped>
.submitBtn{
    background-color: #313743;
    color: white;
}
.container{
    width: 80%;
    display: flex;
    flex-direction: column;
    padding: 10px;
}
.input{
    margin-bottom: 10px;
}
.checkboxgroup{
    display: flex;
    flex-direction: column;
}
</style>

<style>
.hiddenBtn .el-upload{
    display: none ;
}
</style>