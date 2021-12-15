<template>
<div class="full">
    <el-menu router :default-active="$route.path" class="el-menu-demo" mode="horizontal">
        <el-menu-item style="font-weight: bold">Hui Ke</el-menu-item>
    </el-menu>
    <el-form class="Register-container" label-position="left" label-width="80px" v-loading="loading">
        <h3 class="Register_title">注册</h3>
        <el-form-item prop="account" label="用户名">
            <el-input type="text" v-model="RegisterForm.userName" auto-complete="off" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass" label="密码">
            <el-input type="password" v-model="RegisterForm.passwd1" auto-complete="off" placeholder="请输入您的密码"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
            <el-input type="password" v-model="RegisterForm.passwd2" auto-complete="off" placeholder="请重复输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass" label="邮箱">
            <el-input type="email" v-model="RegisterForm.email" auto-complete="off" placeholder="请输入您的邮箱" style="float:left;width: 160px"></el-input>
            <el-button type="primary" v-show="show" style="float: right;font-size: 5px;width: 95px; background-color: #00bcd4;" @click="sendEmail">发送验证码</el-button>
            <el-button type="primary" v-show="!show" style="float: right;font-size: 5px;width: 95px; background-color: #00bcd4;" disabled="disabled">{{count}}</el-button>
        </el-form-item>
        <el-form-item prop="checkPass" label="验证码">
            <el-input type="text" v-model="RegisterForm.code" auto-complete="off" placeholder="请输入验证码"></el-input>
        </el-form-item>
        <br>
        <el-form-item style="width: 80%">
            <el-button type="primary" @click="submit" style="width: 100%; background-color: #00bcd4;">注册</el-button>
        </el-form-item>
    </el-form>
</div>
</template>

<script>
export default {
    data() {
        return {
            checked: true,
            RegisterForm: {
                userName: '',
                passwd1: '',
                passwd2: '',
                email: '',
                code: '',
            },
            show: true,
            count: '',
            timer: null,
            verification: '',
            loading: false
        }
    },
    methods: {
        submit() {
            const emailPattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            if (sessionStorage.getItem("userName") != null || sessionStorage.getItem("userID") != null) {
                // alert("您已登录");
                this.$message({
                    message: '您已登录',
                    type: 'warning',
                    offset: 100,
                });
                this.$router.push("/");
            } else if (!this.RegisterForm.userName) {
                this.$message({
                    message: '用户名不能为空',
                    type: 'warning',
                    offset: 100,
                });
            } else if (!this.RegisterForm.passwd1) {
                this.$message({
                    message: '密码不能为空',
                    type: 'warning',
                    offset: 100,
                });
            } else if (this.RegisterForm.passwd1 !== this.RegisterForm.passwd2) {
                // alert("两次密码不一致，请重新输入");
                this.$message({
                    message: '两次密码不一致，请重新输入',
                    type: 'warning',
                    offset: 100,
                });
            } else if (!emailPattern.test(this.RegisterForm.email)) {
                this.$message({
                    message: '请输入正确的邮箱',
                    type: 'warning',
                    offset: 100,
                });
            } else if (!this.RegisterForm.code) {
                this.$message({
                    message: '请输入验证码',
                    type: 'warning',
                    offset: 100,
                });
            } else if (this.verification !== this.RegisterForm.code) {
                this.$message({
                    message: '验证码错误',
                    type: 'warning',
                    offset: 100,
                });
            } else {
                var encryptionPasswd = this.$md5(this.RegisterForm.passwd1)
                var _this = this
                this.$api.user.postRegisterForm({
                    userName: _this.RegisterForm.userName,
                    passwd: encryptionPasswd,
                    email: _this.RegisterForm.email,
                    code: _this.RegisterForm.code
                }).then(res => {
                    if (Number(res.code) === 200) {
                        sessionStorage.setItem("userName", _this.RegisterForm.userName);
                        sessionStorage.setItem("userID", res.data.userID);
                        this.$message({
                            message: "注册成功",
                            type: 'success',
                            offset: 100,
                        });
                        _this.$router.push("/login");
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
        sendEmail() {
            const emailPattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            if (!emailPattern.test(this.RegisterForm.email)) {
                this.$message({
                    message: '请输入正确的邮箱',
                    type: 'warning',
                    offset: 100,
                });
            } else {
                var _this = this
                const TIME_COUNT = 60
                if (!this.timer) {
                    this.$api.user.sendEmail({
                        email: _this.RegisterForm.email
                    }).then(res => {
                        if (Number(res.code) === 200) {
                            _this.verification = res.data.verification
                            //console.log(_this.verification)
                            this.$message({
                                message: '已发送',
                                type: 'success',
                                offset: 100,
                            });
                        } else {
                            this.$message({
                                message: "发送失败",
                                type: 'error',
                                offset: 100,
                            });
                        }
                    })
                    this.count = TIME_COUNT;
                    this.show = false;
                    this.timer = setInterval(() => {
                        if (this.count > 0 && this.count <= TIME_COUNT) {
                            this.count--;
                        } else {
                            this.show = true;
                            clearInterval(this.timer);
                            this.timer = null;
                        }
                    }, 1000)
                }
            }
        }
    }
}
</script>

<style scoped>
.Register-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 100px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

.Register_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
}

.Register_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
}

.full {
    background: url("../../assets/img/homepage_img/heading-bg.jpg");
    width: 100%;
    height: 100%;
    position: fixed;
    background-size: 100% 100%;
}
</style>
