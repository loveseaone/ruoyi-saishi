package com.ruoyi.activity.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动队伍对象 activity_teams
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityTeams extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 队伍ID */
    private Long teamId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 队伍全称 */
    @Excel(name = "队伍全称")
    @NotBlank(message = "队伍全称不能为空")
    @Size(min = 0, max = 50, message = "队伍全称长度不能超过50个字符")
    private String teamName;

    /** 0 录入 1 待审核 2 已确认 3 已驳回 */
    @Excel(name = "状态", readConverterExp = "0=录入,1=待审核,2=已确认,3=已驳回")
    private Integer status;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 小组赛分组：A/B/C/D */
    @Excel(name = "小组赛分组：A/B/C/D")
    @Size(min = 0, max = 1, message = "小组赛分组长度不能超过1个字符")
    private String groupCode;

    /** 展示排序 */
    @Excel(name = "展示排序")
    private Integer displayOrder;

    /** 领队姓名 */
    @Excel(name = "领队姓名")
    @Size(min = 0, max = 30, message = "领队姓名长度不能超过30个字符")
    private String leaderName;

    /** 领队电话 */
    @Excel(name = "领队电话")
    @Size(min = 0, max = 20, message = "领队电话长度不能超过20个字符")
    private String leaderPhone;

    /** 教练姓名 */
    @Excel(name = "教练姓名")
    @Size(min = 0, max = 30, message = "教练姓名长度不能超过30个字符")
    private String coachName;

    /** 工会联络人 */
    @Excel(name = "工会联络人")
    @Size(min = 0, max = 30, message = "工会联络人长度不能超过30个字符")
    private String unionContact;

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
    public void setTeamName(String teamName) 
    {
        this.teamName = teamName;
    }

    public String getTeamName() 
    {
        return teamName;
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
    public void setGroupCode(String groupCode) 
    {
        this.groupCode = groupCode;
    }

    public String getGroupCode() 
    {
        return groupCode;
    }
    public void setDisplayOrder(Integer displayOrder) 
    {
        this.displayOrder = displayOrder;
    }

    public Integer getDisplayOrder() 
    {
        return displayOrder;
    }
    public void setLeaderName(String leaderName) 
    {
        this.leaderName = leaderName;
    }

    public String getLeaderName() 
    {
        return leaderName;
    }
    public void setLeaderPhone(String leaderPhone) 
    {
        this.leaderPhone = leaderPhone;
    }

    public String getLeaderPhone() 
    {
        return leaderPhone;
    }
    public void setCoachName(String coachName) 
    {
        this.coachName = coachName;
    }

    public String getCoachName() 
    {
        return coachName;
    }
    public void setUnionContact(String unionContact) 
    {
        this.unionContact = unionContact;
    }

    public String getUnionContact() 
    {
        return unionContact;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("teamId", getTeamId())
            .append("activityId", getActivityId())
            .append("teamName", getTeamName())
            .append("status", getStatus())
            .append("sort", getSort())
            .append("groupCode", getGroupCode())
            .append("displayOrder", getDisplayOrder())
            .append("leaderName", getLeaderName())
            .append("leaderPhone", getLeaderPhone())
            .append("coachName", getCoachName())
            .append("unionContact", getUnionContact())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}