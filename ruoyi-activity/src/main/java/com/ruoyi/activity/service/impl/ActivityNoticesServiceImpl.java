package com.ruoyi.activity.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityNoticesMapper;
import com.ruoyi.activity.domain.ActivityNotices;
import com.ruoyi.activity.service.IActivityNoticesService;

/**
 * 活动公告Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Service
public class ActivityNoticesServiceImpl implements IActivityNoticesService 
{
    @Autowired
    private ActivityNoticesMapper activityNoticesMapper;

    /**
     * 查询活动公告
     * 
     * @param noticeId 活动公告主键
     * @return 活动公告
     */
    @Override
    public ActivityNotices selectActivityNoticesByNoticeId(Long noticeId)
    {
        return activityNoticesMapper.selectActivityNoticesByNoticeId(noticeId);
    }

    /**
     * 查询活动公告列表
     * 
     * @param activityNotices 活动公告
     * @return 活动公告
     */
    @Override
    public List<ActivityNotices> selectActivityNoticesList(ActivityNotices activityNotices)
    {
        return activityNoticesMapper.selectActivityNoticesList(activityNotices);
    }

    /**
     * 新增活动公告
     * 
     * @param activityNotices 活动公告
     * @return 结果
     */
    @Override
    public int insertActivityNotices(ActivityNotices activityNotices)
    {
        activityNotices.setCreateTime(DateUtils.getNowDate());
        return activityNoticesMapper.insertActivityNotices(activityNotices);
    }

    /**
     * 修改活动公告
     * 
     * @param activityNotices 活动公告
     * @return 结果
     */
    @Override
    public int updateActivityNotices(ActivityNotices activityNotices)
    {
        activityNotices.setUpdateTime(DateUtils.getNowDate());
        return activityNoticesMapper.updateActivityNotices(activityNotices);
    }

    /**
     * 批量删除活动公告
     * 
     * @param noticeIds 需要删除的活动公告主键
     * @return 结果
     */
    @Override
    public int deleteActivityNoticesByNoticeIds(Long[] noticeIds)
    {
        return activityNoticesMapper.deleteActivityNoticesByNoticeIds(noticeIds);
    }

    /**
     * 删除活动公告信息
     * 
     * @param noticeId 活动公告主键
     * @return 结果
     */
    @Override
    public int deleteActivityNoticesByNoticeId(Long noticeId)
    {
        return activityNoticesMapper.deleteActivityNoticesByNoticeId(noticeId);
    }
}