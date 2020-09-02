<template>
    <div id="showBlogs">
      <el-row>
          <el-col :span="14">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span style="font-size: 0.5rem;     color: #00B5AD;">博客</span>
                <span style="float: right; padding: 3px 0 ;font-size: 0.4rem; color: #00B5AD;">共<span v-text="total" style="color: #F2711C ;font-size: 0.5rem;">2</span>篇</span>
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
              <div style="margin-left:90px;">
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
          </el-col>
        <el-col :span="7" :offset="1">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
             <div style=" width: 80%; height: 30px; line-height: 30px; margin-left: auto; margin-right: auto">
                  <span style="font-size: 0.3rem;"><i class="el-icon-price-tag"></i></span>
                  <span style="font-size: 0.3rem; margin-left:10px;color: #00B5AD;">分类</span>
             </div>
            </div>

            <div v-for="type in types" :key="type.id">

              <el-card  class="typessss">
                <router-link style="color: #333 " :to="'/index/typesBlogs/'+type.id" target="_blank">
                  {{type.name}}
                </router-link>
              </el-card>

            </div>



          </el-card>
          <br/>
          <br/>
          <br/>

          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <div style=" width: 80%; height: 30px; line-height: 30px; margin-left: auto; margin-right: auto">
                <span style="font-size: 0.3rem;"><i class="el-icon-collection-tag"></i></span>
                <span style="font-size: 0.3rem; margin-left:10px;color: #00B5AD;">标签</span>
              </div>
            </div>
            <div v-for="tag in tags" :key="tag.id">
            <el-card  class="typessss">
              <router-link style="color: #333 " :to="'/index/tagsBlogs/'+tag.id" target="_blank">
                    {{tag.name}}
              </router-link>

            </el-card>
            </div>
          </el-card>

        </el-col>
      </el-row>

    </div>

</template>


<script>
  import ElCard from "../../../node_modules/element-ui/packages/card/src/main.vue";
  import ElCol from "element-ui/packages/col/src/col";
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {
      ElRow,
      ElCol,
      ElCard},
    name:'ShowBlogs',
    data(){
      return{
          blogs:[],
          types:[],
          tags:[],
          page:1 ,
          rows:3,
          total:0
      }
    },
    methods:{
      findPageBlogs(page, rows){
        page = page ? page : this.page;
        rows = rows ? rows : this.rows;
        this.$http.get("http://localhost:8989/blog/findPageBlog?page=" + page +"&rows=" + rows).then(res=>{
          this.blogs = res.data.blogs;
          this.total = res.data.total;
        })
      },
      findSize(size) { //用来处理每页显示记录发生变化的方法
          this.rows = size;
          this.findPageBlogs(this.page,size);
      },
      findPage(page){
          this.page = page
          this.findPageBlogs(page,this.rows);
      },
      findAllType(){
        this.$http.get("http://localhost:8989/type/findAllType").then(res=>{
          this.types = res.data;
          console.log(this.types)
        })
      },
      listTag(){
        this.$http.get("http://localhost:8989/tag/findAllTag").then(res=>{
          this.tags = res.data;

        })
      },
    },
    created(){
          this.findPageBlogs();
          this.findAllType();
          this.listTag();
    }
  }
</script>

<style>
  #showBlogs{
    width: 1200px;
    margin-left: auto;
    margin-right: auto;
  }
  .typessss{
      font-size: 13px;

  }
  .typessss:hover{
      background-color:cornsilk;
  }
</style>

