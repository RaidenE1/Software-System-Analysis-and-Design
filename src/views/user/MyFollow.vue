<template>
<div>
    <Header>
    </Header>
    <el-container>
        <Aside>
        </Aside>
        <div id="follow">
            <div id="followtitle">
                <span id="followheader">我的关注</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="followcontent" v-if="follow.length==0">
                暂无关注
            </div>
            <div class="followcontent" v-else>
                <el-row v-for="(person, i) in follow_to_show" v-bind:key="i">
                    <person :basic_info="person"></person>
                </el-row>
            </div>
            <el-divider>
            </el-divider>
            <el-pagination @current-change="handleCurrentChange" :current-page="current_page" :page-size="page_size" layout="total, prev, pager, next, jumper" :total="follow.length">
            </el-pagination>
        </div>
    </el-container>
</div>
</template>

<script>
import Header from "@/components/header.vue";
import Aside from "@/components/UserAside.vue";
import person from "@/components/user/PersonBasic.vue"
export default {
    name: "MyFollow",
    components: {
        Header,
        Aside,
        person,
    },
    mounted() {
        var _this = this
        this.$api.user.getFollowList({
            userID: sessionStorage.getItem("userID")
        }).then(res => {
            if (Number(res.code) === 200) {
                _this.follow = res.data;
                console.log(res.data);
            } else {
                _this.$message.error(res.msg);
            }
        })
    },
    data() {
        return {
            current_page: 1,
            page_size: 10,
            follow: [{
                    expertId: '1111',
                    name: 'ggg',
                },
                {
                    expertId: '1222',
                    name: 'fff',
                }
            ],
        };
    },
    computed: {
        follow_to_show: {
            get: function () {
                let person = [];
                for (var i = (this.current_page - 1) * this.page_size; i < this.follow.length && i < this.current_page * this.page_size; i++) {
                    person.push(this.follow[i])
                }
                return person;
            }
        },
    },
    methods: {
        handleCurrentChange(val) {
            this.current_page = val;
        },
        delFollow(index) {
            var _this = this
            this.$api.scholar.focusScholar({
                scholar_id: _this.follow_list[index].expertId,
                user_id: sessionStorage.getItem("userID")
            }).then(res => {
                if (Number(res.code) === 200) {
                    _this.$message("取消关注成功");
                    _this.follow_list.splice(index, 1);
                }
            })
        },
        goScholar(id) {
            this.$router.push({
                name: "ScholarPage",
                params: {
                    expertid: id
                }
            })
        }
    }
}
</script>

<style lang="scss">
#follow {
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    width: 1400px;
    margin-left: 5%;
    margin-top: 50px;
    padding-bottom: 50px;
    background: #fafafa;
    height: 750px;

    .el-divider.el-divider--horizontal {
        width: 96%;
        align-self: center;
        margin-left: 2%;
    }

    #followtitle {
        margin-top: 20px;
        margin-left: 2%;
        margin-bottom: 30px;
    }

    .followcontent {
        margin-left: 2%;
        width: 96%;
        height: 550px;
    }

    &header {
        margin-left: 10px;
        display: flex;
        font-size: 24px;
    }
}

/* }
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
    font-size: 14px !important;
    color: #555666;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
}

.collection-sublist li .subtitle {
    font-family: Helvetica !important;
    color: #555666;
    display: inline;
    cursor: pointer;
}

.collection-sublist li .subtitle:hover {
    font-family: Helvetica !important;
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
} */
</style>
