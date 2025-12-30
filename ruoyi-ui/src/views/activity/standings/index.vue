<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="队伍名称" prop="teamId">
        <el-select
          v-model="queryParams.teamId"
          placeholder="请选择队伍"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="item in teamsList"
            :key="item.teamId"
            :label="item.teamName"
            :value="item.teamId"
          />
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
          v-hasPermi="['activity:standings:add']"
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
          v-hasPermi="['activity:standings:edit']"
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
          v-hasPermi="['activity:standings:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="standingsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="standingId" width="60" />
      <el-table-column label="队伍名称" align="center" prop="teamName" width="156" />
      <el-table-column label="小组" align="center" prop="groupCode" width="60" />
      <el-table-column label="已赛场次" align="center" prop="matchesPlayed" width="70" />
      <el-table-column label="胜场" align="center" prop="wins" width="60" />
      <el-table-column label="负场" align="center" prop="losses" width="60" />
      <el-table-column label="积分" align="center" prop="points" width="60" />
      <el-table-column label="胜局" align="center" prop="setsWon" width="60" />
      <el-table-column label="负局" align="center" prop="setsLost" width="60" />
      <el-table-column label="总得分" align="center" prop="pointsFor" width="60" />
      <el-table-column label="失分" align="center" prop="pointsAgainst" width="60" />
      <el-table-column label="C值" align="center" prop="cValue" width="49" />
      <el-table-column label="Z值" align="center" prop="zValue" width="49" />
      <el-table-column label="小组排名" align="center" prop="rankInGroup" width="70" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activity:standings:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activity:standings:remove']"
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

    <!-- 添加或修改积分排名对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="队伍名称" prop="teamId">
              <el-select v-model="form.teamId" placeholder="请选择队伍" filterable clearable>
                <el-option
                  v-for="item in teamsList"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="小组" prop="groupCode">
              <el-input v-model="form.groupCode" placeholder="请输入小组标识" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="已赛场次" prop="matchesPlayed">
              <el-input-number v-model="form.matchesPlayed" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="胜场" prop="wins">
              <el-input-number v-model="form.wins" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负场" prop="losses">
              <el-input-number v-model="form.losses" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="积分" prop="points">
              <el-input-number v-model="form.points" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="胜局" prop="setsWon">
              <el-input-number v-model="form.setsWon" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负局" prop="setsLost">
              <el-input-number v-model="form.setsLost" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总得分" prop="pointsFor">
              <el-input-number v-model="form.pointsFor" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="失分" prop="pointsAgainst">
              <el-input-number v-model="form.pointsAgainst" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="C值" prop="cValue">
              <el-input-number v-model="form.cValue" controls-position="right" :min="0" :precision="2" :step="0.01" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Z值" prop="zValue">
              <el-input-number v-model="form.zValue" controls-position="right" :min="0" :precision="2" :step="0.01" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="小组排名" prop="rankInGroup">
              <el-input-number v-model="form.rankInGroup" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStandings, getStanding, delStanding, addStanding, updateStanding } from "@/api/activity/standings";
import { listTeams } from "@/api/activity/teams";

export default {
  name: "Standings",
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
      // 积分排名表格数据
      standingsList: [],
      // 队伍选项列表
      teamsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        teamId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activityId: [
          { required: true, message: "活动ID不能为空", trigger: "blur" }
        ],
        teamId: [
          { required: true, message: "队伍不能为空", trigger: "blur" }
        ],
        groupCode: [
          { required: true, message: "小组标识不能为空", trigger: "blur" }
        ],
        matchesPlayed: [
          { required: true, message: "已赛场次不能为空", trigger: "blur" }
        ],
        wins: [
          { required: true, message: "胜场不能为空", trigger: "blur" }
        ],
        losses: [
          { required: true, message: "负场不能为空", trigger: "blur" }
        ],
        points: [
          { required: true, message: "积分不能为空", trigger: "blur" }
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
    /** 查询积分排名列表 */
    getList() {
      this.loading = true;
      listStandings(this.queryParams).then(response => {
        // 处理字段名不一致的问题
        if (response.rows && Array.isArray(response.rows)) {
          response.rows = response.rows.map(item => {
            if (item.zvalue !== undefined) {
              item.zValue = item.zvalue;
            }
            if (item.cvalue !== undefined) {
              item.cValue = item.cvalue;
            }
            return item;
          });
        }
        this.standingsList = response.rows;
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
        standingId: null,
        teamId: null,
        groupCode: null,
        matchesPlayed: 0,
        wins: 0,
        losses: 0,
        points: 0,
        setsWon: 0,
        setsLost: 0,
        pointsFor: 0,
        pointsAgainst: 0,
        cValue: 0,
        zValue: 0,
        rankInGroup: 0
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
      this.ids = selection.map(item => item.standingId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加积分排名";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const standingId = row.standingId || this.ids;
      getStanding(standingId).then(response => {
        const data = response.data || response;
        // 处理字段名不一致的问题
        if (data.zvalue !== undefined) {
          data.zValue = data.zvalue;
        }
        if (data.cvalue !== undefined) {
          data.cValue = data.cvalue;
        }
        this.form = data;
        this.open = true;
        this.title = "修改积分排名";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.standingId != null) {
            updateStanding(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStanding(this.form).then(response => {
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
      const standingIds = row.standingId || this.ids;
      this.$modal.confirm('是否确认删除积分排名编号为"' + standingIds + '"的数据项？').then(function() {
        return delStanding(standingIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>