package com.ruoyi.activity.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动积分排名对象 activity_standings
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityStandings extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long standingId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 队伍ID */
    @Excel(name = "队伍ID")
    @NotNull(message = "队伍ID不能为空")
    private Long teamId;

    /** 小组标识 A/B/C/D */
    @Excel(name = "小组标识 A/B/C/D")
    @Size(min = 0, max = 1, message = "小组标识长度不能超过1个字符")
    private String groupCode;

    /** 已赛场次 */
    @Excel(name = "已赛场次")
    private Integer matchesPlayed;

    /** 胜场 */
    @Excel(name = "胜场")
    private Integer wins;

    /** 负场 */
    @Excel(name = "负场")
    private Integer losses;

    /** 积分 */
    @Excel(name = "积分")
    private Integer points;

    /** 胜局数 */
    @Excel(name = "胜局数")
    private Integer setsWon;

    /** 负局数 */
    @Excel(name = "负局数")
    private Integer setsLost;

    /** 总得分 */
    @Excel(name = "总得分")
    private Integer pointsFor;

    /** 总失分 */
    @Excel(name = "总失分")
    private Integer pointsAgainst;

    /** C值 = 胜局/负局 */
    @Excel(name = "C值 = 胜局/负局")
    private Double cValue;

    /** Z值 = 总得分/总失分 */
    @Excel(name = "Z값 = 总得分/总失分")
    private Double zValue;

    /** 小组内排名 */
    @Excel(name = "小组内排名")
    private Integer rankInGroup;

    public void setStandingId(Long standingId) 
    {
        this.standingId = standingId;
    }

    public Long getStandingId() 
    {
        return standingId;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setTeamId(Long teamId) 
    {
        this.teamId = teamId;
    }

    public Long getTeamId() 
    {
        return teamId;
    }
    public void setGroupCode(String groupCode) 
    {
        this.groupCode = groupCode;
    }

    public String getGroupCode() 
    {
        return groupCode;
    }
    public void setMatchesPlayed(Integer matchesPlayed) 
    {
        this.matchesPlayed = matchesPlayed;
    }

    public Integer getMatchesPlayed() 
    {
        return matchesPlayed;
    }
    public void setWins(Integer wins) 
    {
        this.wins = wins;
    }

    public Integer getWins() 
    {
        return wins;
    }
    public void setLosses(Integer losses) 
    {
        this.losses = losses;
    }

    public Integer getLosses() 
    {
        return losses;
    }
    public void setPoints(Integer points) 
    {
        this.points = points;
    }

    public Integer getPoints() 
    {
        return points;
    }
    public void setSetsWon(Integer setsWon) 
    {
        this.setsWon = setsWon;
    }

    public Integer getSetsWon() 
    {
        return setsWon;
    }
    public void setSetsLost(Integer setsLost) 
    {
        this.setsLost = setsLost;
    }

    public Integer getSetsLost() 
    {
        return setsLost;
    }
    public void setPointsFor(Integer pointsFor) 
    {
        this.pointsFor = pointsFor;
    }

    public Integer getPointsFor() 
    {
        return pointsFor;
    }
    public void setPointsAgainst(Integer pointsAgainst) 
    {
        this.pointsAgainst = pointsAgainst;
    }

    public Integer getPointsAgainst() 
    {
        return pointsAgainst;
    }
    public void setCValue(Double cValue) 
    {
        this.cValue = cValue;
    }

    public Double getCValue() 
    {
        return cValue;
    }
    public void setZValue(Double zValue) 
    {
        this.zValue = zValue;
    }

    public Double getZValue() 
    {
        return zValue;
    }
    public void setRankInGroup(Integer rankInGroup) 
    {
        this.rankInGroup = rankInGroup;
    }

    public Integer getRankInGroup() 
    {
        return rankInGroup;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("standingId", getStandingId())
            .append("activityId", getActivityId())
            .append("teamId", getTeamId())
            .append("groupCode", getGroupCode())
            .append("matchesPlayed", getMatchesPlayed())
            .append("wins", getWins())
            .append("losses", getLosses())
            .append("points", getPoints())
            .append("setsWon", getSetsWon())
            .append("setsLost", getSetsLost())
            .append("pointsFor", getPointsFor())
            .append("pointsAgainst", getPointsAgainst())
            .append("cValue", getCValue())
            .append("zValue", getZValue())
            .append("rankInGroup", getRankInGroup())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}