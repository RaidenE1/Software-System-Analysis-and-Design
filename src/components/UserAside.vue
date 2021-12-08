<template>
<el-aside style="width: 200px">
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
        <el-menu-item index="/personalInfo" @click="gotoPersonalInfo">
            <i class="el-icon-user"></i>
            <div class="title">个人信息</div>
        </el-menu-item>
        <el-menu-item index="/myAchievement" @click="gotoMyAchievement">
            <i class="el-icon-user"></i>
            <div class="title">我的成果</div>
        </el-menu-item>
        <el-menu-item index="/myFollow" @click="gotoMyFollow">
            <i class="el-icon-collection-tag"></i>
            <div class="title">我的关注</div>
        </el-menu-item>
        <el-menu-item index="/moments" @click="gotoMoments">
            <i class="el-icon-collection-tag"></i>
            <div class="title">学术动态</div>
        </el-menu-item>
        <el-menu-item index="/myCollection" @click="gotoMyCollection">
            <i class="el-icon-star-off"></i>
            <div class="title">我的收藏</div>
        </el-menu-item>
        <el-menu-item index="/myHistory" @click="gotoMyHistory">
            <i class="el-icon-view"></i>
            <div class="title">浏览历史</div>
        </el-menu-item>
    </el-menu>
</el-aside>
</template>

<script>
export default {
    name: "userAside",
    data() {
        return {

        }
    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        gotoPersonalInfo() {
            this.$router.push({
                path: '/personalInfo'
            });
        },
        gotoMyAchievement() {
            this.$router.push({
                path: '/myAchievement'
            })
        },
        gotoMyCollection() {
            this.$router.push({
                path: '/myCollection'
            });
        },
        gotoMyFollow() {
            this.$router.push({
                path: '/myFollow'
            });
        },
        gotoMoments() {
            this.$router.push({
                path: '/moments'
            })
        },
        gotoMyHistory() {
            this.$router.push({
                path: '/myHistory'
            });
        }
    }

}
</script>

<style lang="scss">
body {
    margin-left: 0;
}
.el-aside{
    overflow: hidden;
    margin-left:0;
}
.el-menu-item {
    font-size: 20px;
    height: 72px;
    line-height: 72px;

    span {
        width:90px;
        text-align: justify;
        text-align-last: justify;
    }
    .title {
        display: inline-block;
        width:90px;
        text-align: justify;
        text-align-last: justify;        
        }
}
</style>
