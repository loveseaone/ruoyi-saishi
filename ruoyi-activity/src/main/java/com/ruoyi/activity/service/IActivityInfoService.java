package com.ruoyi.activity.service;

import java.util.List;
import com.ruoyi.activity.domain.ActivityInfo;

/**
 * 活动信息Service接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface IActivityInfoService 
{
    /**
     * 查询活动信息
     * 
     * @param activityId 活动信息主键
     * @return 活动信息
     */
    public ActivityInfo selectActivityInfoByActivityId(Long activityId);

    /**
     * 查询活动信息列表
     * 
     * @param activityInfo 活动信息
     * @return 活动信息集合
     */
    public List<ActivityInfo> selectActivityInfoList(ActivityInfo activityInfo);

    /**
     * 新增活动信息
     * 
     * @param activityInfo 活动信息
     * @return 结果
     */
    public int insertActivityInfo(ActivityInfo activityInfo);

    /**
     * 修改活动信息
     * 
     * @param activityInfo 活动信息
     * @return 结果
     */
    public int updateActivityInfo(ActivityInfo activityInfo);

    /**
     * 批量删除活动信息
     * 
     * @param activityIds 需要删除的活动信息主键集合
     * @return 结果
     */
    public int deleteActivityInfoByActivityIds(Long[] activityIds);

    /**
     * 删除活动信息信息
     * 
     * @param activityId 活动信息主键
     * @return 结果
     */
    public int deleteActivityInfoByActivityId(Long activityId);
}