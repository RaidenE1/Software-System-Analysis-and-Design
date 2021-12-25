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
                <span>门户信息</span>
            </div>
            <el-divider>
            </el-divider>
            <div class="content" v-if="name==''">
                <span class="emptycontent">未认领门户</span>
            </div>
            <div class="expertinfo" v-if="name!=''" @click="gotoScholar">
                <div class="p-name">{{ this.name }}</div>
                <div class="p-scholarID">
                    <div class="p-scholarID-all c-grey">
                        <span class="p-scholarID-id">
                            {{ this.scholar_id }}
                        </span>
                    </div>
                </div>
                <div class="p-affiliate">{{ this.affiliate }}</div>
            </div>
            <el-divider v-if="name!=''">
            </el-divider>
            <div class="content" v-if="name!=''">
                <div class="subtitile">
                    学术成果
                </div>
                <el-divider>
                </el-divider>
                <div class="emptylist" v-if="this.achievement.length==0">
                    暂无学术成果
                </div>
                <div v-else>
                    <infinityscroll :list="achievement" :component="'./user/Achievement.vue'">
                    </infinityscroll>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import userinfo from "@/components/user/Information.vue"
import infinityscroll from "@/components/InfinityScroll.vue"
export default {

    components: {
        navbar,
        userheader,
        userinfo,
        infinityscroll
    },
    data() {
        return {
            name: '',
            affiliate: '',
            scholar_id: '',
            achievement: [
            ],
        }
    },
    methods: {
        getAchievement() {
            this.$api.user.getAchievement({
                userId: sessionStorage.getItem("userID")
            }).then(res => {
                if (Number(res.code) === 200) {
                    this.name = res.data.name;
                    this.scholar_id = res.data.scholar_id;
                    this.affiliate = res.data.affiliate;
                    this.achievement = res.data.achList;
                    console.log(res)
                }
            })
        },
        gotoScholar(){
            this.$router.push({
                path:'/scholarPage/'+this.scholar_id
            })
        }
    },
    mounted() {
        if(sessionStorage.getItem("userID")==null){
            this.$router.replace({
                path:'/'
            })
            return;
        }
        this.getAchievement()
    }

}
</script>

<style lang="scss">
@import "@/css/user.scss";
.expertinfo{
    height: 80px;
    width:94%;
    margin-left:3%;
    padding-top:15px;
}
.expertinfo:hover{
    background-color:#e0e0e0;
    transition:background-color .2s;
}

.p-name {
    display: inline-block;
    text-align: left;
    width: 620px;
    font-size: 20px;
    line-height: 30px;
    color: #333;
    margin-bottom: 8px;
}

.p-scholarID {
    display: inline-block;
    height: 24px;
    background-color: #fafafa;
    padding: 3px;
}

.p-scholarID-all {
    /*width: 190px;*/
    width: 260px;
    height: 22px;
    border: 1px solid #E6E6E6;
    background-color: #fafafa;
    color: #999;
    font-size: 13px;
    text-align: center;
    line-height: 24px;
}

.p-scholarID-id {
    font-size: 13px;
    color: #06c;
}

.p-affiliate {
    text-align: left;
    margin-left: 25px;
}

.c-grey {
    color: #999;
}

.subtitile {
    text-align: left;
    font-size: 22px;
    font-weight: 400;
    margin-top:15px;
    margin-bottom: 15px;
    margin-left:30px;
}

.emptylist{
    font-size: 18px;
    font-weight:400px;
    margin-top:15px;
}
</style>
