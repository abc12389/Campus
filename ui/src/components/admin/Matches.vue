<template>
    <div>
        <!-- 面包屑导航111111 -->
        <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>权限管理</el-breadcrumb-item>
        <el-breadcrumb-item>赛事列表</el-breadcrumb-item>
      </el-breadcrumb> -->
        <!-- 卡片视图区 -->
        <el-card>
            <el-row :gutter="10">
                <el-col :span="4">
                    <el-select v-model="queryInfo.matchStatus" placeholder="请选择赛事状态">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6">
                    <!-- 搜索添加 -->
                    <el-input placeholder="请输入赛事名称" v-model="queryInfo.matchTitle" clearable @clear="getMatchesList">
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
                        @click="getMatchesList">搜索</el-button>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" style="margin-left: 10px;" @click="addDialogVisible = true">添加赛事</el-button>
                </el-col>
            </el-row>
            <!-- 赛事列表 -->
            <!-- border边框   stripe隔行变色 -->
            <el-table :data="matchlist" :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="赛事图片" width="150" prop=" "></el-table-column>
                <el-table-column label="赛事名称" width="150" prop="matchTitle" alin></el-table-column>
                <el-table-column label="赛事类型" width="90" prop="matchType"></el-table-column>
                <el-table-column label="报名时间" width="150" prop=" "></el-table-column>
                <el-table-column label="比赛时间" width="150" prop=" "></el-table-column>
                <el-table-column label="赛事文件" width="90" prop=""></el-table-column>
                <el-table-column label="赛事状态" width="80" prop="matchStatus"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-button type="primary" icon="el-icon-edit" style="background-color: grey; border-color: grey;"
                            circle size="mini" @click="showEditDialog(scope.row.id)"></el-button>
                        <!-- 删除 -->
                        <el-button type="danger" icon="el-icon-delete" circle size="mini"
                            @click="deleteMatch(scope.row.id)"></el-button>

                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum" :page-sizes="[1, 4, 10, 50]" :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
        </el-card>

        <!-- 创建新赛事对话框 -->
        <el-dialog title="添加赛事" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
            <!-- 内容主体区域 -->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
                <!-- 学生学号 -->
                <el-form-item label="学生学号" prop="matchId">
                    <el-input v-model="addForm.matchId"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item label="密码" prop="matchPwd">
                    <el-input v-model="addForm.matchPwd"></el-input>
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
                <el-button type="primary" @click="addmatch">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 修改赛事对话框 -->
        <el-dialog title="修改赛事信息" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
                <!-- 赛事名 -->
                <el-form-item label="赛事名" prop="matchname">
                    <el-input v-model="editForm.matchname" disabled></el-input>
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
                <el-button type="primary" @click="editMatchInfo">确 定</el-button>
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
                // id: "",
                matchTitle: "", //赛事名称
                pageNum: 1,
                pageSize: 4,
                department: "", //学院
            },
            matchlist: [], // 赛事列表
            options: [
                {
                    //学院
                    value: "已结束",
                    label: "已结束",
                },
                {
                    value: "报名中",
                    label: "报名中",
                },
                {
                    value: "比赛中",
                    label: "比赛中",
                },

            ],
            total: 0, // 最大数据记录
            addDialogVisible: false, // 对话框显示
            // 添加赛事表单项
            addForm: {
                matchname: "",
                password: "",
                email: "",
            },
            // 控制修改赛事对话框显示/隐藏
            editDialogVisible: false,
            // 修改赛事信息
            editForm: {},
            // 验证规则
            addFormRules: {
                matchname: [
                    { required: true, message: "请输入赛事名", trigger: "blur" },
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
            // 修改赛事表单验证规则
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
        this.getMatchesList();
    },
    methods: {
        async getMatchesList() {
            // 调用post请求
            const { data: res } = await this.$http.get("allMatches", {
                params: this.queryInfo,
            });
            this.matchlist = res.data; // 将返回数据赋值
            this.total = res.numbers; // 总个数
        },
        // 监听pageSize改变的事件(更改变每页显示条数时)
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize;
            this.getMatchesList(); // 数据发生改变重新申请数据
        },
        // 监听pageNum改变的事件(点击页数时候)
        handleCurrentChange(newPage) {
            this.queryInfo.pageNum = newPage;
            this.getMatchesList(); // 数据发生改变重新申请数据
        },
        // 修改赛事状态
        async matchStateChanged(matchinfo) {
            const { data: res } = await this.$http.put(
                `matchState?id=${matchinfo.id}&state=${matchinfo.state}`
            );
            if (res != "success") {
                matchinfo.state = !matchinfo.state;
                return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
        },
        // 监听添加赛事
        addDialogClosed() {
            this.$refs.addFormRef.resetFields(); // 关闭对话框的时候应该重置表单项，否则下一次再打开会有数据留在那里
        },
        // 添加赛事
        addMatch() {
            this.$refs.addFormRef.validate(async (valid) => {
                if (!valid) return;
                // 发起请求
                const { data: res } = await this.$http.post("addMatches", this.addForm);
                if (res != "success") {
                    matchinfo.state = !matchinfo.state;
                    return this.$message.error("操作失败！！！");
                }
                this.$message.success("操作成功！！！");
                //隐藏
                this.addDialogVisible = false;
                this.getMatchesList();
            });
        },
        // 展示修改框
        async showEditDialog(id) {
            const { data: res } = await this.$http.get("getUpdate?id=" + id);
            // if (res != "success") {
            // matchinfo.state = !matchinfo.state;
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
        editMatchInfo() {
            this.$refs.editFormRef.validate(async (valid) => {
                if (!valid) return;
                // 发起请求
                const { data: res } = await this.$http.put("editMatch", this.editForm);
                if (res != "success") return this.$message.error("操作失败！！！");
                this.$message.success("操作成功！！！");
                //隐藏
                this.editDialogVisible = false;
                this.getMatchesList();
            });
        },
        // 删除按钮
        async deleteMatch(id) {
            // 弹框
            const confirmResult = await this.$confirm(
                "此操作将永久删除该赛事, 是否继续?",
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
            const { data: res } = await this.$http.delete("deleteMatch?id=" + id);
            if (res != "success") {
                return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
            this.getMatchesList();
        },
    },
};
</script>
<style lang="less" scoped></style>