<template>
<div>
    <navbar :status="2" class="header_home"></navbar>
    <div class="banner" id="marked1">
        <div class="container">
            <!-- <vue-particles color="#909399" :clickEffect="true"  :particlesNumber="40">
      </vue-particles> -->
            <el-image :src="require('@/assets/logo/HKW.png')" class="logo" :underline="false">
            </el-image>
            <div class="row" id="row1">
                <div class="col-md-2 advance">
                    <el-button v-popover:popover type="text" class="h_button" icon="el-icon-caret-bottom">高级搜索</el-button>
                    <el-popover ref="popover" placement="bottom" width="400" :offset="170" title="高级搜索" trigger="click">
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
                                <el-input v-model="search_words.origin" placeholder="输入来源"></el-input>
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
                            <div class="ad_button_div">
                                <el-button class="advance_button" @click="goSearch(true)">搜索</el-button>
                            </div>
                        </el-form>
                    </el-popover>
                </div>
                <div class="col-md-8">
                    <div class="header-text caption">
                        <h2></h2>
                        <div id="search-section">
                            <div class="searchText">
                                <input type="text" v-model="search_words.searchWords" @keyup.enter="goSearch(false)" class="searchText" placeholder="请输入查找内容...">
                            </div>
                            <span @click="goSearch(false)"><input type="submit" name="results" class="main-button" value="快速检索文献"></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="top_block">
                <el-row>
                    <div class="top_title">
                        快捷搜索
                    </div>
                </el-row>
                <el-row class="top_card" :gutter="10">
                    <el-col :span="6" v-for="o in this.swList" :key="o.index">
                        <el-card class="card" shadow="always" @click.native="goFastSearch(o.searchWords)">{{o.searchWords}}</el-card>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
    <div ref='box' id='blank'></div>
    <div class="middle_block" id="marked2">
        <el-row>
            <el-col :span='21'>
                <div class="left_block">
                    <h4>热门文献</h4>
                    <el-row>
                        <el-col :span='12'>
                            <div class="list_left">
                                <el-row class="list_item" v-for="(o, index) in this.result_list.slice(0, 5)" :key="index">
                                    <el-image :src="require('@/assets/home_icon/Num-'+(index+1)+'.png')" class="num-icon-ach"></el-image>
                                    <el-link class="list_title" :underline="false" @click="goArticle(o.id)">{{ o.title }}</el-link>
                                    <span class="list_cited" :underline="false">{{o.cited_quantity}}</span>
                                    <br>
                                    <span class="list_author" :underline="false">{{o.time}} {{o.experts}}</span>
                                </el-row>
                            </div>
                        </el-col>
                        <el-col :span='12'>
                            <div class="list_right">
                                <el-row class="list_item" v-for="(o, index) in this.result_list.slice(5, 10)" :key="index">
                                    <el-image :src="require('@/assets/home_icon/Num-'+(index+6)+'.png')" class="num-icon-ach"></el-image>
                                    <el-link class="list_title" :underline="false" @click="goArticle(o.id)">{{ o.title }}</el-link>
                                    <span class="list_cited" :underline="false">{{o.cited_quantity}}</span>
                                    <br>
                                    <span class="list_author" :underline="false">{{o.time}} {{o.experts}}</span>
                                </el-row>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </el-col>

            <el-col :span='3'>
                <div class="right_block">
                    <h4>热门关键字</h4>
                    <el-row class="keyword_list" v-for="(o, index) in this.hotKeywords" :key="index">
                        <el-image :src="require('@/assets/home_icon/Num-'+(index+1)+'.png')" class="num-icon-kw"></el-image>
                        <el-link class="keyword" :underline="false" @click="goKwSearch(o.keyword)">{{o.keyword}}</el-link>
                        <span class="keyword_views">{{o.view}}</span>
                    </el-row>
                </div>
            </el-col>
        </el-row>
    </div>
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="sub-footer">
                        <p>group 17 works</p>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</div>
</template>

