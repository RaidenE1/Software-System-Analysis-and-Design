<template>
  <div class="wrapper">
    <navbar class="header_home"></navbar>
    <div class="container">
      <div id="main-content">
        <!--科研人员信息-->
        <div id="author-info">
          <div class="person-image">
            <!--头像-->
            <div class="person-portrait">
              <div class="person-avatar-wr">
                <el-avatar class="person-avatar" :size="110" :src="sourceUrl"></el-avatar>
              </div>
            </div>
            <!--认证按钮-->
            <div class="person-authen is-hover" v-show="( this.user_id !== null) && !scholar_info.isVerified">
              <p class="authen-button" @click="handleAuthen">我要认证</p>
            </div>
            <!--关注按钮-->
            <div class="person-focus is-hover" v-show="this.user_id !== null">
              <p class="focus-button" @click="handleFocus" v-show="!scholar_info.isFocus">关注</p>
              <p class="unfocus-button" @click="handleFocus" v-show="scholar_info.isFocus">已关注
              <i class="el-icon-check"></i>
              </p>
            </div>
          </div>
          <!-- 基本信息 -->
          <div class="person-baseinfo">
            <div class="p-name">{{ this.scholar_info.name }}</div>
            <div class="p-volume c-grey">{{ this.scholar_info.volume === null ? 0 : this.scholar_info.volume }}人看过</div>
            <div class="p-scholarID">
              <div class="p-scholarID-all c-grey">
                <span class="p-scholarID-id">
                  {{ this.scholar_info.scholar_id }}
                </span>
              </div>
            </div>
            <div class="p-affiliate">{{ this.scholar_info.affiliate }}</div>
            <ul class="p-ach" v-if="this.has_ach">
              <li class="p-ach-item" v-for="(item, index) in this.scholar_info.achList" :key="index">
                <p class="p-ach-type c-grey">{{ item.title }}</p>
                <p class="p-ach-num">{{ item.num }}</p>
              </li>
            </ul>
          </div>
        </div>
        <!--成就统计 & 相关文献-->
        <div id="main-content-left">
          <!--成就展示-->
          <div id="achievement">
            <div class="achievement-pie">
              <div class="pie_item">
                <el-progress type="circle" text-inside="false" :percentage="Math.floor((paper1_count/real_total)*100)" :width="100"></el-progress>
                <p class="pie_title">{{ Math.floor((paper1_count/real_total)*100) + "%"}}<br/>期刊</p>
              </div>
              <div class="pie_item">
                <el-progress type="circle" text-inside="false" :percentage="Math.floor((paper2_count/real_total)*100)" :width="100"></el-progress>
                <p class="pie_title">{{ Math.floor((paper2_count/real_total)*100) + "%" }}<br/>会议</p>
              </div>
              <div class="pie_item">
                <el-progress type="circle" text-inside="false" :percentage="Math.floor((paper3_count/real_total)*100)" :width="100"></el-progress>
                <p class="pie_title">{{ Math.floor((paper3_count/real_total)*100) + "%"}}<br/>专著</p>
              </div>
              <div class="pie_item">
                <el-progress type="circle" text-inside="false" :percentage="Math.floor(((real_total - paper1_count - paper2_count - paper3_count)/real_total)*100)" :width="100"></el-progress>
                <p class="pie_title">{{ Math.floor(((real_total - paper1_count - paper2_count - paper3_count)/real_total)*100) + "%"}}<br/>其他</p>
              </div>
              <div class="pie_total">
                  <p style="margin-bottom: 0; font-weight: bold">总计</p>
                  <p style="font-size: 30px; font-weight: bold; margin-top: 5px">{{ this.real_total }}篇</p>
<!--                  <p style="font-size: 30px; font-weight: bold; margin-top: 5px">1111篇</p>-->
              </div>
            </div>
