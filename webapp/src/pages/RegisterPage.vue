<template>
    <div class="register">
    <div>
      <el-input @input="clearTip"  placeholder="请输入用户名"  v-model="name" clearable class="input_style">{{name}}</el-input>
    </div>
    <div>
      <el-input @input="clearTip"  placeholder="请输入您的邮箱"  v-model="email" clearable class="input_style">{{name}}</el-input>
    </div>
    <div >
      <el-input @input="clearTip" placeholder="请输入密码" v-model="password" show-password class="input_style">{{password}}</el-input>
    </div>
    <div >
      <el-input @input="clearTip" placeholder="请再次输入密码" v-model="passwordCopy" show-password class="input_style">{{password}}</el-input>
    </div>
    <div style="height:30px">
      <div v-if="error" class="errorTip">{{error}}</div>
    </div>
    <div>
      <el-button  @click="registerBtnClick" class="btn_style" style="margin-right: 50px">注册</el-button>
      <el-button  @click="goToLogin" class="btn_style" style="margin-left: 50px">已有账号</el-button>
    </div>
  </div>
</template>
    
<script>
    export default {
      name: "ShopPage",
      data(){
        return{
            name:"",
            email:"",
            password:"",
            passwordCopy:"",
            error:""
        }
      },
      methods:{
        goToLogin(){
            this.$router.push({
                name:"LoginPage"
            })
        },
        registerBtnClick(){
          if(this.name==""){
            this.error="账号不能为空"
          }
          if(this.email==""){
            this.error="邮箱不能为空"
          }
          if(this.password==""){
            this.error="密码不能为空"
            return
          }
          if(this.password!=this.passwordCopy){
            this.error="两次输入密码不同"
            return;
          }
          this.$axios.post("/user/register",
          {
            name:this.name,
            password:this.password,
            email:this.email
          }).then(res=>{
            switch(res.data){
              case 1000:
                this.$router.push({
                  name:"LoginPage"
                })
                break
              case 1001:
                this.error="已经存在该账号"
                break
              default:
                this.error="发生内部错误，注册失败"
                break
            }
          })
        },
        clearTip(){
          this.error=""
        }
      }
    }
</script>
    
<style scoped>
  .register{
    margin-top: 100px;
    height:400px;
  }
  .input_style{
    width: 400px !important;
    margin-top: 30px !important;
  }
  .btn_style{
    width: 150px;
    margin-top:30px;
    background-color: #313743;
    color: white;
  }
  .errorTip{
    font-size:6px;
    color:red;
    margin-top:8px;
  }
</style>