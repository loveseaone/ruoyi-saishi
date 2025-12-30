package com.ruoyi.wx.controller;

import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityMatches;
import com.ruoyi.activity.domain.ActivityMatchesAthletes;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityAthletesService;
import com.ruoyi.activity.service.IActivityMatchesAthletesService;
import com.ruoyi.activity.service.IActivityMatchesService;
import com.ruoyi.activity.service.IActivityTeamsService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信端赛程与比赛接口控制器
 *
 * @author ruoyi
 */
@Api("微信端赛程与比赛接口")
@RestController
@RequestMapping("/wx")
public class WxMatchesController extends BaseController {
    @Autowired
    private IActivityMatchesService activityMatchesService;

    @Autowired
    private IActivityTeamsService activityTeamsService;

    @Autowired
    private IActivityAthletesService activityAthletesService;

    @Autowired
    private IActivityMatchesAthletesService activityMatchesAthletesService;

    /**
     * 4.1 获取全部赛程（按场次编号+日期+时间排序）
     */
    @ApiOperation("获取全部赛程（按场次编号+日期+时间排序）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "date", value = "日期", dataType = "String", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "stage", value = "阶段", dataType = "Integer", dataTypeClass = Integer.class, paramType = "query"),
        @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", dataTypeClass = Integer.class, paramType = "query")
    })
    @Anonymous
    @GetMapping("/matches")
    public AjaxResult listMatches(String date, Integer stage, Integer status) {
        ActivityMatches query = new ActivityMatches();
        if (stage != null) {
            query.setStage(stage);
        }
        
        if (status != null) {
            query.setStatus(status);
        }

        List<ActivityMatches> matches = activityMatchesService.selectActivityMatchesList(query);

        // 过滤日期
        if (date != null && !date.isEmpty()) {
            matches = matches.stream()
                    .filter(m -> m.getMatchDate() != null && date.equals(new SimpleDateFormat("yyyyMMdd").format(m.getMatchDate())))
                    .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
        }

        // 按场次编号+日期和时间排序（场次编号按数值大小排序）
        matches.sort((m1, m2) -> {
            // 首先按场次编号数值排序
            int code1 = Integer.parseInt(m1.getMatchCode());
            int code2 = Integer.parseInt(m2.getMatchCode());
            int codeCompare = Integer.compare(code1, code2);
            if (codeCompare != 0) {
                return codeCompare;
            }
            
            // 然后按日期排序
            int dateCompare = m1.getMatchDate().compareTo(m2.getMatchDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            
            // 最后按时间排序
            return m1.getStartTime().compareTo(m2.getStartTime());
        });

        List<Map<String, Object>> result = new ArrayList<>();
        for (ActivityMatches match : matches) {
            Map<String, Object> matchMap = new HashMap<>();

            matchMap.put("id", match.getMatchId());
            matchMap.put("matchCode", match.getMatchCode());
            matchMap.put("stage", match.getStage());
            matchMap.put("format", match.getFormat());
            matchMap.put("court", match.getCourt());

            if (match.getMatchDate() != null) {
                matchMap.put("matchDate", new SimpleDateFormat("yyyyMMdd").format(match.getMatchDate()));
            }

            // startTime 字段， 格式化 HH:mm:ss 字符串
            matchMap.put("startTime", new SimpleDateFormat("HH:mm:ss").format(match.getStartTime()));
            matchMap.put("status", match.getStatus());
            matchMap.put("roundDesc", match.getRoundDesc());

            // 获取队伍信息
            if (match.getTeamAId() != null) {
                ActivityTeams teamA = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamAId());
                if (teamA != null) {
                    Map<String, Object> teamAMap = new HashMap<>();
                    teamAMap.put("id", teamA.getTeamId());
                    teamAMap.put("name", teamA.getTeamName());
                    matchMap.put("teamA", teamAMap);
                }
            }

            if (match.getTeamBId() != null) {
                ActivityTeams teamB = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamBId());
                if (teamB != null) {
                    Map<String, Object> teamBMap = new HashMap<>();
                    teamBMap.put("id", teamB.getTeamId());
                    teamBMap.put("name", teamB.getTeamName());
                    matchMap.put("teamB", teamBMap);
                }
            }

            // 添加队伍成员信息
            addTeamAthletesInfo(match, matchMap);

            // 比分信息
            if (match.getStatus() != null && match.getStatus() == 2) { // 已结束
                Map<String, Object> scoreMap = new HashMap<>();
                scoreMap.put("aSet1", match.getScoreASet1());
                scoreMap.put("bSet1", match.getScoreBSet1());
                scoreMap.put("aSet2", match.getScoreASet2());
                scoreMap.put("bSet2", match.getScoreBSet2());
                scoreMap.put("aSet3", match.getScoreASet3());
                scoreMap.put("bSet3", match.getScoreBSet3());
                
                // 如果是5局赛制
                if (match.getFormat() != null && match.getFormat() == 5) {
                    scoreMap.put("aSet4", match.getScoreASet4());
                    scoreMap.put("bSet4", match.getScoreBSet4());
                    scoreMap.put("aSet5", match.getScoreASet5());
                    scoreMap.put("bSet5", match.getScoreBSet5());
                }
                
                matchMap.put("score", scoreMap);
            } else {
                matchMap.put("score", null);
            }

            // 添加获胜者ID
            matchMap.put("winnerId", match.getWinnerId());

            result.add(matchMap);
        }

        return AjaxResult.success(result);
    }



    /**
     * 4.2 获取单场比赛详情（含局分）
     */
    @ApiOperation("获取单场比赛详情（含局分）")
    @ApiImplicitParam(name = "matchId", value = "比赛ID", dataType = "Long", dataTypeClass = Long.class, paramType = "path")
    @Anonymous
    @GetMapping("/matches/{matchId}")
    public AjaxResult getMatchDetail(@PathVariable Long matchId) {
        ActivityMatches match = activityMatchesService.selectActivityMatchesByMatchId(matchId);
        if (match == null) {
            return AjaxResult.error("比赛不存在");
        }

        Map<String, Object> result = new HashMap<>();

        result.put("id", match.getMatchId());
        result.put("matchCode", match.getMatchCode());
        
        // 添加比赛时间、开始时间、场地
        if (match.getMatchDate() != null) {
            result.put("matchDate", new SimpleDateFormat("yyyy-MM-dd").format(match.getMatchDate()));
        }
        if (match.getStartTime() != null) {
            result.put("startTime", new SimpleDateFormat("HH:mm:ss").format(match.getStartTime()));
        }
        result.put("court", match.getCourt());
        
        // 获取队伍信息
        if (match.getTeamAId() != null) {
            ActivityTeams teamA = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamAId());
            if (teamA != null) {
                Map<String, Object> teamAMap = new HashMap<>();
                teamAMap.put("id", teamA.getTeamId());
                teamAMap.put("name", teamA.getTeamName());
                result.put("teamA", teamAMap);
            }
        }

        if (match.getTeamBId() != null) {
            ActivityTeams teamB = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamBId());
            if (teamB != null) {
                Map<String, Object> teamBMap = new HashMap<>();
                teamBMap.put("id", teamB.getTeamId());
                teamBMap.put("name", teamB.getTeamName());
                result.put("teamB", teamBMap);
            }
        }

        // 添加队伍成员信息
        addTeamAthletesInfo(match, result);

        // 比分信息
        if (match.getStatus() != null && match.getStatus() == 2) { // 已结束
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("aSet1", match.getScoreASet1());
            scoreMap.put("bSet1", match.getScoreBSet1());
            scoreMap.put("aSet2", match.getScoreASet2());
            scoreMap.put("bSet2", match.getScoreBSet2());
            scoreMap.put("aSet3", match.getScoreASet3());
            scoreMap.put("bSet3", match.getScoreBSet3());
            
            // 如果是5局赛制
            if (match.getFormat() != null && match.getFormat() == 5) {
                scoreMap.put("aSet4", match.getScoreASet4());
                scoreMap.put("bSet4", match.getScoreBSet4());
                scoreMap.put("aSet5", match.getScoreASet5());
                scoreMap.put("bSet5", match.getScoreBSet5());
            }
            
            result.put("score", scoreMap);
        } else {
            result.put("score", null);
        }
        
        // 获胜队伍
        if (match.getWinnerId() != null) {
            ActivityTeams winnerTeam = activityTeamsService.selectActivityTeamsByTeamId(match.getWinnerId());
            if (winnerTeam != null) {
                result.put("winner", winnerTeam.getTeamName());
            }
        }
        
        result.put("status", match.getStatus());
        result.put("winnerId", match.getWinnerId());

        return AjaxResult.success(result);
    }
    
    /**
     * 7.1 获取「我的队伍」赛程
     */
    @ApiOperation("获取「我的队伍」赛程")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "teamId", value = "队伍ID", dataType = "Long", dataTypeClass = Long.class, paramType = "query"),
        @ApiImplicitParam(name = "date", value = "日期", dataType = "String", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", dataTypeClass = Integer.class, paramType = "query")
    })
    @Anonymous
    @GetMapping("/my/matches")
    public AjaxResult getMyMatches(Long teamId, String date, Integer status) {
        try {
            // teamId必须提供
            if (teamId == null) {
                return AjaxResult.error(400, "队伍ID不能为空");
            }
            
            // 查询队伍信息
            ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(teamId);
            if (team == null) {
                return AjaxResult.error(404, "队伍信息不存在");
            }
            
            // 查询该队伍的所有比赛
            ActivityMatches matchQuery = new ActivityMatches();
            List<ActivityMatches> allMatches = activityMatchesService.selectActivityMatchesList(matchQuery);
            
            // 过滤出包含该队伍的比赛
            List<ActivityMatches> teamMatches = allMatches.stream()
                    .filter(m -> (m.getTeamAId() != null && m.getTeamAId().equals(teamId)) || 
                            (m.getTeamBId() != null && m.getTeamBId().equals(teamId)))
                    .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
            
            // 根据日期筛选
            if (date != null && !date.isEmpty()) {
                teamMatches = teamMatches.stream()
                        .filter(m -> m.getMatchDate() != null && date.equals(new SimpleDateFormat("yyyy-MM-dd").format(m.getMatchDate())))
                        .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
            }
            
            // 根据状态筛选
            if (status != null) {
                teamMatches = teamMatches.stream()
                        .filter(m -> m.getStatus() != null && m.getStatus().equals(status))
                        .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
            }
            
            // 按日期和时间排序
            teamMatches.sort((m1, m2) -> {
                int dateCompare = m1.getMatchDate().compareTo(m2.getMatchDate());
                if (dateCompare != 0) {
                    return dateCompare;
                }
                return m1.getStartTime().compareTo(m2.getStartTime());
            });
            
            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("teamName", team.getTeamName());
            
            List<Map<String, Object>> matchesResult = new ArrayList<>();
            for (ActivityMatches match : teamMatches) {
                Map<String, Object> matchMap = new HashMap<>();
                
                matchMap.put("id", match.getMatchId());
                matchMap.put("matchCode", match.getMatchCode());
                matchMap.put("stage", match.getStage());
                matchMap.put("roundDesc", match.getRoundDesc());
                matchMap.put("court", match.getCourt());
                
                if (match.getMatchDate() != null) {
                    matchMap.put("matchDate", new SimpleDateFormat("yyyy-MM-dd").format(match.getMatchDate()));
                }
                
                matchMap.put("startTime", new SimpleDateFormat("HH:mm:ss").format(match.getStartTime()));
                matchMap.put("status", match.getStatus());
                
                // 添加队伍信息
                if (match.getTeamAId() != null) {
                    ActivityTeams teamA = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamAId());
                    if (teamA != null) {
                        Map<String, Object> teamAMap = new HashMap<>();
                        teamAMap.put("id", teamA.getTeamId());
                        teamAMap.put("name", teamA.getTeamName());
                        matchMap.put("teamA", teamAMap);
                    }
                } else {
                    matchMap.put("teamA", null);
                }
                
                if (match.getTeamBId() != null) {
                    ActivityTeams teamB = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamBId());
                    if (teamB != null) {
                        Map<String, Object> teamBMap = new HashMap<>();
                        teamBMap.put("id", teamB.getTeamId());
                        teamBMap.put("name", teamB.getTeamName());
                        matchMap.put("teamB", teamBMap);
                    }
                } else {
                    matchMap.put("teamB", null);
                }
                
                // 添加队伍成员信息
                addTeamAthletesInfo(match, matchMap);
                
                // 设置isMyTeam
                matchMap.put("isMyTeam", true);
                
                // 设置对手信息
                String opponent = "待定（" + match.getRoundDesc() + "）";
                if (match.getTeamAId() != null && match.getTeamAId().equals(teamId) && match.getTeamBId() != null) {
                    ActivityTeams opponentTeam = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamBId());
                    if (opponentTeam != null) {
                        opponent = opponentTeam.getTeamName();
                    }
                } else if (match.getTeamBId() != null && match.getTeamBId().equals(teamId) && match.getTeamAId() != null) {
                    ActivityTeams opponentTeam = activityTeamsService.selectActivityTeamsByTeamId(match.getTeamAId());
                    if (opponentTeam != null) {
                        opponent = opponentTeam.getTeamName();
                    }
                }
                matchMap.put("opponent", opponent);
                
                // 设置场地名称
                String venue = "未知场地";
                if (match.getCourt() != null) {
                    switch (match.getCourt()) {
                        case 1:
                            venue = "一号场地";
                            break;
                        case 2:
                            venue = "二号场地";
                            break;
                        case 3:
                            venue = "三号场地";
                            break;
                        default:
                            venue = match.getCourt() + "号场地";
                    }
                }
                matchMap.put("venue", venue);
                
                // 比分信息
                if (match.getStatus() != null && match.getStatus() == 2) { // 已结束
                    Map<String, Object> scoreMap = new HashMap<>();
                    scoreMap.put("aSet1", match.getScoreASet1());
                    scoreMap.put("bSet1", match.getScoreBSet1());
                    scoreMap.put("aSet2", match.getScoreASet2());
                    scoreMap.put("bSet2", match.getScoreBSet2());
                    scoreMap.put("aSet3", match.getScoreASet3());
                    scoreMap.put("bSet3", match.getScoreBSet3());
                    
                    // 如果是5局赛制
                    if (match.getFormat() != null && match.getFormat() == 5) {
                        scoreMap.put("aSet4", match.getScoreASet4());
                        scoreMap.put("bSet4", match.getScoreBSet4());
                        scoreMap.put("aSet5", match.getScoreASet5());
                        scoreMap.put("bSet5", match.getScoreBSet5());
                    }
                    
                    matchMap.put("score", scoreMap);
                } else {
                    matchMap.put("score", null);
                }
                
                // 添加获胜者ID
                matchMap.put("winnerId", match.getWinnerId());
                
                matchesResult.add(matchMap);
            }
            
            result.put("matches", matchesResult);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取我的赛程失败：" + e.getMessage());
        }
    }
    /**
     * 添加队伍成员信息
     *
     * @param match 比赛信息
     * @param matchMap 返回结果Map
     */
    private void addTeamAthletesInfo(ActivityMatches match, Map<String, Object> matchMap) {
        // 查询A队成员
        if (match.getTeamAId() != null) {
            List<Map<String, Object>> teamAAthletes = getTeamAthletes(match.getMatchId(), match.getTeamAId());
            matchMap.put("teamAPlayers", teamAAthletes);
        }

        // 查询B队成员
        if (match.getTeamBId() != null) {
            List<Map<String, Object>> teamBAthletes = getTeamAthletes(match.getMatchId(), match.getTeamBId());
            matchMap.put("teamBPlayers", teamBAthletes);
        }
    }

    /**
     * 获取指定队伍的成员信息
     *
     * @param matchId 场次ID
     * @param teamId 队伍ID
     * @return 成员列表
     */
    private List<Map<String, Object>> getTeamAthletes(Long matchId, Long teamId) {
        List<Map<String, Object>> athletesList = new ArrayList<>();

        // 查询场次成员关联信息
        ActivityMatchesAthletes matchesAthletesQuery = new ActivityMatchesAthletes();
        matchesAthletesQuery.setMatchId(matchId);
        matchesAthletesQuery.setTeamId(teamId);
        List<ActivityMatchesAthletes> matchesAthletesList = activityMatchesAthletesService.selectActivityMatchesAthletesList(matchesAthletesQuery);

        // 获取成员详细信息
        for (ActivityMatchesAthletes matchesAthletes : matchesAthletesList) {
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(matchesAthletes.getAthleteId());
            if (athlete != null) {
                Map<String, Object> athleteMap = new HashMap<>();
                athleteMap.put("name", athlete.getName());
                athleteMap.put("gender", athlete.getGender());
                athleteMap.put("phone", athlete.getPhone());
                athleteMap.put("idCard", athlete.getIdCard());
                athleteMap.put("role", athlete.getRole());
                athletesList.add(athleteMap);
            }
        }

        return athletesList;
    }
}