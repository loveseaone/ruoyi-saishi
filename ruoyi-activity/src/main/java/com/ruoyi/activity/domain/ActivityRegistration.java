package com.ruoyi.activity.domain;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动报名对象 activity_registration
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityRegistration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报名ID */
    private Long regId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 参与者姓名 */
    @Excel(name = "参与者姓名")
    @NotBlank(message = "参与者姓名不能为空")
    @Size(min = 0, max = 50, message = "参与者姓名长度不能超过50个字符")
    private String participantName;

    /** 参与者手机号 */
    @Excel(name = "参与者手机号")
    @NotBlank(message = "参与者手机号不能为空")
    @Size(min = 0, max = 20, message = "参与者手机号长度不能超过20个字符")
    private String participantPhone;

    /** 参与者邮箱 */
    @Excel(name = "参与者邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 100, message = "参与者邮箱长度不能超过100个字符")
    private String participantEmail;

    /** 参与者单位 */
    @Excel(name = "参与者单位")
    @Size(min = 0, max = 100, message = "参与者单位长度不能超过100个字符")
    private String participantOrg;

    /** 报名时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报名时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registrationTime;

    /** 报名状态：0-待审核，1-已通过，2-已拒绝 */
    @Excel(name = "报名状态：0-待审核，1-已通过，2-已拒绝")
    private Integer status;

    /** 签到状态：0-未签到，1-已签到 */
    @Excel(name = "签到状态：0-未签到，1-已签到")
    private Integer checkinStatus;

    /** 签到时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签到时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkinTime;

    public void setRegId(Long regId) 
    {
        this.regId = regId;
    }

    public Long getRegId() 
    {
        return regId;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setParticipantName(String participantName) 
    {
        this.participantName = participantName;
    }

    public String getParticipantName() 
    {
        return participantName;
    }
    public void setParticipantPhone(String participantPhone) 
    {
        this.participantPhone = participantPhone;
    }

    public String getParticipantPhone() 
    {
        return participantPhone;
    }
    public void setParticipantEmail(String participantEmail) 
    {
        this.participantEmail = participantEmail;
    }

    public String getParticipantEmail() 
    {
        return participantEmail;
    }
    public void setParticipantOrg(String participantOrg) 
    {
        this.participantOrg = participantOrg;
    }

    public String getParticipantOrg() 
    {
        return participantOrg;
    }
    public void setRegistrationTime(Date registrationTime) 
    {
        this.registrationTime = registrationTime;
    }

    public Date getRegistrationTime() 
    {
        return registrationTime;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setCheckinStatus(Integer checkinStatus) 
    {
        this.checkinStatus = checkinStatus;
    }

    public Integer getCheckinStatus() 
    {
        return checkinStatus;
    }
    public void setCheckinTime(Date checkinTime) 
    {
        this.checkinTime = checkinTime;
    }

    public Date getCheckinTime() 
    {
        return checkinTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("regId", getRegId())
            .append("activityId", getActivityId())
            .append("participantName", getParticipantName())
            .append("participantPhone", getParticipantPhone())
            .append("participantEmail", getParticipantEmail())
            .append("participantOrg", getParticipantOrg())
            .append("registrationTime", getRegistrationTime())
            .append("status", getStatus())
            .append("checkinStatus", getCheckinStatus())
            .append("checkinTime", getCheckinTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}