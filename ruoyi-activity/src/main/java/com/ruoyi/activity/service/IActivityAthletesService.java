package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.vo.ActivityAthletesVO;

/**
 * 活动运动员及工作人员Service接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface IActivityAthletesService 
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
     * 查询活动运动员及工作人员列表（包含队伍名称）
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 活动运动员及工作人员VO集合
     */
    public List<ActivityAthletesVO> selectActivityAthletesVOList(ActivityAthletes activityAthletes);

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
     * 批量删除活动运动员及工作人员
     * 
     * @param athleteIds 需要删除的活动运动员及工作人员主键集合
     * @return 结果
     */
    public int deleteActivityAthletesByAthleteIds(Long[] athleteIds);

    /**
     * 删除活动运动员及工作人员信息
     * 
     * @param athleteId 活动运动员及工作人员主键
     * @return 结果
     */
    public int deleteActivityAthletesByAthleteId(Long athleteId);
}