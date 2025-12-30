package com.ruoyi.activity.mapper;

import java.util.List;
import com.ruoyi.activity.domain.ActivityMatches;

/**
 * 活动比赛场次Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface ActivityMatchesMapper 
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
     * 删除活动比赛场次
     * 
     * @param matchId 活动比赛场次主键
     * @return 结果
     */
    public int deleteActivityMatchesByMatchId(Long matchId);

    /**
     * 批量删除活动比赛场次
     * 
     * @param matchIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityMatchesByMatchIds(Long[] matchIds);
}