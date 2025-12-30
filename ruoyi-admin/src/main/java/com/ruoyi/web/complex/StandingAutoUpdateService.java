package com.ruoyi.web.complex;

import com.ruoyi.activity.domain.ActivityMatches;
import com.ruoyi.activity.domain.ActivityStandings;
import com.ruoyi.activity.service.IActivityMatchesService;
import com.ruoyi.activity.service.IActivityStandingsService;
import com.ruoyi.activity.service.IActivityTeamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
public class StandingAutoUpdateService {

    private final IActivityMatchesService matchService;
    private final IActivityStandingsService standingService;
    private final IActivityTeamsService teamService;



    /**
     * 比赛结果提交后自动更新积分（仅小组赛）
     */
    @Transactional
    public void autoUpdateStandingsAfterMatchResult(Long matchId) {
        ActivityMatches match = matchService.selectActivityMatchesByMatchId(matchId);
        if (match == null) {
            throw new IllegalArgumentException("比赛不存在");
        }

        // 仅小组赛触发积分计算 1-小组赛，2-淘汰赛，3-排位赛
        if (match.getStage() == 2 || match.getStatus() == 3) {
            return;
        }

        // 获取两队积分记录（不存在则创建）
        ActivityStandings standingA = getOrCreateStanding(match.getTeamAId(), match.getGroupA());
        ActivityStandings standingB = getOrCreateStanding(match.getTeamBId(), match.getGroupA());

        // 计算局分和总分
        int setsA = countWonSets(match, true);
        int setsB = countWonSets(match, false);
        int pointsA = getTotalPoints(match, true);
        int pointsB = getTotalPoints(match, false);

        boolean aWins = setsA > setsB;
        match.setWinnerId(aWins ? match.getTeamAId() : match.getTeamBId());
        matchService.updateActivityMatches(match);

        // 更新A队数据
        updateStanding(standingA, aWins, setsA, setsB, pointsA, pointsB);
        // 更新B队数据
        updateStanding(standingB, !aWins, setsB, setsA, pointsB, pointsA);

        standingService.updateActivityStandings(standingA);
        standingService.updateActivityStandings(standingB);

        // 重新计算本小组排名（A/B两队可能不同组？但秩序册小组赛同组）
        recalculateGroupRank(standingA.getGroupCode());
        if (!standingA.getGroupCode().equals(standingB.getGroupCode())) {
            recalculateGroupRank(standingB.getGroupCode());
        }
    }

    private ActivityStandings getOrCreateStanding(Long teamId, String groupCode) {
        List<ActivityStandings> standings = standingService.selectActivityStandingsList(new ActivityStandings() {{
            setTeamId(teamId);
            setGroupCode(groupCode);
        }});
        
        if (!standings.isEmpty()) {
            return standings.get(0);
        } else {
            ActivityStandings s = new ActivityStandings();
            s.setTeamId(teamId);
            s.setGroupCode(groupCode);
            resetStanding(s);
            standingService.insertActivityStandings(s);
            return s;
        }
    }

    private void resetStanding(ActivityStandings s) {
        s.setMatchesPlayed(0);
        s.setWins(0);
        s.setLosses(0);
        s.setPoints(0);
        s.setSetsWon(0);
        s.setSetsLost(0);
        s.setPointsFor(0);
        s.setPointsAgainst(0);
    }

    private void updateStanding(ActivityStandings s, boolean isWin,
                                int wonSets, int lostSets,
                                int pointsFor, int pointsAgainst) {
        s.setMatchesPlayed(s.getMatchesPlayed() + 1);
        if (isWin) {
            s.setWins(s.getWins() + 1);
            s.setPoints(s.getPoints() + 2);
        } else {
            s.setLosses(s.getLosses() + 1);
            s.setPoints(s.getPoints() + 1);
        }
        s.setSetsWon(s.getSetsWon() + wonSets);
        s.setSetsLost(s.getSetsLost() + lostSets);
        s.setPointsFor(s.getPointsFor() + pointsFor);
        s.setPointsAgainst(s.getPointsAgainst() + pointsAgainst);
    }

