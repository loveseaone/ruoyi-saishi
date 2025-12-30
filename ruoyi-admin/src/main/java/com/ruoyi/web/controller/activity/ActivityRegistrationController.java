package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityRegistration;
import com.ruoyi.activity.service.IActivityRegistrationService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 活动报名Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动报名管理")
@RestController
@RequestMapping("/activity/registration")
public class ActivityRegistrationController extends BaseController
{
    @Autowired
    private IActivityRegistrationService activityRegistrationService;

    /**
     * 查询活动报名列表
     */
    @ApiOperation("查询活动报名列表")
    @PreAuthorize("@ss.hasPermi('activity:registration:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityRegistration activityRegistration)
    {
        startPage();
        List<ActivityRegistration> list = activityRegistrationService.selectActivityRegistrationList(activityRegistration);
        return getDataTable(list);
    }

    /**
     * 导出活动报名列表
     */
    @ApiOperation("导出活动报名列表")
    @PreAuthorize("@ss.hasPermi('activity:registration:export')")
    @Log(title = "活动报名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityRegistration activityRegistration)
    {
        List<ActivityRegistration> list = activityRegistrationService.selectActivityRegistrationList(activityRegistration);
        ExcelUtil<ActivityRegistration> util = new ExcelUtil<ActivityRegistration>(ActivityRegistration.class);
        util.exportExcel(response, list, "活动报名数据");
    }

    /**
     * 获取活动报名详细信息
     */
    @ApiOperation("获取活动报名详细信息")
    @ApiImplicitParam(name = "regId", value = "报名ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:registration:query')")
    @GetMapping(value = "/{regId}")
    public AjaxResult getInfo(@PathVariable("regId") Long regId)
    {
        return AjaxResult.success(activityRegistrationService.selectActivityRegistrationByRegId(regId));
    }

    /**
     * 新增活动报名
     */
    @ApiOperation("新增活动报名")
    @PreAuthorize("@ss.hasPermi('activity:registration:add')")
    @Log(title = "活动报名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityRegistration activityRegistration)
    {
        // 统一设置activityId为1
        activityRegistration.setActivityId(1L);
        
        // 业务逻辑处理：检查手机号格式
        if (activityRegistration.getParticipantPhone() != null && 
            !activityRegistration.getParticipantPhone().matches("^1[3-9]\\d{9}$")) {
            return AjaxResult.error("手机号格式不正确");
        }
        
        // 检查邮箱格式（如果提供了邮箱）
        if (activityRegistration.getParticipantEmail() != null && 
            !activityRegistration.getParticipantEmail().isEmpty() &&
            !activityRegistration.getParticipantEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return AjaxResult.error("邮箱格式不正确");
        }
        
        return toAjax(activityRegistrationService.insertActivityRegistration(activityRegistration));
    }

    /**
     * 修改活动报名
     */
    @ApiOperation("修改活动报名")
    @PreAuthorize("@ss.hasPermi('activity:registration:edit')")
    @Log(title = "活动报名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityRegistration activityRegistration)
    {
        // 业务逻辑处理：检查手机号格式
        if (activityRegistration.getParticipantPhone() != null && 
            !activityRegistration.getParticipantPhone().matches("^1[3-9]\\d{9}$")) {
            return AjaxResult.error("手机号格式不正确");
        }
        
        // 检查邮箱格式（如果提供了邮箱）
        if (activityRegistration.getParticipantEmail() != null && 
            !activityRegistration.getParticipantEmail().isEmpty() &&
            !activityRegistration.getParticipantEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return AjaxResult.error("邮箱格式不正确");
        }
        
        return toAjax(activityRegistrationService.updateActivityRegistration(activityRegistration));
    }

    /**
     * 删除活动报名
     */
    @ApiOperation("删除活动报名")
    @ApiImplicitParam(name = "regIds", value = "报名ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:registration:remove')")
    @Log(title = "活动报名", businessType = BusinessType.DELETE)
	@DeleteMapping("/{regIds}")
    public AjaxResult remove(@PathVariable Long[] regIds)
    {
        return toAjax(activityRegistrationService.deleteActivityRegistrationByRegIds(regIds));
    }
}