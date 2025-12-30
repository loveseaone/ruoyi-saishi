package com.ruoyi.activity.vo;

import com.ruoyi.activity.domain.ActivityStandings;

/**
 * 活动积分排名VO对象，用于展示活动积分排名及其相关信息
 *
 * @author ruoyi
 * @date 2025-10-27
 */
public class ActivityStandingsVO extends ActivityStandings {
    private static final long serialVersionUID = 1L;

    /**
     * 队伍名称
     */
    private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}