<template>
  <el-container class="container">
    <Header></Header>
    <el-main style="width: 100%; display: flex; justify-content: center; align-items: center; ">
      <div class="login">
        <div>
          <el-input @input="clearTip" placeholder="请输入用户名" v-model="name" clearable class="input_style"></el-input>
        </div>
        <div>
          <el-input @input="clearTip" placeholder="请输入密码" v-model="password" show-password
            class="input_style"></el-input>
        </div>
        <div style="height:30px">
          <div v-if="error" class="errorTip">账号或密码错误</div>
        </div>
        <div>
          <el-button @click="login" class="btn_style" style="margin-right: 50px">登录</el-button>
          <el-button @click="register" class="btn_style" style="margin-left: 50px">注册</el-button>
        </div>
      </div>
    </el-main>
  </el-container>

</template>

<script>
import Header from '@/components/Header.vue'
export default {
  name: "LoginPage",
  data() {
    return {
      name: "",
      password: "",
      error: false
    }
  },
  components: {
    Header
  }
  ,
  methods: {
    login() {
      let that = this
      this.$axios.post("/user/login",
        {
          name: that.name,
          password: that.password
        }
      ).then(res => {
        if (res.data) {
          this.$router.push({
            path: "/MainPage/" + that.name + "/ProductPage",
            query: {
              username: that.name
            }
          });
        }
        else {
          this.error = true
        }
      })
    },
    register() {
      this.$router.push({
        name: "RegisterPage"
      })
    },
    clearTip() {
      this.error = false
    }
  }
}
</script>

<style scoped>
.container {
  height: 50em;
  display: flex;
  flex-direction: column;
}

.login {
  height: 260px !important;
  width: 40%;
  border: 2px solid #313743 !important;
  border-radius: 6px;
}

.input_style {
  width: 400px !important;
  margin-top: 30px !important;
}

.btn_style {
  width: 150px;
  margin-top: 30px;
  background-color: #313743;
  color: white;
}

.errorTip {
  font-size: 6px;
  color: red;
  margin-top: 8px;
}
</style>