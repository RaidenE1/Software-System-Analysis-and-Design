<template>
<div id="usercenter">
    <Navbar>
    </Navbar>
    <el-container>
        <div id="main">
        <div>
            <div id="basicinfo">
                <div id="headcontainer">
                    <div class="avatar">
                        <el-avatar :size="140" :src="circleUrl"></el-avatar>
                    </div>
                    <div id="changehead" v-if="changing_info">
                        <el-upload action="https://jsonplaceholder.typicode.com/posts/">
                            <el-button>更改头像</el-button>
                        </el-upload>
                    </div>

                    <div id="statistics" v-else>
                        <el-col :span=11>
                            <span class="number">{{user_info.follow_num}}</span><br>
                            <span class="label">粉丝</span>
                        </el-col>
                        <el-col :span=2>
                            <el-divider direction="vertical"></el-divider>
                        </el-col>
                        <el-col :span=11>
                            <span class="number">{{user_info.thumbup_num}}</span><br>
                            <span class="label">获赞</span>
                        </el-col>
                    </div>
                </div>
                <el-divider direction="vertical"></el-divider>
                <div id="info">
                    <div v-if="changing_info">
                        <el-form label-width="90px" id="infoform">
                            <el-form-item label="用户名">
                                <el-input type="text" v-model="user_info.username"></el-input>
                            </el-form-item>
                            <el-form-item label="隶属单位">
                                <el-input type="text" v-model="user_info.organazition"></el-input>
                            </el-form-item>
                            <el-form-item label="真实姓名">
                                <el-input type="text" v-model="user_info.real_name"></el-input>
                            </el-form-item>
                            <el-form-item label="学历">
                                <el-input type="text" v-model="user_info.education"></el-input>
                            </el-form-item>
                            <el-form-item label="研究领域">
                                <el-input type="text" v-model="user_info.field"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div v-else>
                        <el-form label-width="90px" id="infoform">
                            <el-form-item label="用户名">
                                <span>{{user_info.username}}</span>
                            </el-form-item>
                            <el-form-item label="隶属单位">
                                {{user_info.organazition}}
                            </el-form-item>
                            <el-form-item label="真实姓名">
                                {{user_info.real_name}}
                            </el-form-item>
                            <el-form-item label="学历">
                                {{user_info.education}}
                            </el-form-item>
                            <el-form-item label="研究领域">
                                {{user_info.field}}
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div id="interaction">
                    <div id="changeinfo">
                        <el-button v-if="changing_info" @click="saveInfo()" type="text" size="medium"><span><i class="el-icon-edit-outline"></i>保存</span></el-button>
                        <el-button v-else @click="changeInfo()" type="text" size="medium"><span><i class="el-icon-edit-outline"></i>修改</span></el-button>
                    </div>
                </div>
            </div>
            <el-divider></el-divider>
            <div id="introduction">
                <el-row type="flex" justify="left" id="introtitle">简&#12288;介</el-row>
                <el-row id="introcontent">
                    <span v-if="changing_info">
                        <el-input v-model="user_info.introduction" type="textarea" autosize></el-input>
                    </span>
                    <span v-else>{{user_info.introduction}}</span>
                </el-row>
            </div>
        </div>
        </div>
    </el-container>
</div>
</template>

