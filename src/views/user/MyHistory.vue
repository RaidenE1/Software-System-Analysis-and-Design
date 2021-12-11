<template>
<div class="background">
    <navbar>
    </navbar>
    <div class="container">
        <userinfo>
        </userinfo>
        <div class="maincontent">
            <userheader>
            </userheader>
            <div class="pagetitle">
                <span>浏览历史</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="content" v-if="history_list.length==0">
                暂无历史记录
            </div>
            <div class="content" v-else>
                <el-row v-for="(item,index) in history_to_show" :key="index">
                    <div class="singlehistory" @click="goArticle(item.id)" @mouseenter="highlight(item.id)" @mouseleave="unhighlight()" v-bind:class="{focus : focus == item.id}">
                        <el-row>
                            <el-col :span=20>
                                <el-row class="singletitle">
                                    <span>
                                        {{item.title}}
                                    </span>
                                </el-row>
                                <el-row class="singletime">
                                    <span>
                                        上次浏览:{{item.time}}
                                    </span>
                                </el-row>
                            </el-col>
                            <el-col :span=4>
                                <div class="delbutton">
                                    <el-button @click="delHistory(index,$event)">删除</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-row>
            </div>
            <el-divider>
            </el-divider>
            <el-pagination @current-change="handleCurrentChange" :current-page="current_page" :page-size="page_size" layout="total, prev, pager, next, jumper" :total="history_list.length">
            </el-pagination>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import userinfo from "@/components/user/Information.vue"
export default {
    name: "MyHistory",
    components: {
        navbar,
        userheader,
        userinfo
    },
    mounted() {
        // this.history_list = JSON.parse(localStorage.getItem(sessionStorage.getItem("userID")));
        // if (this.history_list==null){
        //     this.history_list=[];
        // }
        // console.log(this.history_list);
        this.load();
        window.addEventListener('scroll', this.handleScroll, true)
    },
    data() {
        return {
            current_page: 1,
            page_size: 15,
            focus: '',
            history_list: [{
                    id: 1,
                    title: "title1",
                    time: "2020.12.5"
                },
                {
                    id: 2,
                    title: "title2",
                    time: "2020.12.4"
                },
                {
                    id: 3,
                    title: "title3",
                    time: "2020.12.3",
                },
                {
                    id: 4,
                    title: "title4",
                    time: "2020.12.2"
                }
            ]
        };
    },
    computed: {
        history_to_show() {
            let historys = [];
            for (var i = (this.current_page - 1) * this.page_size; i < this.history_list.length && i < this.current_page * this.page_size; i++) {
                historys.push(this.history_list[i])
            }
            return historys;
        }
    },
    methods: {
        delHistory(index, event) {
            this.$message('删除成功');
            this.history_list.splice(index, 1);
            localStorage.setItem(sessionStorage.getItem("userID"), JSON.stringify(this.history_list));
            event.stopPropagation();
        },
        goArticle(id) {
            this.$router.push({
                path: "/academicShow/" + id,
            })
        },
        highlight(id) {
            this.focus = id;
        },
        unhighlight() {
            this.focus = '';
        },
        handleCurrentChange(val) {
            this.current_page = val;
        }
    }
}
</script>

<style lang="scss">
@import "@/css/user.scss";

.singlehistory {
    border: 1px solid #99CCFF;
    margin-top: 2px;
    margin-bottom: 2px;
    height: auto;
    padding-left: 10px;
    padding-top: 3px;
    padding-bottom: 3px;
    padding-right: 10px;

    .singletitle {
        text-align: left;
        height: 36px;

        span {
            line-height: 36px;
            font-size: 24px;
        }
    }

    .singletime {
        text-align: left;
        height: 20px;

        span {
            font: 14px;
            line-height: 20px;
            color: #aeaeae;

        }
    }

    .delbutton {
        height: 56px;
        line-height: 56px;

        button {
            width: 80%;
        }
    }

}
</style>
