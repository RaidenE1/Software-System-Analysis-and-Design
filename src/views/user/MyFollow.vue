<template>
    <div>
        <Header>
        </Header>
        <el-container>
            <Aside>
            </Aside>
            <el-main>
                <div style="text-align:left;font-size:20px">我的关注</div>
                <ul class="collection-sublist">
                    <li v-for="(item,index) in follow_list" :key="index">
                        <div>
                            <span class="collection-dir" @click="goScholar(item.expertId)">
                                <span class="subtitle">
                                    {{item.name}}
                                </span>                                
                            </span>
                            <div class="collect-detail-right">
                                <el-button class="delFollow" @click="delFollow(index)">取消关注</el-button>
                            </div>
                        </div>
                    </li>
                </ul>
            </el-main>
        </el-container>
    </div>
</template>
<script>
    import Header from "@/components/header.vue";
    import Aside from "@/components/UserAside.vue";
    export default {
        name: "MyFollow",
        components: {
            Header,
            Aside,
        },
        mounted() {
            var _this = this
            this.$api.user.getFollowList({
                userID : sessionStorage.getItem("userID")
            }).then(res=>{
                if(Number(res.code) === 200) {
                    _this.follow_list = res.data;
                     console.log(res.data);
                }
                else {
                    _this.$message.error(res.msg);
                }
            })
        },
        data() {
            return {
                follow_list:[]
            };
        },
        methods: {
            delFollow(index) {
                var _this = this
                this.$api.scholar.focusScholar({
                    scholar_id : _this.follow_list[index].expertId,
                    user_id : sessionStorage.getItem("userID")
                }).then(res=>{
                    if(Number(res.code) === 200){
                        _this.$message("取消关注成功");
                        _this.follow_list.splice(index,1);
                    }
                })
            },
            goScholar(id) {
                this.$router.push({
                    name:"ScholarPage",
                    params:{
                        expertid:id
                    }
                })
            }  
        }
    }
</script>

<style scoped>
    .collection-sublist {
        margin-top: 8px;
        margin-bottom: -17px;
        padding: 0 16px;
    }
    .collection-sublist li {
        padding: 0;
        position: relative;
        margin-top: 0;
        background: #fff;
        border: 1px solid #ededed;
        border-radius: 4px;
        margin-bottom: 8px;
        height: 52px;
        overflow: hidden;
        line-height: 52px;
    }
    .collection-sublist li .collection-dir {
        width: 89%;
        font-size: 14px!important;
        color: #555666;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .collection-sublist li .subtitle {
        font-family: Helvetica!important;
        color: #555666;
        display: inline;
        cursor: pointer;
    }
    .collection-sublist li .subtitle:hover {
        font-family: Helvetica!important;
        color: red;
        display: inline;
        cursor: pointer;
    }
    .collection-sublist li .collect-detail-right {
        position: absolute;
        right: 0;
        width: 150px;
        height: 100%;
        top: 0px;
        text-align: right;
        line-height: 52px;
        padding-right: 18px;
    }
    .delFollow {
        border: 1px solid #999;
        color: #999;
        background-color: #fff;
        border-radius: 16px;
    }
    .delFollow:hover {
        border: 1px solid red;
        color: red;
        background-color: #fff;
        border-radius: 16px;
    }
</style>
