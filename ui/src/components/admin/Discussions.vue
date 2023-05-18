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
                    <el-input placeholder="请输入姓名查找" v-model="queryInfo.studnetName" clearable @clear="getDiscussionsList">
                    </el-input>
                </el-col>
                <el-col :span="6">
                    <!-- 搜索添加 -->
                    <el-input placeholder="请输入内容查找" v-model="queryInfo.disContent" clearable @clear="getDiscussionsList">
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" style="background-color:#009688 ; color: #FFF;"
                        @click="getDiscussionsList">搜索</el-button>
                </el-col>
            </el-row>
            <!-- border边框   stripe隔行变色 -->
            <el-table :data="discussionsList" :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="发表时间" width="170" prop="publishTime">
                    <template slot-scope="scope">
                        {{ scope.row.publishTime | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column label="发表者" width="90" prop="studentName"></el-table-column>
                <el-table-column label="发表内容" width="250" prop="disContent"></el-table-column>
                <el-table-column label="评论者" width="90" prop="studentName"></el-table-column>
                <el-table-column label="评论内容" width="250" prop="studentName"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-button type="primary" icon="el-icon-edit" style="background-color: grey; border-color: grey;"
                            circle size="mini" @click="showEditDialog(scope.row.discussionId)"></el-button>
                        <!-- 删除 -->
                        <el-button type="danger" icon="el-icon-delete" circle size="mini"
                            @click="deleteDiscussions(scope.row.discussionId)"></el-button>

                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum" :page-sizes="[1, 6, 10, 50]" :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
        </el-card>
        <el-dialog title="修改信息" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
            <el-form :model="editForm" ref="editFormRef" label-width="70px">
                <!-- 发表时间 -->
                <el-form-item label="发表时间" prop="publishTime">
                    <el-input v-model="editForm.publishTime" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 发表者 -->
                <el-form-item label="发表者" prop="studentName">
                    <el-input v-model="editForm.studentName" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 发表内容 -->
                <el-form-item label="发表内容" prop="disContent">
                    <el-input v-model="editForm.disContent" :disabled="disabled"></el-input>
                </el-form-item>
                <!-- 评论者 -->
                <el-form-item label="评论者" prop="">
                    <el-input v-model="editForm.studentName"></el-input>
                </el-form-item>
                <!-- 评论内容 -->
                <el-form-item label="评论内容" prop="studentName">
                    <el-input v-model="editForm.studentName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editDiscussionsInfo">确 定</el-button>
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
            discussionsList: [], // 列表
            total: 0, // 最大数据记录
            // 控制修改用户对话框显示/隐藏
            editDialogVisible: false,
            // 修改用户信息
            editForm: {},
        };
    },
    created() {
        this.getDiscussionsList();
    },
    filters: {
        formatDate(time) {
            const
                date = new Date(time),
                y = date.getFullYear(),
                m = date.getMonth() + 1,
                d = date.getDate()
            return `${y}-${m < 10 ? '0' + m : m}-${d < 10 ? '0' + d : d}`
        },
    },
    methods: {
        async getDiscussionsList() {
            // 调用get请求
            const { data: res } = await this.$http.get("allDiscussions", {
                params: this.queryInfo,
            });
            this.discussionsList = res.data; // 将返回数据赋值
            this.total = res.numbers; // 总个数
        },
        async reseteditForm() {
            if (this.title == "修改信息") {
                const { data: res } = await this.$http.get("getUpdateDis?id=" + this.editForm.id);
                this.editForm = res.data;
                return
            }
            this.$refs.editFormRef.resetFields();
        },
        // 监听pageSize改变的事件(更改变每页显示条数时)
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize;
            this.getDiscussionsList(); // 数据发生改变重新申请数据
        },
        // 监听pageNum改变的事件(点击页数时候)
        handleCurrentChange(newPage) {
            this.queryInfo.pageNum = newPage;
            this.getDiscussionsList(); // 数据发生改变重新申请数据
        },
        // 展示修改框
        async showEditDialog(id) {
            const { data: res } = await this.$http.get("getUpdateDis?id=" + id);
            this.editForm = res.data;
            this.editDialogVisible = true;
        },
        // 关闭窗口
        editDialogClosed() {
            this.$refs.editFormRef.resetFields();
        },
        // 确认修改
        editDiscussionsInfo() {
            this.$refs.editFormRef.validate(async (valid) => {
                if (!valid) return;
                // 发起请求
                const { data: res } = await this.$http.put("updateDiscussions", this.editForm);
                if (res.code != 200) return this.$message.error("操作失败！！！");
                this.$message.success("操作成功！！！");
                //隐藏
                this.editDialogVisible = false;
                this.getDiscussionsList();
            });
        },
        // 删除按钮
        async deleteDiscussions(id) {
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
            const { data: res } = await this.$http.delete("deleteDiscussions?id=" + id);
            if (res.code != 200) {
                return this.$message.error("操作失败！！！");
            }
            this.$message.success("操作成功！！！");
            this.getDiscussionsList();
        },
    },
};
</script>
<style lang="less" scoped></style>