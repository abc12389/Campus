<template>
  <div class="containner-all">
    <div class="container">
      <el-row :gutter="0" style="width: 100%; height: 100%">
        <el-col :span="12" style="width: 50%; height: 100%">
          <div class="contain-left"></div>
        </el-col>
        <el-col :span="12"
          ><div class="grid-content bg-purple" style="text-align: center">
            <h2
              style="
                margin-bottom: 20px;
                margin-top: 40px;
                display: inline-block;
              "
            >
              欢迎使用校园赛事系统
            </h2>
            <h5>请登录</h5>
            <div class="container_right">
              <el-form
                class="form"
                id="form2"
                ref="loginFormRef"
                :model="loginForm"
                :rules="loginRules"
                label-width="0px"
                style="width: 80%; display: inline-block"
              >
                <el-form-item prop="username">
                  <el-input
                    v-model="loginForm.username"
                    prefix-icon="iconfont icondenglu"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input
                    v-model="loginForm.password"
                    prefix-icon="iconfont iconmima"
                    type="password"
                  ></el-input>
                </el-form-item>
                <el-form-item class="btns">
                  <el-button type="primary" @click="login">登录</el-button>
                  <el-button type="info" @click="resetLoginForm"
                    >重置</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </div></el-col
        >
      </el-row>
      <!-- Sign In -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        username: "admin",
        password: "123456",
      },
      loginRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 5, max: 8, message: "长度在 5 到 8 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 8, message: "密码为 6~8 位", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      this.$refs.loginFormRef.validate(async (valid) => {
        if (!valid) return;
        // 调用get请求
        const { data: res } = await this.$http.post("login", this.loginForm);
        if (res == "ok") {
          window.sessionStorage.setItem("flag", "ok"); // session 放置
          this.$message.success("登陆成功！！！");
          this.$router.push({ path: "/home" });
        } else {
          this.$message.error("登录失败！！！");
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.containner-all {
  align-items: center;
  background-color: var(--white);
  background: url("https://res.cloudinary.com/dbhnlktrv/image/upload/v1599997626/background_oeuhe7.jpg");
  /* 决定背景图像的位置是在视口内固定，或者随着包含它的区块滚动。 */
  /* https://developer.mozilla.org/zh-CN/docs/Web/CSS/background-attachment */
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: grid;
  height: 100vh;
  place-items: center;
}
.container {
  width: 50%;
  height: 57%;
  background-color: #e6efef;
  border-radius: 10px;
  box-shadow: 4px 4px 10px #6aa0a0;
}
.contain-left {
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  background-color: aliceblue;
  // background-color: var(--lightblue);
  background: url("https://cdn.pixabay.com/photo/2018/08/14/13/23/ocean-3605547_1280.jpg");
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  width: 100%;
  position: relative;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}
</style>