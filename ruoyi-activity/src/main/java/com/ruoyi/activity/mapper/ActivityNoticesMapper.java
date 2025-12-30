package com.ruoyi.activity.mapper;

import java.util.List;
import com.ruoyi.activity.domain.ActivityNotices;

/**
 * 活动公告Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public interface ActivityNoticesMapper 
{
    /**
     * 查询活动公告
     * 
     * @param noticeId 活动公告主键
     * @return 活动公告
     */
    public ActivityNotices selectActivityNoticesByNoticeId(Long noticeId);

    /**
     * 查询活动公告列表
     * 
     * @param activityNotices 活动公告
     * @return 活动公告集合
     */
    public List<ActivityNotices> selectActivityNoticesList(ActivityNotices activityNotices);

    /**
     * 新增活动公告
     * 
     * @param activityNotices 活动公告
     * @return 结果
     */
    public int insertActivityNotices(ActivityNotices activityNotices);

    /**
     * 修改活动公告
     * 
     * @param activityNotices 活动公告
     * @return 结果
     */
    public int updateActivityNotices(ActivityNotices activityNotices);

    /**
     * 删除活动公告
     * 
     * @param noticeId 活动公告主键
     * @return 结果
     */
    public int deleteActivityNoticesByNoticeId(Long noticeId);

    /**
     * 批量删除活动公告
     * 
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityNoticesByNoticeIds(Long[] noticeIds);
}