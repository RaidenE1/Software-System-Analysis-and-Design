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
                <infinityscroll :list="achievement" :component="'Achievement'">
                </infinityscroll>
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
    
    components: {
        navbar,
        userheader,
        userinfo,
        infinityscroll
    },
    data() {
        return {
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
                },{
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
                },{
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

    },

}
</script>

<style lang="scss">
@import "@/css/user.scss";
</style>
