package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityTeams;

/**
 * 活动队伍Service接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface IActivityTeamsService 
{
    /**
     * 查询活动队伍
     * 
     * @param teamId 活动队伍主键
     * @return 活动队伍
     */
    public ActivityTeams selectActivityTeamsByTeamId(Long teamId);

    /**
     * 查询活动队伍列表
     * 
     * @param activityTeams 活动队伍
     * @return 活动队伍集合
     */
    public List<ActivityTeams> selectActivityTeamsList(ActivityTeams activityTeams);

    /**
     * 新增活动队伍
     * 
     * @param activityTeams 活动队伍
     * @return 结果
     */
    public int insertActivityTeams(ActivityTeams activityTeams);

    /**
     * 修改活动队伍
     * 
     * @param activityTeams 活动队伍
     * @return 结果
     */
    public int updateActivityTeams(ActivityTeams activityTeams);

    /**
     * 批量删除活动队伍
     * 
     * @param teamIds 需要删除的活动队伍主键集合
     * @return 结果
     */
    public int deleteActivityTeamsByTeamIds(Long[] teamIds);

    /**
     * 删除活动队伍信息
     * 
     * @param teamId 活动队伍主键
     * @return 结果
     */
    public int deleteActivityTeamsByTeamId(Long teamId);
}