<script>
import navbar from "@/components/header";
export default {
    name: "homePage",
    data() {
        return {
            scrollstatus: 0,
            // url:require('@/assets/home_icon/Num-1.png'),
            result_list: [],
            result_length: 0,
            swList: [{
                    searchWords: '计算机'
                },
                {
                    searchWords: '人工智能'
                },
                {
                    searchWords: '医学'
                },
                {
                    searchWords: '航空航天'
                },
            ],
            hot_keywords: [
                /*'计算机',
                '人工智能',
                '航空',
                '深度学习',
                '合成生物学',
                '糖尿病',
                '航天',
                '新冠',
                '疫情',*/
            ],
            hotKeywords: [],
            search_words: {
                searchWords: '',
                title: '',
                keyWords: '',
                experts: '',
                origin: '',
                startTime: 0,
                endTime: 0,
            }
        }
    },
    methods: {
        goSearch(isAdvanced) {
            if (!isAdvanced) {
                if (this.search_words.searchWords !== '') {
                    this.$router.push({
                        name: "AcademicSearch",
                        params: {
                            search_words: encodeURIComponent(JSON.stringify(this.search_words))
                        }
                    })
                } else {
                    alert("搜索内容为空")
                }
            } else {
                if (this.search_words.searchWords === '' &&
                    this.search_words.title === '' &&
                    this.search_words.keyWords === '' &&
                    this.search_words.experts === '' &&
                    this.search_words.origin === '' &&
                    this.search_words.startTime === 0 &&
                    this.search_words.endTime === 0) {
                    alert("搜索内容为空")
                } else {
                    // alert(this.search_words.startTime)
                    this.$router.push({
                        name: "AcademicSearch",
                        params: {
                            search_words: encodeURIComponent(JSON.stringify(this.search_words)),
                        }
                    })
                }
            }
        },
        goKwSearch(kw) {
            // alert(kw)
            this.search_words.keyWords = kw
            this.goSearch(true)
        },
        goFastSearch(kw) {
            // alert(kw)
            this.search_words.searchWords = kw
            this.goSearch(false)
        },
        goArticle(id) {
            window.open(this.$router.resolve('academicShow/' + id).href)
        },
        // autoScroll() {
            // var _this = this
            // let top = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
            // let windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
            // console.log('wh='+windowHeight)
            // let total = 0;
            // console.log(this.scrollstatus)
            // console.log('top' + top)
            // if (this.scrollstatus == 0) {
            //     if (top > 200) {
            //         let total = document.getElementById('blank').offsetTop;
            //         console.log('tot' + total)
            //         let step = (total - top) / 50;
            //         smoothDown(step, total)

            //             _this.scrollstatus = 1
                    // this.scrollstatus = 1
                // }
            // }
            // console.log('tot'+total)
            // else {
            //     if (top < 200) {
            //         let newTotal = top
            //         let total=0
            //         let step = newTotal / 50;
            //         smoothUp(step,total)
            //         this.scrollstatus = 0
            //     }
            // }

            // function smoothDown(step, total) {
            //     console.log('step ' + step + ' total ' + total)
            //     while (top < total) {
            //         top = top + step
            //         console.log('newtop is' + top)
            //         document.body.scrollTop = top
            //         document.documentElement.scrollTop = top
            //         window.pageYOffset = total;

            //     }
                // if (top < total) {
                //     top = top + step
                //     console.log('newtop is' + top)
                //     document.body.scrollTop = top
                //     document.documentElement.scrollTop = top
                //     window.pageYOffset = total;
                //     setTimeout(smoothDown(step, total, _this), 50)
                // } else {
                //     document.body.scrollTop = total
                //     document.documentElement.scrollTop = total
                //     window.pageYOffset = total;
                //     _this.scrollstatus = 1
                // }
            // }

            // function smoothUp(step,total) {
            //     if (top > total) {
            //         top -= step
            //         document.body.scrollTop = top
            //         document.documentElement.scrollTop = top
            //         window.pageYOffset = total;
            //         setTimeout(smoothUp, 20)
            //     } else {
            //         document.body.scrollTop = total
            //         document.documentElement.scrollTop = total
            //         window.pageYOffset = total;
            //     }
            // }

        // }
    },
    components: {
        navbar,
    },
    mounted() {
        // window.addEventListener('scroll', this.autoScroll, true)
        let _this = this;
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
            userID: sessionStorage.getItem("userID")
        }).then(res => {
            if (res.code === "200") {
                // alert(200)
                _this.result_list = res.data.result_list;
                // _this.filter_list = res.data.filter_list;
                // _this.total_rs = res.data.total;
                _this.result_length = _this.result_list.length;
                // console.log(_this.result_list[0])
                for (let i = 0; i < 10; i++) {

                    _this.result_list[i].time = _this.result_list[i].time.slice(0, 4)
                    _this.result_list[i].experts = _this.result_list[i].experts.replaceAll(',', ' ')
                    // console.log(_this.result_list[i].time)
                }
            } else {
                // this.$message({
                //   message: res.msg,
                //   type: "error"
                // })
                // console.log("Request => getSearchResult : not 200");
            }
        })
        this.$api.academic.getHotKeywords({
            code: 1
        }).then(res => {
            if (res.code === "200") {
                // alert(200)
                this.hotKeywords = res.data.slice(0, 10);
                for (var i = 0; i < this.hotKeywords.length; i++) {
                    this.hotKeywords[i].keyword = this.hotKeywords[i].keyword.split("；")[0];
                }
                console.log(res.data)
                console.log(this.hotKeywords)
            } else {
                // this.$message({
                //   message: res.msg,
                //   type: "error"
                // })
                // console.log("Request => getSearchResult : not 200");
            }
        })
    },
    // destroyed() {
    //     window.removeEventListener('scroll', this.autoScroll)
    // }
}
</script>

