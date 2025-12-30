package com.ruoyi.wx.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 微信端字典数据接口
 * 
 * @author ruoyi
 */
@Api("微信端字典数据接口")
@RestController
@RequestMapping("/wx/dict/data")
public class WxDictDataController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 根据字典类型获取字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据列表（包含dictLabel和dictValue字段）
     */
    @ApiOperation("根据字典类型获取字典数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dictType", value = "字典类型", required = true, dataType = "String", paramType = "query")
    })
    @Anonymous
    @GetMapping("/type")
    public AjaxResult getDictDataByType(String dictType) {
        List<SysDictData> dictDataList = dictTypeService.selectDictDataByType(dictType);
        
        // 处理返回结果为null的情况
        if (dictDataList == null) {
            return AjaxResult.success(Collections.emptyList());
        }
        
        // 过滤掉状态为停用的数据，并按照字典排序升序排列
        List<SysDictData> result = dictDataList.stream()
                .filter(dict -> "0".equals(dict.getStatus())) // 只要状态为正常的字典数据
                .sorted((d1, d2) -> d1.getDictSort().compareTo(d2.getDictSort())) // 按照字典排序升序
                .collect(Collectors.toList());

        return AjaxResult.success(result);
    }


}