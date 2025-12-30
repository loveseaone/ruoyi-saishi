package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityAthletesService;
import com.ruoyi.activity.service.IActivityTeamsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理接口控制器
 *
 * @author ruoyi
 */
@Api("后台用户管理接口")
@RestController
@RequestMapping("/activity/athletes")
public class ActivityUserController extends BaseController {

    @Autowired
    private IActivityAthletesService activityAthletesService;

    @Autowired
    private IActivityTeamsService activityTeamsService;

    /**
     * 2.1.1 待审核列表
     */
    @ApiOperation("待审核列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "matchId", value = "比赛ID", dataType = "Long", paramType = "query"),
        @ApiImplicitParam(name = "status", value = "状态", defaultValue = "1", dataType = "Integer", paramType = "query")
    })
    @PreAuthorize("@ss.hasPermi('activity:athletes:list')")
    @GetMapping("/lineups/list")
    public TableDataInfo getLineupsList(
            @RequestParam(required = false) Long matchId,
            @RequestParam(defaultValue = "1") Integer status) {
        try {
            startPage();
            
            // 构造查询条件
            ActivityAthletes query = new ActivityAthletes();
            
            // 查询所有运动员信息
            List<ActivityAthletes> athletesList = activityAthletesService.selectActivityAthletesList(query);

            // 过滤出指定状态的记录
            if (status != null) {
                athletesList = athletesList.stream()
                        .filter(athlete -> status.equals(athlete.getStatus()))
                        .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
            }

            // 转换为返回数据格式
            List<Map<String, Object>> result = new ArrayList<>();
            for (ActivityAthletes athlete : athletesList) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", athlete.getAthleteId());
                item.put("matchCode", matchId); // 这里需要根据实际业务逻辑关联比赛信息

                // 获取队伍信息
                ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(athlete.getTeamId());
                if (team != null) {
                    item.put("teamName", team.getTeamName());
                }

                item.put("submittedBy", athlete.getCreateBy());
                item.put("submittedAt", athlete.getCreateTime());
                item.put("status", athlete.getStatus());

                // 构造队员信息
                List<Map<String, Object>> players = new ArrayList<>();
                Map<String, Object> player = new HashMap<>();
                player.put("name", athlete.getName());
                player.put("gender", athlete.getGender() == 0 ? "男" : "女");
                // jersey和isCaptain字段需要根据实际业务添加，这里暂时用默认值
                player.put("jersey", "");
                player.put("isCaptain", false);
                players.add(player);

                item.put("players", players);
                result.add(item);
            }

            return getDataTable(result);
        } catch (Exception e) {
            logger.error("获取待审核列表失败", e);
            return getDataTable(new ArrayList<>());
        }
    }

    /**
     * 2.1.2 确认名单
     */
    @ApiOperation("确认名单")
    @ApiImplicitParam(name = "athleteId", value = "运动员ID", required = true, dataType = "Long", paramType = "query")
    @PreAuthorize("@ss.hasPermi('activity:athletes:edit')")
    @Log(title = "上场名单审核", businessType = BusinessType.UPDATE)
    @PostMapping("/lineups/confirm")
    public AjaxResult confirmLineup(@RequestParam Long athleteId) {
        try {
            // 查询运动员信息
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(athleteId);
            if (athlete == null) {
                return AjaxResult.error("运动员信息不存在");
            }

            // 更新状态为已确认（2）
            athlete.setStatus(2);
            activityAthletesService.updateActivityAthletes(athlete);

            return AjaxResult.success("已确认");
        } catch (Exception e) {
            logger.error("确认名单失败", e);
            return AjaxResult.error("确认失败：" + e.getMessage());
        }
    }

    /**
     * 2.1.3 驳回名单
     */
    @ApiOperation("驳回名单")
    @PreAuthorize("@ss.hasPermi('activity:athletes:edit')")
    @Log(title = "上场名单审核", businessType = BusinessType.UPDATE)
    @PostMapping("/lineups/reject")
    public AjaxResult rejectLineup(@RequestBody Map<String, Object> params) {
        try {
            Long athleteId = Long.valueOf(params.get("athleteId").toString());
            String remark = (String) params.get("remark");

            // 查询运动员信息
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(athleteId);
            if (athlete == null) {
                return AjaxResult.error("运动员信息不存在");
            }

            // 更新状态为已驳回（3）
            athlete.setStatus(3);
            athlete.setRemark(remark);
            activityAthletesService.updateActivityAthletes(athlete);

            return AjaxResult.success("已驳回");
        } catch (Exception e) {
            logger.error("驳回名单失败", e);
            return AjaxResult.error("驳回失败：" + e.getMessage());
        }
    }

    /**
     * 2.2.1 绑定用户列表
     */
    @ApiOperation("绑定用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "teamId", value = "队伍ID", dataType = "Long", paramType = "query"),
        @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "query")
    })
    @PreAuthorize("@ss.hasPermi('activity:athletes:list')")
    @GetMapping("/wx-users/list")
    public TableDataInfo getWxUsersList(
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone) {
        try {
            startPage();
            
            // 构造查询条件
            ActivityAthletes query = new ActivityAthletes();
            if (teamId != null) {
                query.setTeamId(teamId);
            }

            List<ActivityAthletes> athletesList = activityAthletesService.selectActivityAthletesList(query);

            // 过滤出已绑定微信的用户（openid不为空）
            athletesList = athletesList.stream()
                    .filter(athlete -> athlete.getOpenid() != null && !athlete.getOpenid().isEmpty())
                    .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));

            // 根据姓名筛选
            if (name != null && !name.isEmpty()) {
                athletesList = athletesList.stream()
                        .filter(athlete -> athlete.getName().contains(name))
                        .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
            }

            // 根据手机号筛选
            if (phone != null && !phone.isEmpty()) {
                athletesList = athletesList.stream()
                        .filter(athlete -> athlete.getPhone() != null && athlete.getPhone().contains(phone))
                        .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
            }

            // 转换为返回数据格式
            List<Map<String, Object>> result = new ArrayList<>();
            for (ActivityAthletes athlete : athletesList) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", athlete.getAthleteId());
                item.put("name", athlete.getName());

                // 手机号脱敏处理
                String phoneNumber = athlete.getPhone();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    phoneNumber = phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                }
                item.put("phone", phoneNumber);

                // 获取队伍信息
                ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(athlete.getTeamId());
                if (team != null) {
                    item.put("teamName", team.getTeamName());
                }

                // openid脱敏处理
                String openid = athlete.getOpenid();
                if (openid != null && !openid.isEmpty()) {
                    if (openid.length() > 6) {
                        openid = openid.substring(0, 3) + "***" + openid.substring(openid.length() - 3);
                    } else {
                        openid = "***";
                    }
                }
                item.put("openid", openid);

                item.put("bindTime", athlete.getCreateTime());
                item.put("lastLogin", athlete.getUpdateTime());

                result.add(item);
            }

            return getDataTable(result);
        } catch (Exception e) {
            logger.error("获取绑定用户列表失败", e);
            return getDataTable(new ArrayList<>());
        }
    }

    /**
     * 2.2.2 解绑用户
     */
    @ApiOperation("解绑用户")
    @ApiImplicitParam(name = "athleteId", value = "运动员ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:athletes:edit')")
    @Log(title = "用户绑定管理", businessType = BusinessType.UPDATE)
    @PostMapping("/wx-users/{athleteId}")
    public AjaxResult unbindUser(@PathVariable Long athleteId) {
        try {
            // 查询运动员信息
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(athleteId);
            if (athlete == null) {
                return AjaxResult.error("运动员信息不存在");
            }

            // 清空openid字段
            athlete.setOpenid(null);
            activityAthletesService.updateActivityAthletes(athlete);

            return AjaxResult.success("解绑成功");
        } catch (Exception e) {
            logger.error("解绑用户失败", e);
            return AjaxResult.error("解绑失败：" + e.getMessage());
        }
    }
}