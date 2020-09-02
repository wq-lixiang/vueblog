<template>
<div class="app">
  <div id="login">
    <span class="footed">博客后台管理系统</span>
    <el-form ref="userForm" :hide-required-asterisk="false" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="用户名：" prop="username">
        <el-input style="width: 300px;" v-model="form.username" placeholder="请输入用户名" prefixIcon="el-icon-user-solid"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input style="width: 300px;" type="password" v-model="form.password" placeholder="请输入密码" prefixIcon="el-icon-key"></el-input>
      </el-form-item>
      <el-form-item style="padding-left:40px; padding-top: 10px ">
        <el-button type="primary" @click="onSubmit('userForm')">登录</el-button>
        <el-button @click="reset()">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
  export default {
    name:'Login',
    data() {
      return {
        form: {
         username:'',
          password:''
        },
        rules:{
          username:[
            { required: true, message: '用户名不能为空', trigger: 'blur' },
          ],
          password:[
            { required: true, message: '密码不能为空', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      onSubmit() {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
            console.log(this.form);
            this.$http.post("http://localhost:8989/user/findUser", this.form).then(res =>{
                  console.log(res.data);
                  if(res.data.code == 200){
                    this.$message.success(res.data.msg);
                    this.$router.push("/admin");
                  }else{
                    this.$message.error(res.data.msg);
                    this.form = {username: this.form.username}
                  }
            })
          } else {
            return false;
          }
        });
      },
      reset(){
        this.form = {};
      }
    }
  }
</script>

<style>
  body{
    background: url("../../assets/admin/ba.png");
  }
    *{
      margin: 0;
      padding: 0;

    }
    .footed{
      position: absolute;
      margin-top:-95px ;
      margin-left:85px;
      font-size: 0.5rem;
      color: #00B5AD!important;
    }
      #login{
        background-color: white;
        padding-top:43px ;
        padding-left:40px ;
        position: absolute;
        top: 30%;
        left: 35%;
        width: 420px;
        height:202px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0);

      }
</style>
