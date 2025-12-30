package com.ruoyi.common.utils;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 创建字段工具类
 * 用于自动填充create_by和create_time字段
 *
 * @author ruoyi
 */
public class CreateFieldUtils {

    /**
     * 填充创建字段
     *
     * @param entity 实体对象
     */
    public static void fillCreateFields(BaseEntity entity) {
        // 获取当前登录用户
        String username = SecurityUtils.getUsername();

        // 填充创建者和创建时间
        entity.setCreateBy(username);
        entity.setCreateTime(new Date());
    }

    /**
     * 填充更新字段
     *
     * @param entity 实体对象
     */
    public static void fillUpdateFields(BaseEntity entity) {
        // 获取当前登录用户
        String username = SecurityUtils.getUsername();

        // 填充更新者和更新时间
        entity.setUpdateBy(username);
        entity.setUpdateTime(new Date());
    }
}