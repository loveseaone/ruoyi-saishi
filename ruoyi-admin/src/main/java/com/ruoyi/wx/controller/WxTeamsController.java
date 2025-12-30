package com.ruoyi.wx.controller;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityAthletesService;
import com.ruoyi.activity.service.IActivityTeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

/**
 * 微信端队伍信息接口控制器
 *
 * @author ruoyi
 */
@Api("微信端队伍信息接口")
@RestController
@RequestMapping("/wx/teams")
public class WxTeamsController extends BaseController {
    @Autowired
    private IActivityTeamsService activityTeamsService;

    @Autowired
    private IActivityAthletesService activityAthletesService;

    /**
     * 3.1 获取所有队伍（按 display_order 排序）
     */
    @ApiOperation("获取所有队伍（按 display_order 排序）")
    @Anonymous
    @GetMapping
    public AjaxResult listAllTeams() {
        ActivityTeams query = new ActivityTeams();
        // 默认按display_order排序
        List<ActivityTeams> teams = activityTeamsService.selectActivityTeamsList(query);
        teams = teams.stream()
                .sorted((t1, t2) -> t1.getDisplayOrder().compareTo(t2.getDisplayOrder()))
                .collect(Collectors.toList());

        List<Map<String, Object>> result = new ArrayList<>();
        for (ActivityTeams team : teams) {
            Map<String, Object> teamMap = new HashMap<>();
            teamMap.put("id", team.getTeamId());
            teamMap.put("teamName", team.getTeamName());
            teamMap.put("groupCode", team.getGroupCode());
            teamMap.put("displayOrder", team.getDisplayOrder());
            teamMap.put("leaderName", team.getLeaderName());
            teamMap.put("coachName", team.getCoachName());
            result.add(teamMap);
        }

        return AjaxResult.success(result);
    }

    /**
     * 3.2 获取单支队伍详情（含人员）
     */
    @ApiOperation("获取单支队伍详情（含人员）")
    @PreAuthorize("@ss.hasPermi('wx:teams:detail')")
    @ApiImplicitParam(name = "teamId", value = "队伍ID", dataType = "Long", dataTypeClass = Long.class, paramType = "path")
    @GetMapping("/{teamId}")
    public AjaxResult getTeamDetail(@PathVariable Long teamId) {
        ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(teamId);
        if (team == null) {
            return AjaxResult.error("队伍不存在");
        }

        // 查询队伍成员
        ActivityAthletes queryAthletes = new ActivityAthletes();
        queryAthletes.setTeamId(teamId);
        List<ActivityAthletes> athletes = activityAthletesService.selectActivityAthletesList(queryAthletes);

        Map<String, Object> result = new HashMap<>();
        result.put("id", team.getTeamId());
        result.put("teamName", team.getTeamName());
        result.put("groupCode", team.getGroupCode());

        List<Map<String, Object>> athletesList = new ArrayList<>();
        for (ActivityAthletes athlete : athletes) {
            Map<String, Object> athleteMap = new HashMap<>();
            athleteMap.put("name", athlete.getName());
            athleteMap.put("role", athlete.getRole());
            
            if (athlete.getPhone() != null && !athlete.getPhone().isEmpty()) {
                // 手机号脱敏处理
                String maskedPhone = athlete.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                athleteMap.put("phone", maskedPhone);
            }
            
            if ("男队员".equals(athlete.getRole()) || "女队员".equals(athlete.getRole())) {
                athleteMap.put("isOnField", athlete.getIsOnField() != null && athlete.getIsOnField() == 1);
            }
            
            athletesList.add(athleteMap);
        }
        result.put("athletes", athletesList);

        return AjaxResult.success(result);
    }
}