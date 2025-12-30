package com.ruoyi.activity.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityTeamsMapper;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityTeamsService;

/**
 * 活动队伍Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Service
public class ActivityTeamsServiceImpl implements IActivityTeamsService 
{
    @Autowired
    private ActivityTeamsMapper activityTeamsMapper;

    /**
     * 查询活动队伍
     * 
     * @param teamId 活动队伍主键
     * @return 活动队伍
     */
    @Override
    public ActivityTeams selectActivityTeamsByTeamId(Long teamId)
    {
        return activityTeamsMapper.selectActivityTeamsByTeamId(teamId);
    }

    /**
     * 查询活动队伍列表
     * 
     * @param activityTeams 活动队伍
     * @return 活动队伍
     */
    @Override
    public List<ActivityTeams> selectActivityTeamsList(ActivityTeams activityTeams)
    {
        return activityTeamsMapper.selectActivityTeamsList(activityTeams);
    }

    /**
     * 新增活动队伍
     * 
     * @param activityTeams 活动队伍
     * @return 结果
     */
    @Override
    public int insertActivityTeams(ActivityTeams activityTeams)
    {
        activityTeams.setCreateTime(DateUtils.getNowDate());
        return activityTeamsMapper.insertActivityTeams(activityTeams);
    }

    /**
     * 修改活动队伍
     * 
     * @param activityTeams 活动队伍
     * @return 结果
     */
    @Override
    public int updateActivityTeams(ActivityTeams activityTeams)
    {
        activityTeams.setUpdateTime(DateUtils.getNowDate());
        return activityTeamsMapper.updateActivityTeams(activityTeams);
    }

    /**
     * 批量删除活动队伍
     * 
     * @param teamIds 需要删除的活动队伍主键
     * @return 结果
     */
    @Override
    public int deleteActivityTeamsByTeamIds(Long[] teamIds)
    {
        return activityTeamsMapper.deleteActivityTeamsByTeamIds(teamIds);
    }

    /**
     * 删除活动队伍信息
     * 
     * @param teamId 活动队伍主键
     * @return 结果
     */
    @Override
    public int deleteActivityTeamsByTeamId(Long teamId)
    {
        return activityTeamsMapper.deleteActivityTeamsByTeamId(teamId);
    }
}