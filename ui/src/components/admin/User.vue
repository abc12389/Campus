<template>
  <div>

    <el-card>
      <Image src="../assets/background.png"></Image>
      <el-row :gutter="10">
        <el-col :span="5">
          <el-select v-model="queryInfo.department" clearable placeholder="请选择学院">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <!-- 搜索添加 -->
          <el-input placeholder="请输入姓名" v-model="queryInfo.studentName" clearable @clear="getUserList">
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
            @click="getUserList">搜索</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" style="margin-left:10px" @click="showAddDialog">添加用户</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表 -->
      <!-- border边框   stripe隔行变色 -->
      <el-table :data="userlist" :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
        border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="学号" width="170" prop="userId"></el-table-column>
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
              size="mini" @click="showUpdateDialog(scope.row.userId)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" circle size="mini"
              @click="deleteUser(scope.row.userId)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum" :page-sizes="[1, 6, 10, 50]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </el-card>

    <!-- 添加新用户对话框 -->
    <el-dialog :title="title" :visible.sync="addUpdateDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="80px">
        <!-- 学生学号 -->
        <el-form-item label="学生学号" prop="userId">
          <el-input v-model="addForm.userId" :disabled="disabled"></el-input>
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
            <el-radio v-model="addForm.studentSex" label="男">男</el-radio>
            <el-radio v-model="addForm.studentSex" label="女">女</el-radio>
          </template>
        </el-form-item>
        <!-- 联系方式 -->
        <el-form-item label="联系方式" prop="studentPhoneNumber">
          <el-input v-model="addForm.studentPhoneNumber"></el-input>
        </el-form-item>
        <!-- 学院 -->
        <el-form-item label="学院" prop="studentDepartment">
          <el-select v-model="addForm.studentDepartment" placeholder="请选择学院">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- 年级 -->
        <el-form-item label="年级" prop="studentGrade">
          <template>
            <el-radio v-model="addForm.studentGrade" label="大一">大一</el-radio>
            <el-radio v-model="addForm.studentGrade" label="大二">大二</el-radio>
            <el-radio v-model="addForm.studentGrade" label="大三">大三</el-radio>
            <el-radio v-model="addForm.studentGrade" label="大四">大四</el-radio>
          </template>
        </el-form-item>
        <!-- 班级 -->
        <el-form-item label="班级" prop="studentClass">
          <el-input v-model="addForm.studentClass"></el-input>
        </el-form-item>
        <!-- 管理员 -->
        <el-form-item label="管理员" prop="isAdmin">
          <template>
            <el-radio v-model="addForm.isAdmin" label="是">是</el-radio>
            <el-radio v-model="addForm.isAdmin" label="否">否</el-radio>
          </template>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="info" @click="resetaddForm">重置</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import { Image } from 'element-ui';

export default {
  data() {
    return {
      title: "增加",
      disabled: false,
      // 请求数据
      queryInfo: {
        studentName: "",
        pageNum: 1,
        pageSize: 6,
        department: "", //学院
      },
      userlist: [],
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
      total: 0,
      addUpdateDialogVisible: false,
      // 添加用户表单项
      addForm: {
        userId: "",
        userPwd: "",
        studentName: "",
        studentSex: "",
        studentPhoneNumber: "",
        studentDepartment: "",
        studentGrade: "",
        studentClass: "",
        isAdmin: "",
      },
      // 验证规则
      addFormRules: {
        userId: [
          { required: true, message: "请输入学生学号", trigger: "blur" },
        ],
        userPwd: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        studentName: [
          { required: true, message: "请输入学生姓名", trigger: "blur" },
        ],
        studentSex: [
          { required: true, message: "请选择性别", trigger: "blur" },
        ],
        studentPhoneNumber: [
          { required: true, message: "请输入学生联系方式", trigger: "blur" },
        ],
        studentDepartment: [
          { required: true, message: "请选择学院", trigger: "blur" },
        ],
        studentGrade: [
          { required: true, message: "请选择年级", trigger: "blur" },
        ],
        studentClass: [
          { required: true, message: "请输入班级", trigger: "blur" },
        ],
        isAdmin: [
          { required: true, message: "请选择是否为管理员", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getUserList();
  },
  watch: {
    "queryInfo.studentName": {
      handler(newVal, oldVal) {
        this.queryInfo.pageNum = 1;
      }
    },
    "queryInfo.studentDepartment": {
      handler(newVal, oldVal) {
        this.queryInfo.pageNum = 1;
      }
    },
  },
  methods: {
    cancelDialog() {
      this.addUpdateDialogVisible = false;
      this.$refs.addFormRef.resetFields();
    },
    async getUserList() {
      // 调用get请求
      const { data: res } = await this.$http.get("allUser", {
        params: this.queryInfo,
      });
      this.userlist = res.data; // 将返回数据赋值
      this.total = res.numbers; // 总个数
    },
    async resetaddForm() {
      if (this.title == "修改用户信息") {
        const { data: res } = await this.$http.get("getUpdateUser?id=" + this.addForm.userId);
        this.addForm = res.data;
        return;
      }
      this.$refs.addFormRef.resetFields();
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
    // 关闭对话框添加用户
    addDialogClosed() {
      this.$refs.addFormRef.resetFields(); // 关闭对话框的时候应该重置表单项，否则下一次再打开会有数据留在那里
    },
    // 添加用户
    addUser() {
      if (this.title == "添加用户") {
        // addUser
        this.addOrUpdate("addUser");
      }
      else if (this.title == "修改用户信息") {
        this.addOrUpdate("updateUser");
      }
    },
    async addOrUpdate(url) {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid)
          return;
        // 发起请求
        const { data: res } = await this.$http.post(url, this.addForm);
        if (res.code != 200) {
          return this.$message.error("操作失败！！！");
        }
        this.$message.success("操作成功！！！");
        //隐藏
        this.addUpdateDialogVisible = false;
        this.getUserList();
      });
    },
    // 展示添加框
    showAddDialog() {
      this.disabled = false;
      this.addUpdateDialogVisible = true;
      this.title = "添加用户";
    },
    // 展示修改框
    async showUpdateDialog(id) {
      this.disabled = true;
      this.addUpdateDialogVisible = true;
      this.title = "修改用户信息";
      const { data: res } = await this.$http.get("getUpdateUser?id=" + id);
      this.addForm = res.data;
    },
    // 关闭窗口
    updateDialogClosed() {
      this.$refs.updateFormRef.resetFields();
    },
    // 确认修改
    updateUserInfo() {
      this.$refs.updateFormRef.validate(async (valid) => {
        if (!valid)
          return;
        // 发起请求
        const { data: res } = await this.$http.put("updateUser", this.updateForm);
        if (res != "success")
          return this.$message.error("操作失败！！！");
        this.$message.success("操作成功！！！");
        //隐藏
        this.updateDialogVisible = false;
        this.getUserList();
      });
    },
    // 删除按钮
    async deleteUser(id) {
      // 弹框
      const confirmResult = await this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).catch((err) => err);
      // 成功删除为confirm 取消为 cancel
      if (confirmResult != "confirm") {
        return this.$message.info("已取消删除");
      }
      const { data: res } = await this.$http.delete("deleteUser?id=" + id);
      if (res.code != 200) {
        return this.$message.error("删除失败！！！");
      }
      this.$message.success("删除成功！！！");
      this.getUserList();
    },
  },
  components: { Image }
};
</script>
<style lang="less" scoped></style>