package com.ruoyi.activity.mapper;

import java.util.List;
import com.ruoyi.activity.domain.ActivityRegistration;

/**
 * 活动报名Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface ActivityRegistrationMapper 
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
     * 删除活动报名
     * 
     * @param regId 活动报名主键
     * @return 结果
     */
    public int deleteActivityRegistrationByRegId(Long regId);

    /**
     * 批量删除活动报名
     * 
     * @param regIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityRegistrationByRegIds(Long[] regIds);
}