<!--            <div class="achievement-line"></div>-->
          </div>
          <!--相关文献-->
          <div id="article-list">
            <div id="article-list-container">
              <!--筛选 filter-->
              <div id="content-top">
                <div class="content-filter">
                  <div class="filter-item">
                    <el-popover
                        placement="bottom-start"
                        width="60"
                        trigger="hover">
                      <!--由后端传具体有哪些年份 for循环-->
                      <div class="sort-item">全部时间</div>
                      <div class="sort-item" @click="yearTo2020">2020</div>
                      <div class="sort-item" @click="yearTo2019">2019</div>
                      <div class="sort-item" @click="yearTo2018">2018</div>
                      <el-button class="button-sort" slot="reference">{{ this.getYear }}
                        <i class="el-icon-s-data" style="float: right" ref="icon_year"></i>
                      </el-button>
                    </el-popover>
                  </div>
                  <div class="filter-item">
                    <el-popover
                        placement="bottom-start"
                        width="60"
                        trigger="hover">
                      <div class="sort-item" @click="paperTo0">全部</div>
                      <div class="sort-item" @click="paperTo1">期刊</div>
                      <div class="sort-item" @click="paperTo2">会议</div>
                      <div class="sort-item" @click="paperTo3">专著</div>
                      <el-button class="button-sort" slot="reference">{{ this.getPaper }}
                        <i class="el-icon-s-data" style="float: right" ref="icon_paper"></i>
                      </el-button>
                    </el-popover>
                  </div>
                  <div class="filter-item">
                    <el-popover
                        placement="bottom-start"
                        width="60"
                        trigger="hover">
                      <div class="sort-item" @click="authorTo0">全部作者</div>
                      <div class="sort-item" @click="authorTo1">第一作者</div>
                      <el-button class="button-sort" slot="reference">{{ this.getAuthor }}
                        <i class="el-icon-s-data" style="float: right" ref="icon_author"></i>
                      </el-button>
                    </el-popover>
                  </div>
                  <div class="filter-item">
                    <el-popover
                        placement="bottom-start"
                        width="60"
                        trigger="hover">
                      <div class="sort-item" @click="sortToTime">按时间降序</div>
                      <div class="sort-item" @click="sortToCited">按被引降序</div>
                      <el-button class="button-sort" slot="reference">{{ this.getSort }}
                        <i class="el-icon-s-data" style="float: right" ref="icon_sort"></i>
                      </el-button>
                    </el-popover>
                  </div>
                </div>
              </div>
              <!--文献列表-->
              <div id="content-result" v-if="has_result">
                <div class="result-list">
                  <div v-for="(result_item,index) in result_list" v-bind:key="index">
                    <academic-item
                        :c_sc = result_item
                        :length=700
                        v-on:toAuthorPage = "searchAuthor"
                        v-on:toSourcePage = "searchSource"
                        v-on:quote = "showQuote"></academic-item>
                  </div>
                </div>
                <div class="result-page" v-show="total_rs > 10">
                  <el-pagination
                      background
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page.sync="currentPage"
                      :page-size="10"
                      layout="prev, pager, next, jumper"
                      :total=total_rs
                      class="page">
                  </el-pagination>
                </div>
              </div>
              <!-- 无结果tip -->
              <div class="no-result-tip" v-if="!has_result" style="text-align: center">
                <br/>
                <p style="margin-bottom: 0; font-size: 28px; font-weight: bold; color: #2c3e50">Sorry</p>
                <p>暂无该学者的相关学术资源</p>
              </div>
            </div>
          </div>
        </div>
        <!--合作统计展示-->
        <div id="main-content-right">
          <!--合作学者展示-->
          <div class="co-author-wr">
            <i class="el-icon-d-arrow-right" style="float: right;margin-right: 10px" v-show="(total_co_authors > 4)">更多</i>
