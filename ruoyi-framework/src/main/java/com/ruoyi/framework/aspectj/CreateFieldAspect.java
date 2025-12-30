package com.ruoyi.framework.aspectj;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.CreateFieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 创建字段填充切面
 *
 * @author ruoyi
 */
@Aspect
@Component
public class CreateFieldAspect {

    /**
     * 在新增操作前填充创建字段
     *
     * @param joinPoint 切点
     */
    @Before("execution(* com.ruoyi.web.controller.crm.*.add(..)) || execution(* com.ruoyi.wx.controller.*.add(..))")
    public void beforeAdd(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BaseEntity) {
                CreateFieldUtils.fillCreateFields((BaseEntity) arg);
                break;
            }
        }
    }

    /**
     * 在更新操作前填充更新字段
     *
     * @param joinPoint 切点
     */
    @Before("execution(* com.ruoyi.web.controller.crm.*.edit(..)) || execution(* com.ruoyi.wx.controller.*.edit(..))")
    public void beforeEdit(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BaseEntity) {
                CreateFieldUtils.fillUpdateFields((BaseEntity) arg);
                break;
            }
        }
    }
}