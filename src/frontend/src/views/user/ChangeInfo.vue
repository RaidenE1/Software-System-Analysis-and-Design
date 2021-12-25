<template>
<div class="background">
    <navbar>
    </navbar>
    <div class="allcontainer">
        <div class="allcontent">
            <div class="avatarcontainer">
                <el-avatar :size="150" :src="perInfo.url" class="realavatar"></el-avatar>
                <el-upload class="avatar-uploader" action="http://124.70.63.71:8082/user/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <el-avatar :size="150" class="hoveravatar"><span>更改头像</span></el-avatar>
                </el-upload>
            </div>
            <div class="formcontainer">
                <el-form label-width="90px" id="infoform">
                    <!-- <el-form-item label="用户名">
                        <el-input type="text" v-model="perInfo.nickName"></el-input>
                    </el-form-item> -->
                    <!-- <el-form-item label="隶属单位">
                                <el-input type="text" v-model="user_info.organazition"></el-input>
                            </el-form-item> -->
                    <el-form-item label="真实姓名">
                        <el-input type="text" v-model="perInfo.realName"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="学历">
                                <el-input type="text" v-model="user_info.education"></el-input>
                            </el-form-item> -->
                    <!-- <el-form-item label="研究领域">
                                <el-input type="text" v-model="user_info.field"></el-input>
                            </el-form-item> -->
                    <el-form-item label="邮箱">
                        <el-input type="email" v-model="perInfo.email" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="电话">
                        <el-input type="text" v-model="perInfo.phoneNum" auto-complete="off"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="个人简介">
                        <el-input type="textarea" v-model="perInfo.introduction" :rows="4"></el-input>
                    </el-form-item> -->
                </el-form>

                <el-button type="primary" @click="preserveInfo">保&#12288;存</el-button>

            </div>
            <div class="returnbutton">
                <el-button @click="returnback" type="text">
                    <span><i class="el-icon-d-arrow-left"></i>返回个人主页</span>
                </el-button>
            </div>
        </div>

    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue";
export default {
    name: "PerInfo",
    components: {
        navbar,
    },
    mounted() {
        if(sessionStorage.getItem("userID")==null){
            this.$router.replace({
                path:'/'
            })
            return;
        }
        
        var _this = this
        this.$api.user.getPerInfo({
            userID: sessionStorage.getItem("userID"),
        }).then(res => {
            if (Number(res.code) === 200) {
                _this.perInfo.nickName = res.data.nickName;
                _this.perInfo.realName = res.data.realName;
                _this.perInfo.email = res.data.email;
                _this.perInfo.phoneNum = res.data.phoneNum;
                _this.perInfo.url = res.data.url||'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';
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
            perInfo: {
                userID: '',
                nickName: '',
                realName: '',
                email: '',
                phoneNum: '',
                url: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
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
                    sessionStorage.setItem("userAvatar", this.perInfo.url)
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
        preserveInfo() {
            const emailPattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            const phoneNumPattern = /^1[3|4|5|7|8][0-9]{9}$/;
            if (!this.perInfo.realName || !this.perInfo.email || !this.perInfo.phoneNum) {
                this.$message({
                    message: '信息不能为空',
                    type: 'warning',
                    offset: 100,
                });
            } else if (!emailPattern.test(this.perInfo.email)) {
                this.$message({
                    message: '请输入正确的邮箱',
                    type: 'warning',
                    offset: 100,
                });
            } else if (!phoneNumPattern.test(this.perInfo.phoneNum)) {
                this.$message({
                    message: '请输入正确的电话号码',
                    type: 'warning',
                    offset: 100,
                });
            } else {
                var _this = this
                this.$api.user.changeInfo({
                    userID: sessionStorage.getItem("userID"),
                    nickName: _this.perInfo.nickName,
                    realName: _this.perInfo.realName,
                    email: _this.perInfo.email,
                    phoneNum: _this.perInfo.phoneNum
                }).then(res => {
                    if (Number(res.code) === 200) {
                        this.$message({
                            message: "信息修改成功",
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
            }
        },

        returnback() {
            this.$router.push({
                path: '/myAchievement'
            })
        }
    }
}
</script>

<style lang="scss">
.allcontainer {
    text-align: center;
    margin-top: 130px;
}

.allcontent {
    border: 1px solid #e0e0e0;
    width: 1000px;
    margin: 0 auto;
    background: #ffffff;
    padding-top: 50px;
    padding-bottom: 50px;
    display: flex;

    .avatarcontainer {
        display: inline-block;
        position: relative;
        height: 160px;
        width: 160px;

        .realavatar {
            position: absolute;
            left: 30px;
            border: 2px solid rgba(255, 255, 255, 100);
        }

        .avatar-uploader {
            left: 30px;
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
                    font-size: 18px;
                }
            }
        }
    }

    .formcontainer {
        margin-left: 60px;
        width: 560px;
        text-align: center;

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
            width: 400px;
        }

        .el-form-item__content::before {
            content: " ";
            width: 20px;
            font-weight: 600;
            display: inline-block;
            margin-right: 10px;
        }

        .el-button {
            width: 120px;
            height: 44px;
            margin: 0 auto;
            font-size: 20px;
        }
    }

    .returnbutton {
        margin-left: 85px;
        margin-top: -45px;
    }
}

.pw {
    margin-top: 0%;
    margin-left: 40%;
    width: 600px;
    height: 300px;
}

.table {
    height: 50px;
    width: 500px;
}

.font {
    font-size: 28px;
    text-align: center;
    font-family: "Microsoft YaHei";
}
</style>
