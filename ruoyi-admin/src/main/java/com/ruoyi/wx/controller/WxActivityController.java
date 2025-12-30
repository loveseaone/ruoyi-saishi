package com.ruoyi.wx.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityRegistration;
import com.ruoyi.activity.service.IActivityAthletesService;
import com.ruoyi.activity.service.IActivityRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

/**
 * 微信端活动报名、签到接口控制器
 *
 * @author ruoyi
 */
@Api("微信端活动报名、签到接口")
@RestController
@RequestMapping("/wx/activity")
public class WxActivityController extends BaseController {

    @Autowired
    private IActivityAthletesService activityAthletesService;

    @Autowired
    private IActivityRegistrationService activityRegistrationService;

    /**
     * 1. 活动报名接口
     */
    @ApiOperation("活动报名")
    @PreAuthorize("@ss.hasPermi('wx:activity:register')")
    @ApiImplicitParam(name = "params", value = "报名参数", dataType = "Map", dataTypeClass = Map.class)
    @PostMapping("/registration")
    public AjaxResult registerActivity(@RequestBody Map<String, Object> params) {
        try {
            // 获取当前登录用户信息
            LoginUser loginUser = SecurityUtils.getLoginUser();
            Long loginId = loginUser.getUserId();

            // 获取活动ID
            Long activityId = null;
            if (params.get("activityId") != null) {
                if (params.get("activityId") instanceof String) {
                    activityId = Long.valueOf((String) params.get("activityId"));
                } else if (params.get("activityId") instanceof Number) {
                    activityId = ((Number) params.get("activityId")).longValue();
                }
            }

            if (activityId == null) {
                return AjaxResult.error(4000, "参数缺失");
            }

            // 通过loginId查询运动员信息
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(loginId);
            if (athlete == null) {
                return AjaxResult.error(4010, "loginID 无效或过期");
            }

            String name = athlete.getName();
            String phone = athlete.getPhone();

            // 唯一性校验
            ActivityRegistration query = new ActivityRegistration();
            query.setActivityId(activityId);
            List<ActivityRegistration> existingRegistrations = activityRegistrationService.selectActivityRegistrationList(query);

            boolean alreadyRegistered = existingRegistrations.stream()
                    .anyMatch(reg -> name.equals(reg.getParticipantName()) && phone.equals(reg.getParticipantPhone()));

            if (alreadyRegistered) {
                return AjaxResult.error(4090, "您已报名，请勿重复提交");
            }

            // 写入新记录
            ActivityRegistration registration = new ActivityRegistration();
            registration.setActivityId(activityId);
            registration.setParticipantName(name);
            registration.setParticipantPhone(phone);
            registration.setStatus(1); // 已通过
            registration.setRegistrationTime(new Date());
            registration.setCreateBy(String.valueOf(loginId));
            registration.setUpdateBy(String.valueOf(loginId));

            activityRegistrationService.insertActivityRegistration(registration);

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("regId", registration.getRegId());
            result.put("activityId", registration.getActivityId());
            result.put("participantName", registration.getParticipantName());
            result.put("participantPhone", registration.getParticipantPhone());
            result.put("status", registration.getStatus());
            result.put("registrationTime", registration.getRegistrationTime());

            return AjaxResult.success("报名成功", result);
        } catch (Exception e) {
            return AjaxResult.error("报名失败：" + e.getMessage());
        }
    }

