<template>
    <div>
        <Header>
        </Header>
        <el-container>
            <Aside>
            </Aside>
            <el-main>
                <div style="text-align:left;font-size:20px">浏览历史</div>
                <ul class="collection-sublist">
                    <li v-for="(item,index) in history_list" :key="index">
                        <div>
                            <div class="collect-detail-left">
                                {{item.time}}
                            </div>
                            <span class="collection-dir" @click="goArticle(item.id)"> 
                                <span class="subtitle">
                                    {{item.title}}
                                </span>                          
                            </span>
                            <div class="collect-detail-right">
                                <el-button class="delHistory" @click="delHistory(index)">删除</el-button>
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
        name: "MyHistory",
        components: {
            Header,
            Aside,
        },
        mounted() {
            this.history_list = JSON.parse(localStorage.getItem(sessionStorage.getItem("userID")));
            console.log(this.history_list);
        },
        data() {
            return {
                history_list:[
                    {
                        id:1,
                        title:"title1",
                        time:"2020.12.5"
                    },
                    {
                        id:2,
                        title:"title2",
                        time:"2020.12.4"
                    },
                    {
                        id:3,
                        title:"title3",
                        time:"2020.12.3",
                    },
                    {
                        id:4,
                        title:"title4",
                        time:"2020.12.2"
                    }                               
                ]                
            };
        },
        methods: {
             delHistory(index) {
                this.$message('删除成功');
                this.history_list.splice(index,1);
                localStorage.setItem(sessionStorage.getItem("userID"),JSON.stringify(this.history_list));
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
    .collection-sublist li .collect-detail-left {
        position: absolute;
        left: 10px;
        width: 100px;
        height: 100%;
        top: 0px;
        text-align: left;
        line-height: 52px;
        padding-right: 18px;
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
    .delHistory {
        border: 1px solid #999;
        color: white;
        background-color: red;
        border-radius: 16px;
    }
    .delHistory:hover {
        border: 1px solid red;
        color: red;
        background-color: #fff;
        border-radius: 16px;
    }
</style>
