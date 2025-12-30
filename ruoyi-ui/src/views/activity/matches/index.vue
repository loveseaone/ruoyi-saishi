<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="比赛日期" prop="matchDate">
        <el-date-picker
          v-model="queryParams.matchDate"
          type="date"
          placeholder="请选择比赛日期"
          value-format="yyyy-MM-dd"
          clearable
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-time-picker
          v-model="queryParams.startTime"
          placeholder="请选择开始时间"
          value-format="HH:mm:ss"
          clearable
        />
      </el-form-item>
      <el-form-item label="阶段" prop="stage">
        <el-select v-model="queryParams.stage" placeholder="请选择阶段" clearable>
          <el-option label="小组赛" :value="1" />
          <el-option label="淘汰赛" :value="2" />
          <el-option label="排位赛" :value="3" />
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
          v-hasPermi="['activity:matches:add']"
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
          v-hasPermi="['activity:matches:edit']"
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
          v-hasPermi="['activity:matches:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="matchesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="场次ID" align="center" prop="matchId" />
      <el-table-column label="场次编号" align="center" prop="matchCode" />
      <el-table-column label="阶段" align="center" prop="stage">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.stage === 1">小组赛</el-tag>
          <el-tag v-else-if="scope.row.stage === 2">淘汰赛</el-tag>
          <el-tag v-else-if="scope.row.stage === 3">排位赛</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="赛制" align="center" prop="format">
        <template slot-scope="scope">
          <span>{{ scope.row.format === 3 ? '三局两胜' : '五局三胜' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="队伍A" align="center" prop="teamAName" />
      <el-table-column label="队伍B" align="center" prop="teamBName" />
      <el-table-column label="场地" align="center" prop="court" />
      <el-table-column label="比赛日期" align="center" prop="matchDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.matchDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0">未开始</el-tag>
          <el-tag type="warning" v-else-if="scope.row.status === 1">进行中</el-tag>
          <el-tag type="success" v-else-if="scope.row.status === 2">已结束</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="胜方队伍" align="center" prop="winnerName" />
      <el-table-column label="得分" align="center" prop="scoreData" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activity:matches:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activity:matches:remove']"
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

    <!-- 添加或修改场次对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="场次编号" prop="matchCode">
              <el-input v-model="form.matchCode" placeholder="请输入场次编号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="阶段" prop="stage">
              <el-select v-model="form.stage" placeholder="请选择阶段" clearable>
                <el-option label="小组赛" :value="1" />
                <el-option label="淘汰赛" :value="2" />
                <el-option label="排位赛" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="赛制" prop="format">
              <el-select v-model="form.format" placeholder="请选择赛制" clearable>
                <el-option label="三局两胜" :value="3" />
                <el-option label="五局三胜" :value="5" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="轮次描述" prop="roundDesc">
              <el-input v-model="form.roundDesc" placeholder="请输入轮次描述" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="A队小组" prop="groupA">
              <el-input v-model="form.groupA" placeholder="请输入A队小组" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="B队小组" prop="groupB">
              <el-input v-model="form.groupB" placeholder="请输入B队小组" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="A队排名" prop="rankA">
              <el-input-number v-model="form.rankA" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="B队排名" prop="rankB">
              <el-input-number v-model="form.rankB" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="队伍A" prop="teamAId">
              <el-select v-model="form.teamAId" placeholder="请选择队伍A" filterable clearable>
                <el-option
                  v-for="item in teamsList"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="队伍B" prop="teamBId">
              <el-select v-model="form.teamBId" placeholder="请选择队伍B" filterable clearable>
                <el-option
                  v-for="item in teamsList"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="场地" prop="court">
              <el-input v-model="form.court" placeholder="请输入场地" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="比赛日期" prop="matchDate">
              <el-date-picker
                v-model="form.matchDate"
                type="date"
                placeholder="请选择比赛日期"
                value-format="yyyy-MM-dd"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-time-picker
                v-model="form.startTime"
                placeholder="请选择开始时间"
                value-format="HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" clearable>
                <el-option label="未开始" :value="0" />
                <el-option label="进行中" :value="1" />
                <el-option label="已结束" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="胜方队伍" prop="winnerId">
              <el-select v-model="form.winnerId" placeholder="请选择胜方队伍" filterable clearable>
                <el-option
                  v-for="item in teamsList"
                  :key="item.teamId"
                  :label="item.teamName"
                  :value="item.teamId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="比分设置">
              <el-table :data="scoreData" style="width: 100%">
                <el-table-column label="局数" prop="setNumber" width="80">
                  <template slot-scope="scope">
                    第{{ scope.row.setNumber }}局
                  </template>
                </el-table-column>
                <el-table-column label="A队得分" width="120">
                  <template slot-scope="scope">
                    <el-input-number
                      v-model="scope.row.scoreA"
                      controls-position="right"
                      :min="0"
                      size="small"
                    />
                  </template>
                </el-table-column>
                <el-table-column label="B队得分" width="120">
                  <template slot-scope="scope">
                    <el-input-number
                      v-model="scope.row.scoreB"
                      controls-position="right"
                      :min="0"
                      size="small"
                    />
                  </template>
                </el-table-column>
              </el-table>
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
import { listMatches, getMatch, delMatch, addMatch, updateMatch } from "@/api/activity/matches";
import { listTeams } from "@/api/activity/teams";

export default {
  name: "Matches",
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
      // 场次表格数据
      matchesList: [],
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
        matchDate: null,
        startTime: null,
        stage: null
      },
      // 表单参数
      form: {},
      // 比分数据
      scoreData: [
        { setNumber: 1, scoreA: 0, scoreB: 0 },
        { setNumber: 2, scoreA: 0, scoreB: 0 },
        { setNumber: 3, scoreA: 0, scoreB: 0 },
        { setNumber: 4, scoreA: 0, scoreB: 0 },
        { setNumber: 5, scoreA: 0, scoreB: 0 }
      ],
      // 表单校验
      rules: {
        activityId: [
          { required: true, message: "活动ID不能为空", trigger: "blur" }
        ],
        matchCode: [
          { required: true, message: "场次编号不能为空", trigger: "blur" }
        ],
        stage: [
          { required: true, message: "阶段不能为空", trigger: "change" }
        ],
        format: [
          { required: true, message: "赛制不能为空", trigger: "change" }
        ],
        teamAId: [
          { required: false, message: "队伍A不能为空", trigger: "change" }
        ],
        teamBId: [
          { required: false, message: "队伍B不能为空", trigger: "change" }
        ],
        court: [
          { required: true, message: "场地不能为空", trigger: "blur" }
        ],
        matchDate: [
          { required: true, message: "比赛日期不能为空", trigger: "change" }
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "change" }
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
    /** 查询场次列表 */
    getList() {
      this.loading = true;
      listMatches(this.queryParams).then(response => {
        this.matchesList = response.rows;
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
        matchId: null,
        matchCode: null,
        stage: null,
        format: null,
        roundDesc: null,
        groupA: null,
        groupB: null,
        rankA: null,
        rankB: null,
        teamAId: null,
        teamBId: null,
        court: null,
        matchDate: null,
        startTime: null,
        status: 0,
        winnerId: null
      };
      
      // 重置比分数据
      this.scoreData = [
        { setNumber: 1, scoreA: 0, scoreB: 0 },
        { setNumber: 2, scoreA: 0, scoreB: 0 },
        { setNumber: 3, scoreA: 0, scoreB: 0 },
        { setNumber: 4, scoreA: 0, scoreB: 0 },
        { setNumber: 5, scoreA: 0, scoreB: 0 }
      ];
      
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
      this.ids = selection.map(item => item.matchId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加场次";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const matchId = row.matchId || this.ids;
      getMatch(matchId).then(response => {
        const data = response.data || response;
        //打印返回数据
        console.log('response.data:',data);
        // 确保stage字段是数字类型
        if (data.stage !== undefined && data.stage !== null) {
          data.stage = parseInt(data.stage);
        }
        // 确保format字段是数字类型
        if (data.format !== undefined && data.format !== null) {
          data.format = parseInt(data.format);
        }
        // 确保teamAId字段是数字类型
        if (data.teamAId !== undefined && data.teamAId !== null) {
          data.teamAId = parseInt(data.teamAId);
        }
        // 确保teamBId字段是数字类型
        if (data.teamBId !== undefined && data.teamBId !== null) {
          data.teamBId = parseInt(data.teamBId);
        }
        // 确保court字段是数字类型
        if (data.court !== undefined && data.court !== null) {
          data.court = parseInt(data.court);
        }
        // 确保status字段是数字类型
        if (data.status !== undefined && data.status !== null) {
          data.status = parseInt(data.status);
        }
        // 确保winnerId字段是数字类型
        if (data.winnerId !== undefined && data.winnerId !== null) {
          data.winnerId = parseInt(data.winnerId);
        }
        this.form = data;
        // 处理比分数据
        const newScoreData = [];
        for (let i = 1; i <= 5; i++) {
          newScoreData.push({
            setNumber: i,
            scoreA: data[`scoreASet${i}`] || 0,
            scoreB: data[`scoreBSet${i}`] || 0
          });
        }
        // 使用 $set 确保响应式更新
        this.$set(this, 'scoreData', newScoreData);
        this.open = true;
        this.title = "修改场次";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理比分数据
          for (let i = 0; i < this.scoreData.length; i++) {
            const set = this.scoreData[i];
            this.form[`scoreASet${set.setNumber}`] = set.scoreA;
            this.form[`scoreBSet${set.setNumber}`] = set.scoreB;
          }
          
          // 如果队伍A或队伍B为空，则设置为0
          if (!this.form.teamAId) {
            this.form.teamAId = 0;
          }
          if (!this.form.teamBId) {
            this.form.teamBId = 0;
          }
          // 如果胜方队伍为空，则设置为0
          if (!this.form.winnerId) {
            this.form.winnerId = 0;
          }
          
          if (this.form.matchId != null) {
            updateMatch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMatch(this.form).then(response => {
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
      const matchIds = row.matchId || this.ids;
      this.$modal.confirm('是否确认删除场次编号为"' + matchIds + '"的数据项？').then(function() {
        return delMatch(matchIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>