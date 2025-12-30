package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityMatchesAthletes;

/**
 * 场次成员关联Service接口
 * 
 * @author ruoyi
 * @date 2025-10-26
 */
public interface IActivityMatchesAthletesService 
{
    /**
     * 查询场次成员关联
     * 
     * @param id 场次成员关联主键
     * @return 场次成员关联
     */
    public ActivityMatchesAthletes selectActivityMatchesAthletesById(Long id);

    /**
     * 查询场次成员关联列表
     * 
     * @param activityMatchesAthletes 场次成员关联
     * @return 场次成员关联集合
     */
    public List<ActivityMatchesAthletes> selectActivityMatchesAthletesList(ActivityMatchesAthletes activityMatchesAthletes);

    /**
     * 新增场次成员关联
     * 
     * @param activityMatchesAthletes 场次成员关联
     * @return 结果
     */
    public int insertActivityMatchesAthletes(ActivityMatchesAthletes activityMatchesAthletes);

    /**
     * 修改场次成员关联
     * 
     * @param activityMatchesAthletes 场次成员关联
     * @return 结果
     */
    public int updateActivityMatchesAthletes(ActivityMatchesAthletes activityMatchesAthletes);

    /**
     * 批量删除场次成员关联
     * 
     * @param ids 需要删除的场次成员关联主键集合
     * @return 结果
     */
    public int deleteActivityMatchesAthletesByIds(Long[] ids);

    /**
     * 删除场次成员关联信息
     * 
     * @param id 场次成员关联主键
     * @return 结果
     */
    public int deleteActivityMatchesAthletesById(Long id);
}