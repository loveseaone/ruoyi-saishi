<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="队伍名称" prop="teamId">
        <el-select v-model="queryParams.teamId" placeholder="请选择队伍名称" clearable filterable>
          <el-option
            v-for="item in teamsList"
            :key="item.teamId"
            :label="item.teamName"
            :value="item.teamId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="角色" prop="role">
        <el-select v-model="queryParams.role" placeholder="请选择角色" clearable>
          <el-option label="领队" value="领队" />
          <el-option label="教练" value="教练" />
          <el-option label="工会人员" value="工会人员" />
          <el-option label="男队员" value="男队员" />
          <el-option label="女队员" value="女队员" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="录入" :value="0" />
          <el-option label="待审核" :value="1" />
          <el-option label="已确认" :value="2" />
          <el-option label="已驳回" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['activity:athletes:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['activity:athletes:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['activity:athletes:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="athletesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="运动员ID" align="center" prop="athleteId" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="gender">
        <template slot-scope="scope">
          <span>{{ scope.row.gender === 0 ? '男' : '女' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="身份证号" align="center" prop="idCard" />
      <el-table-column label="队伍名称" align="center" prop="teamName" />
      <el-table-column label="角色" align="center" prop="role" />
      <el-table-column label="是否上场" align="center" prop="isOnField">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isOnField === 1">是</el-tag>
          <el-tag v-else>否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0">录入</el-tag>
          <el-tag v-else-if="scope.row.status === 1">待审核</el-tag>
          <el-tag v-else-if="scope.row.status === 2">已确认</el-tag>
          <el-tag v-else-if="scope.row.status === 3">已驳回</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activity:athletes:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activity:athletes:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改运动员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别" clearable>
            <el-option label="男" :value="0" />
            <el-option label="女" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="队伍名称" prop="teamId">
          <el-select v-model="form.teamId" placeholder="请选择队伍名称" filterable clearable>
            <el-option
              v-for="item in teamsList"
              :key="item.teamId"
              :label="item.teamName"
              :value="item.teamId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色" clearable>
            <el-option label="领队" value="领队" />
            <el-option label="教练" value="教练" />
            <el-option label="工会人员" value="工会人员" />
            <el-option label="男队员" value="男队员" />
            <el-option label="女队员" value="女队员" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否上场" prop="isOnField">
          <el-select v-model="form.isOnField" placeholder="请选择是否上场" clearable>
            <el-option label="是" :value="1" />
            <el-option label="否" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" clearable>
            <el-option label="录入" :value="0" />
            <el-option label="待审核" :value="1" />
            <el-option label="已确认" :value="2" />
            <el-option label="已驳回" :value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAthletes, getAthlete, delAthlete, addAthlete, updateAthlete } from "@/api/activity/athletes";
import { listTeams } from "@/api/activity/teams";

export default {
  name: "Athletes",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 运动员表格数据
      athletesList: [],
      // 队伍列表数据
      teamsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        teamId: null,
        name: null,
        phone: null,
        role: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" }
        ],
        role: [
          { required: true, message: "角色不能为空", trigger: "change" }
        ],
        isOnField: [
          { required: true, message: "是否上场不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getTeamsList();
    this.getList();
  },
  methods: {
    /** 查询队伍列表 */
    getTeamsList() {
      listTeams({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.teamsList = response.rows;
      });
    },
    /** 查询运动员列表 */
    getList() {
      this.loading = true;
      listAthletes(this.queryParams).then(response => {
        this.athletesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        athleteId: null,
        teamId: null,
        name: null,
        gender: null,
        phone: null,
        idCard: null,
        role: null,
        isOnField: 0,
        status: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.athleteId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加运动员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const athleteId = row.athleteId || this.ids;
      getAthlete(athleteId).then(response => {
        const data = response.data || response;
        // 确保status字段是数字类型
        if (data.status !== undefined && data.status !== null) {
          data.status = parseInt(data.status);
        }
        // 确保gender字段是数字类型
        if (data.gender !== undefined && data.gender !== null) {
          data.gender = parseInt(data.gender);
        }
        // 确保isOnField字段是数字类型
        if (data.isOnField !== undefined && data.isOnField !== null) {
          data.isOnField = parseInt(data.isOnField);
        }
        this.form = data;
        this.open = true;
        this.title = "修改运动员";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.athleteId != null) {
            updateAthlete(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAthlete(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const athleteIds = row.athleteId || this.ids;
      this.$modal.confirm('是否确认删除运动员编号为"' + athleteIds + '"的数据项？').then(function() {
        return delAthlete(athleteIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>