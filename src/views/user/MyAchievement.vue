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
                <span class="emptycontent">暂无学术成果</span>
            </div>
            <div class="content" v-else>
                <infinityscroll :list="achievement" :component="'./user/Achievement.vue'">
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
            achievement: [
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
