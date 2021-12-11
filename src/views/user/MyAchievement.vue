<template>
<div id="myachievement">
    <navbar>
    </navbar>
    <div class="container">
        <userinfo>
        </userinfo>
        <div id="achievements">
        <userheader>
        </userheader>
            <div id="achievementtitle">
                <span id="achievementsheader">学术成果</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="achievementcontent" v-if="achievement.length==0">
                暂无学术成果
            </div>
            <div class="achievementcontent" v-else>
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
#myachievement{
    background-color:#f3f3f3;
}
.container {
    text-align: center;
}
#achievements {
    // border: 1px solid #e0e0e0;
    border: 1px solid #e0e0e0;
    width: 1000px;
    margin: 0 auto;
    padding-bottom: 50px;
    background: #ffffff;
    .el-divider.el-divider--horizontal {
        width: 96%;
        align-self: center;
        margin-left: 2%;
    }
    #achievementtitle{
        margin-top:20px;
        margin-left: 2%;
        margin-bottom: 30px;
    }
    .achievementcontent {
        margin-left: 2%;
        width: 96%;
        height:550px;
    }

    &header {
        margin-left: 10px;
        display: flex;
        font-size: 24px;
    }
}
</style>
