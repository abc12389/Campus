<template>
    <div>
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
                <el-table-column label="赛事名称" width="200" prop="matchTitle"></el-table-column>
                <el-table-column label="赛事类型" width="100" prop="matchType"></el-table-column>
                <el-table-column label="报名时间" prop="enrollStartTime,enrollEndTime" :formatter="formatDate">
                    <template slot-scope="scope">
                        {{ scope.row.enrollStartTime }} - {{ scope.row.enrollEndTime }}
                    </template>
                </el-table-column>
                <el-table-column label="比赛时间" prop="matchStartTime,matchEndTime">
                    <template slot-scope="scope">
                        {{ scope.row.matchStartTime }} - {{ scope.row.matchEndTime }}
                    </template>
                </el-table-column>

                <el-table-column label="赛事状态" width="100" prop="matchStatus"></el-table-column>
                <el-table-column label="操作" width="120">
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
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="90px">
                <!-- 赛事名称 -->
                <el-form-item label="赛事名称" prop="matchTitle">
                    <el-input v-model="addForm.matchTitle"></el-input>
                </el-form-item>
                <!-- 赛事描述 -->
                <el-form-item label="赛事描述" prop="textarea">
                    <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="addForm.textarea">"></el-input>
                </el-form-item>
                <el-form-item label="报名时间" required>
                    <el-col :span="11">
                        <el-form-item prop="enrollStartTime">
                            <template>
                                <div class="block">
                                    <el-date-picker v-model="addForm.enrollStartTime" type="date" placeholder="报名开始日期">
                                    </el-date-picker>
                                </div>
                            </template>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">至</el-col>
                    <el-col :span="11">
                        <el-form-item prop="enrollEndTime">
                            <template>
                                <div class="block">
                                    <el-date-picker v-model="addForm.enrollEndTime" type="date" placeholder="报名结束日期">
                                    </el-date-picker>
                                </div>
                            </template>
                        </el-form-item>
                    </el-col>
                </el-form-item>


                <el-form-item label="比赛时间" required>
                    <el-col :span="11">
                        <el-form-item prop="matchStartTime">
                            <template>
                                <div class="block">
                                    <el-date-picker v-model="addForm.matchStartTime" type="date" placeholder="比赛开始日期">
                                    </el-date-picker>
                                </div>
                            </template>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">至</el-col>
                    <el-col :span="11">
                        <el-form-item prop="matchEndTime">
                            <template>
                                <div class="block">
                                    <el-date-picker v-model="addForm.matchEndTime" type="date" placeholder="比赛结束日期">
                                    </el-date-picker>
                                </div>
                            </template>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <!-- 主办方 -->
                <el-form-item label="主办方" prop="sponsor">
                    <el-input v-model="addForm.sponsor"></el-input>
                </el-form-item>
                <!-- 参赛对象 -->
                <el-form-item label="参赛对象" prop="participant">
                    <template>
                        <el-checkbox-group v-model="addForm.checkList">
                            <el-checkbox label="大一"></el-checkbox>
                            <el-checkbox label="大二"></el-checkbox>
                            <el-checkbox label="大三"></el-checkbox>
                            <el-checkbox label="大四"></el-checkbox>
                        </el-checkbox-group>
                    </template>
                </el-form-item>
                <!-- 赛事类型 -->
                <el-form-item label="赛事类型" prop="matchType">

                    <span class="demonstration" style="margin-left: 10px;">类别: </span>
                    <el-select v-model="addForm.matchType" placeholder="请选择类别">
                        <el-option v-for="item in optionsMatchType" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 性别要求 -->
                <el-form-item label="性别要求" prop="matchSex">
                    <template>
                        <el-radio v-model="addForm.matchSex" label="仅限男生">仅限男生</el-radio>
                        <el-radio v-model="addForm.matchSex" label="仅限女生">仅限女生</el-radio>
                        <el-radio v-model="addForm.matchSex" label="不限性别">不限性别</el-radio>
                    </template>
                </el-form-item>
                <!-- 参赛规模 -->
                <el-form-item label="参赛规模" prop="competitionScale">
                    <el-radio v-model="addForm.competitionScale" label="团体赛事">团体赛事</el-radio>
                    <el-radio v-model="addForm.competitionScale" label="个人赛事">个人赛事</el-radio>
                </el-form-item>

                <el-form-item required>

                    <el-row>
                        <el-col :span="5">
                            <el-form-item label-width="2" label="共" prop="maxNum">
                                <el-input style="width: 60px;" v-model="addForm.maxNum"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col class="line" :span="2">队</el-col>
                        <el-col :span="7">
                            <el-form-item label="每队" prop="teamPersonNum">
                                <el-input style="width: 60px;" v-model="addForm.teamPersonNum"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col class="line" :span="2">人</el-col>
                    </el-row>
                </el-form-item>


                <!-- 奖项设置 -->
                <el-form-item label="奖项设置" prop="prizeFirst">
                    <el-input placeholder="一等奖数量" v-model="addForm.prizeFirst"></el-input>
                </el-form-item>
                <el-form-item label="奖项设置" prop="prizeSecond">
                    <el-input placeholder="二等奖数量" v-model="addForm.prizeSecond"></el-input>
                </el-form-item>
                <el-form-item label="奖项设置" prop="prizeThird">
                    <el-input placeholder="三等奖数量" v-model="addForm.prizeThird"></el-input>
                </el-form-item>

                <!-- 参赛方式 -->
                <el-form-item label="参赛方式" prop="matchEntry">
                    <el-input v-model="addForm.matchEntry"></el-input>
                </el-form-item>
                <!-- 联系方式 -->
                <el-form-item label="联系方式" prop="contactPerson">
                    <el-input v-model="addForm.contactPerson"></el-input>
                </el-form-item>

            </el-form>
            <!-- 内容底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button type="info" @click="resetaddForm">重置</el-button>
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addMatch">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {


            //赛事描述文本
            textarea: '',
            //时间选择器设置
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                },
            },
            // 请求数据
            queryInfo: {
                // id: "",
                matchTitle: "", //赛事名称
                pageNum: 1,
                pageSize: 4,
                department: "", //学院

            },

            matchlist: [], // 表格显示赛事列表
            options: [
                {
                    //赛事状态
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
            optionsMatchType: [
                {
                    value: "文艺",
                    label: "文艺",
                },
                {
                    value: "科技",
                    label: "科技",
                },
            ],


            total: 0, // 最大数据记录
            addDialogVisible: false, // 对话框显示
            // 添加赛事表单项
            addForm: {
                checkList: [],//多选 大一..大四
                matchTitle: "",
                matchInfo: "",
                enrollStartTime: "",
                enrollEndTime: "",
                matchStatTime: "",
                matchEndTime: "",
                sponsor: "",
                //最大队数
                maxNum: "",
                allowFirst: "",
                allowSecond: "",
                allowThird: "",
                allowFourth: "",
                //队伍人数，可以是一个人一个队伍（如果一个队一个人是个人赛）
                teamPersonNum: "",
                //赛事类型
                matchType: "",
                prizeFirst: "",
                prizeSecond: "",
                prizeThird: "",
                matchSex: "",
                //参赛方式
                matchEntry: "",
                //联系方式
                contactPerson: "",
                competitionScale: "",
            },
            // 控制修改赛事对话框显示/隐藏
            editDialogVisible: false,
            // 修改赛事信息
            editForm: {},
            // 验证规则
            addFormRules: {
                matchTitle: [
                    { required: true, message: "请输入赛事名称", trigger: "blur" },
                ],
                textarea: [
                    { required: true, message: "请对赛事描述", trigger: "blur" },
                ],
                enrollTime: [
                    { required: true, message: "请选择报名时间", trigger: "blur" },
                ],
                matchTime: [
                    { required: true, message: "请选择比赛时间", trigger: "blur" },
                ],
                sponsor: [
                    { required: true, message: "请输入主办方", trigger: "blur" },
                ],
                //大一、大二、大三、大四
                participant: [
                    { required: true, message: "请选择参赛对象", trigger: "blur" },
                ],
                matchType: [
                    { required: true, message: "请选择赛事类型", trigger: "blur" },
                ],
                matchSex: [
                    { required: true, message: "请选择性别要求", trigger: "blur" },
                ],
                matchStartTime: [
                    { required: true, message: "请选择比赛开始时间", trigger: "blur" },
                ],
                matchEndTime: [
                    { required: true, message: "请选择比赛结束时间", trigger: "blur" },
                ],
                enrollStartTime: [
                    { required: true, message: "请选择报名开始时间", trigger: "blur" },
                ],
                enrollEndTime: [
                    { required: true, message: "请选择报名结束时间", trigger: "blur" },
                ],

                maxNum: [
                    { required: true, message: "请输入队数", trigger: "blur" },
                ],
                teamPersonNum: [
                    { required: true, message: "请输入人数", trigger: "blur" },
                ],
                prizeFirst: [
                    { required: true, message: "请输入一等奖", trigger: "blur" },
                ],
                prizeSecond: [
                    { required: true, message: "请输入二等奖", trigger: "blur" },
                ],
                prizeThird: [
                    { required: true, message: "请输入三等奖", trigger: "blur" },
                ],

                matchEntry: [
                    { required: true, message: "请输入参赛方式", trigger: "blur" },
                ],
                contactPerson: [
                    { required: true, message: "请输入联系方式", trigger: "blur" },
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

    filters: {
        formatDate(nowTime) {
            var moment = require("moment");
            return moment(nowTime).format("YYYY-MM-DD");
        },
    },

    methods: {


        formatDate(row, column) {
            // 获取单元格数据
            let data = row[column.property];
            if (data == null) {
                return null;
            }
            let dt = new Date(data);
            return (
                dt.getFullYear() +
                "-" +
                (dt.getMonth() + 1) +
                "-" +
                dt.getDate() +
                " " +
                dt.getHours() +
                ":" +
                dt.getMinutes() +
                ":" +
                dt.getSeconds()
            );
        },

        async getMatchesList() {
            // 调用post请求
            const { data: res } = await this.$http.get("allMatches", {
                params: this.queryInfo,
            });
            this.matchlist = res.data; // 将返回数据赋值
            this.total = res.numbers; // 总个数
        },
        resetaddForm() {
            this.$refs.addFormRef.resetFields();
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
            console.log('ref', this.$refs.addFormRef);
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
<style lang="less" scoped>
:v-deep .el-col el-col-12 {
    padding-left: 0;
}
</style>