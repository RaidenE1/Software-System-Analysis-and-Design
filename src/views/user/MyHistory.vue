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
                <span class="emptycontent">暂无历史记录</span>
            </div>
            <div class="content" v-else>
                <infinityscroll :list="history_list" :component="'./user/History.vue'">
                </infinityscroll>
                <!-- <el-row v-for="(item,index) in history_to_show" :key="index">
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
                </el-row> -->
            </div>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import userinfo from "@/components/user/Information.vue"

import infinityscroll from "@/components/InfinityScroll.vue"
export default {
    name: "MyHistory",
    components: {
        navbar,
        userheader,
        userinfo,
        infinityscroll
    },
    mounted() {
        this.history_list = JSON.parse(localStorage.getItem(sessionStorage.getItem("userID")));
        if (this.history_list == null) {
            this.history_list = [];
        }
        console.log(this.history_list);
    },
    data() {
        return {
            current_page: 1,
            page_size: 15,
            focus: '',
            history_list: [],
            /* history_list: [{
                      id: 1,
                      title: "title1",
                      time: "2020.12.5",
                      h_id: 1,
                  },
                  {
                      id: 2,
                      title: "title2",
                      time: "2020.12.4",
                      h_id:2,
                  },
                  {
                      id: 3,
                      title: "title3",
                      time: "2020.12.3",
                      h_id:3,
                  },
                  {
                      id: 4,
                      title: "title4",
                      time: "2020.12.2",
                      h_id:4,
                  }
              ]*/
        };
    },
    methods: {
        delHistory(h_id) {
            this.$message({
                message: '删除成功',
                type: 'success',
                offset: 100,
            });
            for (var i = 0; i < this.history_list.length; i++) {
                if (this.history_list[i].h_id == h_id) {
                    this.history_list.splice(i, 1);
                    break;
                }
            }
            localStorage.setItem(sessionStorage.getItem("userID"), JSON.stringify(this.history_list));
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
