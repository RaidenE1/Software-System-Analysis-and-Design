<template>
  <div class="register-container">
    <p class="tips">注册成为问卷星球用户~</p>
    <el-tabs v-model="regType" class="register-form">
      <el-tab-pane label="邮箱注册" name="regEmail">
        <el-form :model="registerForm" :rules="rules" label-width="0px">
          <el-form-item label="" prop="username">
            <el-input
              v-model="registerForm.username"
              prefix-icon="el-icon-user-solid"
              autocomplete="off"
              placeholder="请输入用户名"
            />
          </el-form-item>
          <el-form-item label="" prop="email">
            <el-input
              v-model="registerForm.email"
              prefix-icon="el-icon-message"
              autocomplete="off"
              placeholder="请输入邮箱"
            />
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input
              v-model="registerForm.password"
              autocomplete="off"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
              show-password
            />
          </el-form-item>
          <el-form-item label="" prop="re_password">
            <el-input
              v-model="registerForm.re_password"
              autocomplete="off"
              placeholder="请再次输入密码"
              prefix-icon="el-icon-lock"
              show-password
            />
          </el-form-item>
          <el-form-item label="" prop="code">
            <el-input
              v-model="registerForm.verificationCode"
              autocomplete="off"
              class="code-input"
              maxlength="6"
              oninput="value=value.replace(/[^\d]/g,'')"
              placeholder="请输入验证码"
            >
            </el-input>
            <el-button
              :disabled="emailValidateCodeBtn"
              type="primary"
              @click="sendEmailCodeHandle"
            >
              {{ emailValidateCodeBtnText }}
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button class="width-full" type="primary" @click="registerHandle"
              >确定</el-button
            ><el-link class="link-btn" @click="backLogin">返回登录页面</el-link>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="手机号注册" name="regPhone"> </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// 引入组件
export default {
  name: "Register",
  data() {
    return {
      regType: "regEmail",
      registerForm: {
        username: "",
        password: "",
        re_password: "",
        email: "",
        verificationCode: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        re_password: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
        ],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
        verificationCode: [
          { required: true, message: "请输入验证码", trigger: "blur" },
        ],
      },
      emailValidateCodeBtn: false,
      emailValidateCodeBtnText: "发送验证码",
    };
  },
  methods: {
    sendEmailCodeHandle() {
      if (this.registerForm.email === "") {
        this.$message.warning("请填写电子邮箱");
        return;
      }
      this.$axios
        .post("/api/send_email", {
          email: this.registerForm.email,
        })
        .then((Response) => {
          if (Response.data.status === 0) {
            this.$message.success("发送成功");
            this.emailValidateCodeBtn = true;
            let count = 60;
            let timer = setInterval(() => {
              count--;
              this.emailValidateCodeBtnText = count + "s后重新发送";
              if (count == 0) {
                this.emailValidateCodeBtnText = "发送验证码";
                this.emailValidateCodeBtn = false;
                clearInterval(timer);
              }
            }, 1000);
          } else {
            console.log(Response.data.status);
          }
        })
        .catch((failResponse) => {
          console.log(failResponse);
        });
    },
    registerHandle() {
      if (Object.values(this.registerForm).some((item) => item === "")) {
        this.$message.warning("请填写所有必要信息");
        return;
      }
      this.$axios
        .post("/api/register", {
          uid: this.registerForm.username,
          password: this.registerForm.password,
          re_password: this.registerForm.re_password,
          email: this.registerForm.email,
          verificationCode: this.registerForm.verificationCode,
        })
        .then((Response) => {
          if (Response.data.status === 0) {
            this.$message.success("注册成功");
            this.backLogin();
          } else {
            this.$message.warning(Response.data.msg);
          }
        })
        .catch((failResponse) => {
          console.log(failResponse);
        });
    },
    backLogin() {
      setTimeout(() => {
        this.$emit("success");
      }, 300);
    },
  },
};
</script>
<style lang="scss" scoped>
.register-container {
  .tips {
    height: 15px;
    font-size: 15px;
    font-weight: 300;
    color: #10141c;
    line-height: 20px;
  }
}
.code-input {
  width: 180px;
  margin-right: 74px;
}
.link-btn {
  font-size: 12px !important;
  line-height: 39px;
  margin-left: 44%;
}
</style>
