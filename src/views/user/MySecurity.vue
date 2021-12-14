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
                <span>安全设置</span>
            </div>
            <el-divider>
            </el-divider>
            <el-collapse v-model="activeName">
                <el-collapse-item title="修改密码" name="1">
                    <div class="formcontainer">
                        <el-form label-position="left" label-width="90px" v-loading="false">
                            <el-form-item label="旧密码">
                                <el-input type="password" v-model="perInfo.oldPasswd" auto-complete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="新密码">
                                <el-input type="password" v-model="perInfo.passwd1" auto-complete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码">
                                <el-input type="password" v-model="perInfo.passwd2" auto-complete="off"></el-input>
                            </el-form-item>
                        </el-form>
                        <div>
                            <el-button class="savebutton" type="primary" @click="preservePasswd">保存</el-button>
                        </div>
                    </div>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import userheader from "@/components/UserHeader.vue"
import userinfo from "@/components/user/Information.vue"
export default {

    components: {
        navbar,
        userheader,
        userinfo,
    },
    data() {
        return {
            activeName: '',
            perInfo: {
                oldPasswd: '',
                passwd1: '',
                passwd2: ''
            }
        };
    },
    methods: {

        preservePasswd() {
            //this.activeName = "";
            if (!this.perInfo.oldPasswd || !this.perInfo.passwd1) {
                this.$message({
                    message: '密码不能为空',
                    type: 'warning'
                });
            } else if (this.perInfo.passwd1 !== this.perInfo.passwd2) {
                this.$message({
                    message: '两次密码不一致，请重新输入',
                    type: 'warning'
                });
            } else {
                var encryptionPasswd1 = this.$md5(this.perInfo.oldPasswd)
                var encryptionPasswd2 = this.$md5(this.perInfo.passwd1)
                var _this = this
                this.$api.user.changePasswd({
                    userID: sessionStorage.getItem("userID"),
                    oldPasswd: encryptionPasswd1,
                    newPasswd: encryptionPasswd2
                }).then(res => {
                    if (Number(res.code) === 200) {
                        _this.$message.success("密码修改成功");
                        this.activeName = '';
                    } else {
                        _this.$message.error(res.msg);
                    }
                })
            }
        },
    }

}
</script>

<style lang="scss">
@import "@/css/user.scss";

.maincontent {

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
        width: 500px;
    }

    .el-form-item__content::before {
        content: " ";
        width: 20px;
        font-weight: 600;
        display: inline-block;
        margin-right: 10px;
    }
    .savebutton {
    width: 120px;
    height: 44px;
    margin: 0 auto;
    font-size: 20px;
}

}

.out {
    padding-left: 50px;
    text-align: left;
}

.subtitle {
    text-align: left;
    font-size: 24px;
}

.formcontainer {
    margin-top: 30px;
    margin-left: 100px;
}

.el-collapse {
    width: 860px;
    margin-left: 70px;
    border: 0;

    .el-collapse-item__header {
        font-size: 24px;
    }
}
</style>
