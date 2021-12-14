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
                <el-row v-for="(item,index) in collection_list" :key="index">
                    <div>
                        <span class="collection-dir" @click="goArticle(item.id)">
                            <span class="subtitle">
                                {{item.title}}
                            </span>
                        </span>
                        <div class="collect-detail-right">
                            <i class="el-icon-star-on" style="font-size:25px;color:orange;cursor:pointer" @click="delCollection(index)"></i>
                        </div>
                    </div>
                </el-row>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import userinfo from "@/components/user/Information.vue"
export default {
    components: {
        navbar,
        userheader,
        userinfo
    },
    mounted() {
        var _this = this
        this.$api.user.getCollectionList({
            userID: sessionStorage.getItem("userID")
        }).then(res => {
            if (Number(res.code) === 200) {
                _this.collection_list = res.data;
            } else {
                _this.$message.error(res.msg);
            }
        })
    },
    data() {
        return {
            collection_list: []
        };
    },
    methods: {
        delCollection(index) {
            var _this = this
            this.$api.academic.favorSc({
                document_id: _this.collection_list[index].id,
                user_id: sessionStorage.getItem("userID"),
                token: sessionStorage.getItem("token")
            }).then(res => {
                if (Number(res.code) === 200) {
                    _this.$message("取消收藏成功");
                    _this.collection_list.splice(index, 1);
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
