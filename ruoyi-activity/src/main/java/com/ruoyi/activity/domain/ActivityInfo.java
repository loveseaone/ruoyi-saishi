package com.ruoyi.activity.domain;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动信息对象 activity_info
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    @NotBlank(message = "活动名称不能为空")
    @Size(min = 0, max = 100, message = "活动名称长度不能超过100个字符")
    private String activityName;

    /** 活动描述 */
    @Excel(name = "活动描述")
    @Size(min = 0, max = 500, message = "活动描述长度不能超过500个字符")
    private String activityDesc;

    /** 活动类型 */
    @Excel(name = "活动类型")
    @NotBlank(message = "活动类型不能为空")
    @Size(min = 0, max = 20, message = "活动类型长度不能超过20个字符")
    private String activityType;

    /** 活动开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "活动开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "活动开始时间不能为空")
    private Date startTime;

    /** 活动结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "活动结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "活动结束时间不能为空")
    private Date endTime;

    /** 活动状态：0-未开始，1-进行中，2-已结束 */
    @Excel(name = "活动状态：0-未开始，1-进行中，2-已结束")
    private Integer status;

    /** 主办方 */
    @Excel(name = "主办方")
    @Size(min = 0, max = 100, message = "主办方长度不能超过100个字符")
    private String organizer;

    /** 联系方式 */
    @Excel(name = "联系方式")
    @Size(min = 0, max = 100, message = "联系方式长度不能超过100个字符")
    private String contactInfo;

    /** 活动地点 */
    @Excel(name = "活动地点")
    @Size(min = 0, max = 200, message = "活动地点长度不能超过200个字符")
    private String location;

    /** 最大参与人数 */
    @Excel(name = "最大参与人数")
    private Integer maxParticipants;

    /** 当前参与人数 */
    @Excel(name = "当前参与人数")
    private Integer currentParticipants;

    /** 报名开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报名开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registrationStart;

    /** 报名结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报名结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registrationEnd;

    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setActivityName(String activityName) 
    {
        this.activityName = activityName;
    }

    public String getActivityName() 
    {
        return activityName;
    }
    public void setActivityDesc(String activityDesc) 
    {
        this.activityDesc = activityDesc;
    }

    public String getActivityDesc() 
    {
        return activityDesc;
    }
    public void setActivityType(String activityType) 
    {
        this.activityType = activityType;
    }

    public String getActivityType() 
    {
        return activityType;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setOrganizer(String organizer) 
    {
        this.organizer = organizer;
    }

    public String getOrganizer() 
    {
        return organizer;
    }
    public void setContactInfo(String contactInfo) 
    {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() 
    {
        return contactInfo;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setMaxParticipants(Integer maxParticipants) 
    {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMaxParticipants() 
    {
        return maxParticipants;
    }
    public void setCurrentParticipants(Integer currentParticipants) 
    {
        this.currentParticipants = currentParticipants;
    }

    public Integer getCurrentParticipants() 
    {
        return currentParticipants;
    }
    public void setRegistrationStart(Date registrationStart) 
    {
        this.registrationStart = registrationStart;
    }

    public Date getRegistrationStart() 
    {
        return registrationStart;
    }
    public void setRegistrationEnd(Date registrationEnd) 
    {
        this.registrationEnd = registrationEnd;
    }

    public Date getRegistrationEnd() 
    {
        return registrationEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("activityId", getActivityId())
            .append("activityName", getActivityName())
            .append("activityDesc", getActivityDesc())
            .append("activityType", getActivityType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("status", getStatus())
            .append("organizer", getOrganizer())
            .append("contactInfo", getContactInfo())
            .append("location", getLocation())
            .append("maxParticipants", getMaxParticipants())
            .append("currentParticipants", getCurrentParticipants())
            .append("registrationStart", getRegistrationStart())
            .append("registrationEnd", getRegistrationEnd())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}