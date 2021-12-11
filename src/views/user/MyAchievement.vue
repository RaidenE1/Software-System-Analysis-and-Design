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
                <span>学术成果</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="content" v-if="achievement.length==0">
                暂无学术成果
            </div>
            <div class="content" v-else>
                <el-row v-for="(achievement, i) in user_achievement" v-bind:key="i">
                    <achievement :achievement="achievement"></achievement>
                </el-row>
            </div>
            <el-divider>
            </el-divider>
            <el-pagination @current-change="handleCurrentChange" :current-page="current_page" :page-size="page_size" layout="total, prev, pager, next, jumper" :total="achievement.length">
            </el-pagination>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import achievement from "@/components/user/AchievementBasic.vue"
import userinfo from "@/components/user/Information.vue"
export default {
    components: {
        navbar,
        userheader,
        achievement,
        userinfo
    },
    data() {
        return {
            current_page: 1,
            page_size: 6,
            achievement: [{
                    title: "aa",
                    date: "2020.03.01",
                },
                {
                    title: "bb",
                    date: "2021.05.02",
                },
                {
                    title: "aa",
                    date: "2020.03.01",
                },
                {
                    title: "bb",
                    date: "2021.05.02",
                },
                {
                    title: "aa",
                    date: "2020.03.01",
                },
                {
                    title: "bb",
                    date: "2021.05.02",
                },
                {
                    title: "aa",
                    date: "2020.03.01",
                },
                {
                    title: "bb",
                    date: "2021.05.02",
                },
            ],
        }
    },
    methods: {
        handleCurrentChange(val) {
            this.current_page=val;
            }
        },
    computed: {
        user_achievement: {
            get: function () {
                let achievements = [];
                for (var i = (this.current_page-1)*this.page_size; i < this.achievement.length&&i<this.current_page*this.page_size; i++) {
                    achievements.push(this.achievement[i]);
                }
                return achievements;
            }
        },
    },

}
</script>

<style lang="scss">
@import "@/css/user.scss";
</style>
