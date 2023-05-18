<template>
  <el-container class="home-container">
    <!--头部布局-->
    <el-header style="background-color: #4481ac">
      <div style="color:#fbf8ff">
        <span>校园赛事管理系统</span>
      </div>
      <span style="margin-left: 60%; font-size: 16px;"> {{ name }} </span>
      <el-button style="background-color: rgb(161, 157, 176);" type="info" @click="logout">退出登录</el-button>
    </el-header>
    <el-container>
      <!--侧边布局-->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <!--伸缩按钮-->
        <div class="toggle-button" @click="toggleCollapase">
          <i class="el-icon-s-fold"></i>
        </div>
        <!--侧边栏菜单区 unique-opened="true" 只保持一个菜单展开
           router开启路由 active-text-color 颜色--
           :collapse="isCollapse" 是否变小 -->
        <!-- 取消伸缩动画 -->
        <el-menu background-color="#4481ac" text-color="#fff" active-text-color="#faab43" unique-opened
          :collapse="isCollapse" :collapse-transition="false" :router="true" :default-active="activePath">
          <template v-for="item in menuList">
            <el-submenu v-if="item.slist.length > 0" :index="item.id + ''" :key="item.id">
              <template slot="title">
                <i :class="iconsObject[item.id]"></i>
                <span>{{ item.title }}</span>
              </template>
              <el-menu-item :index="it.path + ''" v-for="it in item.slist" :key="it.id"
                @click="saveNavState(it.path + '')">
                <template slot="title">
                  <i :class="iconsObject[it.id]"></i>
                  <span>{{ it.title }}</span>
                </template>
              </el-menu-item>
            </el-submenu>

            <el-menu-item v-else :index="item.path + ''" :key="item.id + ''" @click="saveNavState(item.path + '')">
              <i :class="iconsObject[item.id]"></i>
              <span slot="title">{{ item.title }}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </el-aside>
      <!--主体布局-->
      <el-main>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data() {
    return {

      // 左侧菜单
      menuList: [],
      iconsObject: {
        100: "el-icon-house",
        200: "el-icon-coordinate",
        300: "el-icon-user",
        400: "el-icon-user-solid",
        500: "el-icon-s-data",
        600: "el-icon-s-grid",
        700: "el-icon-setting",
        101: "el-icon-tickets",
        102: "el-icon-user-solid",
      },
      isCollapse: false,
      // 被激活的连接
      activePath: "",
      name: "",
    };
  },
  // 类似onload
  created() {
    this.getMenuList();
    this.getName();
    if (window.sessionStorage.getItem("activePath") == null) {
      this.activePath = "/welcome"; // 取出session里的访问路径
    } else {
      this.activePath = window.sessionStorage.getItem("activePath");
    }
  },
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    // 获取所有的导航菜单
    async getMenuList() {
      const { data: res } = await this.$http.get("menus");
      if (res.status != 200) return this.$message.error("操作失败！！！");
      this.menuList = res.data;
    },
    async getName() {
      var id = window.sessionStorage.getItem("id");
      const { data: res } = await this.$http.get("user?id=" + id);
      if (res.code == 200) {
        this.name = "欢迎：" + res.data;
      } else {
        this.name = "用户名获取失败，请刷新重试！！！";
      }
    },
    // 切换菜单折叠与展开
    toggleCollapase() {
      this.isCollapse = !this.isCollapse;
    },
    // 保存二级菜单的路径
    saveNavState(activePath) {
      window.sessionStorage.setItem("activePath", activePath); // 存放点击的二级菜单
      this.activePath = activePath; // 给点击的菜单添加高亮
    },
  },
};
</script>
<style lang="less" scoped>
.el-header {
  background-color: #4481ac;
  display: flex;
  justify-content: space-between; // 左右贴边
  padding-left: 0%; // 左边界
  align-items: center; // 水平
  color: #fff;
  font-size: 25px;


  >div {
    //左侧div加布局
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #4481ac;
  ;

  .el-menu {
    border-right: none; // 对其右边框
  }
}

.el-main {
  background-color: #eaedf1;
  // background: url(../assets/background.png);
}

.home-container {
  height: 100%;
}

.logo_img {
  width: 20%;
  height: 100%;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: #657395;
  font-size: 20px;
  line-height: 30px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.5em;
  cursor: pointer; // 显示鼠标指针为：小手
}
</style>