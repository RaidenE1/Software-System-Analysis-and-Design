<template>
<div id="userinfo">
    <div id="user">
        <div class="avatarcontainer">
            <el-avatar :size="100" :src="perInfo.url" class="realavatar"></el-avatar>
            <el-upload class="avatar-uploader" action="http://124.70.63.71:8082/user/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                <el-avatar :size="100" class="hoveravatar"><span>更改头像</span></el-avatar>
            </el-upload>
        </div>
        <div class="nameandintro">
            <el-row id="name">
                <span id="username">{{perInfo.userName}}</span>
            </el-row>
            <el-row id="intro">
                <span id="introduction">{{perInfo.introduction}}</span>
            </el-row>
        </div>
        <!-- <el-col :span=3>
            <span></span>
        </el-col>
        <el-col :span=3>
        </el-col> -->
        <div class="space">
        </div>
        <div class="buttoncontainer">
            <el-button @click="gotoChangeInfo">修改信息</el-button>
        </div>
    </div>
</div>
</template>

<script>
export default {
    // props: {
    //   userinfo: Object,
    // },
    mounted() {
        var _this = this
        this.$api.user.getPerInfo({
            userID: sessionStorage.getItem("userID"),
        }).then(res => {
            console.log(res)
            if (Number(res.code) === 200) {
                _this.perInfo.userID = sessionStorage.getItem("userID");
                _this.perInfo.userName = sessionStorage.getItem("userName");
                _this.perInfo.realName = res.data.realName;
                _this.perInfo.email = res.data.email;
                _this.perInfo.phoneNum = res.data.phoneNum;
                _this.perInfo.url = res.data.url;
                sessionStorage.setItem("userAvatar", _this.perInfo.url);
            } else {
                this.$message({
                    message: res.msg,
                    type: 'error',
                    offset: 100,
                });
            }
        })
    },
    data() {
        return {
            circleUrl: "",
            perInfo: {
                userID: sessionStorage.getItem("userID"),
                userName: sessionStorage.getItem("userName"),
                realName: '',
                email: '',
                phoneNum: '',
                url: sessionStorage.getItem("userAvatar"),
                introduction: '',
            }
        };
    },
    methods: {
        handleAvatarSuccess(res, file) {
            console.log(file);
            this.perInfo.url = res.data.url;
            var _this = this
            this.$api.user.changeImg({
                userID: sessionStorage.getItem("userID"),
                url: _this.perInfo.url
            }).then(res => {
                if (Number(res.code) === 200) {
                    this.$message({
                        message: "修改头像成功",
                        type: 'success',
                        offset: 100,
                    });
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error',
                        offset: 100,
                    });
                }
            })
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message({
                    message: '上传头像图片只能是 JPG 格式!',
                    type: 'error',
                    offset: 100,
                });
            }
            if (!isLt2M) {
                this.$message({
                    message: '上传头像图片大小不能超过 2MB!',
                    type: 'error',
                    offset: 100,
                });
            }
            return isJPG && isLt2M;
        },
        gotoChangeInfo() {
            this.$router.push({
                path: '/changeInfo'
            })
        }
    }
}
</script>

<style lang="scss">
#userinfo {
    background-image: url("../../assets/perInfoBackgroud1.png");
    background-size: 100%;
    border: 1px solid #e0e0e0;
    border-top: 0;
    height: 120px;
    width: 1000px;
    margin: 0 auto;
    padding-bottom: 10px;
    padding-top: 50px;
    margin-bottom: 12px;
    display: flex;

    #user {
        display: contents;

        .avatarcontainer {
            display: inline-block;
            position: relative;
            height: 120px;
            width: 120px;

            .realavatar {
                position: absolute;
                left: 20px;
                border: 2px solid rgba(255, 255, 255, 100);
            }

            .avatar-uploader {
                left: 20px;
                position: absolute;

                .el-upload {
                    //border:2px solid #fff;

                    border-radius: 50%;
                    cursor: pointer;
                    overflow: hidden;
                    opacity: 0;
                    background-color: rgba(0, 0, 0, 0);
                }

                .el-upload:hover {
                    background-color: rgba(0, 0, 0, 0.5);
                    opacity: 1;
                    transition: background-color .2s, opacity .2s;
                }

                .hoveravatar {
                    width: 100px;
                    height: 100px;
                    display: block;
                    background: rgba(0, 0, 0, 0);
                    border: 2px solid #fff;

                    span {
                        line-height: 100px;
                    }
                }
            }
        }

        .nameandintro {
            display: inline-block;
            width: 500px;
            text-align: left;
            margin-left: 25px;
            margin-top: 0px;

            #username {
                font-size: 26px;
            }

            #introduction {
                font-size: 18px;
            }
        }

        .space {
            width: 220px;
        }

        .buttoncontainer {
            display: inline-block;
        }
    }
}
</style>
