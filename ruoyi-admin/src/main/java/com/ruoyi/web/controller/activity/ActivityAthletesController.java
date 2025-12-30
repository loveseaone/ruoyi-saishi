package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.vo.ActivityAthletesVO;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityAthletesService;
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
 * 活动运动员及工作人员Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动运动员及工作人员管理")
@RestController
@RequestMapping("/activity/athletes")
public class ActivityAthletesController extends BaseController
{
    @Autowired
    private IActivityAthletesService activityAthletesService;

    @Autowired
    private IActivityTeamsService  activityTeamsService;

    /**
     * 查询活动运动员及工作人员列表
     */
    @ApiOperation("查询活动运动员及工作人员列表")
    @PreAuthorize("@ss.hasPermi('activity:athletes:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityAthletes activityAthletes)
    {
        startPage();
        List<ActivityAthletes> list = activityAthletesService.selectActivityAthletesList(activityAthletes);
        TableDataInfo dataTable = getDataTable(list);
        //提供一个方法 List<ActivityAthletes> -> List<ActivityAthletesVO>
        List<ActivityAthletesVO> listVO = convertToVOList(list);
        dataTable.setRows(listVO);
        return dataTable;
    }

    private List<ActivityAthletesVO> convertToVOList(List<ActivityAthletes> athletesList) {

        // 查询所有的队伍信息，构建teamId到teamName的映射
        List<ActivityTeams> teamsList = activityTeamsService.selectActivityTeamsList(new ActivityTeams());
        Map<Long, String> teamMap = teamsList.stream()
                .collect(Collectors.toMap(ActivityTeams::getTeamId, ActivityTeams::getTeamName));

        // 将运动员列表转换为VO列表，并设置队伍名称
        return athletesList.stream().map(athlete -> {
            ActivityAthletesVO vo = new ActivityAthletesVO();
            // 复制运动员基本信息
            vo.setAthleteId(athlete.getAthleteId());
            vo.setTeamId(athlete.getTeamId());
            vo.setActivityId(athlete.getActivityId());
            vo.setOpenid(athlete.getOpenid());
            vo.setStatus(athlete.getStatus());
            vo.setSort(athlete.getSort());
            vo.setName(athlete.getName());
            vo.setGender(athlete.getGender());
            vo.setPhone(athlete.getPhone());
            vo.setIdCard(athlete.getIdCard());
            vo.setRole(athlete.getRole());
            vo.setIsOnField(athlete.getIsOnField());
            vo.setCreateBy(athlete.getCreateBy());
            vo.setCreateTime(athlete.getCreateTime());
            vo.setUpdateBy(athlete.getUpdateBy());
            vo.setUpdateTime(athlete.getUpdateTime());
            vo.setRemark(athlete.getRemark());

            // 设置队伍名称
            vo.setTeamName(teamMap.get(athlete.getTeamId()));

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 导出活动运动员及工作人员列表
     */
    @ApiOperation("导出活动运动员及工作人员列表")
    @PreAuthorize("@ss.hasPermi('activity:athletes:export')")
    @Log(title = "活动运动员及工作人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityAthletes activityAthletes)
    {
        List<ActivityAthletes> list = activityAthletesService.selectActivityAthletesList(activityAthletes);
        ExcelUtil<ActivityAthletes> util = new ExcelUtil<ActivityAthletes>(ActivityAthletes.class);
        util.exportExcel(response, list, "活动运动员及工作人员数据");
    }

    /**
     * 获取活动运动员及工作人员详细信息
     */
    @ApiOperation("获取活动运动员及工作人员详细信息")
    @ApiImplicitParam(name = "athleteId", value = "运动员ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:athletes:query')")
    @GetMapping(value = "/{athleteId}")
    public AjaxResult getInfo(@PathVariable("athleteId") Long athleteId)
    {
        return AjaxResult.success(activityAthletesService.selectActivityAthletesByAthleteId(athleteId));
    }

    /**
     * 新增活动运动员及工作人员
     */
    @ApiOperation("新增活动运动员及工作人员")
    @PreAuthorize("@ss.hasPermi('activity:athletes:add')")
    @Log(title = "活动运动员及工作人员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityAthletes activityAthletes)
    {
        // 统一设置activityId为1
        activityAthletes.setActivityId(1L);
        
        // 业务逻辑处理：检查手机号格式（如果提供了手机号）
        if (activityAthletes.getPhone() != null && 
            !activityAthletes.getPhone().isEmpty() &&
            !activityAthletes.getPhone().matches("^1[3-9]\\d{9}$")) {
            return AjaxResult.error("手机号格式不正确");
        }
        
        // 检查身份证号格式（如果提供了身份证号）
        if (activityAthletes.getIdCard() != null && 
            !activityAthletes.getIdCard().isEmpty() &&
            !activityAthletes.getIdCard().matches("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")) {
            return AjaxResult.error("身份证号格式不正确");
        }
        
        // 检查角色是否有效
        if (activityAthletes.getRole() != null && 
            !activityAthletes.getRole().matches("^(领队|教练|工会人员|男队员|女队员)$")) {
            return AjaxResult.error("角色必须是：领队、教练、工会人员、男队员或女队员之一");
        }
        
        return toAjax(activityAthletesService.insertActivityAthletes(activityAthletes));
    }

    /**
     * 修改活动运动员及工作人员
     */
    @ApiOperation("修改活动运动员及工作人员")
    @PreAuthorize("@ss.hasPermi('activity:athletes:edit')")
    @Log(title = "活动运动员及工作人员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityAthletes activityAthletes)
    {
        // 业务逻辑处理：检查手机号格式（如果提供了手机号）
        if (activityAthletes.getPhone() != null && 
            !activityAthletes.getPhone().isEmpty() &&
            !activityAthletes.getPhone().matches("^1[3-9]\\d{9}$")) {
            return AjaxResult.error("手机号格式不正确");
        }
        
        // 检查身份证号格式（如果提供了身份证号）
        if (activityAthletes.getIdCard() != null && 
            !activityAthletes.getIdCard().isEmpty() &&
            !activityAthletes.getIdCard().matches("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")) {
            return AjaxResult.error("身份证号格式不正确");
        }
        
        // 检查角色是否有效
        if (activityAthletes.getRole() != null && 
            !activityAthletes.getRole().matches("^(领队|教练|工会人员|男队员|女队员)$")) {
            return AjaxResult.error("角色必须是：领队、教练、工会人员、男队员或女队员之一");
        }
        
        return toAjax(activityAthletesService.updateActivityAthletes(activityAthletes));
    }

    /**
     * 删除活动运动员及工作人员
     */
    @ApiOperation("删除活动运动员及工作人员")
    @ApiImplicitParam(name = "athleteIds", value = "运动员ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:athletes:remove')")
    @Log(title = "活动运动员及工作人员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{athleteIds}")
    public AjaxResult remove(@PathVariable Long[] athleteIds)
    {
        return toAjax(activityAthletesService.deleteActivityAthletesByAthleteIds(athleteIds));
    }
}