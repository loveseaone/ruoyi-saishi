package com.ruoyi.web.controller.activity;

import com.ruoyi.activity.domain.ActivityNotices;
import com.ruoyi.activity.service.IActivityNoticesService;
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
 * 活动公告Controller
 * 
 * @author ruoyi
 * @date 2025-10-22
 */
@Api("活动公告管理")
@RestController
@RequestMapping("/activity/notices")
public class ActivityNoticesController extends BaseController
{
    @Autowired
    private IActivityNoticesService activityNoticesService;

    /**
     * 查询活动公告列表
     */
    @ApiOperation("查询活动公告列表")
    @PreAuthorize("@ss.hasPermi('activity:notices:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityNotices activityNotices)
    {
        startPage();
        List<ActivityNotices> list = activityNoticesService.selectActivityNoticesList(activityNotices);
        return getDataTable(list);
    }

    /**
     * 导出活动公告列表
     */
    @ApiOperation("导出活动公告列表")
    @PreAuthorize("@ss.hasPermi('activity:notices:export')")
    @Log(title = "活动公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityNotices activityNotices)
    {
        List<ActivityNotices> list = activityNoticesService.selectActivityNoticesList(activityNotices);
        ExcelUtil<ActivityNotices> util = new ExcelUtil<ActivityNotices>(ActivityNotices.class);
        util.exportExcel(response, list, "活动公告数据");
    }

    /**
     * 获取活动公告详细信息
     */
    @ApiOperation("获取活动公告详细信息")
    @ApiImplicitParam(name = "noticeId", value = "公告ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:notices:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(activityNoticesService.selectActivityNoticesByNoticeId(noticeId));
    }

    /**
     * 新增活动公告
     */
    @ApiOperation("新增活动公告")
    @PreAuthorize("@ss.hasPermi('activity:notices:add')")
    @Log(title = "活动公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ActivityNotices activityNotices)
    {
        // 统一设置activityId为1
        activityNotices.setActivityId(1L);
        
        // 业务逻辑处理：检查是否置顶字段是否有效
        if (activityNotices.getIsPinned() != null && 
            activityNotices.getIsPinned() != 0 && activityNotices.getIsPinned() != 1) {
            return AjaxResult.error("是否置顶字段必须是0或1");
        }
        
        return toAjax(activityNoticesService.insertActivityNotices(activityNotices));
    }

    /**
     * 修改活动公告
     */
    @ApiOperation("修改活动公告")
    @PreAuthorize("@ss.hasPermi('activity:notices:edit')")
    @Log(title = "活动公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ActivityNotices activityNotices)
    {
        // 业务逻辑处理：检查是否置顶字段是否有效
        if (activityNotices.getIsPinned() != null && 
            activityNotices.getIsPinned() != 0 && activityNotices.getIsPinned() != 1) {
            return AjaxResult.error("是否置顶字段必须是0或1");
        }
        
        return toAjax(activityNoticesService.updateActivityNotices(activityNotices));
    }

    /**
     * 删除活动公告
     */
    @ApiOperation("删除活动公告")
    @ApiImplicitParam(name = "noticeIds", value = "公告ID数组", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('activity:notices:remove')")
    @Log(title = "活动公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds)
    {
        return toAjax(activityNoticesService.deleteActivityNoticesByNoticeIds(noticeIds));
    }
}