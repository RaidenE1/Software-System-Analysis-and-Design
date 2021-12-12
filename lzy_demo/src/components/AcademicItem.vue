<template>
    <el-card class="card-body" ref="card" :style="SetWidth">
      <!-- source信息部分 -->
      <div class="sc-content">
        <!-- 标题 -->
        <div class="title">
          <span class="title-click" @click="toDetailPage">{{ item.title }}</span>
        </div>
        <!-- 摘要 -->
        <div class="summary"><b>摘要:</b> {{ item.summary }}</div>
        <!-- 其他信息：所有作者/来源(期刊/出版社/或无)/被引量/年份-->
        <div class="info">
          <span v-for="(item, index) in item.authors" :key="index">
            <span class="name-click" @click="toAuthorPage(item)">{{ item.name }} </span>
          </span>
          <span> - </span>
          <span class="source-click" @click="toSourcePage(item.origin)">{{ item.origin }}</span>
          <span> - 被引量：</span>
          <span class="citation-click" @click="toCitedPage()">{{ item.cited_quantity}}</span>
          <span> - </span>
          <span class="grey-part">{{ item.time.substring(0,4) }}年</span>
        </div>
        <!-- 来源：网站 -->
        <div class="allVersion">
          <span class="grey-part">来源：</span>
          <span class="version-click" @click="toLink(item.link)">{{ item.link }}</span>
        </div>
      </div>
      <!-- source操作部分 -->
      <div class="sc_ext">
        <!-- 收藏/引用/批量引用/（免费下载）-->
        <el-button class="ext-button" id="button-unfavor" type="mini" round icon="el-icon-star-on" @click="favor()" v-if="this.is_favor">已收藏</el-button>
        <el-button class="ext-button" id="button-favor" type="mini" round icon="el-icon-star-off" @click="favor()" v-if="!this.is_favor">收藏</el-button>
        <el-button class="ext-button" id="button-quote" type="mini" round icon="el-icon-share" @click="quote()">引用</el-button>
<!--        <el-button class="ext-button" id="button-batchQuote" type="mini" round icon="el-icon-folder-add" @click="batchQuote()">批量引用</el-button>-->
        <el-button class="ext-button" id="button-download" type="mini" round icon="el-icon-download" @click="toLink(item.link)">查看全文</el-button>
      </div>
    </el-card>
</template>

<script>
  export default {
    name: "AcademicItem",
    props: {
      c_sc: {
        type: Object,
        default: null
      },
      length: {
        type: Number,
        default: 610
      }
    },
    data() {
      return {
        item: {},
        is_favor: false,
        SetWidth: {
          width: '610px'
        }
      }
    },
    methods: {
      toDetailPage() {
        console.log("跳转文章详情页！");
        let url = window.location.origin + "/#/academicShow/"+this.item.id;
        if(window.open(url) === null){
          window.location.herf = url;
        }
      },
      toAuthorPage(item) {
        console.log("跳转学者搜索！")
        // 有id的情况
        if (item.id !== null){
          this.$router.push({
            name: 'ScholarPage',
            params: {
              expertid: item.id
            }
          })
        }
        // 没有id的情况 触发搜索
        else {
          this.$emit("toAuthorPage", item.name);
        }
      },
      toSourcePage(val) {
        // 出版社/杂志期刊
        console.log("跳转来源搜索！")
        this.$emit("toSourcePage", val);
      },
      toCitedPage() {
        // 查看引用信息
        console.log("跳转引用详情页！")
      },
      toLink(link){
        // window.location.href = link;
        window.open(link, '_blank');
      },
      favor() {
        console.log("收藏/取消收藏文章！");
        var _this = this
        this.$api.academic.favorSc({
          document_id: _this.item.documentid,
          user_id: sessionStorage.getItem("userID"),
          token: sessionStorage.getItem("token")
        }).then(res => {
          if (res.code === "200"){
            _this.is_favor = res.data;
            if (_this.is_favor === true){
              _this.$message({
                message: "成功收藏该资源！",
                type: "success"
              })
            }
            else {
              _this.$message({
                message: "成功取消收藏该资源！",
                type: "success"
              })
            }

          }else{
            _this.$message({
              message: res.msg,
              type: "error"
            })
          }
        })
        document.getElementById("button-favor").blur();

      },
      quote() {
        console.log("引用文章！");
        document.getElementById("button-quote").blur();
        this.$emit("quote", this.item)
      },
      batchQuote() {
        console.log("批量引用文章！");
        document.getElementById("button-batchQuote").blur();
      },
      download() {
        console.log("免费下载！");
        document.getElementById("button-download").blur();
      }
    },
    watch: {
      c_sc: {
        handler(){
          this.item = this.c_sc
        },
        deep: true
      }
    },
    mounted() {
      this.item = this.c_sc
      this.is_favor = this.item.is_favor
      console.log("before" + this.$data.SetWidth.width)
      this.$data.SetWidth.width = this.length + "px"
      console.log("after" + this.$data.SetWidth.width)
    }
  }
</script>

<style scoped>
 .el-card{
   width: 610px;
   margin: 0 10px 0 0;
   font-size: 13px;
   border-top: none;
   border-left: none;
   border-right: none;
   box-shadow: none;
   text-align: left
 }
 .el-card /deep/ .el-card__body{
   padding: 10px 10px 15px 0;
 }
 .title-click{
   font-size: 20px;
   color: #0066cc;
 }
 .title-click:hover,.name-click:hover,.source-click:hover,.citation-click:hover,.version-click:hover{
   cursor: pointer;
   text-decoration-line: underline;
 }
 .summary{
   color: #9c9e9c;
   line-height: 24px;
   word-break: break-all;
   white-space: normal;
   display: -webkit-box;
   /*! autoprefixer: ignore next */
   -webkit-box-orient: vertical;
   -webkit-line-clamp: 3;
   overflow: hidden;
 }
 .info{
   margin-top: 4px;
 }
 .allVersion{
   margin-top: 6px;
 }
 .grey-part{
   color: #9fa19f;
 }
 .sc_ext{
   margin-top: 15px;
 }
 .ext-button{
   background-color: #edf9ff;
   border-width: 0;
 }
</style>
