<template>
    <div class="tags">
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>

        <el-table
                border
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    width="100"
                    type="index"
            >
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称"
                    width="300">
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
                    title="这是删除该标签吗？"
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
                <el-col :span="8" :offset="3">
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

                <el-col :span="2" :offset="9" >
                    <el-button @click="dialogForm = true" style="margin: 15px 0px; border: 1px solid #00B5AD; color: #00B5AD;">新增</el-button>
                </el-col>
            </el-row>
        </div>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>


      <el-dialog title="新增标签" :visible.sync="dialogForm" @close="closes()">
        <el-form :model="form" :rules="rules" ref="userForm" :hide-required-asterisk="false">
          <el-form-item label="标签名称" :label-width="formLabelWidth" prop="name">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormClose()">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible('userForm')">确 定</el-button>
        </div>
      </el-dialog>
    </div>

</template>

<script>
  import Cookies from 'js-cookie'
    export  default {
        name:'Tags',
        data(){
            return{
                tableData: [

                ],
                page:1,
                rows:3,
                total:0,
              rules:{
                name:[
                  { required: true, message: '用户名不能为空', trigger: 'blur' },
                ],
              },
              dialogForm: false,
              form: {
                id:'',
                name:''
            },
              formLabelWidth: '80px'
            };
        },
        methods:{
          handleDelete(index,row) {
            this.$http.get("http://39.106.86.151:8989/tag/deleteTag?id="+ row.id , {
              headers: { 'token': Cookies.get('token'),}
            }).then(res =>{
              if(res.data.code == 200){
                this.$message.success(res.data.msg);
                this.page=1;
                this.findPageTag();
              }else{
                this.$message.error(res.data.msg);
              }
            })
            },
            handleEdit(index, row){
              this.$http.get("http://39.106.86.151:8989/tag/findOneTag?id="+row.id).then(res =>{
                this.form=res.data.object;
                this.dialogForm=true;
              })
            },
          findSize(size) { //用来处理每页显示记录发生变化的方法
            this.rows = size;
            this.findPageTag(this.page,size);
          },
          findPage(page){
            this.page = page;
            this.findPageTag(page,this.rows);
          },
          dialogFormVisible(userform){
            this.$refs['userForm'].validate((valid) => {
              if (valid) {
                this.$http.post("http://39.106.86.151:8989/tag/addTag" , this.form).then(res =>{

                  if(res.data.code == 200){
                    this.$message.success(res.data.msg);
                    this.dialogForm=false;
                    this.form={};
                    this.findPageTag();
                  }else{
                    this.$message.error(res.data.msg);
                    this.dialogForm=true;
                  }
                });
              } else {
                return false;
              }
            });
          },
          findPageTag(page , rows){
            page = page ? page : this.page;
            rows = rows ? rows : this.rows;
            this.$http.get("http://39.106.86.151:8989/tag/findPageTag?page=" + page + '&rows='+ rows).then(res =>{
              this.tableData =  res.data.tags;
              this.total = res.data.total;
            })
          },
          closes(){
            this.form={}
          },
          dialogFormClose(){
            this.dialogForm = false;
            this.form = {};
          }

        },

      created(){
        if(Cookies.get('token')) {
          this.findPageTag();
        }else{
          this.$message.error('未登录');
          this.$router.push('/login')
        }
      }
    }
</script>

<style>
    body{
        background:url("../../assets/admin/ba.png");
    }
    .tags{

        width: 800px;
        margin-left: auto;
        margin-right: auto;

        background-color: white;
        border: 1px solid whitesmoke;
    }
    .a{
        border-left: 1px solid gainsboro;
        border-right: 1px solid gainsboro;
        border-bottom: 1px solid gainsboro;
    }
</style>


