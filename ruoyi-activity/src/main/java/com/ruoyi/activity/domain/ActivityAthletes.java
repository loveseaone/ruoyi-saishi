package com.ruoyi.activity.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动运动员及工作人员对象 activity_athletes
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityAthletes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人员ID */
    private Long athleteId;

    /** 所属队伍ID */
    @Excel(name = "所属队伍ID")
    @NotNull(message = "所属队伍ID不能为空")
    private Long teamId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 微信用户的唯一标识（小程序 openid） */
    @Excel(name = "微信用户的唯一标识")
    @Size(min = 0, max = 64, message = "微信用户的唯一标识长度不能超过64个字符")
    private String openid;

    /** 0 录入 1 待审核 2 已确认 3 已驳回 */
    @Excel(name = "状态", readConverterExp = "0=录入,1=待审核,2=已确认,3=已驳回")
    private Integer status;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 姓名 */
    @Excel(name = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(min = 0, max = 30, message = "姓名长度不能超过30个字符")
    private String name;

    /** 性别：0-男，1-女 */
    @Excel(name = "性别：0-男，1-女")
    private Integer gender;

    /** 手机号，用于小程序登录 */
    @Excel(name = "手机号，用于小程序登录")
    @Size(min = 0, max = 20, message = "手机号长度不能超过20个字符")
    private String phone;

    /** 身份证号 */
    @Excel(name = "身份证号")
    @Size(min = 0, max = 18, message = "身份证号长度不能超过18个字符")
    private String idCard;

    /** 角色：领队/教练/工会人员/男队员/女队员 */
    @Excel(name = "角色：领队/教练/工会人员/男队员/女队员")
    @NotBlank(message = "角色不能为空")
    @Size(min = 0, max = 20, message = "角色长度不能超过20个字符")
    private String role;

    /** 是否为上场运动员 */
    @Excel(name = "是否为上场运动员")
    private Integer isOnField;

    public void setAthleteId(Long athleteId) 
    {
        this.athleteId = athleteId;
    }

    public Long getAthleteId() 
    {
        return athleteId;
    }
    public void setTeamId(Long teamId) 
    {
        this.teamId = teamId;
    }

    public Long getTeamId() 
    {
        return teamId;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }
    
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGender(Integer gender) 
    {
        this.gender = gender;
    }

    public Integer getGender() 
    {
        return gender;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }
    public void setRole(String role) 
    {
        this.role = role;
    }

    public String getRole() 
    {
        return role;
    }
    public void setIsOnField(Integer isOnField) 
    {
        this.isOnField = isOnField;
    }

    public Integer getIsOnField() 
    {
        return isOnField;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("athleteId", getAthleteId())
            .append("teamId", getTeamId())
            .append("activityId", getActivityId())
            .append("openid", getOpenid())
            .append("status", getStatus())
            .append("sort", getSort())
            .append("name", getName())
            .append("gender", getGender())
            .append("phone", getPhone())
            .append("idCard", getIdCard())
            .append("role", getRole())
            .append("isOnField", getIsOnField())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}