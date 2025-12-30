package com.ruoyi.wx.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.domain.ActivityStandings;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityInfoService;
import com.ruoyi.activity.service.IActivityStandingsService;
import com.ruoyi.activity.service.IActivityTeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

/**
 * 微信端积分与排名接口控制器
 *
 * @author ruoyi
 */
@Api("微信端积分与排名接口")
@RestController
@RequestMapping("/wx")
public class WxStandingsController extends BaseController {
    @Autowired
    private IActivityStandingsService activityStandingsService;

    @Autowired
    private IActivityTeamsService activityTeamsService;
    
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 5.1 获取小组赛积分榜（按小组）
     */
    @ApiOperation("获取小组赛积分榜（按小组）")
    @PreAuthorize("@ss.hasPermi('wx:standings:group')")
    @ApiImplicitParam(name = "group", value = "小组代码", dataType = "String", dataTypeClass = String.class, paramType = "query")
    @GetMapping("/standings")
    public AjaxResult getGroupStandings(String group) {
        ActivityStandings query = new ActivityStandings();
        List<ActivityStandings> standings = activityStandingsService.selectActivityStandingsList(query);

        // 如果指定了小组，过滤小组
        if (group != null && !group.isEmpty()) {
            standings = standings.stream()
                    .filter(s -> group.equals(s.getGroupCode()))
                    .collect(Collectors.toList());
        }

        // 按小组分组
        Map<String, List<ActivityStandings>> groupedStandings = new HashMap<>();
        for (ActivityStandings standing : standings) {
            String groupCode = standing.getGroupCode();
            if (!groupedStandings.containsKey(groupCode)) {
                groupedStandings.put(groupCode, new ArrayList<>());
            }
            groupedStandings.get(groupCode).add(standing);
        }

        // 对每个小组内的队伍按排名排序
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        for (Map.Entry<String, List<ActivityStandings>> entry : groupedStandings.entrySet()) {
            String groupCode = entry.getKey();
            List<ActivityStandings> groupStandings = entry.getValue();

            // 按排名排序，处理 null 值情况
            groupStandings.sort(Comparator.comparing(ActivityStandings::getRankInGroup, 
                Comparator.nullsLast(Comparator.naturalOrder())));

            List<Map<String, Object>> groupResult = new ArrayList<>();
            for (ActivityStandings standing : groupStandings) {
                // 获取队伍名称
                ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(standing.getTeamId());
                String teamName = (team != null) ? team.getTeamName() : "未知队伍";

                Map<String, Object> standingMap = new HashMap<>();
                standingMap.put("teamName", teamName);
                standingMap.put("matchesPlayed", standing.getMatchesPlayed());
                standingMap.put("wins", standing.getWins());
                standingMap.put("losses", standing.getLosses());
                standingMap.put("points", standing.getPoints());
                
                if (standing.getCValue() != null) {
                    standingMap.put("cValue", new DecimalFormat("0.000").format(standing.getCValue()));
                }
                
                if (standing.getZValue() != null) {
                    standingMap.put("zValue", new DecimalFormat("0.000").format(standing.getZValue()));
                }
                
                standingMap.put("rank", standing.getRankInGroup());

                groupResult.add(standingMap);
            }
            result.put(groupCode, groupResult);
        }

        return AjaxResult.success(result);
    }

    /**
     * 5.2 获取最终排名（1-8 名）
     */
    @ApiOperation("获取最终排名（1-8 名）")
    @PreAuthorize("@ss.hasPermi('wx:standings:final')")
    @GetMapping("/rankings/final")
    public AjaxResult getFinalRankings() {
        try {
            // 查询所有积分排名数据
            ActivityStandings query = new ActivityStandings();
            List<ActivityStandings> standings = activityStandingsService.selectActivityStandingsList(query);
            
            // 按积分降序排序，取前8名
            standings = standings.stream()
                    .sorted((s1, s2) -> {
                        // 首先按积分降序排序
                        int pointsCompare = Integer.compare(
                            (s2.getPoints() != null) ? s2.getPoints() : 0,
                            (s1.getPoints() != null) ? s1.getPoints() : 0
                        );
                        if (pointsCompare != 0) {
                            return pointsCompare;
                        }
                        
                        // 如果积分相同，按C值降序排序
                        int cValueCompare = Double.compare(
                            (s2.getCValue() != null) ? s2.getCValue() : 0.0,
                            (s1.getCValue() != null) ? s1.getCValue() : 0.0
                        );
                        if (cValueCompare != 0) {
                            return cValueCompare;
                        }
                        
                        // 如果C值也相同，按Z值降序排序
                        return Double.compare(
                            (s2.getZValue() != null) ? s2.getZValue() : 0.0,
                            (s1.getZValue() != null) ? s1.getZValue() : 0.0
                        );
                    })
                    .limit(8)
                    .collect(Collectors.toList());
            
            List<Map<String, Object>> result = new ArrayList<>();
            for (int i = 0; i < standings.size(); i++) {
                ActivityStandings standing = standings.get(i);
                
                Map<String, Object> rankMap = new HashMap<>();
                rankMap.put("rank", i + 1);
                
                // 获取队伍名称
                ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(standing.getTeamId());
                String teamName = (team != null) ? team.getTeamName() : "未知队伍";
                rankMap.put("teamName", teamName);
                
                // 获取活动名称
                ActivityInfo activity = activityInfoService.selectActivityInfoByActivityId(standing.getActivityId());
                String activityName = (activity != null) ? activity.getActivityName() : "未知活动";
                rankMap.put("activityName", activityName);
                
                // 设置奖项
                String award = "";
                switch (i + 1) {
                    case 1:
                        award = "金奖";
                        break;
                    case 2:
                        award = "银奖";
                        break;
                    case 3:
                        award = "铜奖";
                        break;
                    default:
                        award = "优秀奖";
                        break;
                }
                rankMap.put("award", award);
                
                rankMap.put("points", standing.getPoints());
                rankMap.put("wins", standing.getWins());
                rankMap.put("losses", standing.getLosses());
                
                if (standing.getCValue() != null) {
                    rankMap.put("cValue", new DecimalFormat("0.000").format(standing.getCValue()));
                }
                
                if (standing.getZValue() != null) {
                    rankMap.put("zValue", new DecimalFormat("0.000").format(standing.getZValue()));
                }
                
                result.add(rankMap);
            }
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            logger.error("获取最终排名时发生错误", e);
            return AjaxResult.error("获取最终排名失败");
        }
    }
}