    /**
     * 2. 活动签到接口
     */
    @ApiOperation("活动签到")
    @PreAuthorize("@ss.hasPermi('wx:activity:checkin')")
    @ApiImplicitParam(name = "params", value = "签到参数", dataType = "Map", dataTypeClass = Map.class)
    @PostMapping("/checkin")
    public AjaxResult checkinActivity(@RequestBody(required = false) Map<String, Object> params) {
        try {
            // 获取当前登录用户信息
            LoginUser loginUser = SecurityUtils.getLoginUser();
            Long loginId = loginUser.getUserId();

            // 通过loginId查询运动员信息
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(loginId);
            if (athlete == null) {
                return AjaxResult.error(4010, "loginID 无效或过期");
            }

            String name = athlete.getName();
            String phone = athlete.getPhone();

            // 查找已通过的报名记录
            ActivityRegistration query = new ActivityRegistration();
            List<ActivityRegistration> registrations = activityRegistrationService.selectActivityRegistrationList(query);

            ActivityRegistration registration = registrations.stream()
                    .filter(reg -> name.equals(reg.getParticipantName()) 
                            && phone.equals(reg.getParticipantPhone()) 
                            && Integer.valueOf(1).equals(reg.getStatus()))
                    .findFirst()
                    .orElse(null);

            if (registration == null) {
                return AjaxResult.error(4040, "未找到已报名记录");
            }

            // 检查是否已签到
            if (Integer.valueOf(1).equals(registration.getCheckinStatus())) {
                return AjaxResult.error(4090, "您已签到，请勿重复扫码");
            }

            // 更新签到状态
            registration.setCheckinStatus(1);
            registration.setCheckinTime(new Date());
            registration.setUpdateBy(String.valueOf(loginId));
            activityRegistrationService.updateActivityRegistration(registration);

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("regId", registration.getRegId());
            result.put("participantName", registration.getParticipantName());
            result.put("checkinTime", registration.getCheckinTime());

            return AjaxResult.success("签到成功", result);
        } catch (Exception e) {
            return AjaxResult.error("签到失败：" + e.getMessage());
        }
    }

    /**
     * 3. 查询当前用户报名和签到状态
     */
    @ApiOperation("查询当前用户报名和签到状态")
    @PreAuthorize("@ss.hasPermi('wx:activity:status')")
    @ApiImplicitParam(name = "activityId", value = "活动ID", dataType = "Long", dataTypeClass = Long.class, paramType = "query")
    @GetMapping("/status")
    public AjaxResult getUserActivityStatus(@RequestParam(required = false) Long activityId) {
        try {
            // 获取当前登录用户信息
            LoginUser loginUser = SecurityUtils.getLoginUser();
            Long loginId = loginUser.getUserId();

            // 通过loginId查询运动员信息
            ActivityAthletes athlete = activityAthletesService.selectActivityAthletesByAthleteId(loginId);
            if (athlete == null) {
                return AjaxResult.error(4010, "loginID 无效或过期");
            }

            String name = athlete.getName();
            String phone = athlete.getPhone();

            // 查询报名记录
            ActivityRegistration query = new ActivityRegistration();
            if (activityId != null) {
                query.setActivityId(activityId);
            }
            List<ActivityRegistration> registrations = activityRegistrationService.selectActivityRegistrationList(query);

            // 过滤出当前用户的记录
            List<ActivityRegistration> userRegistrations = registrations.stream()
                    .filter(reg -> name.equals(reg.getParticipantName()) && phone.equals(reg.getParticipantPhone()))
                    .collect(java.util.stream.Collectors.toList());

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("participantName", name);
            result.put("participantPhone", phone);

            if (!userRegistrations.isEmpty()) {
                ActivityRegistration registration = userRegistrations.get(0);
                result.put("regId", registration.getRegId());
                result.put("activityId", registration.getActivityId());
                result.put("registrationStatus", registration.getStatus());
                result.put("checkinStatus", registration.getCheckinStatus());
                result.put("registrationTime", registration.getRegistrationTime());
                result.put("checkinTime", registration.getCheckinTime());
            } else {
                result.put("regId", null);
                result.put("activityId", activityId);
                result.put("registrationStatus", null);
                result.put("checkinStatus", null);
                result.put("registrationTime", null);
                result.put("checkinTime", null);
            }
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("查询状态失败：" + e.getMessage());
        }
    }
}