<!--            <div style="height:calc(100vh - 50px);">-->
<!--              <SeeksRelationGraph-->
<!--                      ref="seeksRelationGraph"-->
<!--                      :options="graphOptions"-->
<!--                      :on-node-click="onNodeClick"-->
<!--                      :on-line-click="onLineClick" />-->
<!--            </div>-->
            <h3>合作学者</h3>
            <div class="co-author-list" v-if="has_co_author">
              <div v-for="(item, index) in co_authors_list_show" :key="index">
                <div class="co-author-avatar is-hover"  @click="toScholarPage(item.scholar_id)">
                  <el-avatar shape="square" :size="40" :src="sourceUrl"></el-avatar>
                </div>
                <div class="co-author-item">
                  <div class="co-author-name" @click="toScholarPage(item)">{{ item.name }}</div>
                  <div class="co-author-affiliate">{{ item.org }}</div>
                </div>
              </div>
            </div>
            <div v-if="!has_co_author">暂无</div>
          </div>
          <!--合作机构展示-->
          <div class="co-affiliate-wr">
            <h3>合作机构</h3>
            <div v-if="has_co_aff">
              <ul class="co-affiliate-list" v-for="(item, index) in co_affiliate_list" :key="index">
                <li>
                  <span class="co_affiliate_name">{{ item.org }}</span>
                  <span class="co_affiliate_line">
                  <span class="co_affiliate_width"></span>
                  <span class="co_affiliate_count">{{ item.cooperationNum }}</span>
                </span>
                </li>
              </ul>
            </div>
            <div v-if="!has_co_aff">暂无</div>
          </div>
        </div>
      </div>
    </div>
    <!-- 引用 -->
    <el-dialog
        title="引用"
        :visible.sync="quotedialogVisible"
        width="40%">
      <div style="text-align: left">
        <b>以下引用格式为GB/T7714，点击右侧按钮即可复制内容</b>
        <el-button type="warning"
                   size="mini"
                   icon="el-icon-document-copy"
                   style="float: right"
                   v-clipboard:copy="quoteText"
                   v-clipboard:success="copySuccess"
                   v-clipboard:error="copyError"
        ></el-button>
      </div>
      <br/>
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
  </div>
</template>

<script>
import AcademicItem from "@/components/AcademicItem";
import navbar from "@/components/header";
// import SeeksRelationGraph from 'relation-graph'

