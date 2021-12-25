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
                <span>我的关注</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="content" v-if="follow.length==0">
                <span class="emptycontent">暂无关注</span>
            </div>
            <div class="content" v-else>
                <infinityscroll :list="follow" :component="'./user/Person.vue'"></infinityscroll>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue";
import userheader from "@/components/UserHeader.vue";
import userinfo from "@/components/user/Information.vue";
import infinityscroll from "@/components/InfinityScroll.vue"
export default {
    name: "MyFollow",
    components: {
        navbar,
        userheader,
        infinityscroll,
        userinfo,
    },
    mounted() {
        if(sessionStorage.getItem("userID")==null){
            this.$router.replace({
                path:'/'
            })
            return;
        }
        var _this = this
        this.$api.user.getFollowList({
            userID: sessionStorage.getItem("userID")
        }).then(res => {
            if (Number(res.code) === 200) {
                _this.follow = res.data;
                console.log(res.data);
            } else {
                this.$message({
                    message: res.msg,
                    type: 'error',
                    offset: 100,
                });
            }
        })
    },
    data() {
        return {
            follow: [
                // {
                //                     expertId: '1111',
                //                     name: 'ggg',

                //                 },
                //                 {
                //                     expertId: '1222',
                //                     name: 'fff',
                //                 }
            ],
        };
    },
    methods: {

    }
}
</script>

<style lang="scss">
@import "@/css/user.scss"
</style>
