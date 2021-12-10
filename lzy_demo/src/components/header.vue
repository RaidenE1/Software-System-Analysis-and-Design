<template>
  <div>
    <el-header v-if="this.status == 1" class="header_1">
      <span class="icon-home">
        <i class="el-icon-s-home" @click="goHome"></i>
      </span>
      <el-input class="h_search" placeholder="请输入你要查找的内容" v-model="search_words.searchWords" @keyup.enter.native="goSearch(false)">
        <el-button v-popover:search type="text" slot="prepend" icon="el-icon-caret-bottom">高级搜索</el-button>
        <el-button icon="el-icon-search" slot="append" @click="goSearch(false)"></el-button>
      </el-input>
      <el-popover
          ref="search"
          placement="bottom"
          width="600"
          :offset="-250"
          title="高级搜索"
          trigger="click">
        <el-form ref="search_words" :model="search_words" label-width="80px">
          <el-form-item label="检索词">
            <el-input v-model="search_words.searchWords" placeholder="多个检索词用空格分开"></el-input>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="search_words.title" placeholder="输入标题检索"></el-input>
          </el-form-item>
          <el-form-item label="关键字">
            <el-input v-model="search_words.keyWords" placeholder="多个关键字用空格分开"></el-input>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="search_words.experts" placeholder="多个作者用空格分开"></el-input>
          </el-form-item>
          <el-form-item label="来源">
            <el-input v-model="search_words.origin"></el-input>
          </el-form-item>
          <el-form-item label="发表时间">
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择起始日期" v-model="search_words.startTime" style="width: 90%"></el-date-picker>
            </el-col>
            <!--          <el-col class="line" :span="1"> - </el-col>-->
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择截至日期" v-model="search_words.endTime" style="width: 90%"></el-date-picker>
            </el-col>
          </el-form-item>

          <el-form-item>
            <el-button @click="goSearch(true)">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-popover>

      <div class="r_con">
        <el-button v-if="isAdmin" class="r_con_admin" type="text" @click="goAdmin">管 理</el-button>
        <span v-if="isLogin">
          <el-badge  :value="msgList.length" :hidden="msgList.length == 0" class="newMsg">
            <el-button v-popover:popover type="info" icon="el-icon-message" class="r_con_mess_1">消息</el-button>
          </el-badge>
          <el-popover
                ref="popover"
                placement="bottom"
                width="290"
                trigger="click"
                content="123456789"
          >
            <el-tabs>
              <el-tab-pane class="MsgCol" label="未读消息">
                <el-row v-for="(o, index) in this.msgList" :key="index" class="MsgRow">
                  <el-card class="MsgCard">
                    <div slot="header" class="clearfix">
                      <span class="MsgTitle">{{o.type}}认领结果</span>
                      <span class="Msg-time">{{ o.date.substring(0,10) }}</span>
                    </div>
                    <div class="MsgContent">
                      <div v-if="o.type == '文献'">
                        <div v-if="o.success">
                          <span>您需要认领的文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理的审核</span>
                        </div>
                        <div v-else>
                          <span>管理员已拒绝您认领文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span>具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>

                      </div>
                      <div v-else>
                        <div v-if="o.success">
                          <span>恭喜您</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理员审核，您已认证成功{{o.type}}</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>学者</span>
                        </div>
                        <div v-else>
                          <span>经管理员审核，您未能认证成功</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>身份，具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>
                      </div>
                    </div>
                    <br>
                    <div class="button_block">
                      <el-tooltip class="item" effect="dark" content="标记已读" placement="bottom-end">
                        <el-button class="read_button" size="medium"
                                   type="success" icon="el-icon-check"
                                   @click="markMsg(o.msgid,true)"
                                   plain circle></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="删除消息" placement="bottom-end">
                        <el-button class="del_button" size="medium"
                                   type="danger" icon="el-icon-delete"
                                   @click="deleteMsg(o.msgid)"
                                   circle></el-button>
                      </el-tooltip>
                    </div>
                  </el-card>
                  <br>
                </el-row>
              </el-tab-pane>
              <el-tab-pane class="MsgCol" label="已读消息">
                <el-row v-for="(o, index) in this.read_msgList" :key="index" class="MsgRow">
                  <el-card class="MsgCard">
                    <div slot="header" class="clearfix">
                      <span class="MsgTitle">{{o.type}}认领结果</span>
                      <span class="Msg-time">{{ o.date.substring(0,10) }}</span>
                    </div>
                    <div class="MsgContent">
                      <div v-if="o.type == '文献'">
                        <div v-if="o.success">
                          <span>您需要认领的文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理的审核</span>
                        </div>
                        <div v-else>
                          <span>管理员已拒绝您认领文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span>具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>

                      </div>
                      <div v-else>
                        <div v-if="o.success">
                          <span>恭喜您</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理员审核，您已认证成功{{o.type}}</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>学者</span>
                        </div>
                        <div v-else>
                          <span>经管理员审核，您未能认证成功</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>身份，具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>
                      </div>
                    </div>
                    <br>
                    <div class="button_block">
                      <el-tooltip class="item" effect="dark" content="设为未读" placement="bottom-end">
                        <el-button class="read_button" size="medium" type="primary"
                                   @click="markMsg(o.msgid,false)"
                                   icon="el-icon-arrow-left" plain circle></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="删除消息" placement="bottom-end">
                        <el-button size="medium" class="del_button" type="danger"
                                   @click="deleteMsg(o.msgid)"
                                   icon="el-icon-delete" circle></el-button>
                      </el-tooltip>
                    </div>
                  </el-card>
                  <br>
                </el-row>
              </el-tab-pane>
            </el-tabs>

          </el-popover>
        </span>

        <el-button type="text" v-if="isLogin" class="r_con_user" @click="goUser()">{{this.userName}}</el-button>
        <el-button type="text" v-if="isLogin" class="r_con_reLogin" @click="reLogin()">退出登录</el-button>
        <el-button type="text" v-if="!isLogin" class="r_con_login" @click="goLogin()">登录</el-button>
        <el-button type="text" v-if="!isLogin" class="r_con_Register" @click="goRegister()">注册</el-button>
      </div>


    </el-header>

    <el-header v-if="this.status == 2" class="header_2">
      <div class="r_con">
        <el-button v-if="isAdmin" class="r_con_admin" type="text" @click="goAdmin">管 理</el-button>
        <span v-if="isLogin">
          <el-badge  :value="msgList.length" :hidden="msgList.length == 0" class="newMsg">
            <el-button v-popover:popover type="text" icon="el-icon-message" class="r_con_mess_2">消息</el-button>
          </el-badge>
          <el-popover
                ref="popover"
                placement="bottom"
                width="290"
                trigger="click"
                content="123456789"
          >
            <el-tabs>
              <el-tab-pane class="MsgCol" label="未读消息">
                <el-row v-for="(o, index) in this.msgList" :key="index" class="MsgRow">
                  <el-card class="MsgCard">
                    <div slot="header" class="clearfix">
                      <span class="MsgTitle">{{o.type}}认领结果</span>
                      <span class="Msg-time">{{ o.date.substring(0,10) }}</span>
                    </div>
                    <div class="MsgContent">
                      <div v-if="o.type == '文献'">
                        <div v-if="o.success">
                          <span>您需要认领的文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理的审核</span>
                        </div>
                        <div v-else>
                          <span>管理员已拒绝您认领文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span>具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>

                      </div>
                      <div v-else>
                        <div v-if="o.success">
                          <span>恭喜您</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理员审核，您已认证成功{{o.type}}</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>学者</span>
                        </div>
                        <div v-else>
                          <span>经管理员审核，您未能认证成功</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>身份，具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>
                      </div>
                    </div>
                    <br>
                    <div class="button_block">
                      <el-tooltip class="item" effect="dark" content="标记已读" placement="bottom-end">
                        <el-button class="read_button" size="medium"
                                   type="success" icon="el-icon-check"
                                   @click="markMsg(o.msgid,true)"
                                   plain circle></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="删除消息" placement="bottom-end">
                        <el-button class="del_button" size="medium"
                                   type="danger" icon="el-icon-delete"
                                   @click="deleteMsg(o.msgid)"
                                   circle></el-button>
                      </el-tooltip>
                    </div>
                  </el-card>
                  <br>
                </el-row>
              </el-tab-pane>
              <el-tab-pane class="MsgCol" label="已读消息">
                <el-row v-for="(o, index) in this.read_msgList" :key="index" class="MsgRow">
                  <el-card class="MsgCard">
                    <div slot="header" class="clearfix">
                      <span class="MsgTitle">{{o.type}}认领结果</span>
                      <span class="Msg-time">{{ o.date.substring(0,10) }}</span>
                    </div>
                    <div class="MsgContent">
                      <div v-if="o.type == '文献'">
                        <div v-if="o.success">
                          <span>您需要认领的文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理的审核</span>
                        </div>
                        <div v-else>
                          <span>管理员已拒绝您认领文献</span>
                          <span class="Msg-text-link" @click="jumpAcademic(o.objectID)">《{{o.objectName}}》</span>
                          <span>具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>

                      </div>
                      <div v-else>
                        <div v-if="o.success">
                          <span>恭喜您</span>
                          <span class="Msg-agree">已通过</span>
                          <span>管理员审核，您已认证成功{{o.type}}</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>学者</span>
                        </div>
                        <div v-else>
                          <span>经管理员审核，您未能认证成功</span>
                          <span class="Msg-text-link">{{o.objectName}}</span>
                          <span>身份，具体原因为：</span>
                          <p class="Msg-reject">{{o.reason}}</p>
                        </div>
                      </div>
                    </div>
                    <br>
                    <div class="button_block">
                      <el-tooltip class="item" effect="dark" content="设为未读" placement="bottom-end">
                        <el-button class="read_button" size="medium" type="primary"
                                   @click="markMsg(o.msgid,false)"
                                   icon="el-icon-arrow-left" plain circle></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="删除消息" placement="bottom-end">
                        <el-button size="medium" class="del_button" type="danger"
                                   @click="deleteMsg(o.msgid)"
                                   icon="el-icon-delete" circle></el-button>
                      </el-tooltip>
                    </div>
                  </el-card>
                  <br>
                </el-row>
              </el-tab-pane>
            </el-tabs>

          </el-popover>
        </span>
        <el-button v-if="isLogin" class="r_con_user" type="text" @click="goUser()">{{this.userName}}</el-button>
        <el-button v-if="isLogin" class="r_con_reLogin" type="text" @click="reLogin()">退出登录</el-button>
        <el-button v-if="!isLogin" class="r_con_login" type="text" @click="goLogin()">登录</el-button>
        <el-button v-if="!isLogin" class="r_con_Register" type="text" @click="goRegister()">注册</el-button>
      </div>

    </el-header>
  </div>