    // 计算某队胜局数（支持3局或5局）
    private int countWonSets(ActivityMatches m, boolean isTeamA) {
        int wins = 0;
        if (hasSet(m, 1) && getScore(m, 1, isTeamA) > getScore(m, 1, !isTeamA)) wins++;
        if (hasSet(m, 2) && getScore(m, 2, isTeamA) > getScore(m, 2, !isTeamA)) wins++;
        if (hasSet(m, 3) && getScore(m, 3, isTeamA) > getScore(m, 3, !isTeamA)) wins++;
        if (m.getFormat() == 5) {
            if (hasSet(m, 4) && getScore(m, 4, isTeamA) > getScore(m, 4, !isTeamA)) wins++;
            if (hasSet(m, 5) && getScore(m, 5, isTeamA) > getScore(m, 5, !isTeamA)) wins++;
        }
        return wins;
    }

    private boolean hasSet(ActivityMatches m, int set) {
        switch (set) {
            case 1: return m.getScoreASet1() != null;
            case 2: return m.getScoreASet2() != null;
            case 3: return m.getScoreASet3() != null;
            case 4: return m.getScoreASet4() != null;
            case 5: return m.getScoreASet5() != null;
            default: return false;
        }
    }

    private int getScore(ActivityMatches m, int set, boolean isTeamA) {
        switch (set) {
            case 1: return isTeamA ? m.getScoreASet1() : m.getScoreBSet1();
            case 2: return isTeamA ? m.getScoreASet2() : m.getScoreBSet2();
            case 3: return isTeamA ? m.getScoreASet3() : m.getScoreBSet3();
            case 4: return isTeamA ? m.getScoreASet4() : m.getScoreBSet4();
            case 5: return isTeamA ? m.getScoreASet5() : m.getScoreBSet5();
            default: throw new IllegalArgumentException("Invalid set: " + set);
        }
    }

    private int getTotalPoints(ActivityMatches m, boolean isTeamA) {
        int total = 0;
        for (int i = 1; i <= (m.getFormat() == 5 ? 5 : 3); i++) {
            if (hasSet(m, i)) {
                total += getScore(m, i, isTeamA);
            }
        }
        return total;
    }

    // 重新计算小组排名
    private void recalculateGroupRank(String groupCode) {
        ActivityStandings query = new ActivityStandings();
        query.setGroupCode(groupCode);
        List<ActivityStandings> standings = standingService.selectActivityStandingsList(query);
        if (standings.isEmpty()) return;

        standings.sort((s1, s2) -> {
            // 1. 积分高者列前
            int cmpPoints = Integer.compare(s2.getPoints(), s1.getPoints());
            if (cmpPoints != 0) return cmpPoints;

            // 2. C值高者列前
            BigDecimal c1 = calculateCValue(s1);
            BigDecimal c2 = calculateCValue(s2);
            int cmpC = c2.compareTo(c1);
            if (cmpC != 0) return cmpC;

            // 3. Z值高者列前
            BigDecimal z1 = calculateZValue(s1);
            BigDecimal z2 = calculateZValue(s2);
            int cmpZ = z2.compareTo(z1);
            if (cmpZ != 0) return cmpZ;

            // 4. 抽签（代码中暂不实现，标记为同分）
            return 0;
        });

        for (int i = 0; i < standings.size(); i++) {
            standings.get(i).setRankInGroup(i + 1);
            standingService.updateActivityStandings(standings.get(i));
        }
    }

    private BigDecimal calculateCValue(ActivityStandings s) {
        if (s.getSetsLost() == 0) return BigDecimal.valueOf(999.0);
        return BigDecimal.valueOf(s.getSetsWon())
                .divide(BigDecimal.valueOf(s.getSetsLost()), 3, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateZValue(ActivityStandings s) {
        if (s.getPointsAgainst() == 0) return BigDecimal.valueOf(999.0);
        return BigDecimal.valueOf(s.getPointsFor())
                .divide(BigDecimal.valueOf(s.getPointsAgainst()), 3, RoundingMode.HALF_UP);
    }
}