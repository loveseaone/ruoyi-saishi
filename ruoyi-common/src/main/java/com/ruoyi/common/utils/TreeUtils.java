package com.ruoyi.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构工具类
 *
 * @author ruoyi
 */
public class TreeUtils {

    /**
     * 构建树形结构
     *
     * @param list          数据列表
     * @param rootParentId  根节点父ID
     * @param idField       ID字段名
     * @param parentIdField 父ID字段名
     * @return 树形结构列表
     */
    public static <T> List<T> buildTree(List<T> list, Object rootParentId, String idField, String parentIdField) {
        List<T> tree = new ArrayList<>();
        try {
            for (T item : list) {
                Object parentId = org.apache.commons.beanutils.BeanUtils.getProperty(item, parentIdField);
                if ((parentId == null && rootParentId == null) ||
                        (parentId != null && parentId.equals(rootParentId))) {
                    tree.add(item);
                    buildChildTree(list, item, idField, parentIdField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tree;
    }

    /**
     * 递归构建子树
     *
     * @param list          数据列表
     * @param parent        父节点
     * @param idField       ID字段名
     * @param parentIdField 父ID字段名
     */
    private static <T> void buildChildTree(List<T> list, T parent, String idField, String parentIdField) {
        try {
            Object parentId = org.apache.commons.beanutils.BeanUtils.getProperty(parent, idField);
            List<T> children = new ArrayList<>();

            for (T item : list) {
                Object itemParentId = org.apache.commons.beanutils.BeanUtils.getProperty(item, parentIdField);
                if (itemParentId != null && itemParentId.equals(parentId)) {
                    children.add(item);
                    buildChildTree(list, item, idField, parentIdField);
                }
            }

            if (!children.isEmpty()) {
                org.apache.commons.beanutils.BeanUtils.setProperty(parent, "children", children);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}