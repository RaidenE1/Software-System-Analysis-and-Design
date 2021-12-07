<template>
  <div>
<!--    <Header status="2"></Header>-->
    <el-container>
      <el-aside style="width: 14%">
        <el-menu default-active="2-1">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>认领门户</template>
            <el-menu-item index="1-1" @click="changeState('1-1')">
              <template slot="title">待处理申请</template>
            </el-menu-item>
            <el-menu-item index="1-2" @click="changeState('1-2')">
              <template slot="title">全部申请</template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-message"></i>认领文献</template>
            <el-menu-item index="2-1" @click="changeState('2-1')">
              <template slot="title">待处理申请</template>
            </el-menu-item>
            <el-menu-item index="2-2" @click="changeState('2-2')">
              <template slot="title">全部申请</template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <el-table
                :data="showApp.rows">
          <el-table-column
                  prop="userName"
                  label="用户名">
<!--            <template slot-scope="scope" >-->
<!--              <p style=" cursor: pointer; " @click="checkUser(scope.row.userID)">-->
<!--                {{scope.row.userName}}-->
<!--              </p>-->
<!--            </template>-->
          </el-table-column>
          <el-table-column
                  prop="objectName"
                  :label="openExperts?'学者名':'文献标题'">
            <template slot-scope="scope" >
              <p class="text-link" @click="checkObject(scope.row)">
                {{scope.row.objectName}}
              </p>
            </template>
          </el-table-column>
          <el-table-column
                  prop="email"
                  label="联系邮箱">
          </el-table-column>
          <el-table-column
                  prop="time"
                  label="时间">
          </el-table-column>
          <el-table-column
                  prop="result"
                  label="状态">
            <template slot-scope="scope">
              {{scope.row.result===0?'未审核':'已审核'}}
            </template>
          </el-table-column>
          <el-table-column
                  prop="msg"
                  label="审批意见"
                  width="300">
          </el-table-column>
          <el-table-column v-if="openTodeal"  width="80">
            <template  slot-scope="scope">
              <el-button v-if="openTodeal" type="primary" plain @click="agreeSubmit(scope.row)">通过</el-button>
            </template>
          </el-table-column>
          <el-table-column v-if="openTodeal" width="80">
            <template slot-scope="scope">
              <el-button type="danger" plain @click="reject(scope.row)">拒绝</el-button>
            </template>
          </el-table-column>
          <el-table-column v-if="!openTodeal" width="100">
            <template  slot-scope="scope">
              <div v-if="scope.row.result==1" class="my-button my-button-primary-plain" >已通过</div>
              <div v-if="scope.row.result==2" class="my-button my-button-danger-plain" >未通过</div>
            </template>
          </el-table-column>
        </el-table>
        <div class="block">
          <el-pagination
                  layout="prev, pager, next"
                  :current-page="showApp.page"
                  @current-change="changePage()"
                  :total="showApp.totalElements">
          </el-pagination>
        </div>
      </el-main>
    </el-container>

    <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%">
      <el-input
              placeholder="请输入拒绝原因"
              v-model="rejectReason"
              clearable>
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="rejectSubmit">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  // import Header from '@/components/header.vue';
  export default {
    name: "admin",
    components: {
      // Header
    },
    data(){
      return {
        allAppExperts: {},
        toDealAppExperts:{},
        allAppAcademic:{},
        toDealAppAcademic:{},
        showApp:{
          totalPages:0,
          page:0,
          totalElements:0,
          rows:[]
        },

        openExperts:false,
        openTodeal:true,
        dialogVisible:false,
        rejectItem:null,
        rejectReason:null,

        current_page:3,
        total_page:10,
        total_elements:1000,
      }
    },
    methods:{
      checkUser(userid){
        console.log(userid)
        //this.$router.push('/')
      },
      checkObject(row){
        if(row.flag === 1){//门户
          this.$router.push('/scholarPage/'+row.objectID)
        }else{
          this.$router.push('/academicShow/'+row.objectID)
        }
      },
      reject(row){
        this.dialogVisible = true;
        this.rejectItem = row;
      },
      rejectSubmit(){
        this.dialogVisible = false
        let vue = this;
        console.log(this.rejectItem+this.rejectReason)
        this.$api.application.reject({
          formID:vue.rejectItem.formID,
          token:sessionStorage.getItem("token"),
          reason:vue.rejectReason
        })
        this.$message({
          message: '已拒绝该申请',
          type: 'warning'
        });
      },
      agreeSubmit(row){
        this.$api.application.agree({
          formID:row.formID,
          token:sessionStorage.getItem("token"),
        })
        this.$message({
          message: '已同意该申请',
          type: 'success'
        });
      },
      changePage(){
        let showApp = this.showApp;
        let page = this.showApp.page;
        if(showApp === this.allAppAcademic){
          this.getOneApp(true,0,page);
        }else if(showApp === this.allAppExperts){
          this.getOneApp(true,1,page);
        }else if(showApp === this.toDealAppAcademic){
          this.getOneApp(false,0,page);
        }else if(showApp === this.toDealAppExperts){
          this.getOneApp(false,1,page);
        }
        //this.getOneApp(isAll,flag,this.showApp.page);
      },
      changeState(id){
        if(id === '1-1'){
          this.showApp = this.toDealAppExperts
          this.openExperts = true;
          this.openTodeal = true;
        }
        if(id === '1-2'){
          this.showApp = this.allAppExperts;
          this.openExperts = true
          this.openTodeal = false;
        }
        if(id === '2-1'){
          this.showApp = this.toDealAppAcademic
          this.openExperts = false
          this.openTodeal = true;
        }
        if(id === '2-2'){
          this.showApp = this.allAppAcademic
          this.openExperts = false
          this.openTodeal = false;
        }
      },
      getAllApp(){
        this.getOneApp(true,1,1);
        this.getOneApp(false,1,1);
        this.getOneApp(true,0,1);
        this.getOneApp(false,0,1);
      },
      getOneApp(isAll,flag,page){
        let vue = this;
        this.$api.application.getAll({
          size : 10,
          page:page,
          isAll,
          flag,
          token:sessionStorage.getItem("token"),
        }).then(res =>{
          if(res.code === "200"){
            console.log(res.data)
            if(isAll && flag === 1)
              vue.allAppExperts = res.data;
            if(isAll && flag === 0) {
              vue.allAppAcademic = res.data;
            }
            if((isAll === false) && flag === 1) {
              vue.toDealAppExperts = res.data;
            }
            if((isAll === false) && flag === 0) {
              vue.toDealAppAcademic = res.data;
              vue.showApp = res.data;
            }
          }else{
            console.log(res.code,res.msg);
          }
        })
      }
    },
    mounted() {
      this.$api.user.isadmin({
        token:sessionStorage.getItem("token")
      }).then((res)=>{
        if(res.code == 200){
          this.getAllApp();
        }else{
          this.$message.error('您没有权限查看该页面');
        }
      }).then((err)=>{
        console.error(err)
      })
    }
  }
</script>

<style scoped>
  .text-link{
    cursor: pointer;
  }
  .text-link:hover{
    color: #409EFF;
  }
  .my-button{
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    background: #FFF;
    border: 1px solid #DCDFE6;
    border-top-color: rgb(220, 223, 230);
    border-right-color: rgb(220, 223, 230);
    border-bottom-color: rgb(220, 223, 230);
    border-left-color: rgb(220, 223, 230);
    color: #606266;
    -webkit-appearance: none;
    text-align: center;
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    transition: .1s;
    font-weight: 500;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;

  }

  .my-button-primary-plain{
    color: #409EFF;
    background: #ecf5ff;
    border-color: #b3d8ff;
  }
  .my-button-danger-plain{
    color: #F56C6C;
    background: #fef0f0;
    border-color: #fbc4c4;
  }
</style>