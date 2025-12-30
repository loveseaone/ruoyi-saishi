package com.ruoyi.activity.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 场次成员关联对象 activity_matches_athletes
 * 
 * @author ruoyi
 * @date 2025-10-26
 */
public class ActivityMatchesAthletes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 场次id */
    @Excel(name = "场次id")
    private Long matchId;

    /** 队伍id */
    @Excel(name = "队伍id")
    private Long teamId;

    /** 参赛人员id */
    @Excel(name = "参赛人员id")
    private Long athleteId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMatchId(Long matchId) 
    {
        this.matchId = matchId;
    }

    public Long getMatchId() 
    {
        return matchId;
    }
    public void setTeamId(Long teamId) 
    {
        this.teamId = teamId;
    }

    public Long getTeamId() 
    {
        return teamId;
    }
    public void setAthleteId(Long athleteId) 
    {
        this.athleteId = athleteId;
    }

    public Long getAthleteId() 
    {
        return athleteId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("matchId", getMatchId())
            .append("teamId", getTeamId())
            .append("athleteId", getAthleteId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}