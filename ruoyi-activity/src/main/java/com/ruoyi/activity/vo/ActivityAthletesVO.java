package com.ruoyi.activity.vo;

/**
 * 活动运动员及工作人员VO对象，用于展示运动员及其队伍名称
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
public class ActivityAthletesVO extends com.ruoyi.activity.domain.ActivityAthletes {
    private static final long serialVersionUID = 1L;

    /** 队伍名称 */
    private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}