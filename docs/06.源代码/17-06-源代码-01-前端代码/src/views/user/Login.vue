<template>
<div class="full">
    <el-menu router :default-active="$route.path" class="el-menu-demo" mode="horizontal">
        <el-menu-item style="font-weight: bold" @click="goHome()">Hui Ke</el-menu-item>
    </el-menu>
    <el-form class="login-container" label-position="left" label-width="0px" v-loading="loading" style="margin-top: 7%">
        <h3 class="login_title">登录</h3>
        <el-form-item prop="account">
            <el-input type="text" v-model="user.userName" auto-complete="off" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
            <el-input type="password" v-model="user.passwd" auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <br>
        <el-form-item style="width: 100%">
            <el-button type="primary" @click="submit" style="width: 50%; background-color: #00bcd4;">登录</el-button>
        </el-form-item>
        <el-button type="text" @click="gotoRegister">没有账号?立即注册</el-button>
        <!--    <router-link :to="{path:'/register'}" >还没有账号?快去注册</router-link>-->
    </el-form>
</div>
</template>

<script>
export default {
    data() {
        return {
            checked: true,
            user: {
                userName: '',
                passwd: ''
            },
            loading: false,

        }
    },
    methods: {
        submit() {
            var encryptionPasswd = this.$md5(this.user.passwd);
            //        alert(encryptionPasswd);
            //         if(sessionStorage.getItem("userName")!=null||sessionStorage.getItem("userID")!=null){
            //           // alert("您已登录");
            //           this.$message({
            //             message: '您已登录',
            //             type: 'warning'
            //           });
            //           this.$router.push("/");
            //         }
            //         else {
            var _this = this
            this.$api.user.postLoginForm({
                userName: _this.user.userName,
                passwd: encryptionPasswd
            }).then(res => {
                if (Number(res.code) === 200) {
                    sessionStorage.setItem("userName", _this.user.userName);
                    sessionStorage.setItem("userID", res.data.userID);
                    sessionStorage.setItem("token", res.data.token);
                    console.log(res.data.token);
                    sessionStorage.setItem("is_admin", res.data.is_admin);
                    _this.$router.push("/");
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error',
                        offset: 100,
                    });
                }
            })
            // }
        },
        gotoRegister() {
            this.$router.push("/register");
        },
        goHome() {
            this.$router.push('/');
        },
    }
}
</script>

<style scoped>
.login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

.login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
}

.login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
}

body {
    margin: 0;
    padding: 0;
}

.full {
    background: url("../../assets/img/homepage_img/heading-bg.jpg");
    width: 100%;
    height: 100%;
    position: fixed;
    background-size: 100% 100%;
}
</style>
