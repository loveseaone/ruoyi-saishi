package com.ruoyi.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityAthletesMapper;
import com.ruoyi.activity.mapper.ActivityTeamsMapper;
import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.vo.ActivityAthletesVO;
import com.ruoyi.activity.service.IActivityAthletesService;

/**
 * 活动运动员及工作人员Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Service
public class ActivityAthletesServiceImpl implements IActivityAthletesService 
{
    @Autowired
    private ActivityAthletesMapper activityAthletesMapper;

    @Autowired
    private ActivityTeamsMapper activityTeamsMapper;

    /**
     * 查询活动运动员及工作人员
     * 
     * @param athleteId 活动运动员及工作人员主键
     * @return 活动运动员及工作人员
     */
    @Override
    public ActivityAthletes selectActivityAthletesByAthleteId(Long athleteId)
    {
        return activityAthletesMapper.selectActivityAthletesByAthleteId(athleteId);
    }

    /**
     * 查询活动运动员及工作人员列表
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 活动运动员及工作人员
     */
    @Override
    public List<ActivityAthletes> selectActivityAthletesList(ActivityAthletes activityAthletes)
    {
        return activityAthletesMapper.selectActivityAthletesList(activityAthletes);
    }

    /**
     * 查询活动运动员及工作人员列表（包含队伍名称）
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 活动运动员及工作人员VO集合
     */
    @Override
    public List<ActivityAthletesVO> selectActivityAthletesVOList(ActivityAthletes activityAthletes)
    {
        // 先查询所有符合条件的运动员
        List<ActivityAthletes> athletesList = activityAthletesMapper.selectActivityAthletesList(activityAthletes);
        
        // 查询所有的队伍信息，构建teamId到teamName的映射
        List<ActivityTeams> teamsList = activityTeamsMapper.selectActivityTeamsList(new ActivityTeams());
        Map<Long, String> teamMap = teamsList.stream()
                .collect(Collectors.toMap(ActivityTeams::getTeamId, ActivityTeams::getTeamName));
        
        // 将运动员列表转换为VO列表，并设置队伍名称
        return athletesList.stream().map(athlete -> {
            ActivityAthletesVO vo = new ActivityAthletesVO();
            // 复制运动员基本信息
            vo.setAthleteId(athlete.getAthleteId());
            vo.setTeamId(athlete.getTeamId());
            vo.setActivityId(athlete.getActivityId());
            vo.setOpenid(athlete.getOpenid());
            vo.setStatus(athlete.getStatus());
            vo.setSort(athlete.getSort());
            vo.setName(athlete.getName());
            vo.setGender(athlete.getGender());
            vo.setPhone(athlete.getPhone());
            vo.setIdCard(athlete.getIdCard());
            vo.setRole(athlete.getRole());
            vo.setIsOnField(athlete.getIsOnField());
            vo.setCreateBy(athlete.getCreateBy());
            vo.setCreateTime(athlete.getCreateTime());
            vo.setUpdateBy(athlete.getUpdateBy());
            vo.setUpdateTime(athlete.getUpdateTime());
            vo.setRemark(athlete.getRemark());
            
            // 设置队伍名称
            vo.setTeamName(teamMap.get(athlete.getTeamId()));
            
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 新增活动运动员及工作人员
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 结果
     */
    @Override
    public int insertActivityAthletes(ActivityAthletes activityAthletes)
    {
        activityAthletes.setCreateTime(DateUtils.getNowDate());
        return activityAthletesMapper.insertActivityAthletes(activityAthletes);
    }

    /**
     * 修改活动运动员及工作人员
     * 
     * @param activityAthletes 活动运动员及工作人员
     * @return 结果
     */
    @Override
    public int updateActivityAthletes(ActivityAthletes activityAthletes)
    {
        activityAthletes.setUpdateTime(DateUtils.getNowDate());
        return activityAthletesMapper.updateActivityAthletes(activityAthletes);
    }

    /**
     * 批量删除活动运动员及工作人员
     * 
     * @param athleteIds 需要删除的活动运动员及工作人员主键
     * @return 结果
     */
    @Override
    public int deleteActivityAthletesByAthleteIds(Long[] athleteIds)
    {
        return activityAthletesMapper.deleteActivityAthletesByAthleteIds(athleteIds);
    }

    /**
     * 删除活动运动员及工作人员信息
     * 
     * @param athleteId 活动运动员及工作人员主键
     * @return 结果
     */
    @Override
    public int deleteActivityAthletesByAthleteId(Long athleteId)
    {
        return activityAthletesMapper.deleteActivityAthletesByAthleteId(athleteId);
    }
}