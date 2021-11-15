<template>
  <div class="resetPsw-container">
    <p class="tips">重设密码</p>
        <el-form :model="resetForm" :rules="rules" label-width="0px">
          <el-form-item label="" prop="email">
            <el-input
              v-model="resetPswForm.email"
              prefix-icon="el-icon-message"
              autocomplete="off"
              placeholder="请输入邮箱"
            />
          </el-form-item>
          <el-form-item label="" prop="code">
            <el-input
              v-model="resetPswForm.verificationCode"
              autocomplete="off"
              class="code-input"
              maxlength="6"
              oninput="value=value.replace(/[^\d]/g,'')"
              placeholder="请输入验证码"
            >
              <font-icon slot="prefix" class=" el-input__icon fa fa-shield" />
            </el-input>
            <el-button
              :disabled="emailValidateCodeBtn"
              type="primary"
              @click="sendEmailCodeHandle"
            >
              {{ emailValidateCodeBtnText }}
            </el-button>
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input
              v-model="resetPswForm.password"
              autocomplete="off"
              placeholder="请输入新密码"
              prefix-icon="el-icon-lock"
              show-password
            />
          </el-form-item>
          <el-form-item label="" prop="re_password">
            <el-input
              v-model="resetPswForm.re_password"
              autocomplete="off"
              placeholder="请再次输入新密码"
              prefix-icon="el-icon-lock"
              show-password
            />
          </el-form-item>
          <el-form-item>
            <el-button class="width-full" type="primary" @click="resetPswHandle"
              >确定</el-button
            ><el-link class="link-btn" @click="backLogin">返回登录页面</el-link>
          </el-form-item>
        </el-form>
  </div>
</template>
<script>
  // 引入组件
  export default {
    name: "resetPsw",
    data() {
      return {
        regType: "regEmail",
        resetPswForm: {
          password: "",
          re_password: "",
          email: "",
          verificationCode: "",
        },
        rules: {
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
        if (this.resetPswForm.email === "") {
          this.$message.warning("请填写电子邮箱");
          return;
        }
        this.$axios
          .post("/api/send_email", {
            email: this.resetPswForm.email,
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
      resetPswHandle() {
        if (Object.values(this.resetPswForm).some((item) => item === "")) {
          this.$message.warning("请填写所有必要信息");
          return;
        }
        this.$axios
          .post("/api/find_change_password", {
            password: this.resetPswForm.password,
            re_password: this.resetPswForm.re_password,
            email: this.resetPswForm.email,
            verificationCode: this.resetPswForm.verificationCode,
          })
          .then((Response) => {
            if (Response.data.status === 0) {
              this.$message.success("重置成功");
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
.resetPsw-container {
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
