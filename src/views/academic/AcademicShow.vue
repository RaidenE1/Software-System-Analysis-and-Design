<template>
  <div>
    <div class="background">
    </div>
    <br>
    <div style="width: 1150px; margin: 0 auto;">
      <el-col :span="17">
        <el-row>
          <div @click="jumptoLink(academic.link)"
               style="font-size: 35px; color: #0066cc; float: left;cursor: pointer; ">
            {{academic.title}}
          </div>
        </el-row>
        <table>
          <tr>
            <td style="width: 70px;">
            <span class="tableleft">
              作者：
            </span>
            </td>
            <td style="float:left;cursor: pointer; ">
              <span v-for="author in academic.authors" :key="author"
                    @click="findByExpert(author.name)"
                    class="expert">
                {{author.name}}
              </span>
            </td>
          </tr>
          <tr valign="top">
            <td>
            <span class="tableleft" >
              摘要：
            </span>
            </td>
            <td style="float:left; text-align: left">
            <span style="white-space: normal">
              {{academic.summary}}
            </span>
            </td>
          </tr>
          <tr >
            <td>
              <span class="tableleft" >关键词：</span>
            </td>
            <td style="float:left;cursor: pointer; ">
            <span v-for="word in academic.keywordList" :key="word"
                  @click="findByKw(word)"
                  class="keyword">
              {{word}}
            </span>
            </td>
          </tr>
          <tr>
            <td>
              <span class="tableleft" >时间：</span>
            </td>
            <td style="float:left;">
              {{academic.time.substring(0,10)}}
            </td>
          </tr>

          <tr>
            <td>
              <span class="tableleft" >引用量：</span>
            </td>
            <td style="float:left;">
              {{academic.cited_quantity}}
            </td>
          </tr>
          <tr>
            <td>
              <span class="tableleft" >来源：</span>
            </td>
            <td style="float:left;">
              {{academic.origin}}
            </td>
          </tr>
          <tr>
            <td>
              <span class="tableleft" >浏览量：</span>
            </td>
            <td style="float:left;">
              {{academic.views}}
            </td>
          </tr>
        </table>
        <el-row style="margin-top: 20px">
          <el-col :offset="1" :span="4">
            <el-button size="medium" type="primary" @click="jumptoLink(academic.link)">查看全文</el-button>
          </el-col>
          <el-col :span="4">
            <el-button v-if="academic.is_favor" size="medium" type="warning" plain @click="cancelFavorite" >
              <i class="el-icon-star-on"></i>
              取消收藏</el-button>
            <el-button v-else size="medium" type="primary" plain @click="favorite" >
              <i class="el-icon-star-off"></i>
              收藏
              </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="info" size="medium" plain @click="sharedialogVisible = true">
              <i class="el-icon-link"></i>
              分享
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="info" plain size="medium" @click="showQuote">
              <i class="el-icon-position"></i>
              引用
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="success" size="medium"  @click="dialogVisible = true">
              <i class="el-icon-trophy"></i>
              认领
            </el-button>
          </el-col>
        </el-row>

        <hr color="#9c9e9c">
        <div >
          <p style="text-align: left;font-size: 25px">相关文献</p>
          <div style="padding-left: 80px;" v-for="(result_item,index) in relation_list" v-bind:key="index">
            <academic-item :c_sc = result_item></academic-item>
          </div>
          <div v-if="relation_list === null">
            <p>暂无信息</p>
          </div>
        </div>


      </el-col>
      <el-col :offset="2" :span="5" align="left">
        <span style="font-size: 20px;">
          本篇关键词
        </span>
        <br>
        <br>
        <el-row>
          <div v-for="word in academic.keywordList" :key="word">
            <a class="search-word"
               @click="findByKw(word)">
              <i class="el-icon-search"></i>
              {{word}}
            </a>
          </div>
        </el-row>
        <span style="font-size: 20px;">
          热门关键词
        </span>
        <br>
        <br>
        <el-row>
          <div v-for="word in hot_keywords" :key="word">
            <a class="search-word"
               @click="findByKw(word)">
              <i class="el-icon-search"></i>
              {{word}}
            </a>
          </div>
        </el-row>
<!--        <el-row >-->
<!--          <div style="display:flex; width: 300px;flex-wrap: wrap;">-->
<!--            <div v-for="word in academic.keywordList" :key="word"-->
<!--                 @click="findByKw(word)"-->
<!--                 style="cursor: pointer;">-->
<!--              &lt;!&ndash;            <div class="hotword" :style="'width:'+(30+word.length*20)+'px'">&ndash;&gt;-->
<!--              &lt;!&ndash;              <div style="margin: 0 auto; line-height:40px;">&ndash;&gt;-->
<!--              &lt;!&ndash;                {{word}}&ndash;&gt;-->
<!--              &lt;!&ndash;              </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--              <div :style="'width:'+(word.length*22+50)+'px;'">-->
<!--                <el-card style="width: 90%;text-align:center;">-->
<!--                  &lt;!&ndash;              <div style="margin: 0 auto; ">&ndash;&gt;-->
<!--                  {{word}}-->
<!--                  &lt;!&ndash;              </div>&ndash;&gt;-->
<!--                </el-card>-->
<!--                <br>-->
<!--              </div>-->

