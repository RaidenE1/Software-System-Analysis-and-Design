<template>
<div id="usercenter">
    <div id="left">
        <div id="basicinfo">
            <div id="headcontainer">
                <div class="avatar">
                    <el-avatar :size="120" :src="circleUrl"></el-avatar>
                </div>
            </div>
            <div id="info">
                <div>
                    <el-row type="flex" justify="left" class="singleinfo">用户名:{{ user_info.username }}</el-row>
                    <el-row type="flex" justify="left" class="singleinfo">邮箱:{{ user_info.email }}</el-row>
                    <el-row type="flex" justify="left" class="singleinfo">隶属单位:{{ user_info.organazition }}</el-row>
                    <el-row type="flex" justify="left" class="singleinfo">真实姓名:{{ user_info.real_name }}</el-row>
                    <el-row type="flex" justify="left" class="singleinfo">学历:{{ user_info.education }}</el-row>
                    <el-row type="flex" justify="left" class="singleinfo">研究领域:{{ user_info.field }}</el-row>
                </div>
            </div>
            <div id="interaction">
                <el-button @click="subscribe" type="text" size="medium"><span><i class="el-icon-plus"></i>关注</span></el-button>
                <div id="space">
                </div>
                <el-button @click="contact" type="text" size="medium"><span><i class="el-icon-chat-dot-square"></i>联系</span></el-button>
            </div>
        </div>
        <div id="introduction">
            <el-row type="flex" justify="left" id="introtitle">简介:</el-row>
            <el-row id="introcontent">
                <p>{{user_info.introduction}}</p>
            </el-row>
        </div>
        <div id="achievements">
            <div class="achievementcontent">
                <span id="achievementsheader">学术成果</span>
            </div>
            <div class="achievementcontent" v-if="user_info.achievement.length==0">
            </div>
            <div class="achievementcontent" v-else>
                <el-row v-for="(achievement, i) in user_achievement" v-bind:key="i">
                    <achievement :achievement="achievement"></achievement>
                </el-row>
                <el-row>
                    <el-button @click="seeMoreAchievement()" type="text" class="seemore">
                        <div v-if="show_more_achievement">收起<br><i class="el-icon-arrow-up"></i></div>
                        <div v-else>更多<br><i class="el-icon-arrow-down"></i></div>
                    </el-button>
                </el-row>
            </div>
        </div>
    </div>
    <div id="right">
        <div id="associateperson">
            <div class="associatetitle">
                <el-row>
                    <el-col :span=18 class="mainastitle">相关人员</el-col>
                    <el-col :span=3>
                        共{{associate_person.length}}人
                    </el-col>
                    <el-col :span=3>
                        <el-button @click="viewAllAsPerson" type="text">查看全部</el-button>
                    </el-col>
                </el-row>
            </div>
            <div class="nodata" v-if="associate_person.length==0">
                <span>没有数据</span>
            </div>
            <div v-else>
                <el-row v-for="(person, i) in associate_person_to_show" v-bind:key="i">
                    <person :basic_info="person"></person>
                </el-row>
            </div>
        </div>
        <div id="associateachievement">
            <div class="associatetitle">
                <el-row>
                    <el-col :span=21 class="mainastitle">相关学术成果</el-col>
                    <el-col :span=3>
                        <el-button @click="viewAllAsPerson" type="text">查看全部</el-button>
                    </el-col>
                </el-row>
            </div>
            <div class="nodata" v-if="associate_person.length==0">
                <span>没有数据</span>
            </div>
            <div v-else>
                <el-row v-for="(achievement, i) in associate_achievement_to_show" v-bind:key="i">
                    <narrowachievement :achievement="achievement"></narrowachievement>
                </el-row>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import person from "../components/user/PersonBasic.vue";
