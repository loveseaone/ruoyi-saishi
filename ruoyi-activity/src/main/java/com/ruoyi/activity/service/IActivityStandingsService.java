package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityStandings;

/**
 * 活动积分排名Service接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface IActivityStandingsService 
{
    /**
     * 查询活动积分排名
     * 
     * @param standingId 活动积分排名主键
     * @return 活动积分排名
     */
    public ActivityStandings selectActivityStandingsByStandingId(Long standingId);

    /**
     * 查询活动积分排名列表
     * 
     * @param activityStandings 活动积分排名
     * @return 活动积分排名集合
     */
    public List<ActivityStandings> selectActivityStandingsList(ActivityStandings activityStandings);

    /**
     * 新增活动积分排名
     * 
     * @param activityStandings 活动积分排名
     * @return 结果
     */
    public int insertActivityStandings(ActivityStandings activityStandings);

    /**
     * 修改活动积分排名
     * 
     * @param activityStandings 活动积分排名
     * @return 结果
     */
    public int updateActivityStandings(ActivityStandings activityStandings);

    /**
     * 批量删除活动积分排名
     * 
     * @param standingIds 需要删除的活动积分排名主键集合
     * @return 结果
     */
    public int deleteActivityStandingsByStandingIds(Long[] standingIds);

    /**
     * 删除活动积分排名信息
     * 
     * @param standingId 活动积分排名主键
     * @return 结果
     */
    public int deleteActivityStandingsByStandingId(Long standingId);
}