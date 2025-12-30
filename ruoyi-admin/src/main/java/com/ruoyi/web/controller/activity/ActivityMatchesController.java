package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityMatches;
import com.ruoyi.activity.vo.ActivityMatchesVO;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityMatchesService;
import com.ruoyi.activity.service.IActivityTeamsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.complex.StandingAutoUpdateService;
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
 * 活动比赛场次Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动比赛场次管理")
@RestController
@RequestMapping("/activity/matches")
public class ActivityMatchesController extends BaseController
{
    @Autowired
    private IActivityMatchesService activityMatchesService;
    
    @Autowired
    private IActivityTeamsService activityTeamsService;
    
    @Autowired
    private StandingAutoUpdateService standingService;

    /**
     * 查询活动比赛场次列表
     */
    @ApiOperation("查询活动比赛场次列表")
    @PreAuthorize("@ss.hasPermi('activity:matches:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityMatches activityMatches)
    {
        startPage();
        List<ActivityMatches> list = activityMatchesService.selectActivityMatchesList(activityMatches);
        TableDataInfo dataTable = getDataTable(list);
        //提供一个方法 List<ActivityMatches> -> List<ActivityMatchesVO>
        List<ActivityMatchesVO> listVO = convertToVOList(list);
        dataTable.setRows(listVO);
        return dataTable;
    }

    private List<ActivityMatchesVO> convertToVOList(List<ActivityMatches> matchesList) {
        // 查询所有的队伍信息，构建teamId到teamName的映射
        List<ActivityTeams> teamsList = activityTeamsService.selectActivityTeamsList(new ActivityTeams());
        Map<Long, String> teamMap = teamsList.stream()
                .collect(Collectors.toMap(ActivityTeams::getTeamId, ActivityTeams::getTeamName));

        // 将比赛列表转换为VO列表，并设置队伍名称
        return matchesList.stream().map(match -> {
            ActivityMatchesVO vo = new ActivityMatchesVO();
            // 复制比赛基本信息
            vo.setMatchId(match.getMatchId());
            vo.setActivityId(match.getActivityId());
            vo.setMatchCode(match.getMatchCode());
            vo.setStage(match.getStage());
            vo.setFormat(match.getFormat());
            vo.setRoundDesc(match.getRoundDesc());
            vo.setGroupA(match.getGroupA());
            vo.setRankA(match.getRankA());
            vo.setTeamAId(match.getTeamAId());
            vo.setTeamBId(match.getTeamBId());
            vo.setCourt(match.getCourt());
            vo.setMatchDate(match.getMatchDate());
            vo.setStartTime(match.getStartTime());
            vo.setStatus(match.getStatus());
            vo.setScoreASet1(match.getScoreASet1());
            vo.setScoreBSet1(match.getScoreBSet1());
            vo.setScoreASet2(match.getScoreASet2());
            vo.setScoreBSet2(match.getScoreBSet2());
            vo.setScoreASet3(match.getScoreASet3());
            vo.setScoreBSet3(match.getScoreBSet3());
            vo.setScoreASet4(match.getScoreASet4());
            vo.setScoreBSet4(match.getScoreBSet4());
            vo.setScoreASet5(match.getScoreASet5());
            vo.setScoreBSet5(match.getScoreBSet5());
            vo.setWinnerId(match.getWinnerId());
            vo.setCreateBy(match.getCreateBy());
            vo.setCreateTime(match.getCreateTime());
            vo.setUpdateBy(match.getUpdateBy());
            vo.setUpdateTime(match.getUpdateTime());
            vo.setRemark(match.getRemark());

            // 设置队伍名称
            vo.setTeamAName(teamMap.get(match.getTeamAId()));
            vo.setTeamBName(teamMap.get(match.getTeamBId()));
            vo.setWinnerName(teamMap.get(match.getWinnerId()));
            
            // 构建scoreData字段
            StringBuilder scoreData = new StringBuilder();
            boolean hasScore = false;
            
            // 根据format字段决定局数
            int sets = (match.getFormat() != null) ? match.getFormat() : 3;
            
            // 添加第一局比分（如果非0-0）
            if (match.getScoreASet1() != null && match.getScoreBSet1() != null) {
                if (match.getScoreASet1() != 0 || match.getScoreBSet1() != 0) {
                    scoreData.append("第一局：").append(match.getScoreASet1()).append("-").append(match.getScoreBSet1());
                    hasScore = true;
                }
            }
            
            // 添加第二局比分（如果非0-0）
            if (match.getScoreASet2() != null && match.getScoreBSet2() != null) {
                if (match.getScoreASet2() != 0 || match.getScoreBSet2() != 0) {
                    if (hasScore) {
                        scoreData.append(" ");
                    }
                    scoreData.append("第二局：").append(match.getScoreASet2()).append("-").append(match.getScoreBSet2());
                    hasScore = true;
                }
            }
            
            // 添加第三局比分（如果非0-0）
            if (sets >= 3 && match.getScoreASet3() != null && match.getScoreBSet3() != null) {
                if (match.getScoreASet3() != 0 || match.getScoreBSet3() != 0) {
                    if (hasScore) {
                        scoreData.append(" ");
                    }
                    scoreData.append("第三局：").append(match.getScoreASet3()).append("-").append(match.getScoreBSet3());
                    hasScore = true;
                }
            }
            
            // 添加第四局比分（如果非0-0）
            if (sets >= 5 && match.getScoreASet4() != null && match.getScoreBSet4() != null) {
                if (match.getScoreASet4() != 0 || match.getScoreBSet4() != 0) {
                    if (hasScore) {
                        scoreData.append(" ");
                    }
                    scoreData.append("第四局：").append(match.getScoreASet4()).append("-").append(match.getScoreBSet4());
                    hasScore = true;
                }
            }
            
            // 添加第五局比分（如果非0-0）
            if (sets >= 5 && match.getScoreASet5() != null && match.getScoreBSet5() != null) {
                if (match.getScoreASet5() != 0 || match.getScoreBSet5() != 0) {
                    if (hasScore) {
                        scoreData.append(" ");
                    }
                    scoreData.append("第五局：").append(match.getScoreASet5()).append("-").append(match.getScoreBSet5());
                }
            }
            
            vo.setScoreData(scoreData.toString());

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 导出活动比赛场次列表
     */
    @ApiOperation("导出活动比赛场次列表")
    @PreAuthorize("@ss.hasPermi('activity:matches:export')")
    @Log(title = "活动比赛场次", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityMatches activityMatches)
    {
        List<ActivityMatches> list = activityMatchesService.selectActivityMatchesList(activityMatches);
        ExcelUtil<ActivityMatches> util = new ExcelUtil<ActivityMatches>(ActivityMatches.class);
        util.exportExcel(response, list, "活动比赛场次数据");
    }

    /**
     * 获取活动比赛场次详细信息
     */
    @ApiOperation("获取活动比赛场次详细信息")
    @ApiImplicitParam(name = "matchId", value = "比赛ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:matches:query')")
    @GetMapping(value = "/{matchId}")
    public AjaxResult getInfo(@PathVariable("matchId") Long matchId)
    {
        return AjaxResult.success(activityMatchesService.selectActivityMatchesByMatchId(matchId));
    }

    /**
     * 新增活动比赛场次
     */
    @ApiOperation("新增活动比赛场次")
    @PreAuthorize("@ss.hasPermi('activity:matches:add')")
    @Log(title = "活动比赛场次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityMatches activityMatches)
    {
        // 统一设置activityId为1
        activityMatches.setActivityId(1L);
        
        // 业务逻辑处理：检查比赛阶段是否有效
        if (activityMatches.getStage() != null && 
            (activityMatches.getStage() < 1 || activityMatches.getStage() > 3)) {
            return AjaxResult.error("比赛阶段必须是1、2或3");
        }
        
        // 检查赛制是否有效
        if (activityMatches.getFormat() != null && 
            activityMatches.getFormat() != 3 && activityMatches.getFormat() != 5) {
            return AjaxResult.error("赛制必须是3或5");
        }
        
        // 检查场地编号是否有效
        if (activityMatches.getCourt() != null && 
            (activityMatches.getCourt() < 1 || activityMatches.getCourt() > 3)) {
            return AjaxResult.error("场地编号必须是1、2或3");
        }
        
        // 检查小组标识是否有效
        if (activityMatches.getGroupA() != null && 
            !activityMatches.getGroupA().isEmpty() &&
            !activityMatches.getGroupA().matches("^[A-D]$")) {
            return AjaxResult.error("小组标识必须是A、B、C或D中的一个");
        }
        
        // 检查比分是否有效
        if (!isValidScores(activityMatches)) {
            return AjaxResult.error("比分必须是非负整数");
        }
        
        return toAjax(activityMatchesService.insertActivityMatches(activityMatches));
    }

    /**
     * 修改活动比赛场次
     */
    @ApiOperation("修改活动比赛场次")
    @PreAuthorize("@ss.hasPermi('activity:matches:edit')")
    @Log(title = "活动比赛场次", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityMatches activityMatches)
    {
        // 业务逻辑处理：检查比赛阶段是否有效
        if (activityMatches.getStage() != null && 
            (activityMatches.getStage() < 1 || activityMatches.getStage() > 3)) {
            return AjaxResult.error("比赛阶段必须是1、2或3");
        }
        
        // 检查赛制是否有效
        if (activityMatches.getFormat() != null && 
            activityMatches.getFormat() != 3 && activityMatches.getFormat() != 5) {
            return AjaxResult.error("赛制必须是3或5");
        }
        
        // 检查场地编号是否有效
        if (activityMatches.getCourt() != null && 
            (activityMatches.getCourt() < 1 || activityMatches.getCourt() > 3)) {
            return AjaxResult.error("场地编号必须是1、2或3");
        }
        
        // 检查小组标识是否有效
        if (activityMatches.getGroupA() != null && 
            !activityMatches.getGroupA().isEmpty() &&
            !activityMatches.getGroupA().matches("^[A-D]$")) {
            return AjaxResult.error("小组标识必须是A、B、C或D中的一个");
        }
        
        // 检查比分是否有效
        if (!isValidScores(activityMatches)) {
            return AjaxResult.error("比分必须是非负整数");
        }
        activityMatchesService.updateActivityMatches(activityMatches);
        // 自动触发积分计算（仅小组赛）
//        standingService.autoUpdateStandingsAfterMatchResult(activityMatches.getMatchId());
        return AjaxResult.success();
    }

    /**
     * 删除活动比赛场次
     */
    @ApiOperation("删除活动比赛场次")
    @ApiImplicitParam(name = "matchIds", value = "比赛ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:matches:remove')")
    @Log(title = "活动比赛场次", businessType = BusinessType.DELETE)
	@DeleteMapping("/{matchIds}")
    public AjaxResult remove(@PathVariable Long[] matchIds)
    {
        return toAjax(activityMatchesService.deleteActivityMatchesByMatchIds(matchIds));
    }
    
    /**
     * 检查比分是否有效
     * @param activityMatches
     * @return
     */
    private boolean isValidScores(ActivityMatches activityMatches) {
        // 检查所有比分字段是否为非负整数
        Integer[] scores = {
            activityMatches.getScoreASet1(),
            activityMatches.getScoreBSet1(),
            activityMatches.getScoreASet2(),
            activityMatches.getScoreBSet2(),
            activityMatches.getScoreASet3(),
            activityMatches.getScoreBSet3(),
            activityMatches.getScoreASet4(),
            activityMatches.getScoreBSet4(),
            activityMatches.getScoreASet5(),
            activityMatches.getScoreBSet5()
        };
        
        for (Integer score : scores) {
            if (score != null && score < 0) {
                return false;
            }
        }
        
        return true;
    }
}