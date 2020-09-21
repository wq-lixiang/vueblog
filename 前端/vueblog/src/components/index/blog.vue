<template>

  <el-container>
    <el-header style="padding: 0" height="70px">
      <el-row>
        <el-col :span="24">
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#1B1C1D"
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <span style="color: #00B5AD; float: left;width:200px ; height: 70px; line-height: 70px; text-align: center; font-size: 2em; ">VueBlog</span>
            <el-menu-item index="/index/showBlogs" style="height: 70px; line-height: 70px; width: 100px; text-align: center; font-size: 16px;"><i class="el-icon-s-home"></i>首页</el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
    <el-footer style="background-color:#1B1C1D; color: white; height: 262px; font-size: 13px;" >
      <div class="all">
        <el-row style="text-align: center">
          <el-col :span="4">
            <img src="../../assets/admin/grvs.jpg" style="width: 100px;">
          </el-col>

          <el-col  :span="5">
            <h4>推荐博客</h4>
            <ul>
              <li v-for="blogs in blog" :key="blogs.id"> <router-link  :to="'/index/'+blogs.id" target="_blank">{{blogs.description}}</router-link></li>
            </ul>
          </el-col>

          <el-col  :span="5" >
            <h4>联系我</h4>
            <ul>
              <li><a href="#">Email：wq_lixiang@163.com</a></li>
              <li><a href="#">QQ:2875467580</a></li>
              <li><a href="#">微信:lx2875467580</a></li>
            </ul>
          </el-col>
          <el-col :offset="1" :span="9">
            <h4>Blog</h4>
            <p style="margin-top:12px; ">这是我的个人博客、会分享关于自己生活的简介和自己学习的知识，希望可以给来到这儿的人有所帮助</p>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <p>Copyright © 2020 - 2021 LiXiang Designed by LiXiang</p>
      </div>
    </el-footer>
  </el-container>

</template>

<script>
  import ElRow from "element-ui/packages/row/src/row";
  import ElCol from "element-ui/packages/col/src/col";

  export default {
    components: {
      ElCol,
      ElRow},
    name:'Blog',
    data() {
      return {
        activeIndex: this.$route.path,
        blog:[]
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        this.$router.push(key);
      },
      oneBlog(){
        this.$http.get("http://39.106.86.151:8989/show/selectOne").then(res=>{
          this.blog =  res.data;
          console.log(this.blog)
        })
      }
    },
    created(){
      this.oneBlog();
    }
  }
</script>

<style>
  body{
    background:url("../../assets/admin/ba.png");
  }
  *{
    margin: 0;
    padding:0;
  }
  .all {
    width: 950px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 5em;
  }

  a{
    text-decoration: none;
    color: white;

  }
  li{
    padding-top: 5px;
    list-style-type: none;
  }
  li :hover{
    color: #00a8c6;
  }
  p{
    text-align: center;
  }
  h4{
    margin-top:-6px ;
  }
</style>
