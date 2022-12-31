<template>
  <el-container class="container">
    <PageHeader></PageHeader>
  <el-container>
    <NavBar :username="username"></NavBar>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
  </el-container>
</template>

<script>
import NavBar from "@/components/NavBar.vue"
import PageHeader from "@/components/Header.vue"
export default {
  name: "MainPage",
  components:{
    NavBar,PageHeader
  },
  data(){
    return{
      username:"",
      isCollapse:false
    }
  },
  mounted(){
    this.username = this.$route.params.username
  }
}
</script>

<style scoped>
.container{
  height: 50em;
  display: flex;
  flex-direction: column;
}
</style>