package com.ruoyi.wx.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 微信端公告与通知接口控制器
 *
 * @author ruoyi
 */
@Api("微信端公告与通知接口")
@RestController
@RequestMapping("/wx/notices")
public class WxNoticesController extends BaseController {
    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 6.1 获取公告列表
     */
    @ApiOperation("获取公告列表")
    @PreAuthorize("@ss.hasPermi('wx:notices:list')")
    @GetMapping
    public AjaxResult listNotices() {
        List<SysNotice> result = noticeService.selectNoticeList(new SysNotice());
        return AjaxResult.success(result);
    }
}