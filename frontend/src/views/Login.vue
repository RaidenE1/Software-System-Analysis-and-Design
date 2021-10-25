<template>
  <div class="login-container">
    <div class="logo-content">
      <span class="hello">Hello ，</span>
      <span class="tips">欢迎使用问卷星球！</span>
      <div class="login-block" v-if="formType == 'login'">
        <el-tabs
          v-model="loginType"
          class="login-form-tab"
        >
          <el-tab-pane label="账号密码登录" name="account">
            <el-form ref="accountLoginForm" :model="loginForm" class="account-login-form" hide-required-asterisk label-position="top" size="small" status-icon @keyup.enter.native="loginHandle">
              <el-form-item prop="account">
                <el-input
                  v-model="loginForm.username"
                  autocomplete="off"
                  placeholder="请输入用户名/邮箱"
                  prefix-icon="el-icon-user-solid"
                />
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="loginForm.password"
                  autocomplete="off"
                  placeholder="请输入密码"
                  prefix-icon="el-icon-lock"
                  show-password
                />
              </el-form-item>
              <el-form-item style="text-align:center;margin-top:30px">
                <el-button class="loginButton" type="primary" @click="loginHandle">登录</el-button>
              </el-form-item>
            </el-form>
            <el-link class="link-btn-left" @click="formType = 'reset'"
              >忘记密码？</el-link
            >
            <el-link class="link-btn-right" @click="formType = 'reg'"
              >立即注册</el-link
            >
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="reg-block" v-else-if="formType == 'reg'">
        <register
          @success="registerSuccessHandle"
        />
      </div>
      <div class="resetPsw-block" v-else>
        <resetPsw @success="resetSuccessHandle" style="padding-top: 30px;min-height: 420px"></resetPsw>
      </div>

      <p class="desc">
        关于问卷星球登录 <br/><br/>
        若登录出现问题，可通过如下邮箱联系制作人904231270@qq.com
      </p>
<!--      <p class="desc">-->
<!--        -->
<!--      </p>-->
    </div>
  </div>
</template>

<script>
import Register from "../components/user/Register.vue";
import ResetPsw from "../components/user/ResetPsw.vue";
export default {
  name: "Login",
  components: {
    Register,
    ResetPsw,
  },
  data() {
    return {
      formType: "login",
      loginType: "account",
      loginForm: {
        username: "",
        password: "",
      },
    };
  },
  mounted() {
      let a = 0
      console.log(!a === true)
  },
  computed: {
    enableWx() {
      return true;
    },
  },
  methods: {
    registerSuccessHandle() {
      this.formType = "login";
      this.loginType = "account";
    },
    resetSuccessHandle() {
      this.formType = "login";
      this.loginType = "account";
    },
    loginHandle() {
      if (Object.values(this.loginForm).some((item) => item === "")) {
        this.$message.warning("请填写所有必要信息");
        return;
      }
      console.log(this.loginForm);
      this.$axios
        .post("/api/login", {
          key: this.loginForm.username,
          password: this.loginForm.password,
        })
        .then((Response) => {
          console.log(Response);
          if (Response.data.status === 0) {
            this.$message.success("登录成功");
            this.$store.commit("login");
            this.$axios.get("/api/my_info").then((response) => {
              console.log(response.data.data.user.userID);
              this.$store.commit("setID", response.data.data.user.userID);
            });
            console.log(this.$store.state.voteUrl);
            if (this.$store.state.voteUrl != "") {
              let myurl = this.$store.state.voteUrl;
              this.$store.commit("resetUrl");
              this.$router.push({
                name: "Write",
                query: {
                  key: myurl,
                },
              });
            } else {
              this.$router.push("/manage");
            }
          } else {
            this.$message.warning(Response.data.msg);
          }
        })
        .catch((failResponse) => {
          console.log(failResponse);
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.login-container {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  padding-top: 50px;
  //align-items: center;
  text-align: center;
  min-height: 643px;
  background: url("../assets/indexImage/index-login.png") left;
  background-size: cover;
  // background-size: 1600px 690px;
}

.logo-content {
  width: 400px;

  .hello {
    font-size: 40px;
    font-weight: bold;
    color: hsl(220, 27%, 9%);
    line-height: 80px;
  }

  .tips {
    font-size: 21px;
    font-weight: bold;
    color: #10141c;
    line-height: 100px;
  }

  .desc {
    font-size: 14px;
    font-weight: 400;
    color: #c0c4cc;
    line-height: 18px;
    text-align: center;
    position: fixed;
  }

  .login-form-tab {
    padding-top: 60px;
    //min-height: 300px;
    width: 60%;
    margin: 0 80px 60px auto !important;
  }
}

.logo-content ::v-deep.el-tabs__nav-wrap::after {
  position: static !important;
}

.logo-content ::v-deep.el-tabs__active-bar {
  width: 59px !important;
  height: 7px !important;
  border-radius: 4px !important;
  left: 5% !important;
  background-color: #d8d8d8 !important;
}

.logo-tabs ::v-deep.el-tabs__item.is-active {
  color: #10141c !important;
}

::v-deep .el-input {
  height: 39px !important;
  line-height: 39px !important;
}

.link-btn-left {
  margin-right: 27%;
  font-size: 12px !important;
  line-height: 39px;
}

.link-btn-right {
  margin-left: 27%;
  font-size: 12px !important;
  line-height: 39px;
}

::v-deep .el-input__inner {
  height: 39px !important;
  line-height: 39px !important;
  background: #ececec !important;
  border: none;
}

::v-deep .el-button {
  background: #408efc;
  border-radius: 10px;
  width: 145px;
}

.el-icon-back {
  font-size: 22px;
  font-weight: 550;
  cursor: pointer;
  color: #000;
  margin-right: 75px;

  &:hover {
    color: rgb(32, 160, 255);
  }
}

.loginButton{
    height: 40px;
    width: 180px;
    font-size: 13px;
}
</style>
<style scoped>
  .login-block {
    margin-top: 30px;
    border: #d8d8d8 2px solid;
    border-radius: 20px;
    background: #ffffff;
    opacity: 0.9;
    margin-bottom: 30px;
  }
  .reg-block {
    width: 400px;
    padding: 0 30px;
    border: #d8d8d8 2px solid;
    border-radius: 20px;
    background: #ffffff;
    opacity: 0.9;
  }
  .resetPsw-block {
    width: 400px;
    padding: 0 30px;
    margin-bottom: 15px;
    text-align: left;
    border: #d8d8d8 2px solid;
    border-radius: 20px;
    background: #ffffff;
    opacity: 0.9;
  }
</style>