</template>

<script>
export default {
  name: "header",
  data(){
    return {
      search_words: {
        searchWords:'',
        title:'',
        keyWords:'',
        experts:'',
        origin:'',
        startTime: 0,
        endTime: 0,
      },
      isLogin:false,
      isAdmin:false,
      userName:'',
      isRead: false,
      msgList:[],
      msgNum: 0,
      read_msgList:[],
    }
  },
  props: {
    status: {
      // type: String,
      required: false,
      default: "1",
    }
  },
  methods:{
    markMsg(id,status){
      this.$api.message.setMessageStatus({
        token:sessionStorage.getItem("token"),
        messageID:id,
        status
      }).then((res)=>{
        if(res.code == 200){
          this.getAllMessage()
        }else{
          this.$message.error(res.msg);
        }
      })

    },
    deleteMsg(id){
      this.$api.message.deleteMessage({
        token:sessionStorage.getItem("token"),
        messageID:id,
      }).then((res)=>{
        if(res.code == 200){
          this.getAllMessage()
        }else{
          this.$message.error(res.msg);
        }
      })
    },
    jumpAcademic(id){
      this.$router.push('academicShow/'+id);
    },
    jumpExpert(id){
      this.$router.push('scholarPage/'+id);
    },
    goHome(){
      this.$router.push('/');
    },
    goAdmin(){
      let url = window.location.origin + "/#/admin"
      if(window.open(url) === null){
        window.location.herf = url;
      }
    },
    goSearch(isAdvanced){
      if (!isAdvanced) {
        if (this.search_words.searchWords !== '') {
          this.$router.push({
            name:"AcademicSearch",
            params:{
              search_words: encodeURIComponent(JSON.stringify(this.search_words))
            }
          })
        } else {
          alert("搜索内容为空")
        }
      } else {
        if (this.search_words.searchWords === ''
            && this.search_words.title === ''
            && this.search_words.keyWords === ''
            && this.search_words.experts === ''
            && this.search_words.origin === ''
            && this.search_words.startTime === 0
            && this.search_words.endTime === 0) {
          alert("搜索内容为空")
        } else {
          // alert(this.search_words.startTime)
          this.$router.push({
            name:"AcademicSearch",
            params:{
              search_words: encodeURIComponent(JSON.stringify(this.search_words)),
            }
          })
        }
      }
    },
    goUser() {
      this.$router.push({
        name:"PerInfo"
      })
    },
    goLogin() {
      this.$router.push({
        name:"Login"
      })
    },
    goRegister() {
      this.$router.push({
        name:"Register"
      })
    },
    reLogin() {
      sessionStorage.clear()
      location.reload()
    },
    getAllMessage() {
      // 获取消息列表
      let _this = this
      this.$api.message.getMessage({
        userID: sessionStorage.getItem("userID"),
        token: sessionStorage.getItem("token"),
      }).then(res => {
        if (res.code === "200"){
          _this.msgList = res.data.filter((o)=>(!o.is_read))
          _this.read_msgList = res.data.filter((o)=>(o.is_read))
          _this.msgNum = _this.msgList.length;
          console.log(_this.read_msgList)
        } else {
          // _this.$message({
          //   message: res.msg,
          //   type: "error"
          // })
        }
      })
    }
  },
  mounted() {
    if(sessionStorage.getItem("userName")!=null||sessionStorage.getItem("userID")!=null){
      this.isLogin = true
      this.userName = sessionStorage.getItem("userName")
      this.getAllMessage()
    }
    let vue = this;
    if(this.isLogin){
      this.$api.user.isadmin({
        token:sessionStorage.getItem("token")
      }).then((res)=>{
        if(res.code == 200){
          vue.isAdmin = true;
        }
      })
    }
  }
}
</script>

