<template>
<div id="userinfo">
    <div id="user">
        <el-row>
            <el-col :span=3>
                <div class="avatar">
                    <el-avatar :size="100" :src="circleUrl"></el-avatar>
                    <el-avatar :size="100" id="hoveravatar">修改头像</el-avatar>
                </div>
            </el-col>
            <el-col :span=12 id="nameandintro">
                <el-row id="name">
                    <span id="username">{{perInfo.userName}}</span>
                </el-row>
                <el-row id="intro">
                    <span id="introduction">{{perInfo.introduction}}</span>
                </el-row>
            </el-col>
            <el-col :span=3>
              <span></span>
            </el-col>
            <el-col :span=3>
            </el-col>
            <el-col :span=3>
            </el-col>
        </el-row>
    </div>
</div>
</template>

<script>
export default {
    // props: {
    //   userinfo: Object,
    // },
    data() {
        return {
            circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            perInfo: {
                userID: '',
                userName: 'Taka',
                realName: '',
                email: '',
                phoneNum: '',
                url: '',
                introduction: 'my introduction',
            }
        };
    }
}
</script>

<style lang="scss">
#userinfo {
    //background-image: url();
    border: 1px solid #e0e0e0;
    
    width: 1000px;
    margin: 0 auto;
    background: #ffffff;
    padding-bottom:10px;
    padding-top: 100px;
    margin-bottom:12px;

    .avatar {
        .el-avatar {
            border: 5px #ffffff;
        }

        #hoveravatar {
            position: absolute;
            left: 12.5px;
            opacity: 0;
            transition: opacity .2s, background-color .2s;

        }

        #hoveravatar:hover {
            opacity: 1;
            background-color: rgba(0, 0, 0, 0.4);
        }
    }

    #nameandintro {
        text-align: left;
        margin-left: 5px;

        #username {
            font-size: 26px;
        }

        #introduction {
            font-size: 18px;
        }
    }

}
</style>
