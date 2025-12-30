package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.service.IActivityInfoService;
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
 * 活动信息Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动信息管理")
@RestController
@RequestMapping("/activity/info")
public class ActivityInfoController extends BaseController
{
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 查询活动信息列表
     */
    @ApiOperation("查询活动信息列表")
    @PreAuthorize("@ss.hasPermi('activity:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityInfo activityInfo)
    {
        startPage();
        List<ActivityInfo> list = activityInfoService.selectActivityInfoList(activityInfo);
        return getDataTable(list);
    }

    /**
     * 导出活动信息列表
     */
    @ApiOperation("导出活动信息列表")
    @PreAuthorize("@ss.hasPermi('activity:info:export')")
    @Log(title = "活动信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityInfo activityInfo)
    {
        List<ActivityInfo> list = activityInfoService.selectActivityInfoList(activityInfo);
        ExcelUtil<ActivityInfo> util = new ExcelUtil<ActivityInfo>(ActivityInfo.class);
        util.exportExcel(response, list, "活动信息数据");
    }

    /**
     * 获取活动信息详细信息
     */
    @ApiOperation("获取活动信息详细信息")
    @ApiImplicitParam(name = "activityId", value = "活动ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:info:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return AjaxResult.success(activityInfoService.selectActivityInfoByActivityId(activityId));
    }

    /**
     * 新增活动信息
     */
    @ApiOperation("新增活动信息")
    @PreAuthorize("@ss.hasPermi('activity:info:add')")
    @Log(title = "活动信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityInfo activityInfo)
    {
        // 统一设置activityId为1
        activityInfo.setActivityId(1L);
        
        // 业务逻辑处理：检查活动时间是否合理
        if (activityInfo.getStartTime().after(activityInfo.getEndTime())) {
            return AjaxResult.error("活动开始时间不能晚于结束时间");
        }
        
        // 检查报名时间是否合理
        if (activityInfo.getRegistrationStart() != null && activityInfo.getRegistrationEnd() != null) {
            if (activityInfo.getRegistrationStart().after(activityInfo.getRegistrationEnd())) {
                return AjaxResult.error("报名开始时间不能晚于结束时间");
            }
            
            if (activityInfo.getRegistrationStart().after(activityInfo.getStartTime())) {
                return AjaxResult.error("报名开始时间不能晚于活动开始时间");
            }
        }
        
        return toAjax(activityInfoService.insertActivityInfo(activityInfo));
    }

    /**
     * 修改活动信息
     */
    @ApiOperation("修改活动信息")
    @PreAuthorize("@ss.hasPermi('activity:info:edit')")
    @Log(title = "活动信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityInfo activityInfo)
    {
        // 业务逻辑处理：检查活动时间是否合理
        if (activityInfo.getStartTime().after(activityInfo.getEndTime())) {
            return AjaxResult.error("活动开始时间不能晚于结束时间");
        }
        
        // 检查报名时间是否合理
        if (activityInfo.getRegistrationStart() != null && activityInfo.getRegistrationEnd() != null) {
            if (activityInfo.getRegistrationStart().after(activityInfo.getRegistrationEnd())) {
                return AjaxResult.error("报名开始时间不能晚于结束时间");
            }
            
            if (activityInfo.getRegistrationStart().after(activityInfo.getStartTime())) {
                return AjaxResult.error("报名开始时间不能晚于活动开始时间");
            }
        }
        
        return toAjax(activityInfoService.updateActivityInfo(activityInfo));
    }

    /**
     * 删除活动信息
     */
    @ApiOperation("删除活动信息")
    @ApiImplicitParam(name = "activityIds", value = "活动ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:info:remove')")
    @Log(title = "活动信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(activityInfoService.deleteActivityInfoByActivityIds(activityIds));
    }
}