package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityRegistration;

/**
 * 活动报名Service接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface IActivityRegistrationService 
{
    /**
     * 查询活动报名
     * 
     * @param regId 活动报名主键
     * @return 活动报名
     */
    public ActivityRegistration selectActivityRegistrationByRegId(Long regId);

    /**
     * 查询活动报名列表
     * 
     * @param activityRegistration 活动报名
     * @return 活动报名集合
     */
    public List<ActivityRegistration> selectActivityRegistrationList(ActivityRegistration activityRegistration);

    /**
     * 新增活动报名
     * 
     * @param activityRegistration 活动报名
     * @return 结果
     */
    public int insertActivityRegistration(ActivityRegistration activityRegistration);

    /**
     * 修改活动报名
     * 
     * @param activityRegistration 活动报名
     * @return 结果
     */
    public int updateActivityRegistration(ActivityRegistration activityRegistration);

    /**
     * 批量删除活动报名
     * 
     * @param regIds 需要删除的活动报名主键集合
     * @return 结果
     */
    public int deleteActivityRegistrationByRegIds(Long[] regIds);

    /**
     * 删除活动报名信息
     * 
     * @param regId 活动报名主键
     * @return 结果
     */
    public int deleteActivityRegistrationByRegId(Long regId);
}