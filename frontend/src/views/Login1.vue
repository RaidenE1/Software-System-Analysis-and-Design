<template>
    <div class="login_container">
        <div class="login_box">
            <h1 class="title">问卷星球</h1>
            <el-form :model="loginForm" label-width="80px" class="login_form">
                <div class="inputbox" >
                  <div class="inputText">
                        <span>
                            <i class="el-icon-user"></i>
                        </span>
                    <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名" style="width: 300px;height: 30px;"/>
					</div>
                  <div class="inputText">
                    <span>
                            <i class="el-icon-lock"></i></span>
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" style="width: 300px;height: 30px;">
                        </el-input>
                  </div>
                  <el-button class="inputButton" @click="login" type="primary">登录</el-button>
                  <div class="sign_up">
                    还没有账号？ <a @click="register">立即注册</a>
                  </div>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            loginForm: {
                username: '',
                password: '',
                userid: '',
                email: ''
            }
        };
    },
	methods: {
		async login() {
				if (this.loginForm.username === '' || this.loginForm.password === '') {
					this.$message.error('账号或密码不能为空');
				}
				else {
					const { data: res } = await this.$http.post("/login", this.loginForm);//这里传数据需要注意，把userid、email也传进去了
					if (!res.success) return this.$message.error('登录失败');

					this.$message.success('登录成功');
					this.loginForm.userid = res.message.userid;
					this.loginForm.email = res.message.email;
					sessionStorage.setItem('userInfo', JSON.stringify(this.loginForm));					
					this.$router.push('/manage');
				}
		},
		register() {
			this.$router.push('/register');
		}
	}
};
</script>

<style src="../assets/css/home_css/newhome.css" scoped></style>
