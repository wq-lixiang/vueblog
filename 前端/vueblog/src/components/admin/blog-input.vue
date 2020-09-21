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
          <mavon-editor style="height: 700px;"
                        :ishljs = "true" v-model="blogs.content"></mavon-editor>
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
        <span style="font-size: 20px; margin-left: 50px; color: #00a8c6">添加图片</span>
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :on-error="handleAvatarError"
          :before-upload="beforeAvatarUpload"
          class="avatar-uploader"
          action="http://localhost:8989/oss/upload?module=vueblog">
          <img v-if="blogs.first_picture" :src="blogs.first_picture" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
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
  import Cookies from 'js-cookie'

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
              jwtId:'',
              tags:[],
            },
          flag:[
          {key: 1 , value:'原创'},
          {key: 2 , value:'转载'},
          {key: 3 , value:'翻译'}
        ],
          type:[],
          tag:[],
          jwtInfo:{}
        }
      },
    methods:{
      onSubmit(){
         this.blogs.jwtId  = this.jwtInfo.id
        console.log(this.blogs.jwtId);
        this.$http.post("http://39.106.86.151:8989/blog/insertBlog", this.blogs).then(res=>{
          if(res.data.code == 200){
              this.$message.success(res.data.msg);
              this.$router.push("/admin/blogs")
          }else{
            this.$message.error(res.data.msg);
          }
        })
      },
      clears(){
        this.blogs={flay:'原创' ,recommend:false}
      },
      listType(){
        this.$http.get("http://39.106.86.151:8989/type/findAllType").then(res=>{
              this.type =  res.data;
        })
      },
      listTag(){
        this.$http.get("http://39.106.86.151:8989/tag/findAllTag").then(res=>{
            this.tag = res.data;

        })
      },
      selectOneBlog(){
        this.$http.get("http://39.106.86.151:8989/blog/selectOneBlog?id=" + this.$route.params.id).then(res=>{
          this.blogs = res.data.blog;
          this.blogs.tags=res.data.list;
        })
        },
      // 文件上传成功
      handleAvatarSuccess(res) {
        console.log(res)
        if (res.code == 200) {
          this.blogs.first_picture = res.object
          // 强制重新渲染
          this.$forceUpdate()
          this.$message.success('上传成功');
        } else {
          this.$message.error('上传失败! （非20000）')
        }
      },
    // 文件上传失败（http）
    handleAvatarError() {
      this.$message.error('上传失败! 网络不佳！！！')
    },
      // 文件上传前的校验
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg' // MIME
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
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
      }


    },
    created(){
      if(Cookies.get('token')) {
        this.listType();
        this.listTag();
        this.getUserInfo();
        if (this.$route.params.id != undefined) {
          this.selectOneBlog();
        }
      }else{
        this.$message.error('未登录');
        this.$router.push('/login')
      }
    },
    components: {
      ElRow,
      ElCol,
      ElFormItem,
      ElForm,
      mavonEditor,
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

  <style>
   .avatar-uploader .el-upload {
     border: 1px dashed #d9d9d9;
     border-radius: 6px;
     cursor: pointer;
     position: relative;
     overflow: hidden;
   }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>



