<template>
<div>
    <navbar class="header_home"></navbar>
    <div class="wrapper">
        <div class="container">
            <!-- 右侧热门显示 -->
            <div class="content-right">
                <div style="margin-left: 20px">
                    <!-- 热门关键词 -->
                    <div>
                        <h4 style="margin-top: 8px; margin-bottom: 10px">热门关键词</h4>
                        <el-row class="keyword_list" v-for="(o, index) in this.hot_keywords" :key="index">
                            <el-link class="keyword" :underline="false" @click="searchWords(o)" style="line-height: 30px; font-weight: bold; font-size: 14px;"><i class="el-icon-search" style="margin-right: 5px"></i>{{ o }}</el-link>
                        </el-row>
                    </div>
                    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
                    <!-- 热门文献 -->
                    <div>
                        <h4 style="margin-top: 14px; margin-bottom: 10px">热门文献</h4>
                        <div class="hot_source">
                            <div class="hot_source_item" v-for="(o, index) in this.hot_source" :key="index">
                                <div class="hot_title" @click="goArticle(o.id)">{{ o.title }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 搜索结果 -->
            <div class="content-left" v-if="has_result">
                <!-- filter -->
                <div class="content-left-nav">
                    <div class="left-nav" v-for="(item, index) in filter_list" :key="index">
                        <filter-item :filter_item="item" :c_filter_words="filter_words" v-on:getFilter="handleSelect" v-on:cancelSelect="handleCancel"></filter-item>
                    </div>
                </div>
                <!-- 搜索结果 -->
                <div class="content-left-rs">
                    <div class="rs-list">
                        <!-- 学者列表 -->
                        <div class="e_result" v-show="has_experts">
                            <div class="e_border">
                                <div class="e_wrapper">
                                    <p class="e_tips">为您找到{{ experts_count }}个学者：</p>
                                    <div>
                                        <div class="e_item" v-for="(item, index) in e_result_list" :key="index">
                                            <div class="e_avatar">
                                                <el-avatar :size="40" :src="sourceUrl"></el-avatar>
                                            </div>
                                            <div class="e_info">
                                                <div class="e_info_name" @click="handleToAuthor(item.expertid)">{{ item.name }}</div>
                                                <div class="e_info_aff">{{ item.org }}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 工具栏 -->
                        <div class="toolbar">
                            <div class="sort-container">
                                <el-popover placement="top-start" width="70" trigger="hover">
                                    <div class="sort-item" @click="sortByViews">按浏览量</div>
                                    <div class="sort-item" @click="sortByCited">按被引量</div>
                                    <div class="sort-item" @click="sortByTime">按时间降序</div>
                                    <el-button class="button-sort" slot="reference">
                                        <i class="el-icon-sort">按{{ this.getSortMethod }}</i>
                                    </el-button>
                                </el-popover>
                            </div>
                            <span class="nums">找到约{{ this.total_rs }}条相关结果</span>
                        </div>
                        <!-- 文献列表 -->
                        <div v-for="(result_item,index) in result_list" v-bind:key="index">
                            <academic-item :c_sc=result_item v-on:toAuthorPage="searchAuthor" v-on:toSourcePage="searchSource" v-on:quote="showQuote"></academic-item>
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
                <!-- 页码 -->
                <p class="page">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-size="10" layout="prev, pager, next, jumper" :total=total_rs>
                    </el-pagination>
                </p>
            </div>
            <!-- 无结果tip -->
            <div class="no_result_tip" v-if="!has_result">
                <p class="no_result_tip_warning">No Results Found</p>
                <p style="margin-top: 8px">没有找到与“{{ " " + search_words.searchWords + " " +search_words.title + " " +search_words.keyWords + " " + search_words.experts + " " + search_words.origin + " "}}”相关的学术结果</p>
                <p style="line-height: 26px">
                    <b style="font-size: 17px">请尝试：</b><br />
                    1. 检查输入是否有误<br />
                    2. 简化输入词<br />
                    3. 更换相关词<br />
                </p>
            </div>
        </div>
        <!-- 引用dialog -->
        <el-dialog title="引用" :visible.sync="quotedialogVisible" width="40%">
            <div style="text-align: left">
                <b>GB/T7714</b>
                <el-button size="mini" icon="el-icon-document-copy" style="float: right; background-color: #00bcd4; color: #fff;" v-clipboard:copy="quoteText" v-clipboard:success="copySuccess" v-clipboard:error="copyError"></el-button>
            </div>
            <br />
            <el-input type="textarea" placeholder="url" autosize v-model="quoteText" :readonly="true">
            </el-input>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="quotedialogVisible=false">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</div>
</template>

<script>
import AcademicItem from "@/components/AcademicItem";
import FilterItem from "@/components/FilterItem";
import navbar from "@/components/header";
export default {
    name: "AcademicSearch",
    components: {
        navbar,
        FilterItem,
        AcademicItem
    },
    data() {
        return {
            sourceUrl: "https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png",
            result_list: [],
            total_rs: 0, // 搜索结果数目，非当前页面展示数量
            result_length: 0, // 当前页的显示数量
            filter_list: [],
            currentPage: 1,
            sort: "views",
            search_words: {
                searchWords: '',
                title: '',
                keyWords: '',
                experts: '',
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
            e_result_list: [],
            has_experts: false,
            experts_count: 0,
            has_result: true,
            quoteText: "",
            quotedialogVisible: false,
            sp_result: {},
            hot_keywords: [
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
            hot_source: []
        }
    },
    methods: {
        goArticle(val) {
            let url = window.location.origin + "/#/academicShow/" + val;
            if (window.open(url) === null) {
                window.location.herf = url;
            }
        },
        showQuote(val) {
            this.quoteText = this.getQuote(val)
            this.quotedialogVisible = true;
            this.sp_result = val;
        },
        getQuote(document) {
            let res = ""
            for (let i in document.authors) {
                if (i != 0) {
                    res += ",";
                }
                res += document.authors[i].name;
            }
            res += '.';
            res += document.title + '['
            let dtype = document.dtype;
            if (dtype === '专利') {
                res += 'P'
            } else if (dtype === '会议') {
                res += 'C'
            } else if (dtype === '图书') {
                res += 'M'
            } else if (dtype === '学位') {
                res += 'D'
            } else if (dtype === '期刊') {
                res += 'J'
            }
            res += '].'
            res += document.origin;
            if (document.time.length >= 4) {
                res += ',' + document.time.substring(0, 4);
            }
            return res;
        },
        copySuccess() {
            this.$message({
                message: '复制成功',
                type: 'success',
                offset: 100,
            });
            this.sharedialogVisible = false;
        },
        copyError() {
            this.$message({
                message: '您的浏览器不支持该功能，请自行复制链接内容',
                type: 'info',
                offset: 100,
            });
        },
        handleSelect(val, name) {
            console.log("父组件监听到点击了filter类别：" + name + " 的选线： " + val)
            this.filter_words[name] = val
        },
        handleCancel(name) {
            console.log(name + "父组件监听到取消了filter类别：" + name);
            this.filter_words[name] = '';
        },
        handleToAuthor(id) {
            this.$router.push({
                name: 'ScholarPage',
                params: {
                    expertid: id
                }
            })
        },
        searchAuthor(val) {
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
        searchWords(val) {
            var search_words = {
                searchWords: val,
                title: '',
                keyWords: '',
                experts: '',
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
        sortByViews() {
            console.log("按浏览量排序！");
            this.sort = "views";
        },
        sortByCited() {
            console.log("按被引量排序！");
            this.sort = "cited";
        },
        sortByTime() {
            console.log("按时间降序排序！");
            this.sort = "time";
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        },
        loadSearchSc() {
            var _this = this;
            this.$api.academic.getSearchResult({
                search_words: _this.search_words,
                filter_words: _this.filter_words,
                sort: _this.sort,
                page: _this.currentPage,
                userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
            }).then(res => {
                if (res.code === "200") {
                    _this.has_result = true
                    _this.result_list = res.data.result_list;
                    _this.filter_list = res.data.filter_list;
                    _this.total_rs = res.data.total;
                    _this.result_length = _this.result_list.length;
                    _this.e_result_list = res.data.expert_list;
                    console.log(_this.e_result_list);
                    if (_this.e_result_list.length === 0) {
                        _this.has_experts = false
                    } else {
                        _this.has_experts = true
                        _this.experts_count = _this.e_result_list.length
                    }
                } else if (res.code === "100") {
                    _this.has_result = false
                } else {
                    _this.has_result = false
                    this.$message({
                        message: res.msg,
                        type: "error",
                        offset: 100,
                    })
                    console.log("Request => getSearchResult : not 200");
                }
            })
        },
        loadHotSource() {
            var _this = this;
            this.$api.academic.getSearchResult({
                search_words: {
                    searchWords: '',
                    keyWords: '',
                    title: '',
                    experts: '',
                    origin: '',
                    startTime: '0',
                    endTime: '0',
                },
                filter_words: {},
                sort: "cited",
                page: 1,
                userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
            }).then(res => {
                if (res.code === "200") {
                    _this.hot_source = res.data.result_list
                } else {
                    this.$message({
                        message: res.msg,
                        type: "error",
                        offset: 100,
                    })
                    console.log("Request => getSearchResult : not 200");
                }
            })
        }
    },
    computed: {
        getSortMethod: function () {
            if (this.sort === "views") {
                return "浏览量";
            } else if (this.sort === "cited") {
                return "被引量";
            } else {
                return "时间降序";
            }
        }
    },
    watch: {
        sort: function () {
            // 监听：排序方式
            console.log("sort被修改")
            sessionStorage.setItem("sort", this.sort)
            document.body.scrollTop = document.documentElement.scrollTop = 0;
            var _this = this;
            this.result_list = []
            this.$api.academic.getSearchResult({
                search_words: _this.search_words,
                filter_words: _this.filter_words,
                sort: _this.sort,
                page: _this.currentPage,
                userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
            }).then(res => {
                if (res.code === "200") {
                    _this.has_result = true
                    _this.result_list = res.data.result_list;
                    _this.total_rs = res.data.total;
                    _this.result_length = _this.result_list.length;
                } else if (res.code === "100") {
                    _this.has_result = false
                } else {
                    _this.has_result = false
                    this.$message({
                        message: res.msg,
                        type: "error",
                        offset: 100,
                    })
                    console.log("Request => getSearchResult : not 200");
                }
            })
        },
        currentPage: function () {
            // 监听：页码
            console.log("page被修改")
            sessionStorage.setItem("current_page", this.currentPage.toString());
            document.body.scrollTop = document.documentElement.scrollTop = 0;
            var _this = this
            this.result_list = []
            this.$api.academic.getSearchResult({
                search_words: _this.search_words,
                filter_words: _this.filter_words,
                sort: _this.sort,
                page: _this.currentPage,
                userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
            }).then(res => {
                if (res.code === "200") {
                    _this.has_result = true
                    _this.result_list = res.data.result_list;
                    _this.result_length = _this.result_list.length;
                    _this.total_rs = res.data.total;
                } else if (res.code === "100") {
                    _this.has_result = false
                } else {
                    _this.has_result = false
                    this.$message({
                        message: res.msg,
                        type: "error",
                        offset: 100,
                    })
                    console.log("Request => getSearchResult : not 200");
                }
            })
        },
        filter_words: {
            // 监听左侧过滤项点击
            handler() {
                console.log("filters改变")
                document.body.scrollTop = document.documentElement.scrollTop = 0;
                var _this = this
                this.result_list = []
                this.$api.academic.getSearchResult({
                    search_words: _this.search_words,
                    filter_words: _this.filter_words,
                    sort: _this.sort,
                    page: _this.currentPage,
                    userID: sessionStorage.getItem("userID") === null ? -1 : sessionStorage.getItem("userID")
                }).then(res => {
                    if (res.code === "200") {
                        _this.has_result = true
                        _this.result_list = res.data.result_list;
                        // _this.filter_list = res.data.filter_list;
                        _this.result_length = _this.result_list.length;
                        _this.total_rs = res.data.total;
                    } else if (res.code === "100") {
                        _this.has_result = false
                    } else {
                        _this.has_result = false
                        this.$message({
                            message: res.msg,
                            type: "error",
                            offset: 100,
                        })
                        console.log("Request => getSearchResult : not 200");
                    }
                })
            },
            deep: true
        }
    },
    mounted() {
        document.body.scrollTop = document.documentElement.scrollTop = 0;
        this.search_words = JSON.parse(decodeURIComponent(this.$route.params.search_words));
        if (window.performance.navigation.type === 1) {
            console.log("页面被刷新")
            this.currentPage = parseInt(sessionStorage.getItem("current_page"))
            this.sort = sessionStorage.getItem("sort")
            if (this.sort === null) {
                this.sort = "views"
            }
            if (this.currentPage === null) {
                this.currentPage = 1
            }
        } else {
            console.log("首次被加载")
            this.currentPage = 1
            sessionStorage.setItem("current_page", "1")
            this.sort = "views"
            sessionStorage.setItem("sort", "views")
        }
        console.log("获取关键词：" + this.search_words)
        console.log("页码：" + this.currentPage)
        console.log("排序方式" + this.sort)
        // 加载检索数据
        this.loadSearchSc()
        this.loadHotSource()
    }
}
</script>

<style scoped>
.wrapper {
    margin-top: 50px;
    min-width: 1100px;
    text-align: center;
}

.container {
    width: 1100px;
    min-height: 1000px;
    text-align: left;
    margin: 0 auto;
}

.content-right {
    width: 240px;
    min-height: 220px;
    float: right;
}

.content-left {
    width: 860px;
    min-height: 1000px;
    /*margin-right: 300px;*/
}

.content-left-nav {
    float: left;
    width: 240px;
    min-height: 208px;
}

.content-left-rs {
    float: left;
    width: 620px;
    padding-top: 10px;
}

.e_result {
    width: 610px;
    margin-bottom: 15px;
    font-size: 13px;
    line-height: 1.54;
}

.e_border {
    padding: 9px;
    border: 1px solid #e3e3e3;
    border-bottom-color: #e0e0e0;
    border-right-color: #ececec;
    box-shadow: 1px 2px 1px rgba(0, 0, 0, .072);
}

.e_tips {
    margin: 0 0 10px 0;
}

.e_item {
    display: inline-block;
    margin-right: 10px;
    width: 180px;
    overflow: hidden
}

.e_avatar {
    display: inline-block;
    margin-right: 5px;
}

.e_info {
    display: inline-block;
    vertical-align: top;
}

.e_info_name {
    font-size: 14px;
    color: #0066cc;
    width: 130px;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    overflow: hidden;
}

.e_info_name:hover {
    cursor: pointer;
    text-decoration: underline;
}

.e_info_aff {
    font-size: 12px;
    width: 130px;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    overflow: hidden;
}

.toolbar {
    font-size: 13px;
    color: #9fa19f;
    margin-right: 20px;
}

.sort-container {
    float: right;
}

.sort-item {
    font-size: 12px;
    line-height: 25px;
    min-width: 70px;
}

.sort-item:hover {
    cursor: pointer;
}

.button-sort {
    border: none;
    padding: 0;
    font-size: 12px;
}

.page {
    margin-left: 240px;
    text-align: center;
}

.no_result_tip {
    width: 500px;
    text-align: left;
    margin: 50px auto;
}

.no_result_tip_warning {
    /*text-align:center;*/
    position: relative;
    right: 3px;
    color: #2c3e50;
    font-size: 40px;
    font-weight: bold;
    margin-bottom: 0;
}

.hot_title {
    margin-bottom: 10px;
    font-size: 12px;
    line-height: 20px;
}

.hot_title:hover {
    color: #0066cc;
    text-decoration-line: underline;
    cursor: pointer;
}

.keyword_list {
    transition: background-color .1s;
    float: left;
    cursor: pointer;
    display: block;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    max-width: 230px;
    margin: 0 10px 10px 0;
    padding: 4px 10px;
    border: 2px solid #0066cc;
    text-decoration: none;
    font-size: 14px;
    pointer-events: all;
}

.keyword_list:hover {
    background-color: #f1f1f1;
}
</style><style>
.el-popover {
    min-width: 70px;
    padding: 2px 12px 2px 12px;
    box-shadow: none;
}

.el-dialog__body {
    padding: 20px 20px 10px;
}
</style>
