<template>
<div class='person' @click="goScholar">
    <el-row>
        <!-- <el-col :span=5 class="head">
            <el-avatar :size="50" :src="show_info.avatar_url"></el-avatar>
        </el-col> -->
        <el-col :span=19>
            <el-row class='name'>
                <span>{{show_info.name}}</span>
            </el-row>
            <el-row class="expertid">
                <span>id:{{show_info.expertId}}</span>
            </el-row>
        </el-col>
        <el-col :span=4>
            <div class="operatesub">
                <el-button v-if="is_subscribe" type="info" @click="handleFocus($event)"><i class="el-icon-finished"></i>取消关注</el-button>
                <el-button v-else type="primary" @click="handleFocus($event)"><i class="el-icon-plus"></i>&#8194;关&#12288;注&#8194;</el-button>

            </div>

        </el-col>
    </el-row>
</div>
</template>

<script>
export default {
    data() {
        return {
            circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            is_subscribe: true,
        }
    },
    props: {
        show_info: Object,

    },
    // computed: {
    //     showed_name: {
    //         get: function () {
    //             if (this.basic_info.real_name != '') {
    //                 if (this.basic_info.username != '') {
    //                     return this.basic_info.real_name + '(' + this.basic_info.username + ')';
    //                 } else {
    //                     return this.basic_info.real_name
    //                 }
    //             } else {
    //                 return this.basic_info.username;
    //             }
    //         }
    //     }
    // },
    methods: {
        handleFocus(event) {
            var _this = this
            this.$api.scholar.focusScholar({
                    scholar_id: _this.show_info.expertId,
                    user_id: sessionStorage.getItem("userID")
                }).then(res => {
                    if (Number(res.code) === 200) {
                        this.$message({
                            message: "取消关注成功",
                            type: 'success',
                            offset: 100,
                        });
                        this.is_subscribe=res.data;
                        // _this.follow_list.splice(index, 1);
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        
            event.stopPropagation();
        },
        goScholar() {
            this.$router.push({
                name: "ScholarPage",
                params: {
                    expertid: this.show_info.expertId
                }
            })
        }

    }
}
</script>

<style lang="scss" scoped>
.person {
    margin-top: 2px;
    margin-bottom: 2px;
    padding-bottom: 5px;
    background-color: #ffffff;
    width: 920px;
    margin-left: 20px;
    height: 72px;
    padding-left: 0px;
    padding-top: 3px;
    padding-bottom: 3px;
    padding-right: 0px;

    .name {
        text-align: left;
        height: 48px;
        margin-left: 20px;

        span {
            line-height: 32px;
            font-size: 18px;
        }
    }

    .expertid {
        text-align: left;
        height: 20px;
        margin-left: 20px;

        span {
            font-size: 14px;
            line-height: 16px;
            color: #aeaeae;

        }
    }

    .operatesub {
        height: 72px;
        line-height: 72px;
        margin-left: 20px;

        button {
            width: 80%;
        }
    }
}

.person:hover {
    background-color: #e0e0e0;
    box-shadow: 1px 2px 3px rgba(48, 48, 48, 0.6);
    transition: box-shadow .2s, background-color .2s;
}
</style>