import achievement from "../components/user/AchievementBasic.vue";
import narrowachievement from "../components/user/AchievementBasicNarrow.vue"
export default {
    components: {
        person,
        achievement,
        narrowachievement
    },
    data() {
        return {
            circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            show_more_achievement: false,
            user_info: {
                username: "takanashi",
                email: "takanashi@xx.com",
                organazition: "BUAA",
                real_name: "GJX",
                education: "NONE",
                field: "Computer",
                introduction: "xxxx\nbbbbcccc",
                achievement: [{
                        title: "aa",
                        date: "2020.03.01",
                    },
                    {
                        title: "bb",
                        date: "2021.05.02",
                    },
                    {
                        title: "aa",
                        date: "2020.03.01",
                    },
                    {
                        title: "bb",
                        date: "2021.05.02",
                    },
                    {
                        title: "aa",
                        date: "2020.03.01",
                    },
                    {
                        title: "bb",
                        date: "2021.05.02",
                    },
                    {
                        title: "aa",
                        date: "2020.03.01",
                    },
                    {
                        title: "bb",
                        date: "2021.05.02",
                    },
                ],
            },
            associate_person: [{
                    head: "",
                    username: "tanama",
                },
                {
                    head: "",
                    username: "kouji",
                },
            ],
            associate_achievement: [{
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
        user_achievement: {
            get: function () {
                if (this.user_info.achievement.length < 5) {
                    return this.user_info.achievement;
                } else {
                    if (this.show_more_achievement == true) {
                        return this.user_info.achievement;
                    } else {
                        let achievements = [];
                        for (var i = 0; i < 5; i++) {
                            achievements.push(this.user_info.achievement[i]);
                        }
                        return achievements;
                    }
                }
            },
        },
        associate_person_to_show: {
            get: function () {
                if (this.associate_person.length < 7) {
                    return this.associate_person;
                } else {
                    let person = [];
                    for (var i = 0; i < 7; i++) {
                        person.push(this.associate_person[i]);
                    }
                    return person;
                }
            }
        },
        associate_achievement_to_show: {
            get: function () {
                if (this.associate_achievement.length < 5) {
                    return this.associate_achievement;
                } else {
                    let achievements = [];
                    for (var i = 0; i < 7; i++) {
                        achievements.push(this.associate_person[i]);
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
        subscribe() {}
    },
};
</script>

<style lang="scss" scoped>
#usercenter {
    max-width: 1840px;
}

#left {
    border: 1px dashed black;
    float: left;
    width: 50%;
    margin-left: 10%;
    display: flex;
    flex-direction: column;

    #basicinfo {
        border: 1px solid brown;
        display: flex;
        flex-direction: row;
        position: relative;
        margin-bottom: 10px;

        #headcontainer {
            width: 30%;
            margin-left: 5%;
            margin-right: 5%;
            border: 1px solid gray;

            .avatar {
                width: 160px;
                height: 160px;
            }
        }

        #info {
            display: flex;
            flex-direction: row;
            width: 40%;

            .singleinfo {
                font-size: 20px;
            }
        }

        #interaction {
            display: flex;
            flex-direction: row;
            margin-right: 0px;
            width: 30%;

            span {
                font-size: 20px;
            }

            #space {
                margin-left: 10px;
                margin-right: 10px;
            }
        }
    }

    #introduction {
        margin-left: 20px;
        margin-right: 30px;

        #introtitle {
            font-size: 20px;
            line-height: 50px;
        }

        #introcontent {
            text-align: left;

            p {
                text-indent: 2em;
                white-space: nowrap;
            }
        }
    }

    #achievements {
        width: 100%;
        position: relative;
        margin-top: 100px;
        border: 1px solid black;

        .achievementcontent {
            width: 100%;
        }

        &header {
            margin-left: 10px;
            display: flex;
            font-size: 24px;
        }
    }
}

#right {
    float: left;
    width: 30%;
    margin-left: 5%;

    .associatetitle {
        margin-top: 10px;
        margin-bottom: 10px;
        line-height:42px;
        .mainastitle {

            font-size: 24px;
            text-align: left;
        }
    }

    .count {
        float: right;
        line-height: 30px;
    }

    #associateperson {
        min-height: 300px;
    }
}

.seemore {
    font-size: 20px;
}
</style>