<style scoped>
  .icon-home{
    color: white;
    cursor: pointer;
    font-size: 30px;
    position: absolute;
    left: 10px;
    top:10px;
  }
  .icon-home:hover{
    color: #e0e0e0;
  }
  .header_1 {
    /*position: absolute;*/
    min-width: 800px;
    /*width: 100%;*/
    background-color: #2c3e50;
    margin: -10px -8px 0 -8px;
    padding: 0;
  }

  .h_search {
    width: 400px;
    margin: 10px;
  }
  .h_search >>> .el-input__inner {
    border-radius: 0 0 0 0;
    border-width: 2px 0px 2px 0px;
    border-width: 0;
    border-color: #245cc0;
    font-size: 18px;
    /*height: 60px;*/
  }

  .h_search >>> .el-input-group__append {
    border-radius: 0 15px 15px 0;
    border-width: 0;
    /*border-style: dashed;*/
    /*border-color: grey;*/
    background: white;
    /*height: 60px;*/
    width: 20px;
    font-size: 18px;
    /*color: black;*/
  }

  .h_search >>> .el-input-group__prepend {
    border-radius: 15px 0 0 15px;
    border-width: 0 1px 0 0;
    background: white;
    border-style: solid dashed solid solid;
    /*height: 60px;*/
    width: 60px;
    font-size: 15px;
    color: grey;
  }

  @media screen and (min-width: 1400px){
    .h_search {
      width: 600px;
    }
  }
  .r_con {
    //position: absolute;
    margin-top: 12px;
    width: 300px;
    float: right;
    left: calc(100% - 200px);
  }
  .r_con_mess_1 {
    border-radius: 15px;
    /*margin-right: 10px;*/
    border:1px solid #66b1ff;
    background: #2c3e50;
    color: #66b1ff;
    /*color: #8c939d;*/
  }
  .r_con_mess_1:hover{
    background: #66b1ff;
    color: #2c3e50;
  }
  .r_con_mess_2 {
    border-radius: 15px;
    border:1px solid #66b1ff;
    /*background: #2c3e50;*/
    color: #66b1ff;
    width: 70px;

    /*color: #8c939d;*/
  }
  .r_con_mess_2:hover{
    background: #66b1ff;
    color: white;
  }
  .newMsg {
    width: 77px;
    margin-right: 20px;
    /*height: 10px;*/
  }
  .r_con_admin{
    margin-right: 20px;
    color: white;
    font-weight: 700;
    font-size: 18px;
  }
  .r_con_user {
    margin-right: 15px;
    color: white;
    font-weight: 700;
    font-size: 18px;
    /*margin-left: 50px;*/
  }
  .r_con_reLogin {
    left: 10px;
    color: white;
    font-weight: 700;
    font-size: 18px;
  }
  .r_con_login {
    color: white;
    font-weight: 700;
    font-size: 18px;
    margin-right: 10px;
    margin-left: 35px;
  }
  .r_con_Register {
    color: white;
    font-weight: 700;
    font-size: 18px;
  }

  .MsgTitle {
    /*margin-top: 10px;*/
    /*font-size: 20px;*/
    font-weight: bold;
  }

  .Msg-time{
    float: right;
    color: #9fa19f;
  }

  .MsgCol {
    /*margin-top: 20px;*/
    max-height: 500px;
    width: 300px;
    overflow-x: hidden;
  }

   .MsgCol::-webkit-scrollbar {
      width: 6px;
      height: 6px;
   }
   .MsgCol::-webkit-scrollbar-thumb {
     background-color: #ddd;
     border-radius: 3px;
   }

  .MsgRow{
    /*bottom: 20px;*/
    width: 280px;
  }
  .MsgCard {
    /*height: 250px;*/
  }
  .MsgContent {
    /*height: 100px;*/
    /*margin-bottom: 10px;*/
  }

  .Msg-text-link{
    color: #0066cc;
    cursor: pointer;
  }
  .Msg-text-link:hover{
    text-decoration: underline;
  }

  .Msg-agree{
    color: #42b983;
  }

  .Msg-reject{
    margin-left: 20px;
    color: #ff3f41;
  }

  .button_block {
    /*position: absolute;*/
    text-align: right;
    /*width: 200px;*/
    /*right: 0px;*/
    /*bottom: 50px;*/
  }
  .r_button_block {
    position: absolute;
    width: 200px;
    left: 100px;
    top: -90px;
  }

  .read_button {
    /*right: 100px;*/
  }
  .not_read_button {
    background: #409EFF;
    color: white;
    border-radius: 25px;
  }

  .del_button {
    /*float: right;*/
  }

  .header_2 {
    background-color: #344CB7;
    min-width: 800px;
    margin: -10px -8px 0 -10px;
    padding: 0;
  }
</style>