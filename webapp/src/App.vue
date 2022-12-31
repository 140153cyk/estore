<template>
  <div id="app">
    <el-container style="height: 100%;">
      <el-main id="main">
        <keep-alive>
          <router-view style="height: 100%;"></router-view>
        </keep-alive>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app,body,html {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  margin: 0px;
  padding: 0px;
}
#main{
  display:flex;
  justify-content: center;
  margin: 0px;
  padding: 0px;
}
</style>