<script>
import userheader from "@/components/UserHeader.vue"
import Navbar from "@/components/header.vue";
export default {
    components: {
        Navbar,
        userheader,
    },
    data() {
        return {
            circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",

            changing_info: false,
            user_info: {
                username: "takanashi",
                email: "takanashi@xx.com",
                organazition: "BUAA",
                real_name: "GJX",
                education: "NONE",
                field: "Computer",
                introduction: "xxxx\nbbbbcccc",
                follow_num: 0,
                thumbup_num: 0,
            },
            sub_person: [{
                    head: "",
                    username: "tanama",
                },
                {
                    head: "",
                    username: "kouji",
                },
            ],
            recent_view_achievement: [{
                    title: "cc",
                    date: "2020.02.21",
                },
                {
                    title: "dd",
                    date: "2021.06.04",
                },
            ],
        };
    },
    computed: {

        associate_achievement_to_show: {
            get: function () {
                if (this.recent_view_achievement.length < 5) {
                    return this.recent_view_achievement;
                } else {
                    let achievements = [];
                    for (var i = 0; i < 7; i++) {
                        achievements.push(this.recent_view_achievement[i]);
                    }
                    return achievements;
                }
            }
        }
    },
    methods: {
        seeMoreAchievement() {
            this.show_more_achievement = !this.show_more_achievement;
        },
        changeInfo() {
            this.changing_info = true;
        },
        saveInfo() {
            this.changing_info = false;
        }
        /*saveInfo() {
            this.$axios({
                    method: 'post',
                    url: "",
                    data: this.user_info.basic_info,
                })
                .then(response => {
                    if (response.data.res) {
                        this.changing_info = false;

                    } else {
                        console.log(response.data.message);
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
        },
        changeHead() {

        },
        getData() {
            this.getUserInfo();
            this.getSubPerson();
            this.getRecentView();
        },
        getUserInfo() {
            this.$axios({
                    method: "post",
                    url: '',
                    data: this.user_info.email,
                })
                .then(response => {
                    this.user_info = response.data.user_info;
                })
        },
        getSubPerson() {
            this.$axios({
                    method: "post",
                    url: '',
                    data: this.user_info.email,
                })
                .then(response => {
                    this.sub_person = response.data.sub_person;
                })
        },
        getRecentView() {
            this.$axios({
                    method: "post",
                    url: '',
                    data: this.user_info.email,
                })
                .then(response => {
                    this.user_info = response.data.user_info;
                })
        },

        viewAllAsPerson() {}
    },
    mounted() {
        this.getData();
    }*/
    }
}
</script>

<style lang="scss">
.el-container {
    text-align: center;
}
#main {
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    width: 1000px;
    margin: 0 auto;
    padding-bottom: 50px;
    background: #fafafa;
    height: 750px;

    #basicinfo {
        height: 350px;
        display: flex;
        flex-direction: row;
        margin-bottom: 10px;
        padding: 40px 20px 40px 40px;

        .el-divider--vertical {
            height: 300px;
            margin-right: 3%;
        }

        #headcontainer {
            align-items: center;
            width: 25%;
            margin-left: 5%;

            .avatar {
                width: 240px;
                height: 240px;
            }

            #changehead {
                width: 240px;
                margin-top: 20px;

                button {
                    font-size: 18px;
                }
            }

            #statistics {
                margin-top: 50px;
                width: 240px;

                .number {
                    font-size: 24px;
                }

                .el-divider--vertical {
                    height: 44px;
                }

                .label {
                    font-size: 16px;
                }
            }
        }

        #info {
            display: flex;
            flex-direction: row;
            width: 55%;

            .el-form-item {
                height: 42px;
            }

            .el-form-item__label {
                font-size: 18px;
                font-weight: 600;
                display: inline-block;
                text-align: justify;
                text-align-last: justify;
            }

            .el-form-item__content {
                font-size: 18px;
                white-space: nowrap;
                text-align: left;
            }

            .el-form-item__content::before {
                content: ":";
                font-size: 20px;
                font-weight: 600;
                display: inline-block;
                margin-right: 10px;
            }
        }

        #interaction {
            display: flex;
            flex-direction: row;
            margin-top: 10px;

            #changeinfo {
                margin-right: 0px;
            }

            span {
                font-size: 20px;
            }
        }
    }

    .el-divider.el-divider--horizontal {
        width: 92%;
        align-self: center;
        margin-left: 4%;
    }

    #introduction {
        margin-left: 5%;
        margin-right: 5%;

        #introtitle {
            font-size: 22px;
            font-weight: 600;
            line-height: 50px;
        }

        #introcontent {
            font-size: 18px;
            text-align: left;
            white-space: pre-wrap;

        }
    }

}

.seemore {
    font-size: 20px;
}
</style>
