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
                <span>我的收藏</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="content" v-if="collection_list.length==0">
                <span class="emptycontent">暂无收藏</span>
            </div>
            <div class="content" v-else>
                <infinity-scroll :list='collection_list' :component="'./user/Collection.vue'">
                </infinity-scroll>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import userinfo from "@/components/user/Information.vue"
import InfinityScroll from '../../components/InfinityScroll.vue'
export default {
    components: {
        navbar,
        userheader,
        userinfo,
        InfinityScroll
    },
    mounted() {
        if(sessionStorage.getItem("userID")==null){
            this.$router.replace({
                path:'/'
            })
            return;
        }
        var _this = this
        this.$api.user.getCollectionList({
            userID: sessionStorage.getItem("userID")
        }).then(res => {
            if (Number(res.code) === 200) {
                _this.collection_list = res.data;
                console.log(res)
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
            collection_list: []
        };
    },
    methods: {
        delCollection(id) {
            this.$api.academic.favorSc({
                document_id: id,
                user_id: sessionStorage.getItem("userID"),
                token: sessionStorage.getItem("token")
            }).then(res => {
                if (Number(res.code) === 200) {
                    this.$message({
                        message: "取消收藏成功",
                        type: 'success',
                        offset: 100,
                    });
                    for (var i = 0; i < this.collection_list.length; i++) {
                        if (this.collection_list[i].id == id) {
                            this.collection_list.splice(i, 1);
                            break;
                        }
                    }
                }
            })
        },
        goArticle(id) {
            this.$router.push({
                path: "/academicShow/" + id,
            })
        }
    }
}
</script>

<style lang="scss">
@import "@/css/user.scss"
</style>
