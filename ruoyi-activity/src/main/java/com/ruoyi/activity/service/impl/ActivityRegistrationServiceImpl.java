package com.ruoyi.activity.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityRegistrationMapper;
import com.ruoyi.activity.domain.ActivityRegistration;
import com.ruoyi.activity.service.IActivityRegistrationService;

/**
 * 活动报名Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Service
public class ActivityRegistrationServiceImpl implements IActivityRegistrationService 
{
    @Autowired
    private ActivityRegistrationMapper activityRegistrationMapper;

    /**
     * 查询活动报名
     * 
     * @param regId 活动报名主键
     * @return 活动报名
     */
    @Override
    public ActivityRegistration selectActivityRegistrationByRegId(Long regId)
    {
        return activityRegistrationMapper.selectActivityRegistrationByRegId(regId);
    }

    /**
     * 查询活动报名列表
     * 
     * @param activityRegistration 活动报名
     * @return 活动报名
     */
    @Override
    public List<ActivityRegistration> selectActivityRegistrationList(ActivityRegistration activityRegistration)
    {
        return activityRegistrationMapper.selectActivityRegistrationList(activityRegistration);
    }

    /**
     * 新增活动报名
     * 
     * @param activityRegistration 活动报名
     * @return 结果
     */
    @Override
    public int insertActivityRegistration(ActivityRegistration activityRegistration)
    {
        activityRegistration.setCreateTime(DateUtils.getNowDate());
        return activityRegistrationMapper.insertActivityRegistration(activityRegistration);
    }

    /**
     * 修改活动报名
     * 
     * @param activityRegistration 活动报名
     * @return 结果
     */
    @Override
    public int updateActivityRegistration(ActivityRegistration activityRegistration)
    {
        activityRegistration.setUpdateTime(DateUtils.getNowDate());
        return activityRegistrationMapper.updateActivityRegistration(activityRegistration);
    }

    /**
     * 批量删除活动报名
     * 
     * @param regIds 需要删除的活动报名主键
     * @return 结果
     */
    @Override
    public int deleteActivityRegistrationByRegIds(Long[] regIds)
    {
        return activityRegistrationMapper.deleteActivityRegistrationByRegIds(regIds);
    }

    /**
     * 删除活动报名信息
     * 
     * @param regId 活动报名主键
     * @return 结果
     */
    @Override
    public int deleteActivityRegistrationByRegId(Long regId)
    {
        return activityRegistrationMapper.deleteActivityRegistrationByRegId(regId);
    }
}