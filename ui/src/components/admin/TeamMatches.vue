<template>
    <div>
        <el-card>
            <el-row :gutter="10">
                <el-col :span="4">
                    <el-select v-model="queryInfo.status" clearable placeholder="请选择报名状态">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6">
                    <!-- 搜索添加 -->
                    <el-input placeholder="请输入赛事名称" v-model="queryInfo.matchTitle" clearable @clear="getTeamMatchesList">
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
                        @click="getTeamMatchesList">搜索</el-button>
                </el-col>
            </el-row>
            <!-- 用户列表 -->
            <!-- border边框   stripe隔行变色 -->
            <el-table :data="teamMatcheslist" :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="赛事编号" width="140" prop="matchId" alin></el-table-column>
                <el-table-column label="赛事名称" width="290" prop="matchTitle" alin></el-table-column>
                <el-table-column label="团队编号" width="90" prop="teamId"></el-table-column>
                <el-table-column label="团队名称" width="140" prop="teamName"></el-table-column>
                <el-table-column label="成绩" width="90" prop="teamScore"></el-table-column>
                <el-table-column label="报名状态" width="100" prop="status" :formatter="statusFormatter"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-button type="primary" icon="el-icon-edit" style="background-color: grey; border-color: grey;"
                            circle size="mini" @click="showUpdateDialog(scope.row.matchId)"></el-button>
                        <!-- 删除 -->
                        <el-button type="danger" icon="el-icon-delete" circle size="mini"
                            @click="deleteTeamMatches(scope.row.matchId)"></el-button>

                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum" :page-sizes="[1, 6, 10, 50]" :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
        </el-card>


        <!-- 修改团队赛事对话框 -->
        <el-dialog title="修改团队赛事" :visible.sync="updateDialogVisible" width="50%" @close="updateDialogClosed">
            <!-- 内容主体区域 -->
            <el-form :model="updateForm" :rules="updateFormRules" ref="updateFormRef" label-width="90px">
                <!-- 赛事编号 -->
                <el-form-item label="赛事编号" prop="matchId">
                    <el-input v-model="updateForm.matchId" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 赛事名称 -->
                <el-form-item label="赛事名称" prop="matchTitle">
                    <el-input v-model="updateForm.matchTitle" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 团队编号 -->
                <el-form-item label="团队编号" prop="teamId">
                    <el-input v-model="updateForm.teamId" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 团队名称 -->
                <el-form-item label="团队名称" prop="teamName">
                    <el-input v-model="updateForm.teamName" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 成绩 -->
                <el-form-item label="成绩" prop="teamScore">
                    <el-input v-model="updateForm.teamScore"></el-input>
                </el-form-item>
                <!-- 报名状态 -->
                <el-form-item label="报名状态" prop="status">
                    <template>
                        <el-radio v-model="updateForm.status" label="1">已通过</el-radio>
                        <el-radio v-model="updateForm.status" label="2">未通过</el-radio>
                    </template>
                </el-form-item>

            </el-form>
            <!-- 内容底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button type="info" @click="resetupdateForm">重置</el-button>
                <el-button @click="updateDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateTeamMatchesInfo">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {

            disabled: false,//修改的时候不能修改
            // 请求数据
            queryInfo: {
                status: "",
                pageNum: 1,
                pageSize: 6,
                matchTitle: "",
            },
            teamMatcheslist: [], // 用户列表
            options: [
                {
                    //报名状态
                    value: "申请中",
                    label: "申请中",
                },
                {
                    value: "已通过",
                    label: "已通过",
                },
                {
                    value: "未通过",
                    label: "未通过",
                },
            ],
            total: 0, // 最大数据记录
            // 控制修改用户对话框显示/隐藏
            updateDialogVisible: false,
            // 修改用户信息
            updateForm: {
                matchId: '',
                matchTitle: '',
                teamId: '',
                teamName: '',
                teamScore: '',
                status: '',
            },
            // 修改用户表单验证规则
            updateFormRules: {
                teamScore: [
                    { required: true, message: "请输入成绩", trigger: "blur" },
                ],
                status: [
                    { required: true, message: "请设置状态", trigger: "blur" },
                ],
            },
        };
    },
    created() {
        this.getTeamMatchesList();
    },
    methods: {
        statusFormatter(row, column) {
            const status = row.status
            if (status == 1) {
                return '已通过'
            } else if (status == 2) {
                return '未通过'
            } else {
                return '申请中'
            }
        },
        async getTeamMatchesList() {
            // 调用get请求
            const { data: res } = await this.$http.get("allTeamMatches", {
                params: this.queryInfo,
            });
            this.teamMatcheslist = res.data; // 将返回数据赋值
            this.total = res.numbers; // 总个数
        },
        async resetupdateForm() {
            if (this.title == "修改团队赛事") {
                const { data: res } = await this.$http.get("getUpdateTeamMatches?id=" + this.updateForm.matchId);
                this.updateForm = res.data;
                return
            }
            this.$refs.updateFormRef.resetFields();
        },
        // 监听pageSize改变的事件(更改变每页显示条数时)
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize;
            this.getTeamMatchesList(); // 数据发生改变重新申请数据
        },
        // 监听pageNum改变的事件(点击页数时候)
        handleCurrentChange(newPage) {
            this.queryInfo.pageNum = newPage;
            this.getTeamMatchesList(); // 数据发生改变重新申请数据
        },


        // 展示修改框
        async showUpdateDialog(id) {
            this.disabled = true
            console.log()
            const { data: res } = await this.$http.get("getUpdateTeamMatches?id=" + id);
            this.updateForm = res.data;
            this.updateDialogVisible = true;
        },
        // 关闭窗口
        updateDialogClosed() {
            this.$refs.updateFormRef.resetFields();
        },
        // 确认修改
        updateTeamMatchesInfo() {
            this.$refs.updateFormRef.validate(async (valid) => {
                if (!valid) return;
                // 发起请求
                const { data: res } = await this.$http.put("updateTeamMatches", this.updateForm);
                if (res.code != "success") return this.$message.error("操作失败！！！");
                this.$message.success("操作成功！！！");
                //隐藏
                this.updateDialogVisible = false;
                this.getTeamMatchesList();
            });
        },
        // 删除按钮
        async deleteTeamMatches(id) {
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
            const { data: res } = await this.$http.delete("deleteTeamMatches?id=" + id);
            if (res.code != "success") {
                return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
            this.getTeamMatchesList();
        },
    },
};
</script>
<style lang="less" scoped></style>