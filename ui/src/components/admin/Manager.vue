<template>
    <div>
        <el-card>
            <el-row :gutter="10">
                <el-col :span="5">
                    <el-select v-model="queryInfo.department" clearable placeholder="请选择学院">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6">
                    <!-- 搜索添加 -->
                    <el-input placeholder="请输入姓名" v-model="queryInfo.studentName" clearable @clear="getStuManagerList">
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
                        @click="getStuManagerList">搜索</el-button>
                </el-col>
            </el-row>
            <!-- 学生管理员列表 -->
            <!-- border边框   stripe隔行变色 -->
            <el-table :data="stuManagerlist" :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="学号" width="170" prop="userId" alin></el-table-column>
                <el-table-column label="姓名" width="90" prop="studentName"></el-table-column>
                <el-table-column label="性别" width="90" prop="studentSex"></el-table-column>
                <el-table-column label="学院" width="170" prop="studentDepartment"></el-table-column>
                <el-table-column label="手机号码" prop="studentPhoneNumber"></el-table-column>
                <el-table-column label="管理员" prop="isAdmin" :formatter="isAdminFormatter"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-button type="primary" icon="el-icon-edit" style="background-color: grey; border-color: grey;"
                            circle size="mini" @click="showUpdateDialog(scope.row.userId)"></el-button>
                        <!-- 删除 -->
                        <el-button type="danger" icon="el-icon-delete" circle size="mini"
                            @click="deleteStuManager(scope.row.userId)"></el-button>

                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum" :page-sizes="[1, 6, 10, 50]" :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
        </el-card>


        <!-- 修改学生管理员对话框 -->
        <el-dialog title="修改学生管理员信息" :visible.sync="updateDialogVisible" width="50%" @colse="updateDialogClosed">
            <el-form :model="updateForm" ref="updateFormRef" label-width="70px">
                <!-- 学生学号 -->
                <el-form-item label="学生学号" prop="userId">
                    <el-input v-model="updateForm.userId" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item label="密码" prop="userPwd">
                    <el-input v-model="updateForm.userPwd" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 学生姓名 -->
                <el-form-item label="学生姓名" prop="studentName">
                    <el-input v-model="updateForm.studentName" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 性别 -->
                <el-form-item label="性别" prop="studentSex">
                    <template :disabled="disabled">
                        <el-radio v-model="updateForm.studentSex" label="男" :disabled="disabled">男</el-radio>
                        <el-radio v-model="updateForm.studentSex" label="女" :disabled="disabled">女</el-radio>
                    </template>
                </el-form-item>
                <!-- 联系方式 -->
                <el-form-item label="联系方式" prop="studentPhoneNumber">
                    <el-input v-model="updateForm.studentPhoneNumber" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 学院 -->
                <el-form-item label="学院" prop="studentDepartment">
                    <el-select v-model="updateForm.studentDepartment" placeholder="请选择学院" :disabled="disabled">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- 年级 -->
                <el-form-item label="年级" prop="studentGrade">
                    <template>
                        <el-radio v-model="updateForm.studentGrade" label="大一" :disabled="disabled">大一</el-radio>
                        <el-radio v-model="updateForm.studentGrade" label="大二" :disabled="disabled">大二</el-radio>
                        <el-radio v-model="updateForm.studentGrade" label="大三" :disabled="disabled">大三</el-radio>
                        <el-radio v-model="updateForm.studentGrade" label="大四" :disabled="disabled">大四</el-radio>
                    </template>
                </el-form-item>
                <!-- 班级 -->
                <el-form-item label="班级" prop="studentClass">
                    <el-input placeholder="请输入阿拉伯数字" v-model="updateForm.studentClass" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 管理员 -->
                <el-form-item label="管理员" prop="isAdmin">
                    <template>
                        <el-radio v-model="updateForm.isAdmin" label="是">是</el-radio>
                        <el-radio v-model="updateForm.isAdmin" label="否">否</el-radio>
                    </template>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateManagerInfo">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {
            disabled: true,
            // 请求数据
            queryInfo: {
                studentName: "", //姓名
                pageNum: 1,
                pageSize: 6,
                department: "", //学院
            },
            stuManagerlist: [], // 学生管理员列表
            //学院
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

            // 控制修改学生管理员对话框显示/隐藏
            updateDialogVisible: false,
            // 修改学生管理员信息
            updateForm: {
                userId: "",
                studentName: "",
                studentSex: "",
                studentDepartment: "",
                studentPhoneNumber: "",
                isAdmin: "",
            },
        };
    },
    created() {
        this.getStuManagerList();
    },
    methods: {
        isAdminFormatter(row, column) {
            const isAdmin = row.isAdmin
            if (isAdmin == 0) {
                return '否'
            } else if (isAdmin == 1) {
                return '是'
            }
        },


        async getStuManagerList() {
            // 调用get请求
            const { data: res } = await this.$http.get("allStuManager", {
                params: this.queryInfo,
            });
            this.stuManagerlist = res.data; // 将返回数据赋值
            this.total = res.numbers; // 总个数
        },
        async resetupdateForm() {
            if (this.title == "修改学生管理员信息") {
                const { data: res } = await this.$http.get("getUpdateStuManager?id=" + this.updateForm.id);
                this.updateForm = res.data;
                return
            }
            this.$refs.updateFormRef.resetFields();
        },
        // 监听pageSize改变的事件(更改变每页显示条数时)
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize;
            this.getStuMangerList(); // 数据发生改变重新申请数据
        },
        // 监听pageNum改变的事件(点击页数时候)
        handleCurrentChange(newPage) {
            this.queryInfo.pageNum = newPage;
            this.getStuManagerList(); // 数据发生改变重新申请数据
        },
        // 展示修改框
        async showUpdateDialog(id) {
            const { data: res } = await this.$http.get("getUpdateStuManager?id=" + id);
            this.updateForm = res.data;
            this.updateDialogVisible = true;
        },
        // 关闭窗口
        updateDialogClosed() {
            this.$refs.updateFormRef.resetFields();
        },
        // 确认修改
        updateManagerInfo() {
            this.$refs.updateFormRef.validate(async (valid) => {
                if (!valid) return;
                // 发起请求
                const { data: res } = await this.$http.put("updateStuManager", this.updateForm);
                if (res.code != 200) return this.$message.error("操作失败！！！");
                this.$message.success("操作成功！！！");
                //隐藏
                this.updateDialogVisible = false;
                this.getStuManagerList();
            });
        },
        // 删除按钮
        async deleteStuManager(id) {
            // 弹框
            const confirmResult = await this.$confirm(
                "此操作将永久删除该学生管理员, 是否继续?",
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
            const { data: res } = await this.$http.delete("deleteStuManager?id=" + id);
            if (res.code != 200) {
                return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
            this.getStuManagerList();
        },
    },
};
</script>
<style lang="less" scoped></style>