<!--            </div>-->
<!--          </div>-->
<!--        </el-row>-->
        <br>

      </el-col>
      <el-dialog
              title="分享"
              :visible.sync="sharedialogVisible"
              width="30%">
        <el-row style="text-align: left">
          快把优质文献分享出去吧
          <el-button icon="el-icon-document-copy"
                     style="float: right;"
                     type='info'
                     v-clipboard:copy="url"
                     v-clipboard:success="copySuccess"
                     v-clipboard:error="copyError"
          ></el-button>
        </el-row>

        <el-input type="textarea"
                  autosize
                  placeholder="url"
                  v-model="url"
                  :readonly="true">
        </el-input>


        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="sharedialogVisible=false">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
              title="引用"
              :visible.sync="quotedialogVisible"
              width="30%">
        <el-row style="text-align: left">
          引用格式为GB/T7714
          <el-button icon="el-icon-document-copy"
                     style="float: right"
                     type='info'
                     v-clipboard:copy="getQuote(academic)"
                     v-clipboard:success="copySuccess"
                     v-clipboard:error="copyError"
          ></el-button>
        </el-row>

        <el-input
                type="textarea"
                placeholder="url"
                autosize
                v-model="quoteText"
                :readonly="true">
        </el-input>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="quotedialogVisible=false">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
              title="认领文献"
              :visible.sync="dialogVisible"
              width="30%">
        <el-row style="text-align: left">
          请输入您的常用邮箱，管理员会在和您联系后审核您的申请。
        </el-row>

        <el-input
                placeholder="请输入您的邮箱"
                v-model="email"
                clearable>
        </el-input>
        <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="claimSubmit">确 定</el-button>
      </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import AcademicItem from "@/components/AcademicItem";
  export default {
    name: "AcademicShow",
    components: {
      AcademicItem
    },
    data() {
      return {
        img:require('../../../src/assets/adver.jpg'),
        academic: {
          title: "this is the title",
          summary: "摘要 this is the summary this is the summary this is the summary this is the summary this is the summary this is the summary this is the summary this is the summary this is the summary this is the summary this is the summary",
          authors: [ "学者1","学者2","学者3"],
          keywordList:["fds","kdfIFD","段通风户籍"],
          link: "this is the link",
          cited_quantity: 100,
          time: 1990,
          origin: "中国知网",
          views:123,
          is_favor:true,
        },
        relation_list:null,
        dialogVisible:false,
        sharedialogVisible:false,
        quotedialogVisible:false,
        academicID:123,
        url:window.location,
        quoteText:"",
        email:"",
        search_words: {
          searchWords: '',  // 普通搜索词
          title: '', // 标题
          keyWords: '', // 关键词
          experts: '', // 学者名
          origin: '', // 机构
          startTime: '0',
          endTime: '0'
        },
        hot_keywords:[
          '神经网络',
          '数据库',
          'python',
          '5G',
          '大数据',
          '人工智能',
          '航天',
          '新冠',
          '碳中和',
        ],
      }
    },
    methods:{
      jumpToHome(){
        this.$router.push('/');
      },
      showQuote(){
        this.quoteText = this.getQuote(this.academic)
        this.quotedialogVisible = true;
      },
      getQuote(document){
        let res = ""
        for(let i in document.authors){
          if(i != 0){
            res += ",";
          }
          res += document.authors[i].name;
        }
        res += '.';
        res += document.title+'['
        let dtype = document.dtype;
        if(dtype == '专利'){
          res += 'P'
        }else if(dtype == '会议'){
          res += 'C'
        }else if(dtype == '图书'){
          res += 'M'
        }else if(dtype == '学位'){
          res += 'D'
        }else if(dtype == '期刊'){
          res += 'J'
        }
        res += '].'
        res += document.origin;
        if(document.time.length >= 4){
          res += ','+document.time.substring(0,4);
        }
        return res;
      },
      findByExpert(expertName){
        this.search_words.experts = expertName;
        this.$router.push({
          name:"AcademicSearch",
          params:{
            search_words: encodeURIComponent(JSON.stringify(this.search_words)),
          }
        })
      },
      findByKw(keyword){
        this.search_words.searchWords = keyword;
        this.$router.push({
          name:"AcademicSearch",
          params:{
            search_words: encodeURIComponent(JSON.stringify(this.search_words)),
          }
        })
      },
      favorite(){
        let vue = this;
        this.$api.academic.favorSc({
          document_id:vue.academicID,
          token: sessionStorage.getItem("token"),
          user_id: sessionStorage.getItem("userID"),
        }).then((res)=>{
          if(res.code == 200){
            this.$message({
              message: '收藏成功',
              type: 'success'
            });
            vue.academic.is_favor = true;
          }else{
            this.$message.error(res.msg)
          }

        }).catch(err=>{
          this.$message.error("请先登录")
          console.log(err)
        })
      },
      cancelFavorite(){
        let vue = this;
        this.$api.academic.favorSc({
          document_id:vue.academicID,
          token: sessionStorage.getItem("token"),
          user_id: sessionStorage.getItem("userID"),
        }).then((res)=>{
          if(res.code == 200){
            this.$message({
              message: '取消收藏成功',
              type: 'success'
            });
            vue.academic.is_favor = false;
          }else{
            this.$message.error(res.msg)
          }
        }).catch(err=>{
          this.$message.error("请先登录")
          console.log(err)
        })
      },
      copySuccess(){
        this.$message({
          message: '复制成功',
          type: 'success'
        });
        this.sharedialogVisible = false;
      },
      copyError(){
        this.$message.error('您的浏览器不支持该功能，请自行复制链接内容');
      },
      claimSubmit(){
        let vue = this;
        console.log("objectid is "+vue.academicID);
        this.$api.application.create({
          token: sessionStorage.getItem("token"),
          userID: sessionStorage.getItem("userID"),
          objectID: vue.academicID,
          flag: 0,
          email: vue.email
        }).then(res=>{
          if(res.code !== "200"){
            this.$message.error(res.msg);
          }else{
            this.$message({
              message: '认领申请成功',
              type: 'success'
            });
          }
        }).catch(err=>{
          console.log(err)
        })
      },
      jumptoLink(url){
        console.log(url)
        if(window.open(url) === null){
          window.location.herf = url;
        }
      },
      getRelation(){
        let vue = this;
        this.$api.academic.getSearchResult({
          search_words:{
            experts:"",
            origin:"",
            searchWords:vue.academic.keywordList[0],
            startTime:0,
            endTime:0
          },
          filter_words: {},
          sort: '',
          page: 1,
          userID:-1
        }).then((res)=> {
          if(res.code == 200){
            vue.relation_list = res.data.result_list.filter((o)=>(o.id!==vue.academicID))
            console.log('get relation list', vue.relation_list)
          }
        })
      }
    },
    mounted() {
      let vue = this;
      this.academicID = this.$route.params.academicID
      console.log(this.academicID)
      let user_id = sessionStorage.getItem("userID")
      if(user_id == null){
        user_id = -1;
      }
      this.$api.academic.getById(
          {
            document_id:vue.academicID,
            user_id,
          }
      ).then(
          res =>{
            if(res.code == 200) {
              vue.academic = res.data;
              console.log(vue.academic);
              
              var date = new Date();
              var history = [];
              var userID = sessionStorage.getItem("userID");
              if(!localStorage.getItem(userID)) {
                history = [{id:vue.academicID, title:vue.academic.title, time:date.toLocaleDateString(),h_id:0}];
                localStorage.setItem(userID,JSON.stringify(history));
              } else {
                history = JSON.parse(localStorage.getItem(userID));
                var hid=history[-1].h_id+1;
                history.push({id:vue.academicID, title:vue.academic.title, time:date.toLocaleDateString(),h_id:hid});
                localStorage.setItem(userID,JSON.stringify(history));
              }

              this.getRelation();
            }else{
              this.$message.error("文章不存在或已被删除")
              this.$router.replace('/404')
            }
          }
      ).catch(err=>{
        console.log(err)
        this.$message.error("文章不存在或已被删除")
        this.$router.replace('/404')
      })
    }
  }
