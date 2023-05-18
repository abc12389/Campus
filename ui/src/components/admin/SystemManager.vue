<template>
    <div>
        <el-card>
            <el-row :gutter="10">
                <!-- <el-col :span="5">
                    <el-input placeholder="请输入姓名查找" v-model="queryInfo.studnetName" clearable @clear="getSysManagerList">
                    </el-input>
                </el-col> -->
                <el-col :span="6">
                    <!-- 搜索添加 -->
                    <el-input placeholder="请输入内容查找" v-model="queryInfo.disContent" clearable @clear="getSysManagerList">
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
                        @click="getSysManagerList">搜索</el-button>
                </el-col>

            </el-row>
            <!-- 系统列表 -->
            <!-- border边框   stripe隔行变色 -->
            <el-table :data="SysManagerList" :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="反馈编号" width="70" prop="feedbackId"></el-table-column>
                <el-table-column label="学生学号" width="150" prop="studentId"></el-table-column>
                <el-table-column label="赛事编号" width="70" prop="matchId"></el-table-column>
                <el-table-column label="反馈内容" width="250" prop="content"></el-table-column>
                <el-table-column label="回复" width="250" prop="reply"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-button type="primary" icon="el-icon-edit" style="background-color: grey; border-color: grey;"
                            circle size="mini" @click="showEditDialog(scope.row.feedbackId)"></el-button>
                        <!-- 删除 -->
                        <el-button type="danger" icon="el-icon-delete" circle size="mini"
                            @click="deleteSysManager(scope.row.feedbackId)"></el-button>

                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum" :page-sizes="[1, 6, 10, 50]" :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
        </el-card>
        <el-dialog title="反馈回复" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
            <el-form :model="editForm" ref="editFormRef" label-width="70px">
                <!-- 学生学号 -->
                <el-form-item label="学生学号" prop="studentId">
                    <el-input v-model="editForm.studentId" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 赛事编号 -->
                <el-form-item label="赛事编号" prop="matchId">
                    <el-input v-model="editForm.matchId" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 反馈内容 -->
                <el-form-item label="反馈内容" prop="content">
                    <el-input v-model="editForm.content" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 回复 -->
                <el-form-item label="回复" prop="reply">
                    <el-input v-model="editForm.reply"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editSysManagerInfo">确 定</el-button>
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
                pageNum: 1,
                pageSize: 6,
                content: "",
                studentName: "",
            },
            SysManagerList: [], // 列表
            total: 0, // 最大数据记录
            // 控制修改系统对话框显示/隐藏
            editDialogVisible: false,
            // 修改系统信息
            editForm: {},
        };
    },
    created() {
        this.getSysManagerList();
    },
    methods: {
        async getSysManagerList() {
            // 调用get请求
            const { data: res } = await this.$http.get("allSysManager", {
                params: this.queryInfo,
            });
            this.SysManagerList = res.data; // 将返回数据赋值
            this.total = res.numbers; // 总个数
        },
        async reseteditForm() {
            if (this.title == "反馈回复") {
                const { data: res } = await this.$http.get("getUpdateSysManager?id=" + this.editForm.id);
                this.editForm = res.data;
                return
            }
            this.$refs.editFormRef.resetFields();
        },
        // 监听pageSize改变的事件(更改变每页显示条数时)
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize;
            this.getSysManagerList(); // 数据发生改变重新申请数据
        },
        // 监听pageNum改变的事件(点击页数时候)
        handleCurrentChange(newPage) {
            this.queryInfo.pageNum = newPage;
            this.getSysManagerList(); // 数据发生改变重新申请数据
        },
        // 展示修改框
        async showEditDialog(id) {
            const { data: res } = await this.$http.get("getUpdateSysManager?id=" + id);
            this.editForm = res.data;
            this.editDialogVisible = true;
        },
        // 关闭窗口
        editDialogClosed() {
            this.$refs.editFormRef.resetFields();
        },
        // 确认修改
        editSysManagerInfo() {
            this.$refs.editFormRef.validate(async (valid) => {
                if (!valid) return;
                // 发起请求
                const { data: res } = await this.$http.put("updateSysManager", this.editForm);
                if (res.code != 200) return this.$message.error("操作失败！！！");
                this.$message.success("操作成功！！！");
                //隐藏
                this.editDialogVisible = false;
                this.getSysManagerList();
            });
        },
        // 删除按钮
        async deleteSysManager(id) {
            // 弹框
            const confirmResult = await this.$confirm(
                "此操作将永久删除该系统, 是否继续?",
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
            const { data: res } = await this.$http.delete("deleteSysManager?id=" + id);
            if (res.code != 200) {
                return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
            this.getSysManagerList();
        },
    },
};
</script>
<style lang="less" scoped></style> 