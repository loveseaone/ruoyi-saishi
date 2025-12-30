package com.ruoyi.activity.domain;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动公告对象 activity_notices
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityNotices extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long noticeId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 公告标题 */
    @Excel(name = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 100, message = "公告标题长度不能超过100个字符")
    private String title;

    /** 公告内容 */
    @Excel(name = "公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishedAt;

    /** 是否置顶 */
    @Excel(name = "是否置顶")
    private Integer isPinned;

    public void setNoticeId(Long noticeId) 
    {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() 
    {
        return noticeId;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setPublishedAt(Date publishedAt) 
    {
        this.publishedAt = publishedAt;
    }

    public Date getPublishedAt() 
    {
        return publishedAt;
    }
    public void setIsPinned(Integer isPinned) 
    {
        this.isPinned = isPinned;
    }

    public Integer getIsPinned() 
    {
        return isPinned;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noticeId", getNoticeId())
            .append("activityId", getActivityId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("publishedAt", getPublishedAt())
            .append("isPinned", getIsPinned())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}