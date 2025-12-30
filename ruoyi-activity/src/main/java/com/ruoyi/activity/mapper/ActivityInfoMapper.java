package com.ruoyi.activity.mapper;

import java.util.List;
import com.ruoyi.activity.domain.ActivityInfo;

/**
 * 活动信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface ActivityInfoMapper 
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
     * 删除活动信息
     * 
     * @param activityId 活动信息主键
     * @return 结果
     */
    public int deleteActivityInfoByActivityId(Long activityId);

    /**
     * 批量删除活动信息
     * 
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityInfoByActivityIds(Long[] activityIds);
}