<style src='../vendor/bootstrap/css/bootstrap.min.css' scoped></style>
<style src='../assets/css/homepage.css' scoped></style><style scoped>
.banner {
    height: 100vh;
}

.background {
    position: fixed;
    background-image: url("../assets/img/homepage_img/banner-bg.jpg");
    top: 0;
    left: 0;
    z-index: -10;
    background-size: cover;
    width: 100%;
    height: 100%;
}

#row1 {
    margin-top: 80px;
}

.logo {
    position: absolute;
    top: 140px;
    left: calc((100% - 600px)/2);
    width: 600px;
    font-size: 70px;
}

.search {
    display: flex;
    padding: 20px;
    ;
    left: 15%;
    width: 70%;
}

.search>>>.el-input__inner {
    border-radius: 0 0 0 0;
    border-width: 2px 0px 2px 0px;
    border-color: #245cc0;
    font-size: 18px;
    height: 60px;
}

.search>>>.el-input__prefix .el-input__icon {
    margin-left: 5px;
    font-size: 18px;
}

.search>>>.el-input-group__append {
    border-width: 0;
    background: rgba(0, 0, 0, 0);
}

.search>>>.el-input-group__append {
    border-radius: 0 25px 25px 0;
    /*border-width: 2px;*/
    background: #245cc0;
    height: 60px;
    width: 80px;
    font-size: 18px;
    color: white;
}

.search>>>.el-input-group__prepend {
    border-radius: 25px 0 0 25px;
    border-color: #245cc0 grey #245cc0 #245cc0;
    border-width: 2px;
    border-style: solid dotted solid solid;
    width: 70px;
    font-size: 16px;
    background: white;
    /*right: 100%;*/
}

.top_block {
    /*background: blue;*/
    margin: -8px;
    padding: 0;
    position: absolute;
    display: table;
    top: 550px;
    left: calc((100% - 900px)/2);
    /*right: 10%;*/
    width: 900px;
    height: 20%;
}

.top_block .el-row {
    width: 100%;
}

.top_title {
    position: absolute;
    left: 20px;
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 20px;
    color: #FFF;
}

.top_card {
    top: 40px;
    font-weight: bold;
    font-size: 18px;
}

.card {
    margin: 5px;
    width: 95%;
    height: 30%;
    cursor: pointer;
    background-color: #fff;
    color: #00bcd4;
    border: 4px solid #00bcd4;
}

.card :hover {
    background-color: #cccccc;
}

.middle_block {
    /*background: red;*/
    margin: 8px;
    padding: 0;
    position: relative;
    top: 75%;
    /*max-width: 1000px;*/
    left: calc((100% - 1000px)/2);
    width: 800px;
    height: 100%;
    min-height: 400px;
    /*display: flex;*/
}

.left_block {
    /*background: black;*/
    /*margin: -8px;*/
    padding: 0;
    /*top: 80%;*/
    left: 5%;
    /*max-width: 1200px;*/
    margin-bottom: 40px;
}

.left_block h4 {
    /* background: #005cd9; */
    padding: 20px;
    text-align: left;
    top: -25px;
    width: 20%;
    left: 10px;
}

.left_block .el-row {
    top: 5%;
}

