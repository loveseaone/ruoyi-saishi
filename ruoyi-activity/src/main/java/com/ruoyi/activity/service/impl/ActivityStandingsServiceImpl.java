package com.ruoyi.activity.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityStandingsMapper;
import com.ruoyi.activity.domain.ActivityStandings;
import com.ruoyi.activity.service.IActivityStandingsService;

/**
 * 活动积分排名Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Service
public class ActivityStandingsServiceImpl implements IActivityStandingsService 
{
    @Autowired
    private ActivityStandingsMapper activityStandingsMapper;

    /**
     * 查询活动积分排名
     * 
     * @param standingId 活动积分排名主键
     * @return 活动积分排名
     */
    @Override
    public ActivityStandings selectActivityStandingsByStandingId(Long standingId)
    {
        return activityStandingsMapper.selectActivityStandingsByStandingId(standingId);
    }

    /**
     * 查询活动积分排名列表
     * 
     * @param activityStandings 活动积分排名
     * @return 活动积分排名
     */
    @Override
    public List<ActivityStandings> selectActivityStandingsList(ActivityStandings activityStandings)
    {
        return activityStandingsMapper.selectActivityStandingsList(activityStandings);
    }

    /**
     * 新增活动积分排名
     * 
     * @param activityStandings 活动积分排名
     * @return 结果
     */
    @Override
    public int insertActivityStandings(ActivityStandings activityStandings)
    {
        activityStandings.setCreateTime(DateUtils.getNowDate());
        return activityStandingsMapper.insertActivityStandings(activityStandings);
    }

    /**
     * 修改活动积分排名
     * 
     * @param activityStandings 活动积分排名
     * @return 结果
     */
    @Override
    public int updateActivityStandings(ActivityStandings activityStandings)
    {
        activityStandings.setUpdateTime(DateUtils.getNowDate());
        return activityStandingsMapper.updateActivityStandings(activityStandings);
    }

    /**
     * 批量删除活动积分排名
     * 
     * @param standingIds 需要删除的活动积分排名主键
     * @return 结果
     */
    @Override
    public int deleteActivityStandingsByStandingIds(Long[] standingIds)
    {
        return activityStandingsMapper.deleteActivityStandingsByStandingIds(standingIds);
    }

    /**
     * 删除活动积分排名信息
     * 
     * @param standingId 活动积分排名主键
     * @return 结果
     */
    @Override
    public int deleteActivityStandingsByStandingId(Long standingId)
    {
        return activityStandingsMapper.deleteActivityStandingsByStandingId(standingId);
    }
}