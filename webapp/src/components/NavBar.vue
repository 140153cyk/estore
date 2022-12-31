<template>
      <el-aside class="navbar" :width="isCollapse? '64px':'200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu class="navmenu" active-text-color="#409Eff"
        background-color="#313743"
        text-color="#fff" unique-opened :collapse="isCollapse" :collapse-transition="false">
        <!-- 一级菜单 -->
          <el-sub-menu :index="item.id+''" v-for="item in menulist" :key="item.id">
          <!-- 一级菜单模板区域 -->
            <template #title>
              <el-icon >
                <ShoppingCart v-if="(item.id==1)" />
                <SoldOut v-else-if="(item.id==2)" />
                <User v-else-if="(item.id==3)"/>
              </el-icon>
              <span>{{item.authName}}</span>
            </template>
          <!-- 二级菜单 -->
            <el-menu-item :index="subItem.id+''" v-for="subItem in item.children" 
            :key="subItem.id" @click="goToPage(subItem.pageName)">
              <template #title>
                <el-icon><List/></el-icon>
                <span>{{subItem.authName}}</span>
              </template>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      
</template>

<script>
import { List, ShoppingCart, SoldOut, User } from '@element-plus/icons-vue'

// import { useRouter, useRoute } from 'vue-router'
export default {
    props:{
      username:String
    },
    name: "NavBar",
    data() {
        return {
            // 左侧菜单数据对象
            menulist: [],
            // 是否折叠
            isCollapse: false
        };
    },
    methods: {
        toggleCollapse() {
            this.isCollapse = !this.isCollapse;
        },
        goToPage(pageName){
          this.$router.push({
            path:"/MainPage/"+this.username+"/"+pageName,
            query:{
              username:this.username
            }
          })
        }
    },
    created() {
        this.$axios("/menu/getMenus").then(res => {
            this.menulist = res.data;
        });
    },
    components: { ShoppingCart, SoldOut, User, List }
}
</script>

<style scoped>
  .toggle-button{
    background-color: #4A5064;
    font-size:10px;
    color:#fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor:pointer;
  }
  .navbar{
    background-color: #313743;
  }
  .navmenu{
    border-right: none;
  }

</style>
