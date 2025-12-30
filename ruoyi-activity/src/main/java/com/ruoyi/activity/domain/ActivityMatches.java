package com.ruoyi.activity.domain;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动比赛场次对象 activity_matches
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
public class ActivityMatches extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 场次ID */
    private Long matchId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 场次编号 */
    @Excel(name = "场次编号")
    @Size(min = 0, max = 10, message = "场次编号长度不能超过10个字符")
    private String matchCode;

    /** 阶段：1-小组赛，2-淘汰赛，3-排位赛 */
    @Excel(name = "阶段：1-小组赛，2-淘汰赛，3-排位赛")
    private Integer stage;

    /** 赛制：3-三局两胜，5-五局三胜 */
    @Excel(name = "赛制：3-三局两胜，5-五局三胜")
    private Integer format;

    /** 轮次描述 */
    @Excel(name = "轮次描述")
    @Size(min = 0, max = 50, message = "轮次描述长度不能超过50个字符")
    private String roundDesc;

    /** A方小组 */
    @Excel(name = "A方小组")
    @Size(min = 0, max = 1, message = "A方小组长度不能超过1个字符")
    private String groupA;

    /** A方小组排名 */
    @Excel(name = "A方小组排名")
    private Integer rankA;

    /** B方小组 */
    @Excel(name = "B方小组")
    @Size(min = 0, max = 1, message = "B方小组长度不能超过1个字符")
    private String groupB;

    /** B方小组排名 */
    @Excel(name = "B方小组排名")
    private Integer rankB;

    /** A方队伍ID */
    @Excel(name = "A方队伍ID")
    private Long teamAId;

    /** B方队伍ID */
    @Excel(name = "B方队伍ID")
    private Long teamBId;

    /** 场地编号：1/2/3 */
    @Excel(name = "场地编号：1/2/3")
    private Integer court;

    /** 比赛日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "比赛日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "比赛日期不能为空")
    private Date matchDate;

    /** 开始时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /** 状态：0-未开始，1-进行中，2-已结束 */
    @Excel(name = "状态：0-未开始，1-进行中，2-已结束")
    private Integer status;

    /** A方第一局得分 */
    @Excel(name = "A方第一局得分")
    private Integer scoreASet1;

    /** B方第一局得分 */
    @Excel(name = "B方第一局得分")
    private Integer scoreBSet1;

    /** A方第二局得分 */
    @Excel(name = "A方第二局得分")
    private Integer scoreASet2;

    /** B方第二局得分 */
    @Excel(name = "B方第二局得分")
    private Integer scoreBSet2;

    /** A方第三局得分 */
    @Excel(name = "A方第三局得分")
    private Integer scoreASet3;

    /** B方第三局得分 */
    @Excel(name = "B方第三局得分")
    private Integer scoreBSet3;

    /** A方第四局得分 */
    @Excel(name = "A方第四局得分")
    private Integer scoreASet4;

    /** B方第四局得分 */
    @Excel(name = "B方第四局得分")
    private Integer scoreBSet4;

    /** A方第五局得分 */
    @Excel(name = "A方第五局得分")
    private Integer scoreASet5;

    /** B方第五局得分 */
    @Excel(name = "B方第五局得分")
    private Integer scoreBSet5;

    /** 胜方队伍ID */
    @Excel(name = "胜方队伍ID")
    private Long winnerId;

    public void setMatchId(Long matchId) 
    {
        this.matchId = matchId;
    }

    public Long getMatchId() 
    {
        return matchId;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setMatchCode(String matchCode) 
    {
        this.matchCode = matchCode;
    }

    public String getMatchCode() 
    {
        return matchCode;
    }
    public void setStage(Integer stage) 
    {
        this.stage = stage;
    }

    public Integer getStage() 
    {
        return stage;
    }
    public void setFormat(Integer format) 
    {
        this.format = format;
    }

    public Integer getFormat() 
    {
        return format;
    }
    public void setRoundDesc(String roundDesc) 
    {
        this.roundDesc = roundDesc;
    }

    public String getRoundDesc() 
    {
        return roundDesc;
    }
    public void setGroupA(String groupA) 
    {
        this.groupA = groupA;
    }

    public String getGroupA() 
    {
        return groupA;
    }
    public void setRankA(Integer rankA) 
    {
        this.rankA = rankA;
    }

    public Integer getRankA() 
    {
        return rankA;
    }
    public void setGroupB(String groupB) 
    {
        this.groupB = groupB;
    }

    public String getGroupB() 
    {
        return groupB;
    }
    public void setRankB(Integer rankB) 
    {
        this.rankB = rankB;
    }

    public Integer getRankB() 
    {
        return rankB;
    }
    public void setTeamAId(Long teamAId) 
    {
        this.teamAId = teamAId;
    }

    public Long getTeamAId() 
    {
        return teamAId;
    }
    public void setTeamBId(Long teamBId) 
    {
        this.teamBId = teamBId;
    }

    public Long getTeamBId() 
    {
        return teamBId;
    }
    public void setCourt(Integer court) 
    {
        this.court = court;
    }

    public Integer getCourt() 
    {
        return court;
    }
    public void setMatchDate(Date matchDate) 
    {
        this.matchDate = matchDate;
    }

    public Date getMatchDate() 
    {
        return matchDate;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setScoreASet1(Integer scoreASet1) 
    {
        this.scoreASet1 = scoreASet1;
    }

    public Integer getScoreASet1() 
    {
        return scoreASet1;
    }
    public void setScoreBSet1(Integer scoreBSet1) 
    {
        this.scoreBSet1 = scoreBSet1;
    }

    public Integer getScoreBSet1() 
    {
        return scoreBSet1;
    }
    public void setScoreASet2(Integer scoreASet2) 
    {
        this.scoreASet2 = scoreASet2;
    }

    public Integer getScoreASet2() 
    {
        return scoreASet2;
    }
    public void setScoreBSet2(Integer scoreBSet2) 
    {
        this.scoreBSet2 = scoreBSet2;
    }

    public Integer getScoreBSet2() 
    {
        return scoreBSet2;
    }
    public void setScoreASet3(Integer scoreASet3) 
    {
        this.scoreASet3 = scoreASet3;
    }

    public Integer getScoreASet3() 
    {
        return scoreASet3;
    }
    public void setScoreBSet3(Integer scoreBSet3) 
    {
        this.scoreBSet3 = scoreBSet3;
    }

    public Integer getScoreBSet3() 
    {
        return scoreBSet3;
    }
    public void setScoreASet4(Integer scoreASet4) 
    {
        this.scoreASet4 = scoreASet4;
    }

    public Integer getScoreASet4() 
    {
        return scoreASet4;
    }
    public void setScoreBSet4(Integer scoreBSet4) 
    {
        this.scoreBSet4 = scoreBSet4;
    }

    public Integer getScoreBSet4() 
    {
        return scoreBSet4;
    }
    public void setScoreASet5(Integer scoreASet5) 
    {
        this.scoreASet5 = scoreASet5;
    }

    public Integer getScoreASet5() 
    {
        return scoreASet5;
    }
    public void setScoreBSet5(Integer scoreBSet5) 
    {
        this.scoreBSet5 = scoreBSet5;
    }

    public Integer getScoreBSet5() 
    {
        return scoreBSet5;
    }
    public void setWinnerId(Long winnerId) 
    {
        this.winnerId = winnerId;
    }

    public Long getWinnerId() 
    {
        return winnerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("matchId", getMatchId())
            .append("activityId", getActivityId())
            .append("matchCode", getMatchCode())
            .append("stage", getStage())
            .append("format", getFormat())
            .append("roundDesc", getRoundDesc())
            .append("groupA", getGroupA())
            .append("rankA", getRankA())
            .append("groupB", getGroupB())
            .append("rankB", getRankB())
            .append("teamAId", getTeamAId())
            .append("teamBId", getTeamBId())
            .append("court", getCourt())
            .append("matchDate", getMatchDate())
            .append("startTime", getStartTime())
            .append("status", getStatus())
            .append("scoreASet1", getScoreASet1())
            .append("scoreBSet1", getScoreBSet1())
            .append("scoreASet2", getScoreASet2())
            .append("scoreBSet2", getScoreBSet2())
            .append("scoreASet3", getScoreASet3())
            .append("scoreBSet3", getScoreBSet3())
            .append("scoreASet4", getScoreASet4())
            .append("scoreBSet4", getScoreBSet4())
            .append("scoreASet5", getScoreASet5())
            .append("scoreBSet5", getScoreBSet5())
            .append("winnerId", getWinnerId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}