</script>

<style scoped>
  .search-word{
    transition: background-color .1s;
    float: left;
    cursor: pointer;
    display: block;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    max-width: 230px;
    margin: 0 10px 10px 0;
    padding: 7px 14px;
    border: 1px solid #0066cc;
    text-decoration: none;
    font-size: 14px;
    color: #333;
    pointer-events: all;
  }
  .search-word:hover{
    background-color: #f1f1f1;
    color: #409eff;
  }
  .hotword{
    border:1px solid #409EFF;
    border-radius:12px;
    height: 40px;
    font-size: 18px;
    text-align:center;
  }
  .expert{
    color: #0066cc;
  }
  .expert:hover{
    text-decoration: underline;
  }
  .keyword{
    color: #0066cc;
  }
  .keyword:hover{
    text-decoration: underline;
  }
  .el-row {
    margin-bottom: 20px;
  }
  .td{
    float: left;
  }
  .tableleft{
    text-align: left;
    float: left;
    color: #9c9e9c;
  }
  .background{
    background-image: url("../../assets/backgroud1.jpg");
    top: 0;
    left: 0;
    z-index:-10;
    background-size: cover;
    width: 100%;
    height: 100%;
    position: fixed;
    background-attachment: fixed;
  }
  .el-card{
    background-color: transparent;
  }
</style>
