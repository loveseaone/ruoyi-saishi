package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityMatches;
import com.ruoyi.activity.domain.ActivityMatchesAthletes;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.vo.ActivityMatchesAthletesVO;
import com.ruoyi.activity.service.IActivityAthletesService;
import com.ruoyi.activity.service.IActivityMatchesAthletesService;
import com.ruoyi.activity.service.IActivityMatchesService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 场次成员关联Controller
 * 
 * @author ruoyi
 * @date 2025-10-26
 */
@Api("场次成员关联管理")
@RestController
@RequestMapping("/activity/matches/athletes")
public class ActivityMatchesAthletesController extends BaseController
{
    @Autowired
    private IActivityMatchesAthletesService activityMatchesAthletesService;

    @Autowired
    private IActivityMatchesService activityMatchesService;

    @Autowired
    private IActivityTeamsService activityTeamsService;

    @Autowired
    private IActivityAthletesService activityAthletesService;

    /**
     * 查询场次成员关联列表
     */
    @ApiOperation("查询场次成员关联列表")
    @PreAuthorize("@ss.hasPermi('activity:matches:athletes:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityMatchesAthletes activityMatchesAthletes)
    {
        startPage();
        List<ActivityMatchesAthletes> list = activityMatchesAthletesService.selectActivityMatchesAthletesList(activityMatchesAthletes);
        TableDataInfo dataTable = getDataTable(list);
        //提供一个方法 List<ActivityMatchesAthletes> -> List<ActivityMatchesAthletesVO>
        List<ActivityMatchesAthletesVO> listVO = convertToVOList(list);
        dataTable.setRows(listVO);
        return dataTable;
    }

    /**
     * 导出场次成员关联列表
     */
    @ApiOperation("导出场次成员关联列表")
    @PreAuthorize("@ss.hasPermi('activity:matches:athletes:export')")
    @Log(title = "场次成员关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityMatchesAthletes activityMatchesAthletes)
    {
        List<ActivityMatchesAthletes> list = activityMatchesAthletesService.selectActivityMatchesAthletesList(activityMatchesAthletes);
        ExcelUtil<ActivityMatchesAthletes> util = new ExcelUtil<ActivityMatchesAthletes>(ActivityMatchesAthletes.class);
        util.exportExcel(response, list, "场次成员关联数据");
    }

    /**
     * 获取场次成员关联详细信息
     */
    @ApiOperation("获取场次成员关联详细信息")
    @ApiImplicitParam(name = "id", value = "场次成员关联ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:matches:athletes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(activityMatchesAthletesService.selectActivityMatchesAthletesById(id));
    }

    /**
     * 新增场次成员关联
     */
    @ApiOperation("新增场次成员关联")
    @PreAuthorize("@ss.hasPermi('activity:matches:athletes:add')")
    @Log(title = "场次成员关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityMatchesAthletes activityMatchesAthletes)
    {
        return toAjax(activityMatchesAthletesService.insertActivityMatchesAthletes(activityMatchesAthletes));
    }

    /**
     * 修改场次成员关联
     */
    @ApiOperation("修改场次成员关联")
    @PreAuthorize("@ss.hasPermi('activity:matches:athletes:edit')")
    @Log(title = "场次成员关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityMatchesAthletes activityMatchesAthletes)
    {
        return toAjax(activityMatchesAthletesService.updateActivityMatchesAthletes(activityMatchesAthletes));
    }

    /**
     * 删除场次成员关联
     */
    @ApiOperation("删除场次成员关联")
    @ApiImplicitParam(name = "ids", value = "场次成员关联ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:matches:athletes:remove')")
    @Log(title = "场次成员关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activityMatchesAthletesService.deleteActivityMatchesAthletesByIds(ids));
    }

    private List<ActivityMatchesAthletesVO> convertToVOList(List<ActivityMatchesAthletes> matchesAthletesList) {
        // 查询所有的场次信息，构建matchId到matchCode的映射
        List<ActivityMatches> matchesList = activityMatchesService.selectActivityMatchesList(new ActivityMatches());
        Map<Long, String> matchMap = matchesList.stream()
                .collect(Collectors.toMap(ActivityMatches::getMatchId, ActivityMatches::getMatchCode));

        // 查询所有的队伍信息，构建teamId到teamName的映射
        List<ActivityTeams> teamsList = activityTeamsService.selectActivityTeamsList(new ActivityTeams());
        Map<Long, String> teamMap = teamsList.stream()
                .collect(Collectors.toMap(ActivityTeams::getTeamId, ActivityTeams::getTeamName));

        // 查询所有的参赛人员信息，构建athleteId到athleteName的映射
        List<ActivityAthletes> athletesList = activityAthletesService.selectActivityAthletesList(new ActivityAthletes());
        Map<Long, String> athleteMap = athletesList.stream()
                .collect(Collectors.toMap(ActivityAthletes::getAthleteId, ActivityAthletes::getName));

        // 将场次成员列表转换为VO列表，并设置扩展字段
        return matchesAthletesList.stream().map(matchAthlete -> {
            ActivityMatchesAthletesVO vo = new ActivityMatchesAthletesVO();
            // 复制基本信息
            vo.setId(matchAthlete.getId());
            vo.setMatchId(matchAthlete.getMatchId());
            vo.setTeamId(matchAthlete.getTeamId());
            vo.setAthleteId(matchAthlete.getAthleteId());
            vo.setCreateBy(matchAthlete.getCreateBy());
            vo.setCreateTime(matchAthlete.getCreateTime());
            vo.setUpdateBy(matchAthlete.getUpdateBy());
            vo.setUpdateTime(matchAthlete.getUpdateTime());
            vo.setRemark(matchAthlete.getRemark());

            // 设置扩展字段
            vo.setMatchCode(matchMap.get(matchAthlete.getMatchId()));
            vo.setTeamName(teamMap.get(matchAthlete.getTeamId()));
            vo.setAthleteName(athleteMap.get(matchAthlete.getAthleteId()));

            return vo;
        }).collect(Collectors.toList());
    }
}