export default {
  name: "ScholarPage",
  components: {
    AcademicItem,
    navbar,
    // SeeksRelationGraph
  },
  data() {
    return {
      user_id: '0',
      scholar_id: '0',
      scholar_info: {
        name: '',
        volume: 0,
        scholar_id: '',
        affiliate: '',
        achList: [],
        isVerified: false,
        isFocus: false
      },
      co_authors_list:[],
      co_authors_list_show:[],
      co_affiliate_list: [],
      co_affiliate_list_show: [],
      total_co_authors: 0,
      total_co_affs: 0,
      result_list:[],
      result_list_first: [],
      result_list_show: [],
      total_rs: 0,
      has_result: false,
      sort_words: {
        sc_year: '',
        paper_type: '',
        first_author : '',
        sc_sort: 'time',
      },
      currentPage: 1,
      sourceUrl: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
      has_ach: false,
      has_co_author: false,
      has_co_aff: false,
      quoteText:"",
      quotedialogVisible:false,
      author_type : '0',
      paper1_count: 0,
      paper2_count: 0,
      paper3_count: 0,
      real_total: 0,
      search_count: 0,
      // 图谱
      // graphOptions: {
      //   allowSwitchLineShape: true,
      //   allowSwitchJunctionPoint: true,
      //   defaultJunctionPoint: 'border',
      //   'layouts': [
      //     {
      //       'label': '中心',
      //       'layoutName': 'center',
      //       'layoutClassName': 'seeks-layout-center'
      //     }
      //   ],
      //   // 这里可以参考"Graph 图谱"中的参数进行设置
      // }
    }
  },
  methods: {
    // showSeeksGraph() {
    //   let nodes = [];
    //   let links = [];
    //   for(let author of this.co_authors_list_show){
    //     nodes.push({
    //       id:author.name,
    //       text:author.name,
    //       width: 30, height: 30
    //     })
    //     links.push({
    //       from:this.scholar_info.name,
    //       to:author.name
    //     })
    //   }
    //   let __graph_json_data = {
    //     rootId: this.scholar_info.name,
    //     nodes,
    //     links
    //   }
    //   this.$refs.seeksRelationGraph.setJsonData(__graph_json_data, (seeksRGGraph) => {
    //     seeksRGGraph;
    //     // 这些写上当图谱初始化完成后需要执行的代码
    //   })
    // },
    // onNodeClick(nodeObject) {
    //   console.log('onNodeClick:', nodeObject)
    // },
    // onLineClick(lineObject) {
    //   console.log('onLineClick:', lineObject)
    // },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    showQuote(val){
      console.log("正在引用")
      this.quoteText = this.getQuote(val)
      this.quotedialogVisible = true;
      this.sp_result = val;
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
      if(dtype === '专利'){
        res += 'P'
      }else if(dtype === '会议'){
        res += 'C'
      }else if(dtype === '图书'){
        res += 'M'
      }else if(dtype === '学位'){
        res += 'D'
      }else if(dtype === '期刊'){
        res += 'J'
      }
      res += '].'
      res += document.origin;
      if(document.time.length >= 4){
        res += ','+document.time.substring(0,4);
      }
      return res;
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
    searchAuthor(val){
      var search_words = {
        searchWords: '',
        title: '',
        keyWords: '',
        experts: val,
        origin: '',
        startTime: '0',
        endTime: '0'
      }
      this.$router.push({
        name: "AcademicSearch",
        params: {
          search_words: encodeURIComponent(JSON.stringify(search_words))
        }
      })
    },
    searchSource(val) {
      var search_words = {
        searchWords: '',
        title: '',
        keyWords: '',
        experts: '',
        origin: val,
        startTime: '0',
        endTime: '0'
      }
      this.$router.push({
        name: "AcademicSearch",
        params: {
          search_words: encodeURIComponent(JSON.stringify(search_words))
        }
      })
    },
    toScholarPage(scholar) {
      // 有 ID
      if (scholar.expertid !== null){
        this.$router.push({
          name: "ScholarPage",
          params: {
            expertid: scholar.expertid
          }
        })
      }
      // 无 ID 跳转搜索页
      else {
        var search_words = {
          searchWords: '',
          title: '',
          keyWords: '',
          experts: scholar.name,
          origin: '',
          startTime: '0',
          endTime: '0'
        }
        this.$router.push({
          name: "AcademicSearch",
          params: {
            search_words: encodeURIComponent(JSON.stringify(search_words))
          }
        })
      }
    },
    handleAuthen(){
      console.log("点击了申请认领门户");
      var _this = this
      this.$prompt('请使用您所在机构的邮箱认证！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: '邮箱格式不正确'
      }).then(({ value }) => {
        _this.authen(value);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    authen(value){
      var _this = this
      console.log('token is '+sessionStorage.getItem("token"))
      console.log('userID is '+sessionStorage.getItem("userID"))
      console.log('objID is '+_this.scholar_id)
      console.log("email is "+value)
      this.$api.application.create({
        token: sessionStorage.getItem("token"),
        userID: sessionStorage.getItem("userID"),
        objectID: _this.scholar_id,
        flag: 1,
        email: value
      }).then(res => {
        console.log(res);
        if (res.code === "200"){
          _this.$message({
            type: "success",
            message: '成功提交申请'
          });
        } else {
          _this.$message({
            message: res.msg,
            type: "error"
          })
        }
      })
    },
    handleFocus() {
      var _this = this
      this.$api.scholar.focusScholar({
        scholar_id: _this.scholar_id,
        user_id: _this.user_id
      }).then(res=>{
        if (res.code === "200"){
          _this.scholar_info.isFocus = res.data
          if (_this.scholar_info.isFocus){
            _this.$message({
              message: "成功关注该门户！",
              type: "success"
            })
          } else {
            _this.$message({
              message: "成功取消关注该门户！",
              type: "success"
            })
          }
        } else {
          _this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    yearTo2020() {
      this.sort_words.sc_year = '2020'
    },
    yearTo2019() {
      this.sort_words.sc_year = '2019'
    },
    yearTo2018() {
      this.sort_words.sc_year = '2018'
    },
    paperTo0() {
      this.sort_words.paper_type = '';
    },
    paperTo1() {
      this.sort_words.paper_type = '期刊';
    },
    paperTo2() {
      this.sort_words.paper_type = '会议';
    },
    paperTo3() {
      this.sort_words.paper_type = '图书';
    },
    getPaperCount() {
      var _this = this
      console.log(_this.scholar_info.name + "正在搜索文献")
      this.$api.academic.getSearchResult({
        search_words: {
          searchWords: '',
          title: '',
          keyWords: '',
          experts: _this.scholar_info.name,
          origin: '',
          startTime: '0',
          endTime: '0'
        },
        filter_words: {
          year: '',
          cate: '',
          level: '',
          savetype: '',
          keywords: '',
          type: '',
          authors: '',
          jnls: '',
          affs: '',
        },
        sort: _this.sort_words.sc_sort,
        page: _this.currentPage,
        userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
      }).then(res => {
        if (res.code === "200"){
          _this.paper1_count = res.data.filter_list[1].filter_itemList[0].count;
          _this.paper2_count = res.data.filter_list[1].filter_itemList[3].count;
          _this.paper3_count = res.data.filter_list[1].filter_itemList[1].count;
        }
        else if (res.code === "100"){
          _this.has_result = false;
        }
        else {
          _this.has_result = false;
          _this.$message({
            message: res.msg,
            type: "error"
          })
        }
      })
    },
    authorTo0() {
      // this.author_type = "0";
      // this.result_list_show = this.result_list
      this.sort_words.first_author = '0'
    },
    authorTo1() {
      // this.author_type = "1";
      // this.result_list_show = this.result_list_first
      this.sort_words.first_author = '1'
    },
    sortToTime() {
      this.sort_words.sc_sort = 'time';
    },
    sortToCited() {
      this.sort_words.sc_sort = 'cited';
    },
    getFirstSc() {
      this.result_list_first = []
      var name = this.scholar_info.name
      for (var i=0 ; i < this.result_list.length; i++){
        if (this.result_list[i].authors[0].name === name){
          this.result_list_first.push(this.result_list[i])
        }
      }
      console.log("第一作者文章：" + this.result_list_first)
    },
    loadInfo(){
      
      var _this = this
      console.log("scholar id is"+this.scholar_id);
      this.$api.scholar.getInfo({
        scholar_id: _this.scholar_id,
        user_id: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
      }).then(res => {
        if (res.code === "200"){
          _this.scholar_info = res.data
          if (_this.scholar_info.achList.length === 0){
            _this.has_ach = false;
          } else {
            _this.has_ach = true;
          }
          this.loadRelateSc()
          this.getPaperCount()
          console.log(_this.paper1_count);
          console.log(_this.paper2_count);
          console.log(_this.paper3_count);
          // this.loadCoAuthorsList()
          this.loadCoAffList()
        }else {
          _this.$message({
            message: res.message,
            type: "error"
          })
          _this.$router.replace('/404')
        }
      }).catch(err => {
        console.log(err)
        this.$router.replace('/404')
      })
    },
    loadRelateSc(){
      var _this = this
      console.log(_this.scholar_info.name + "正在搜索文献")
      this.$api.academic.getSearchResult({
        search_words: {
          searchWords: '',
          title: '',
          keyWords: '',
          experts: _this.scholar_info.name,
          origin: '',
          startTime: '0',
          endTime: '0'
        },
        filter_words: {
          year: _this.sort_words.sc_year,
          cate: '',
          level: '',
          savetype: '',
          keywords: '',
          // type: '',
          type: _this.sort_words.paper_type,
          authors: '',
          jnls: '',
          affs: '',
        },
        sort: _this.sort_words.sc_sort,
        page: _this.currentPage,
        userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
      }).then(res => {
        if (res.code === "200"){
          _this.result_list = res.data.result_list
          // 通过文章计算合作学者
          _this.loadCoAuthorsListResult();
          _this.total_rs = res.data.total
          if (_this.search_count === 0){ // 首次加载
            _this.real_total = _this.total_rs
            _this.search_count = 1;
          }
          if (_this.result_list.length === 0){
            _this.has_result = false;
          } else {
            _this.has_result = true
          }
        }
        else if (res.code === "100"){
          _this.has_result = false;
        }
        else {
          _this.has_result = false;
          _this.$message({
            message: res.msg,
            type: "error"
          })
        }
      })
    },
    loadCoAuthorsListResult(){
      let author_list = [];
      for(let doc of this.result_list){
        for(let author of doc.authors){
          if(author.name == this.scholar_info.name)
            continue;
          let repeate = false;
          for(let a of author_list){
            if(a.name == author.name){
              repeate = true;
              a.fre++;
              break;
            }
          }
          if(!repeate && author.name != "") {
            author.fre = 0;
            console.log(author)
            author_list.push(author)
          }
        }
      }
      if(author_list.length>0)
        this.has_co_author = true;
      if(author_list.length > 10){
        author_list = author_list.slice(0,10);
      }
      author_list = author_list.sort((a,b)=>(-a.fre+b.fre))
      this.co_authors_list_show = author_list

      // 绘制图谱
      // this.showSeeksGraph()
    },
    loadCoAuthorsList(){
      var _this = this
      this.$api.scholar.getCoAuthors({
        scholar_id: _this.scholar_id
      }).then(res => {
        if (res.code === "200") {
          _this.co_authors_list = res.data.expert_list
          _this.total_co_authors = res.data.total
          if (_this.co_authors_list === null){
            _this.has_co_author = false;
          } else {
            _this.has_co_author = true;
            _this.co_authors_list_show = _this.co_authors_list
            // if (_this.total_co_authors > 4){
            //   _this.co_authors_list_show = _this.co_authors_list.slice(0,4)
            // } else{
            //   _this.co_authors_list_show = _this.co_authors_list
            // }
          }
        } else {
          _this.$message({
            message: res.msg,
            type: "error"
          })
        }
      })
    },
    loadCoAffList() {
      var _this = this
      this.$api.scholar.getCoAffiliate({
        scholar_id: _this.scholar_id
      }).then(res => {
        if (res.code === "200") {
          _this.co_affiliate_list = res.data.expert_list
          _this.total_co_affs = res.data.total
          if (_this.co_affiliate_list === null){
            _this.has_co_aff = false;
          } else {
            _this.has_co_aff = true;
          }
        } else {
          _this.$message({
            message: res.msg,
            type: "error"
          })
        }
      })
    }
  },
  computed: {
    getYear() {
      switch (this.sort_words.sc_year) {
        case "2020":
          return '2020'
        case '2019':
          return '2019'
        case '2018':
          return '2018'
        default:
          return '全部年份'
      }
    },
    getPaper() {
      switch (this.sort_words.paper_type){
        case "":
          return '全部类型'
        case "期刊":
          return '期刊'
        case "会议":
          return '会议'
        case "图书":
          return '专著'
        default:
          return '全部类型'
      }

    },
    getAuthor() {
      switch (this.sort_words.first_author){
        case "0":
          return '全部作者'
        case "1":
          return '第一作者'
        default:
          return '全部作者'
      }
    },
    getSort() {
      switch (this.sort_words.sc_sort){
        case "time":
          return '按时间降序'
        case "cited":
          return '按被引降序'
        default:
          return '按时间降序'
      }
    }
  },
  watch: {
    sort_words: {
      // 监听：过滤关键词
      handler: function () {
        console.log("sort_words有改动")
        this.loadRelateSc()
      },
      deep: true
    },
    currentPage: function () {
      // 监听：页码
      console.log("page有改动")
      this.loadRelateSc();
    }
  },
  mounted() {
    this.user_id = sessionStorage.getItem('userID')
    this.scholar_id = this.$route.params.expertid
    this.currentPage = 1
    this.loadInfo()
  }
}
</script>

<style scoped>
  #main-content{
    width: 1100px;
    margin: 0 auto;
    overflow: hidden;
    position: relative;
    z-index: 10;
    font-size: 13px;
    text-align: left;
  }
  #author-info{
    border-bottom: 1px solid #e3e3e3;
    padding: 32px 0 20px 0;
    overflow: hidden;
  }
  #main-content-left{
    float: left;
    width: 700px;
  }
  #main-content-right{
    float: right;
    width: 330px;
  }
  .person-image{
    width: 125px;
    float: left;
    text-align: center;
    margin-right: 60px;
    /*min-height: 250px;*/
  }
  .person-portrait{
    position: relative;
    display: inline-block;
    width: 122px;
    height: 132px;
  }
  .person-avatar{
    border: 6px solid #fff;
    box-shadow: 0 1px 6px rgba(0,0,0,.25);
  }
  .person-authen,.person-focus{
    width: 94px;
    display: inline-block;
  }
  .person-authen{
    display: inline-block;
  }
  .authen-button{
    display: inline-block;
    color: #ffffff;
    height: 34px;
    width: 94px;
    line-height: 34px;
    font-size: 14px;
    background-color: #0066cc;
    border-radius: 2px;
    text-align: center;
    margin: 0;
  }
  .person-focus{
    position: relative;
    display: inline-block;
    width: 90px;
    /*height: 23px;*/
  }
  .focus-button{
    color: #06c;
    border: 1px solid #87bcf0;
    border-radius: 23px;
    height: 23px;
    line-height: 23px;
  }
  .unfocus-button{
    height: 23px;
    line-height: 23px;
    color: #999;
    border: 1px solid #e5e5e5;
    border-radius: 23px;
  }
  .person-baseinfo{
    float: left;
    width: 510px;
  }
  .p-name{
    float: left;
    font-size: 28px;
    line-height: 30px;
    color: #333;
    margin-bottom: 8px;
  }
  .p-volume{
    float: left;
    font-size: 14px;
    color: #999;
    margin-top: 10px;
    margin-left: 20px;
  }
  .p-scholarID{
    float: right;
    /*width: 192px;*/
    width: 262px;
    height: 24px;
    background-color: #fafafa;
    padding: 3px;
  }
  .p-scholarID-all{
    /*width: 190px;*/
    width: 260px;
    height: 22px;
    border: 1px solid #E6E6E6;
    background-color: #fafafa;
    color: #999;
    font-size: 13px;
    text-align: center;
    line-height: 24px;
  }
  .p-scholarID-id{
    font-size: 13px;
    color: #06c;
  }
  .p-affiliate{
    clear: both;
  }
  .p-ach-item{
    float: left;
    border-right: 1px solid #efefef;
    margin-right: 10px;
    font-size: 20px;
    width: 78px;
  }
  .p-ach-type{
    font-size: 12px;
    margin: 0;
  }
  .c-grey{
    color: #999;
  }
  .p-ach-num{
    margin: 10px 0 0 0;
  }
  ul, li{
    list-style: none;
    padding-left: 0;
  }
  #achievement{
    border-bottom: 1px dotted #bfbfbf;
  }
  .achievement-pie{
    height: 130px;
    margin-top: 26px;
    /*text-align: center;*/
  }
  .pie_item{
    display: inline-block;
    margin-right: 50px;
  }
  .pie_title{
    position: relative;
    bottom: 76px;
    margin-top: 10px;
    text-align: center;
    font-weight: bold;
  }
  .pie_total{
    display: inline-block;
    text-align: center;
    position: relative;
    bottom: 66px;
  }
  .achievement-line{
    height: 170px;
  }
  #article-list{
    margin-top: 20px;
  }
  .content-filter{
    float: left;
  }
  .filter-item{
    position: relative;
    float: left;
    margin-right: 20px;
  }
  .button-sort{
    border: none;
    padding: 0;
    font-size: 12px;
  }
  .co-author-wr{
    border-bottom: 1px dotted #bfbfbf;
    padding-bottom: 15px;
  }
  .co-author-avatar{
    display: inline-block;
  }
  .co-author-item{
    margin-bottom: 10px;
    display: inline-block;
    margin-left: 12px;
    vertical-align: top;
  }
  .co-author-name{
    color: #005cd9;
  }
  .co-author-name:hover{
    cursor: pointer;
  }
  .co_affiliate_list span {
    display: inline-block;
    vertical-align: middle;
    height: 16px;
  }
  .co_affiliate_line{
    float: right;
  }
  .result-page{
    width: 610px;
  }
  .page{
    margin-top: 16px;
    text-align: center;
  }
  .is-hover:hover{
    cursor: pointer;
  }
  .sort-item{
    font-size: 12px;
    line-height: 20px;
    min-width: 50px;
  }
  .sort-item:hover{
    cursor: pointer;
  }
</style>
<style>
  .el-popover{
    min-width: 70px;
    padding: 2px 12px 2px 12px;
    box-shadow: none;
  }
</style>
