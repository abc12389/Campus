<template>
  <div>
    <!-- 面包屑导航111111 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb> -->
    <!-- 卡片视图区 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="5">
          <el-select v-model="queryInfo.department" placeholder="请选择学院">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <!-- 搜索添加 -->
          <el-input placeholder="按学号查找" v-model="queryInfo.id" clearable @clear="getUserList">
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
            @click="getUserList">搜索</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" style="margin-left:10px" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表 -->
      <!-- border边框   stripe隔行变色 -->
      <el-table :data="userlist" :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
        border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="学号" width="170" prop="userId" alin></el-table-column>
        <el-table-column label="密码" width="90" prop="userPwd"></el-table-column>
        <el-table-column label="姓名" width="90" prop="studentName"></el-table-column>
        <el-table-column label="性别" width="90" prop="studentSex"></el-table-column>
        <el-table-column label="学院" width="170" prop="studentDepartment"></el-table-column>
        <el-table-column label="年级" width="90" prop="studentGrade"></el-table-column>
        <el-table-column label="班级" width="60" prop="studentClass"></el-table-column>
        <el-table-column label="手机号码" prop="studentPhoneNumber"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button type="primary" icon="el-icon-edit" style="background-color: grey; border-color: grey;" circle
              size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" circle size="mini"
              @click="deleteUser(scope.row.id)"></el-button>

          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum" :page-sizes="[1, 6, 10, 50]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </el-card>

    <!-- 创建新用户对话框 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <!-- 学生学号 -->
        <el-form-item label="学生学号" prop="userId">
          <el-input v-model="addForm.userId"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="userPwd">
          <el-input v-model="addForm.userPwd"></el-input>
        </el-form-item>
        <!-- 学生姓名 -->
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="addForm.studentName"></el-input>
        </el-form-item>
        <!-- 性别 -->
        <el-form-item label="性别" prop="studentSex">
          <template>
            <el-radio v-model="queryInfo.radio" label="1">男</el-radio>
            <el-radio v-model="queryInfo.radio" label="2">女</el-radio>
          </template>
        </el-form-item>
        <!-- 联系方式 -->
        <el-form-item label="联系方式" prop="studentPhoneNumber">
          <el-input v-model="addForm.studentPhoneNumber"></el-input>
        </el-form-item>
        <!-- 学院 -->
        <el-form-item label="学院" prop="studentDepartment">
          <el-input v-model="addForm.studentDepartment"></el-input>
        </el-form-item>
        <!-- 年级 -->
        <el-form-item label="年级" prop="studentGrade">
          <!-- <el-input v-model="addForm.studentGrade"></el-input> -->
          <template>
            <el-radio v-model="queryInfo.radio2" label="freshman">大一</el-radio>
            <el-radio v-model="queryInfo.radio2" label="sophmore">大二</el-radio>
            <el-radio v-model="queryInfo.radio2" label="junior">大三</el-radio>
            <el-radio v-model="queryInfo.radio2" label="senior">大四</el-radio>
          </template>
        </el-form-item>
        <!-- 班级 -->
        <el-form-item label="班级" prop="studentClass">
          <el-input v-model="addForm.studentClass"></el-input>
        </el-form-item>
        <!-- 团队编号 -->
        <el-form-item label="团队编号" prop="teamId">
          <el-input v-model="addForm.teamId"></el-input>
        </el-form-item>
        <!-- 管理员 -->
        <el-form-item label="管理员" prop="isAdmin">
          <el-input v-model="addForm.isAdmin"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户对话框 -->
    <el-dialog title="修改用户信息" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <!-- 用户名 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      // 请求数据
      queryInfo: {
        id: "", //学号
        pageNum: 1,
        pageSize: 6,
        department: "", //学院
      },
      userlist: [], // 用户列表
      options: [
        {
          //学院
          value: "管理学院",
          label: "管理学院",
        },
        {
          value: "航空旅游学院",
          label: "航空旅游学院",
        },
        {
          value: "机电工程学院",
          label: "机电工程学院",
        },
        {
          value: "电子信息与自动化学院",
          label: "电子信息与自动化学院",
        },
        {
          value: "汽车工程学院",
          label: "汽车工程学院",
        },
        {
          value: "能源与建筑学院",
          label: "能源与建筑学院",
        },
        {
          value: "计算机科学与工程学院",
          label: "计算机科学与工程学院",
        },
        {
          value: "外语外贸学院",
          label: "外语外贸学院",
        },
        {
          value: "媒与艺术学院",
          label: "传媒与艺术学院",
        },
        {
          value: "理学院",
          label: "理学院",
        },
        {
          value: "航空宇航学院",
          label: "航空宇航学院",
        },
      ],
      total: 0, // 最大数据记录
      addDialogVisible: false, // 对话框显示
      // 添加用户表单项
      addForm: {
        username: "",
        password: "",
        email: "",
      },
      // 控制修改用户对话框显示/隐藏
      editDialogVisible: false,
      // 修改用户信息
      editForm: {},
      // 验证规则
      addFormRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 5, max: 8, message: "长度在 5 到 8 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 8, message: "长度在 6 到 8 个字符", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { min: 5, max: 15, message: "请输入正确邮箱地址", trigger: "blur" },
        ],
      },
      // 修改用户表单验证规则
      editFormRules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 8, message: "长度在 6 到 8 个字符", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { min: 5, max: 15, message: "请输入正确邮箱地址", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getUserList();
  },
  methods: {
    async getUserList() {
      // 调用post请求
      const { data: res } = await this.$http.get("allUser", {
        params: this.queryInfo,
      });
      this.userlist = res.data; // 将返回数据赋值
      this.total = res.numbers; // 总个数
    },
    // 监听pageSize改变的事件(更改变每页显示条数时)
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getUserList(); // 数据发生改变重新申请数据
    },
    // 监听pageNum改变的事件(点击页数时候)
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getUserList(); // 数据发生改变重新申请数据
    },
    // 修改用户状态
    async userStateChanged(userinfo) {
      const { data: res } = await this.$http.put(
        `userState?id=${userinfo.id}&state=${userinfo.state}`
      );
      if (res != "success") {
        userinfo.state = !userinfo.state;
        return this.$message.error("操作失败！！！");
      }
      this.$message.success("操作成功！！！");
    },
    // 监听添加用户
    addDialogClosed() {
      this.$refs.addFormRef.resetFields(); // 关闭对话框的时候应该重置表单项，否则下一次再打开会有数据留在那里
    },
    // 添加用户
    addUser() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        // 发起请求
        const { data: res } = await this.$http.post("addUser", this.addForm);
        if (res != "success") {
          userinfo.state = !userinfo.state;
          return this.$message.error("操作失败！！！");
        }
        this.$message.success("操作成功！！！");
        //隐藏
        this.addDialogVisible = false;
        this.getUserList();
      });
    },
    // 展示修改框
    async showEditDialog(id) {
      const { data: res } = await this.$http.get("getUpdate?id=" + id);
      // if (res != "success") {
      // userinfo.state = !userinfo.state;
      // return this.$message.error("操作失败！！！");
      // }
      // this.$message.success("操作成功！！！");

      this.editForm = res;
      this.editDialogVisible = true;
    },
    // 关闭窗口
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    // 确认修改
    editUserInfo() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (!valid) return;
        // 发起请求
        const { data: res } = await this.$http.put("editUser", this.editForm);
        if (res != "success") return this.$message.error("操作失败！！！");
        this.$message.success("操作成功！！！");
        //隐藏
        this.editDialogVisible = false;
        this.getUserList();
      });
    },
    // 删除按钮
    async deleteUser(id) {
      // 弹框
      const confirmResult = await this.$confirm(
        "此操作将永久删除该用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      // 成功删除为confirm 取消为 cancel
      if (confirmResult != "confirm") {
        return this.$message.info("已取消删除");
      }
      const { data: res } = await this.$http.delete("deleteUser?id=" + id);
      if (res != "success") {
        return this.$message.error("操作失败！！！");
      }
      this.$message.success("操作成功！！！");
      this.getUserList();
    },
  },
};
</script>
<style lang="less" scoped></style>