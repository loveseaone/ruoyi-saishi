package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityMatches;
import com.ruoyi.activity.vo.ActivityMatchesVO;

/**
 * 活动比赛场次Service接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface IActivityMatchesService 
{
    /**
     * 查询活动比赛场次
     * 
     * @param matchId 活动比赛场次主键
     * @return 活动比赛场次
     */
    public ActivityMatches selectActivityMatchesByMatchId(Long matchId);

    /**
     * 查询活动比赛场次列表
     * 
     * @param activityMatches 活动比赛场次
     * @return 活动比赛场次集合
     */
    public List<ActivityMatches> selectActivityMatchesList(ActivityMatches activityMatches);

    /**
     * 查询活动比赛场次列表（包含队伍名称）
     * 
     * @param activityMatches 活动比赛场次
     * @return 活动比赛场次VO集合
     */
    public List<ActivityMatchesVO> selectActivityMatchesVOList(ActivityMatches activityMatches);

    /**
     * 新增活动比赛场次
     * 
     * @param activityMatches 活动比赛场次
     * @return 结果
     */
    public int insertActivityMatches(ActivityMatches activityMatches);

    /**
     * 修改活动比赛场次
     * 
     * @param activityMatches 活动比赛场次
     * @return 结果
     */
    public int updateActivityMatches(ActivityMatches activityMatches);

    /**
     * 批量删除活动比赛场次
     * 
     * @param matchIds 需要删除的活动比赛场次主键集合
     * @return 结果
     */
    public int deleteActivityMatchesByMatchIds(Long[] matchIds);

    /**
     * 删除活动比赛场次信息
     * 
     * @param matchId 活动比赛场次主键
     * @return 结果
     */
    public int deleteActivityMatchesByMatchId(Long matchId);
}