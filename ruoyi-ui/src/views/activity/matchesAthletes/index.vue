<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="场次名称" prop="matchId">
        <el-select v-model="queryParams.matchId" placeholder="请选择场次" clearable filterable>
          <el-option
            v-for="item in matchesList"
            :key="item.matchId"
            :label="item.matchCode"
            :value="item.matchId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="队伍名称" prop="teamId">
        <el-select v-model="queryParams.teamId" placeholder="请选择队伍" clearable filterable>
          <el-option
            v-for="item in teamsList"
            :key="item.teamId"
            :label="item.teamName"
            :value="item.teamId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="参赛人员" prop="athleteId">
        <el-select v-model="queryParams.athleteId" placeholder="请选择参赛人员" clearable filterable>
          <el-option
            v-for="item in athletesList"
            :key="item.athleteId"
            :label="item.name"
            :value="item.athleteId">
          </el-option>
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
          v-hasPermi="['activity:matches:athletes:add']"
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
          v-hasPermi="['activity:matches:athletes:edit']"
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
          v-hasPermi="['activity:matches:athletes:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="matchesAthletesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="场次名称" align="center" prop="matchCode" />
      <el-table-column label="队伍名称" align="center" prop="teamName" />
      <el-table-column label="参赛人员" align="center" prop="athleteName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activity:matches:athletes:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activity:matches:athletes:remove']"
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

    <!-- 添加或修改场次成员关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="场次名称" prop="matchId">
          <el-select v-model="form.matchId" placeholder="请选择场次" filterable clearable>
            <el-option
              v-for="item in matchesList"
              :key="item.matchId"
              :label="item.matchCode"
              :value="item.matchId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="队伍名称" prop="teamId">
          <el-select v-model="form.teamId" placeholder="请选择队伍" filterable clearable @change="handleTeamChange">
            <el-option
              v-for="item in teamsList"
              :key="item.teamId"
              :label="item.teamName"
              :value="item.teamId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参赛人员" prop="athleteId">
          <el-select v-model="form.athleteId" placeholder="请选择参赛人员" filterable clearable>
            <el-option
              v-for="item in formAthletesList"
              :key="item.athleteId"
              :label="item.name"
              :value="item.athleteId">
            </el-option>
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
import { listMatchesAthletes, getMatchesAthletes, delMatchesAthletes, addMatchesAthletes, updateMatchesAthletes } from "@/api/activity/matchesAthletes";
import { listMatches } from "@/api/activity/matches";
import { listTeams } from "@/api/activity/teams";
import { listAthletes } from "@/api/activity/athletes";

export default {
  name: "MatchesAthletes",
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
      // 场次成员关联表格数据
      matchesAthletesList: [],
      // 场次列表数据
      matchesList: [],
      // 队伍列表数据
      teamsList: [],
      // 运动员列表数据（用于搜索）
      athletesList: [],
      // 运动员列表数据（用于表单）
      formAthletesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        matchId: null,
        teamId: null,
        athleteId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        matchId: [
          { required: true, message: "场次不能为空", trigger: "change" }
        ],
        teamId: [
          { required: true, message: "队伍不能为空", trigger: "change" }
        ],
        athleteId: [
          { required: true, message: "参赛人员不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getMatchesList();
    this.getTeamsList();
    this.getAthletesList();
    this.getList();
  },
  methods: {
    /** 查询场次列表 */
    getMatchesList() {
      listMatches({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.matchesList = response.rows;
      });
    },
    /** 查询队伍列表 */
    getTeamsList() {
      listTeams({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.teamsList = response.rows;
      });
    },
    /** 查询运动员列表 */
    getAthletesList() {
      listAthletes({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.athletesList = response.rows;
      });
    },
    /** 根据队伍ID查询运动员列表 */
    getAthletesByTeamId(teamId) {
      if (teamId) {
        listAthletes({ teamId: teamId, pageNum: 1, pageSize: 1000 }).then(response => {
          this.formAthletesList = response.rows;
        });
      } else {
        this.formAthletesList = [];
      }
    },
    /** 队伍选择变化时触发 */
    handleTeamChange(teamId) {
      this.getAthletesByTeamId(teamId);
      // 清空已选择的运动员
      this.form.athleteId = null;
    },
    /** 查询场次成员关联列表 */
    getList() {
      this.loading = true;
      listMatchesAthletes(this.queryParams).then(response => {
        this.matchesAthletesList = response.rows;
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
        id: null,
        matchId: null,
        teamId: null,
        athleteId: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加场次成员关联";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMatchesAthletes(id).then(response => {
        this.form = response.data;
        // 根据选中的队伍加载运动员列表
        if (this.form.teamId) {
          this.getAthletesByTeamId(this.form.teamId);
        }
        this.open = true;
        this.title = "修改场次成员关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMatchesAthletes(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMatchesAthletes(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除场次成员关联编号为"' + ids + '"的数据项？').then(function() {
        return delMatchesAthletes(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>