.list_left {
    /*float: left;*/
    /*background-color: black;*/
    text-align: left;
    /* max-width: 400px; */
    height: 70%;
}

.list_right {
    /*float: left;*/
    /*background-color: red;*/
    text-align: left;
    /* max-width: 400px; */
    height: 70%;

}

.num-icon-ach {
    width: 40px;
    height: 40px;
    top: 26px;
}

.list_title {
    /*background: #8c939d;*/
    /* position: absolute; */
    font-size: 15px;
    font-weight: bold;
    color: #555666;
    height: 18px;
    width: 200px;
    left: 50px;
    top: -16px;
    display: -webkit-box;
    /*作为弹性伸缩盒子模型显示*/
    -webkit-line-clamp: 1;
    /*显示的行数；如果要设置2行加...则设置为2*/
    overflow: hidden;
    /*超出的文本隐藏*/
    text-overflow: ellipsis;
    /* 溢出用省略号*/
    /* white-space: nowrap; */
    -webkit-box-orient: vertical
}

.list_author {
    /* background: #9fa19f; */
    position: absolute;
    color: grey;
    width: 160px;
    left: 52px;
    top: 55px;
    font-size: 14px;
    display: -webkit-box;
    /*作为弹性伸缩盒子模型显示*/
    -webkit-line-clamp: 1;
    /*显示的行数；如果要设置2行加...则设置为2*/
    overflow: hidden;
    /*超出的文本隐藏*/
    text-overflow: ellipsis;
    /* 溢出用省略号*/
    /* white-space: nowrap; */
    -webkit-box-orient: vertical
}

.list_cited {
    font-size: 15px;
    color: #005cd9;
    position: absolute;
    left: 300px;
    top: 29px;
}

.right_block {
    /* background: blue; */
    /*margin: -8px;*/
    padding: 0;
    top: -25px;
    /*margin-left: 90%;*/
    margin-bottom: 150px;
}

.right_block h4 {
    /* top: -25px; */
    padding: 20px 0;
    width: 120px;
    left: 45px;
}

.num-icon-kw {
    width: 30px;
    height: 30px;
    top: 10px;
}

.keyword_list {
    height: 45px;
    text-align: left;
    /* //width: 200px; */

}

.keyword {
    font-size: 15px;
    color: black;
    /* //width: 10px; */
    top: -20px;
    left: 40px;
    width: 180px;
    display: -webkit-box;
    /*作为弹性伸缩盒子模型显示*/
    -webkit-line-clamp: 1;
    /*显示的行数；如果要设置2行加...则设置为2*/
    overflow: hidden;
    /*超出的文本隐藏*/
    text-overflow: ellipsis;
    /* 溢出用省略号*/
    /* //white-space: nowrap; */
    -webkit-box-orient: vertical
}

.keyword_views {
    position: relative;
    font-size: 15px;
    color: #245cc0;
    text-align: left;
    left: 220px;
    top: -40px;
}

@media screen and (min-width: 1400px) {
    .middle_block {
        left: calc((100% - 1200px)/2);
        width: 1000px;
    }

    .top_block {
        left: calc((100% - 1000px)/2);
        width: 1000px;
    }

    .left_block h4 {
        width: 20%;
        left: 70px;
    }

    .list_left {
        width: 300px;
        left: 200px;
    }

    .list_right {
        width: 300px;
        left: 650px;
    }

    .list_title {
        /*background: #8c939d;*/
        /* position: absolute;  */
        font-size: 15px;
        /*height: 20px;*/
        width: 250px;
        left: 50px;
        top: -16px;
        display: -webkit-box;
        /*作为弹性伸缩盒子模型显示*/
        -webkit-line-clamp: 1;
        /*显示的行数；如果要设置2行加...则设置为2*/
        overflow: hidden;
        /*超出的文本隐藏*/
        text-overflow: ellipsis;
        /* 溢出用省略号*/
        /* //white-space: nowrap; */
        -webkit-box-orient: vertical
    }

    .list_author {
        width: 200px;
    }

    .right_block {
        top: -25px;
        float: left;
        left: 950px;
        width: 200px;
        height: 100%;
    }
}

.ad_button_div {
    display: flex;
    justify-content: center;
    padding-bottom: 30px;
}

.advance_button {
    background-color: #00bcd4;
    color: #fff;
}
</style>
