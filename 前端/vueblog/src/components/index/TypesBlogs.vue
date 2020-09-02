<template>
      <div id="typesBlogs">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size: 0.5rem;     color: #00B5AD;">分类</span>
            <span style="float: right; padding: 3px 0 ;font-size: 0.4rem; color: #00B5AD;">共<span style="color: #F2711C ;font-size: 0.5rem;">{{total}}</span>篇</span>
          </div>
          <div v-if="total === 0">
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <p style="color: #00B5AD; font-size: 30px;">没有类似的博客....</p>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
          </div>
          <div v-for="blog in blogs" :key="blog.id">
            <el-card >
              <el-col style="height:200px;">
                <el-row :span="14" >
                  <div style="margin-top: 30px;">
                    <router-link style="color: #333 " :to="'/index/'+blog.id" target="_blank">  <h3>{{blog.description}}</h3></router-link>
                    <br/>
                    <br/>
                    <span style="font-size: 0.3rem; padding: 5px">作者：李 想</span>
                    <span style="font-size: 0.3rem; padding: 5px"><i class="el-icon-date" ></i>{{blog.create_time}} </span>
                    <span style="font-size: 0.3rem; padding: 5px"><i class="el-icon-view" ></i>{{blog.views}} </span>
                    <br/>
                    <br/>
                    <br/>
                    <el-tag>{{blog.type.name}}</el-tag>
                  </div>
                </el-row>
                <el-row :span="10" style=" float: right; margin-top: -130px;">
                  <router-link style="color: #333 " :to="'/index/'+blog.id" target="_blank">
                    <img style="width: 150px; height: 150px;" :src="blog.first_picture"/>
                  </router-link>
                </el-row>
              </el-col>
            </el-card>
          </div>

          <div style="margin-left:200px;">
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
          </div>
        </el-card>
      </div>
</template>

<script>
    export  default {
        name:'TypesBlogs',
        data(){
          return{
              blogs:[],
              page : 1,
              rows:3,
              total:0
          }
        },
      methods:{
        selectAllType(page , rows){
            page = page ? page : this.page;
            rows =rows ? rows : this.rows;
            this.$http.get("http://localhost:8989/blog/selectAllType?page=" + page +"&rows=" + rows + "&id=" + this.$route.params.id).then(res=>{
                  this.blogs =  res.data.blogs;
                  this.total  = res.data.total;
                  console.log(this.blogs);
                  console.log(this.total);
            })
       },
        findSize(size) { //用来处理每页显示记录发生变化的方法
          this.rows = size;
          this.selectAllType(this.page,size);
        },
        findPage(page){
          this.page = page
          this.selectAllType(page,this.rows);
        },

        findPageBlogs(page, rows){
          page = page ? page : this.page;
          rows = rows ? rows : this.rows;
          this.$http.get("http://localhost:8989/blog/findPageBlog?page=" + page +"&rows=" + rows).then(res=>{
            this.blogs = res.data.blogs;
            this.total = res.data.total;
          })
        }
      },
      created(){
        if(this.$route.params.id != undefined){
          this.selectAllType()
        }else{
          this.findPageBlogs();
        }
      }
    }
</script>

<style>
    #typesBlogs{
      width: 970px;
      margin-left: auto;
      margin-right: auto;
    }
</style>

