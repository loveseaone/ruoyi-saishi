package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityStandings;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.vo.ActivityStandingsVO;
import com.ruoyi.activity.service.IActivityStandingsService;
import com.ruoyi.activity.service.IActivityTeamsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动积分排名Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动积分排名管理")
@RestController
@RequestMapping("/activity/standings")
public class ActivityStandingsController extends BaseController
{
    @Autowired
    private IActivityStandingsService activityStandingsService;

    @Autowired
    private IActivityTeamsService activityTeamsService;

    /**
     * 查询活动积分排名列表
     */
    @ApiOperation("查询活动积分排名列表")
    @PreAuthorize("@ss.hasPermi('activity:standings:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityStandings activityStandings)
    {
        startPage();
        List<ActivityStandings> list = activityStandingsService.selectActivityStandingsList(activityStandings);
        TableDataInfo dataTable = getDataTable(list);
        //提供一个方法 List<ActivityStandings> -> List<ActivityStandingsVO>
        List<ActivityStandingsVO> listVO = convertToVOList(list);
        dataTable.setRows(listVO);
        return dataTable;
    }

    private List<ActivityStandingsVO> convertToVOList(List<ActivityStandings> standingsList) {

        // 查询所有的队伍信息，构建teamId到teamName的映射
        List<ActivityTeams> teamsList = activityTeamsService.selectActivityTeamsList(new ActivityTeams());
        Map<Long, String> teamMap = teamsList.stream()
                .collect(Collectors.toMap(ActivityTeams::getTeamId, ActivityTeams::getTeamName));

        // 将积分排名列表转换为VO列表，并设置队伍名称
        return standingsList.stream().map(standing -> {
            ActivityStandingsVO vo = new ActivityStandingsVO();
            // 复制积分排名基本信息
            vo.setStandingId(standing.getStandingId());
            vo.setActivityId(standing.getActivityId());
            vo.setTeamId(standing.getTeamId());
            vo.setGroupCode(standing.getGroupCode());
            vo.setMatchesPlayed(standing.getMatchesPlayed());
            vo.setWins(standing.getWins());
            vo.setLosses(standing.getLosses());
            vo.setPoints(standing.getPoints());
            vo.setSetsWon(standing.getSetsWon());
            vo.setSetsLost(standing.getSetsLost());
            vo.setPointsFor(standing.getPointsFor());
            vo.setPointsAgainst(standing.getPointsAgainst());
            vo.setCValue(standing.getCValue());
            vo.setZValue(standing.getZValue());
            vo.setRankInGroup(standing.getRankInGroup());
            vo.setCreateBy(standing.getCreateBy());
            vo.setCreateTime(standing.getCreateTime());
            vo.setUpdateBy(standing.getUpdateBy());
            vo.setUpdateTime(standing.getUpdateTime());
            vo.setRemark(standing.getRemark());

            // 设置队伍名称
            vo.setTeamName(teamMap.get(standing.getTeamId()));

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 导出活动积分排名列表
     */
    @ApiOperation("导出活动积分排名列表")
    @PreAuthorize("@ss.hasPermi('activity:standings:export')")
    @Log(title = "活动积分排名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityStandings activityStandings)
    {
        List<ActivityStandings> list = activityStandingsService.selectActivityStandingsList(activityStandings);
        ExcelUtil<ActivityStandings> util = new ExcelUtil<ActivityStandings>(ActivityStandings.class);
        util.exportExcel(response, list, "活动积分排名数据");
    }

    /**
     * 获取活动积分排名详细信息
     */
    @ApiOperation("获取活动积分排名详细信息")
    @ApiImplicitParam(name = "standingId", value = "积分排名ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:standings:query')")
    @GetMapping(value = "/{standingId}")
    public AjaxResult getInfo(@PathVariable("standingId") Long standingId)
    {
        return AjaxResult.success(activityStandingsService.selectActivityStandingsByStandingId(standingId));
    }

    /**
     * 新增活动积分排名
     */
    @ApiOperation("新增活动积分排名")
    @PreAuthorize("@ss.hasPermi('activity:standings:add')")
    @Log(title = "活动积分排名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityStandings activityStandings)
    {
        // 统一设置activityId为1
        activityStandings.setActivityId(1L);
        
        // 业务逻辑处理：检查小组标识是否有效
        if (activityStandings.getGroupCode() != null && 
            !activityStandings.getGroupCode().isEmpty() &&
            !activityStandings.getGroupCode().matches("^[A-D]$")) {
            return AjaxResult.error("小组标识必须是A、B、C或D中的一个");
        }
        
        // 检查数值字段是否为非负数
        if (!isValidNonNegativeValues(activityStandings)) {
            return AjaxResult.error("数值字段必须为非负数");
        }
        
        return toAjax(activityStandingsService.insertActivityStandings(activityStandings));
    }

    /**
     * 修改活动积分排名
     */
    @ApiOperation("修改活动积分排名")
    @PreAuthorize("@ss.hasPermi('activity:standings:edit')")
    @Log(title = "活动积分排名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityStandings activityStandings)
    {
        // 业务逻辑处理：检查小组标识是否有效
        if (activityStandings.getGroupCode() != null && 
            !activityStandings.getGroupCode().isEmpty() &&
            !activityStandings.getGroupCode().matches("^[A-D]$")) {
            return AjaxResult.error("小组标识必须是A、B、C或D中的一个");
        }
        
        // 检查数值字段是否为非负数
        if (!isValidNonNegativeValues(activityStandings)) {
            return AjaxResult.error("数值字段必须为非负数");
        }
        
        return toAjax(activityStandingsService.updateActivityStandings(activityStandings));
    }

    /**
     * 删除活动积分排名
     */
    @ApiOperation("删除活动积分排名")
    @ApiImplicitParam(name = "standingIds", value = "积分排名ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:standings:remove')")
    @Log(title = "活动积分排名", businessType = BusinessType.DELETE)
	@DeleteMapping("/{standingIds}")
    public AjaxResult remove(@PathVariable Long[] standingIds)
    {
        return toAjax(activityStandingsService.deleteActivityStandingsByStandingIds(standingIds));
    }
    
    /**
     * 检查数值字段是否为非负数
     * @param activityStandings
     * @return
     */
    private boolean isValidNonNegativeValues(ActivityStandings activityStandings) {
        // 检查所有数值字段是否为非负数
        Integer[] integerValues = {
            activityStandings.getMatchesPlayed(),
            activityStandings.getWins(),
            activityStandings.getLosses(),
            activityStandings.getPoints(),
            activityStandings.getSetsWon(),
            activityStandings.getSetsLost(),
            activityStandings.getPointsFor(),
            activityStandings.getPointsAgainst(),
            activityStandings.getRankInGroup()
        };
        
        for (Integer value : integerValues) {
            if (value != null && value < 0) {
                return false;
            }
        }
        
        // 检查Double字段
        Double[] doubleValues = {
            activityStandings.getCValue(),
            activityStandings.getZValue()
        };
        
        for (Double value : doubleValues) {
            if (value != null && value < 0) {
                return false;
            }
        }
        
        return true;
    }
}