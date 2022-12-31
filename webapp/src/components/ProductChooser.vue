<template>
    <el-header style="height: 30%;">
        <div class="header">
          <div class="choiceContainer">
            <div @click="changeLabel(item)" :class="item==selectedLabel?'selectedChoice':'choice'" v-for="item in labels" :key="item" >
              {{labelMap[item]}}
            </div>
          </div>
          <div class="searchContainer">
            <el-input v-model="searchSegment" @input="search"></el-input>
            <el-icon><Search/></el-icon>
          </div>
        </div>

    </el-header>
    <el-footer>
      <div class="paginationContainer">
          <el-pagination
            layout="prev, pager, next"
            :total="totalPage"
            :page-size="1"
            :page-sizes="[1,2,3,4]"
            :current-page="page"
            :disabled="false"
            @current-change="turnPage">
          </el-pagination>
        </div>
    </el-footer>
</template>
    
<script>
import { Search } from '@element-plus/icons-vue';
import { ElPagination } from 'element-plus';
  export default {
        
    name: "ProductChooser",
    setup(props,context){
      const update = (products)=>{
        context.emit("send",products)
      }
      return {update}
    },
    data() {
        return {
            searchSegment:"",
            labels: [],
            totalPage:1,
            page:1,
            selectedLabel:"All",
            isSearch:false,
            labelMap: {
                "All": "所有商品",
                "Cloth": "服饰衣物",
                "Food": "食物饮品",
                "Daily": "日常生活"
            }
        };
    },
    methods: {
      changeLabel(label){
        this.isSearch = false
        this.selectedLabel = label;
        this.$axios("/product/selectByLabelAndPage?label="+label+"&page=1").then(res=>{
          this.update(res.data)
          this.page = 1
        })
        this.$axios("/product/totalPage?label="+this.selectedLabel).then(res=>{
          this.totalPage = res.data
        })
      },
      search(){
        this.isSearch = true
        this.$axios("/product/search?segment="+this.searchSegment+"&page=1").then(res=>{
          this.update(res.data)
          this.page = 1
        })
        this.$axios("/product/totalPage?segment="+this.searchSegment).then(res=>{
          this.totalPage = res.data
        })
      },
      turnPage(val){
        this.page = val
        if(this.isSearch){
          this.$axios("/product/search?segment="+this.searchSegment+"&page="+this.page).then(res=>{
            this.update(res.data)
          })
        }
        else{
          this.$axios("/product/selectByLabelAndPage?label="+this.selectedLabel+"&page="+this.page).then(res=>{
            this.update(res.data)
          })
        }
      }
    },
    created() {
        this.$axios("/product/getLabels").then(res => {
            this.labels = res.data;
        });
        this.$axios("/product/totalPage?label=All").then(res=>{
            this.totalPage = res.data
        })
    },
    components: { Search,ElPagination }
}
</script>
    
<style scoped>
.header{
  display:flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
}
.choice{
  padding: 5px;
  display: flex;
  align-items: center;
  border: 2px solid #313743;
  border-radius: 20%;
  text-align: center;
  color: #313743;
  cursor: pointer;
}
.selectedChoice{
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
.searchContainer{
  width: 300px;
  padding: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.choiceContainer{
  display: flex;
  justify-content: space-around;
  width: 400px;
  padding: 10px;
}
.paginationContainer{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}  
</style>