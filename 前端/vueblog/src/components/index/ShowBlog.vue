<template>
    <div id="showBlog">
      <el-card class="box-card">
          <span style="font-size: 0.3rem; padding: 5px">作者：李 想</span>
          <span style="font-size: 0.3rem; padding: 5px"><i class="el-icon-date" ></i>{{blogs.update_time}} </span>
          <span style="font-size: 0.3rem; padding: 5px"><i class="el-icon-view" ></i>{{blogs.views}} </span>
      </el-card>
      <el-card class="box-card">
        <img style="width: 100%; height: 100%;" :src="blogs.first_picture"/>
      </el-card>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span style="margin-left: 44%; font-size: 25px; color: rgb(0, 181, 173);">文章内容</span>
        </div>
          <div style="float: right">
            <el-tag type="warning">{{blogs.flag}}</el-tag>
          </div>
            <br/>
            <br/>
            <br/>
            <br/>
          <div v-html="blogs.content">
          </div>
          <br/>
          <br/>
          <div>
            <el-tag v-for=" tags in blogs.tag" :key="tags.id" type="success">{{tags.name}}</el-tag>
          </div>
      </el-card>
    </div>
</template>


<script>
    export default {
      name:'ShowBlog',
      data(){
        return{
            blogs:{}
        }
      },
      methods:{
          selectoneBlog(){
              this.$http.get("http://localhost:8989/show/selectOneBlog?id=" + this.$route.params.id).then(res=>{
                this.blogs =  res.data
                console.log(this.blogs)
              })

          }
      },
      created(){
        this.selectoneBlog();
      }
    }
</script>


<style>
    #showBlog{
      width: 800px;
      margin-left: auto;
      margin-right: auto;
    }
</style>


