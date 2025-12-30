package com.ruoyi.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityMatchesMapper;
import com.ruoyi.activity.mapper.ActivityTeamsMapper;
import com.ruoyi.activity.domain.ActivityMatches;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.vo.ActivityMatchesVO;
import com.ruoyi.activity.service.IActivityMatchesService;

/**
 * 活动比赛场次Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Service
public class ActivityMatchesServiceImpl implements IActivityMatchesService 
{
    @Autowired
    private ActivityMatchesMapper activityMatchesMapper;

    @Autowired
    private ActivityTeamsMapper activityTeamsMapper;

    /**
     * 查询活动比赛场次
     * 
     * @param matchId 活动比赛场次主键
     * @return 活动比赛场次
     */
    @Override
    public ActivityMatches selectActivityMatchesByMatchId(Long matchId)
    {
        return activityMatchesMapper.selectActivityMatchesByMatchId(matchId);
    }

    /**
     * 查询活动比赛场次列表
     * 
     * @param activityMatches 活动比赛场次
     * @return 活动比赛场次
     */
    @Override
    public List<ActivityMatches> selectActivityMatchesList(ActivityMatches activityMatches)
    {
        return activityMatchesMapper.selectActivityMatchesList(activityMatches);
    }

    /**
     * 查询活动比赛场次列表（包含队伍名称）
     * 
     * @param activityMatches 活动比赛场次
     * @return 活动比赛场次VO集合
     */
    @Override
    public List<ActivityMatchesVO> selectActivityMatchesVOList(ActivityMatches activityMatches)
    {
        // 先查询所有符合条件的比赛
        List<ActivityMatches> matchesList = activityMatchesMapper.selectActivityMatchesList(activityMatches);
        
        // 查询所有的队伍信息，构建teamId到teamName的映射
        List<ActivityTeams> teamsList = activityTeamsMapper.selectActivityTeamsList(new ActivityTeams());
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
            
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 新增活动比赛场次
     * 
     * @param activityMatches 活动比赛场次
     * @return 结果
     */
    @Override
    public int insertActivityMatches(ActivityMatches activityMatches)
    {
        activityMatches.setCreateTime(DateUtils.getNowDate());
        return activityMatchesMapper.insertActivityMatches(activityMatches);
    }

    /**
     * 修改活动比赛场次
     * 
     * @param activityMatches 活动比赛场次
     * @return 结果
     */
    @Override
    public int updateActivityMatches(ActivityMatches activityMatches)
    {
        activityMatches.setUpdateTime(DateUtils.getNowDate());
        return activityMatchesMapper.updateActivityMatches(activityMatches);
    }

    /**
     * 批量删除活动比赛场次
     * 
     * @param matchIds 需要删除的活动比赛场次主键
     * @return 结果
     */
    @Override
    public int deleteActivityMatchesByMatchIds(Long[] matchIds)
    {
        return activityMatchesMapper.deleteActivityMatchesByMatchIds(matchIds);
    }

    /**
     * 删除活动比赛场次信息
     * 
     * @param matchId 活动比赛场次主键
     * @return 结果
     */
    @Override
    public int deleteActivityMatchesByMatchId(Long matchId)
    {
        return activityMatchesMapper.deleteActivityMatchesByMatchId(matchId);
    }
}