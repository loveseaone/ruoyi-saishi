package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityTeams;
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

/**
 * 活动队伍Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动队伍管理")
@RestController
@RequestMapping("/activity/teams")
public class ActivityTeamsController extends BaseController
{
    @Autowired
    private IActivityTeamsService activityTeamsService;

    /**
     * 查询活动队伍列表
     */
    @ApiOperation("查询活动队伍列表")
    @PreAuthorize("@ss.hasPermi('activity:teams:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityTeams activityTeams)
    {
        startPage();
        List<ActivityTeams> list = activityTeamsService.selectActivityTeamsList(activityTeams);
        return getDataTable(list);
    }

    /**
     * 导出活动队伍列表
     */
    @ApiOperation("导出活动队伍列表")
    @PreAuthorize("@ss.hasPermi('activity:teams:export')")
    @Log(title = "活动队伍", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityTeams activityTeams)
    {
        List<ActivityTeams> list = activityTeamsService.selectActivityTeamsList(activityTeams);
        ExcelUtil<ActivityTeams> util = new ExcelUtil<ActivityTeams>(ActivityTeams.class);
        util.exportExcel(response, list, "活动队伍数据");
    }

    /**
     * 获取活动队伍详细信息
     */
    @ApiOperation("获取活动队伍详细信息")
    @ApiImplicitParam(name = "teamId", value = "队伍ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:teams:query')")
    @GetMapping(value = "/{teamId}")
    public AjaxResult getInfo(@PathVariable("teamId") Long teamId)
    {
        return AjaxResult.success(activityTeamsService.selectActivityTeamsByTeamId(teamId));
    }

    /**
     * 新增活动队伍
     */
    @ApiOperation("新增活动队伍")
    @PreAuthorize("@ss.hasPermi('activity:teams:add')")
    @Log(title = "活动队伍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityTeams activityTeams)
    {
        // 统一设置activityId为1
        activityTeams.setActivityId(1L);
        
        // 业务逻辑处理：检查分组代码是否有效
        if (activityTeams.getGroupCode() != null && 
            !activityTeams.getGroupCode().isEmpty() &&
            !activityTeams.getGroupCode().matches("^[A-D]$")) {
            return AjaxResult.error("小组赛分组必须是A、B、C或D中的一个");
        }
        
        // 检查领队电话格式（如果提供了电话）
        if (activityTeams.getLeaderPhone() != null && 
            !activityTeams.getLeaderPhone().isEmpty() &&
            !activityTeams.getLeaderPhone().matches("^1[3-9]\\d{9}$")) {
            return AjaxResult.error("领队电话格式不正确");
        }
        
        return toAjax(activityTeamsService.insertActivityTeams(activityTeams));
    }

    /**
     * 修改活动队伍
     */
    @ApiOperation("修改活动队伍")
    @PreAuthorize("@ss.hasPermi('activity:teams:edit')")
    @Log(title = "活动队伍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityTeams activityTeams)
    {
        // 业务逻辑处理：检查分组代码是否有效
        if (activityTeams.getGroupCode() != null && 
            !activityTeams.getGroupCode().isEmpty() &&
            !activityTeams.getGroupCode().matches("^[A-D]$")) {
            return AjaxResult.error("小组赛分组必须是A、B、C或D中的一个");
        }
        
        // 检查领队电话格式（如果提供了电话）
        if (activityTeams.getLeaderPhone() != null && 
            !activityTeams.getLeaderPhone().isEmpty() &&
            !activityTeams.getLeaderPhone().matches("^1[3-9]\\d{9}$")) {
            return AjaxResult.error("领队电话格式不正确");
        }
        
        return toAjax(activityTeamsService.updateActivityTeams(activityTeams));
    }

    /**
     * 删除活动队伍
     */
    @ApiOperation("删除活动队伍")
    @ApiImplicitParam(name = "teamIds", value = "队伍ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:teams:remove')")
    @Log(title = "活动队伍", businessType = BusinessType.DELETE)
	@DeleteMapping("/{teamIds}")
    public AjaxResult remove(@PathVariable Long[] teamIds)
    {
        return toAjax(activityTeamsService.deleteActivityTeamsByTeamIds(teamIds));
    }
}