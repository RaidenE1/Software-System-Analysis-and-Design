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
import Aside from "@/components/UserHeader.vue";
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
</style>
