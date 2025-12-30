package com.ruoyi.activity.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityMatchesAthletesMapper;
import com.ruoyi.activity.domain.ActivityMatchesAthletes;
import com.ruoyi.activity.service.IActivityMatchesAthletesService;

/**
 * 场次成员关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-26
 */
@Service
public class ActivityMatchesAthletesServiceImpl implements IActivityMatchesAthletesService 
{
    @Autowired
    private ActivityMatchesAthletesMapper activityMatchesAthletesMapper;

    /**
     * 查询场次成员关联
     * 
     * @param id 场次成员关联主键
     * @return 场次成员关联
     */
    @Override
    public ActivityMatchesAthletes selectActivityMatchesAthletesById(Long id)
    {
        return activityMatchesAthletesMapper.selectActivityMatchesAthletesById(id);
    }

    /**
     * 查询场次成员关联列表
     * 
     * @param activityMatchesAthletes 场次成员关联
     * @return 场次成员关联
     */
    @Override
    public List<ActivityMatchesAthletes> selectActivityMatchesAthletesList(ActivityMatchesAthletes activityMatchesAthletes)
    {
        return activityMatchesAthletesMapper.selectActivityMatchesAthletesList(activityMatchesAthletes);
    }

    /**
     * 新增场次成员关联
     * 
     * @param activityMatchesAthletes 场次成员关联
     * @return 结果
     */
    @Override
    public int insertActivityMatchesAthletes(ActivityMatchesAthletes activityMatchesAthletes)
    {
        activityMatchesAthletes.setCreateTime(DateUtils.getNowDate());
        return activityMatchesAthletesMapper.insertActivityMatchesAthletes(activityMatchesAthletes);
    }

    /**
     * 修改场次成员关联
     * 
     * @param activityMatchesAthletes 场次成员关联
     * @return 结果
     */
    @Override
    public int updateActivityMatchesAthletes(ActivityMatchesAthletes activityMatchesAthletes)
    {
        activityMatchesAthletes.setUpdateTime(DateUtils.getNowDate());
        return activityMatchesAthletesMapper.updateActivityMatchesAthletes(activityMatchesAthletes);
    }

    /**
     * 批量删除场次成员关联
     * 
     * @param ids 需要删除的场次成员关联主键
     * @return 结果
     */
    @Override
    public int deleteActivityMatchesAthletesByIds(Long[] ids)
    {
        return activityMatchesAthletesMapper.deleteActivityMatchesAthletesByIds(ids);
    }

    /**
     * 删除场次成员关联信息
     * 
     * @param id 场次成员关联主键
     * @return 结果
     */
    @Override
    public int deleteActivityMatchesAthletesById(Long id)
    {
        return activityMatchesAthletesMapper.deleteActivityMatchesAthletesById(id);
    }
}