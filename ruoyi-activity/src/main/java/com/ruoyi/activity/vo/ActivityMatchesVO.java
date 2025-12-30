package com.ruoyi.activity.vo;

/**
 * 活动比赛场次VO对象，用于展示比赛及其队伍名称
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
public class ActivityMatchesVO extends com.ruoyi.activity.domain.ActivityMatches {
    private static final long serialVersionUID = 1L;

    /** A方队伍名称 */
    private String teamAName;

    /** B方队伍名称 */
    private String teamBName;

    /** 胜方队伍名称 */
    private String winnerName;
    
    /** 得分数据 */
    private String scoreData;

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
    
    public String getScoreData() {
        return scoreData;
    }
    
    public void setScoreData(String scoreData) {
        this.scoreData = scoreData;
    }
}