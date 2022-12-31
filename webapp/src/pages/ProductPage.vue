<template>
  <el-container class="container">
    <ProductChooser @send="updateProducts"></ProductChooser>
    <el-container>
      <el-main class="ItemContainer">
        <el-row>
          <el-col :span="5" :offset="1" v-for="item in products" :key="item" >
            <el-card  :body-style="{ padding: '2px' }" class="productItem" @click="goToDetail(item)">
          <img :src="('http://localhost:8081/image/'+item.picturePath)" rel="external nofollow" class="image"/>
          <div style="padding: 14px;">
            <span>{{item.name}}</span>
            <div class="bottom">
              <time class="time">{{ currentDate }}</time>
              <span>￥{{item.price}}</span>
            </div>
          </div>
        </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>
  
<script>
  import ProductChooser from '@/components/ProductChooser.vue';
  import { useRouter } from "vue-router";
  export default {
    name:"ProductPage",
    data(){
      return{
        username:"",
        page:1,
        products:[]
      }
    },
    components:{
      ProductChooser
    },
    methods:{
      updateProducts(products){
        this.products=products
      },
      goToDetail(item){
        this.$router.push({
            path:"/MainPage/"+this.username+"/DetailedProduct",
            query:{
              username:this.username,
              product:item.id
            }
        })
      }
    },
    created(){
      const router = useRouter()
      this.username = router.currentRoute.value.query.username
      this.$axios("/product/selectByLabelAndPage?label=All&page=1").then(res=>{
        this.products = res.data
      })
    }
    
  }
</script>
  
<style scoped>
.itemContainer{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;
}
.container{
  height: 200px;
  display: flex;
  flex-direction: column;
}
.productItem{
  margin-bottom: 40px;
}
.image{
  width: 100px;
  height: 100px;
}
</style>