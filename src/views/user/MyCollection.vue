<template>
    <div>
        <Header>
        </Header>
        <el-container>
            <Aside>
            </Aside>
            <el-main>
                <div style="text-align:left;font-size:20px">我的收藏</div>
                <ul class="collection-sublist">
                    <li v-for="(item,index) in collection_list" :key="index">
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
                    </li>
                </ul>
            </el-main>
        </el-container>
    </div>
</template>
<script>
    import Header from "@/components/header.vue";
    import Aside from "@/components/UserHeader.vue";
    export default {
        name: "MyCollection",
        components: {
            Header,
            Aside,
        },
        mounted() {
            var _this = this
            this.$api.user.getCollectionList({
                userID : sessionStorage.getItem("userID")
            }).then(res=>{
                if(Number(res.code) === 200) {
                    _this.collection_list = res.data;
                }
                else {
                    _this.$message.error(res.msg);
                }
            })
        },
        data() {
            return {
                collection_list:[]
            };
        },
        methods: {
            delCollection(index) {
                var _this = this
                this.$api.academic.favorSc({
                    document_id : _this.collection_list[index].id,
                    user_id : sessionStorage.getItem("userID"),
                    token : sessionStorage.getItem("token")
                }).then(res=>{
                    if(Number(res.code) === 200){
                        _this.$message("取消收藏成功");
                        _this.collection_list.splice(index,1);
                    }
                })
            },
            goArticle(id){
                this.$router.push({
                    path:"/academicShow/"+id,
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
        top: 3px;
        text-align: right;
        line-height: 52px;
        padding-right: 18px;
    }
</style>
