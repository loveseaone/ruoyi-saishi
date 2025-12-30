package com.ruoyi.activity.vo;

import com.ruoyi.activity.domain.ActivityMatchesAthletes;

/**
 * 场次成员关联VO对象，用于展示场次成员关联及其相关信息
 *
 * @author ruoyi
 * @date 2025-10-26
 */
public class ActivityMatchesAthletesVO extends ActivityMatchesAthletes {
    private static final long serialVersionUID = 1L;

    /**
     * 场次编号
     */
    private String matchCode;

    /**
     * 队伍名称
     */
    private String teamName;

    /**
     * 参赛人员姓名
     */
    private String athleteName;

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }
}