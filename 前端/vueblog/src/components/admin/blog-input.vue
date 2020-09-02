<template>
    <div id="blog-input">
      <br/>
      <br/>
      <br/>

              <el-select v-model="blogs.flag" style="width: 80px;">
                <el-option  :label="flags.value" :value="flags.value" :key="flags.key" v-for="flags in this.flag"></el-option>
              </el-select>
              <el-input v-model="blogs.description" placeholder="标题" style="width: 890px; margin-left: -4px;"></el-input>
        <br/>
        <br/>
          <mavon-editor style="height: 700px;" v-model="blogs.content"></mavon-editor>
        <br/>

      <div>
          <span style="font-size: 20px; color: #00a8c6">类型：</span><el-select v-model="blogs.type_id" style="width: 390px;" clearable placeholder="分类">
        <el-option  :label="types.name" :value="types.id" :key="types.id" v-for="types in this.type"></el-option>
      </el-select>

        <span style="font-size: 20px; margin-left: 50px; color: #00a8c6">标签：</span><el-select v-model="blogs.tags" filterable  multiple style="width: 390px;" placeholder="标签">
        <el-option  :label="tags.name" :value="tags.id" :key="tags.id" v-for="tags in this.tag"></el-option>
      </el-select>
      </div>
      <br/>
      <br/>

      <div>
        <span style="font-size: 20px;  color: #00a8c6">上传图片：</span>
        <span> <input type="file"  ref="myFile"></span>
      </div>
    <br/>
    <br/>
      <div>
        <el-checkbox style="margin-left: 850px;" v-model="blogs.recommend">是否推荐</el-checkbox>
      </div>
        <br/>
        <br/>
        <br/>
          <div style="margin-left: 750px;">
            <el-button type="primary" @click="onSubmit()">提交</el-button>
            <el-button type="primary" @click="clears()">重置</el-button>
          </div>



    </div>
</template>

<script>


  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import ElForm from "../../../node_modules/element-ui/packages/form/src/form.vue";
  import ElFormItem from "../../../node_modules/element-ui/packages/form/src/form-item.vue";
  import ElCol from "element-ui/packages/col/src/col";
  import ElRow from "element-ui/packages/row/src/row";

  export  default {
      name:'Blog_input',
      data(){
        return{
            blogs:{
              id:'',
              flag:'原创',
              content:'',
              description:'',
              type_id:'',
              first_picture:'',
              recommend:false,
              tags:[]
            },
          flag:[
          {key: 1 , value:'原创'},
          {key: 2 , value:'转载'},
          {key: 3 , value:'翻译'}
        ],
          type:[],
          tag:[]
        }
      },
    methods:{
      onSubmit(){
        let myFile = this.$refs.myFile;
        let files = myFile.files;
        let file = files[0];
        let formData = new FormData();
        formData.append("pic",file);
        formData.append("flag",this.blogs.flag);
        formData.append("id",this.blogs.id);
        formData.append("description",this.blogs.description);
        formData.append("content",this.blogs.content);
        formData.append("type_id",this.blogs.type_id);
        formData.append("tags",this.blogs.tags);
        formData.append("recommend",this.blogs.recommend);
        this.$http({
          method:'post',
          url:'http://localhost:8989/blog/insertBlog',
          data:formData,
          headers:{
            'Content-Type':'multipart/form-data'
          }
        }).then(res=>{
          if(res.data.code == 200){
            console.log("进去")
            this.$message.success(res.data.msg);
            this.$router.push("/admin");
            this.clears();
          }else{
            this.$message.error(res.data.msg);
          }
        })

      },
      clears(){
        this.blogs={flay:'原创' ,recommend:false}
      },
      listType(){
        this.$http.get("http://localhost:8989/type/findAllType").then(res=>{
              this.type =  res.data;
        })
      },
      listTag(){
        this.$http.get("http://localhost:8989/tag/findAllTag").then(res=>{
            this.tag = res.data;

        })
      },
      selectOneBlog(){
        this.$http.get("http://localhost:8989/blog/selectOneBlog?id=" + this.$route.params.id).then(res=>{
          this.blogs = res.data.blog;
          this.blogs.tags=res.data.list;
          console.log(this.tag);
          console.log();
        })
      }
    },
    created(){
      this.listType();
      this.listTag();
       if(this.$route.params.id != undefined){
            this.selectOneBlog();
       }

    },
    components: {
      ElRow,
      ElCol,
      ElFormItem,
      ElForm,
      mavonEditor
    }

  }
</script>

<style>
  body{
    background:url("../../assets/admin/ba.png");
  }
  #blog-input{
    width: 970px;
    margin-left: auto;
    margin-right: auto;
    background-color: white;
  }


</style>

