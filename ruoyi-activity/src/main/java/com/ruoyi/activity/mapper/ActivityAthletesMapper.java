package com.ruoyi.activity.mapper;

import java.util.List;
import com.ruoyi.activity.domain.ActivityAthletes;

/**
 * 活动运动员及工作人员Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface ActivityAthletesMapper 
{
    /**
     * 查询活动运动员及工作人员
     * 
     * @param athleteId 活动运动员及工作人员主键
     * @return 活动运动员及工作人员
     */
    public ActivityAthletes selectActivityAthletesByAthleteId(Long athleteId);

    /**
     * 查询活动运动员及工作人员列表
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 活动运动员及工作人员集合
     */
    public List<ActivityAthletes> selectActivityAthletesList(ActivityAthletes activityAthletes);

    /**
     * 新增活动运动员及工作人员
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 结果
     */
    public int insertActivityAthletes(ActivityAthletes activityAthletes);

    /**
     * 修改活动运动员及工作人员
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 结果
     */
    public int updateActivityAthletes(ActivityAthletes activityAthletes);

    /**
     * 删除活动运动员及工作人员
     * 
     * @param athleteId 活动运动员及工作人员主键
     * @return 结果
     */
    public int deleteActivityAthletesByAthleteId(Long athleteId);

    /**
     * 批量删除活动运动员及工作人员
     * 
     * @param athleteIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityAthletesByAthleteIds(Long[] athleteIds);
}