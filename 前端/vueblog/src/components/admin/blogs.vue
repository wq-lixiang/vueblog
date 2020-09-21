<template>
  <div id="blogs">
    <br>
    <br>
    <br>
    <br>
    <br>
    <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04); height: 40px; line-height: 40px; padding-top: 20px; padding-left: 40px; background-color: white">
      <span style="color: #00B5AD; float: left;width:200px ; height: 40px; line-height: 40px; text-align: center; font-size: 1.5em; ">当前用户是:{{jwtInfo.nickname}}</span>
      <el-button style="float: right" type="info" @click="layout()" icon="el-icon-delete">退出当前登录</el-button>
    </div>
      <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04); padding-top: 20px; padding-left: 40px; background-color: white">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item >
            <el-input v-model="formInline.description" placeholder="标题"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="formInline.id" placeholder="分类">
              <el-option :label="types.name" :key="types.id" :value="types.id" v-for="types in this.type"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" icon="el-icon-search">查询</el-button>
            <el-button type="info" @click="clears()" icon="el-icon-delete">清空</el-button>

          </el-form-item>
        </el-form>
      </div>

    <br/>
    <br/>
      <div class="blogss">
        <el-table
          border
          :data="tableData"
          style="width: 100%">
          <el-table-column
            type="index"
            >
          </el-table-column>
          <el-table-column
            prop="description"
            label="标题"
            width="300">
          </el-table-column>
          <el-table-column
            prop="type.name"
            label="类型"
            width="180">
          </el-table-column>
          <el-table-column
            prop="recommend"
            label="推荐"
            width="80">
            <template slot-scope="scope">
                <span>{{scope.row.recommend == 1 ? '是':'否'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="update_time"
            label="更新时间"
            width="200">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span  style="margin-left: 10px">{{ scope.row.update_time }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-popconfirm
                confirmButtonText='删除'
                cancelButtonText='不删除'
                icon="el-icon-info"
                confirmButtonType="text"
                iconColor="red"
                title="这是删除该博客吗？"
                @onConfirm="handleDelete(scope.$index, scope.row)"
              >
                <el-button
                  size="mini"
                  type="danger"
                  slot="reference"
                >删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

        <div class="a">
          <el-row>
            <el-col :span="8" :offset="4">
              <el-pagination
                background
                prev-text="上一页"
                next-text="下一页"
                style="margin: 15px 0px;"
                layout="prev, pager,next,total ,sizes,jumper"
                :current-page="page"
                :page-size="rows"
                :page-sizes="[3,4,5,6]"
                :total="total"
                @current-change = "findPage"
                @size-change="findSize"
              >

              </el-pagination>
            </el-col>

            <el-col :span="2" :offset="10" >
              <el-button @click="addBlog()" style="margin: 15px 0px; border: 1px solid #00B5AD; color: #00B5AD;">新增</el-button>
            </el-col>
          </el-row>
        </div>
      </div>

    <br/>
  </div>

</template>

<script>
  import ElCard from "../../../node_modules/element-ui/packages/card/src/main.vue";
  import ElCol from "element-ui/packages/col/src/col";
  import Cookies from 'js-cookie'



  export default {
    components: {
      ElCol,
      ElCard},
    name:'Blogs',
      data(){
       return {
         formInline: {
           description: '',
           jwtId:'',
           id: '',
           page : 1,
           rows : 3
         },
         wheres:true,
         tableData: [],
         page:1,
         rows:3,
         total:0,
         type:[],
         jwtInfo:{
           id:''
         }
       }
       },
    mounted() {
      if(Cookies.get('token')){
        this.getUserInfo();
        this.findPageBlogs();
        this.findAllType();
      }else{
        this.$message.error('未登录');
        this.$router.push('/login')
      }

    },
  created(){
    const token = this.$route.query.token;
    // 将jwt写入cookie
    if(token){
      Cookies.set('token', token);
      window.location.href = '#/admin/blogs'
    }
  },
    methods:{
      handleDelete(index,row) {
        this.$http.get("http://39.106.86.151:8989/blog/deleteBlog?id=" + row.id).then(res=>{
              if(res.data.code == 200){
                this.$message.success(res.data.msg);
                this.page= 1;
                this.findPageBlogs();
              }else{
                this.$message.error(res.data.msg);
              }
        })
      },
      handleEdit(index, row){
        this.$router.push("/admin/blog_input/"+row.id);
      },
      findSize(size) { //用来处理每页显示记录发生变化的方法
        if(this.wheres){
          this.rows = size;
          this.findPageBlogs(this.page,size);
        }else{
          this.formInline.rows = size;
          this.onSubmit();
        }

      },
      findPage(page){
        if(this.wheres){
          this.page = page
          this.findPageBlogs(page,this.rows);
        }else{
          this.formInline.page = page;
          this.onSubmit();
        }

      },
      onSubmit(){
         this.formInline.jwtId = this.jwtInfo.id
        if(this.formInline.description ==''&& this.formInline.id == ''){
          this.wheres = true;
            this.findPageBlogs();
        }else{
          this.wheres = false;
          this.$http.post("http://39.106.86.151:8989/blog/findWhereBlog" ,this.formInline).then(res=>{
            this.tableData = res.data.Blogs;
            this.total = res.data.total;
            console.log(this.tableData)
          })
        }

      },
      findPageBlogs(page, rows){

        page = page ? page : this.page;
        rows = rows ? rows : this.rows;
        this.$http.get("http://39.106.86.151:8989/blog/findPageBlog?page=" + page +"&rows="+ rows,{
          headers: { 'token': Cookies.get('token'),
        }
        }).then(res=>{

            this.tableData = res.data.blogs;
            this.total = res.data.total;
            console.log(this.tableData);
        })
      },
      findAllType(){
        this.$http.get("http://39.106.86.151:8989/type/findAllType").then(res=>{
            this.type = res.data;
        })
      },
      clears(){
        this.formInline={page:1,rows:3 ,description:'',id:''};
        this.findPageBlogs();
      },
      addBlog(){
        this.$router.push("/admin/blog_input");
      },
      getUserInfo(){
        this.$http.get("http://39.106.86.151:8989/user/get-login-info" , {
          headers: { 'token': Cookies.get('token'),
            }
        }).then(res=>{
          if(res.data.code == 200){
              this.jwtInfo = res.data.object;
              console.log(this.jwtInfo)
          }else{
            this.$message.error('登录过期了');
            Cookies.remove('token');
            this.$router.push('/login')
          }
        })
      },
      layout(){
        Cookies.remove('token')
        this.$router.push('/login')
      }
    }
  }
</script>

<style>
  body{
    background:url("../../assets/admin/ba.png");
  }
  #blogs{
    width: 970px;
    margin-left: auto;
    margin-right: auto;
  }
  .blogss{
    background-color: white;
    border: 1px solid whitesmoke;

  }
  .a{
    border-left: 1px solid gainsboro;
    border-right: 1px solid gainsboro;
    border-bottom: 1px solid gainsboro;
  }
</style>

