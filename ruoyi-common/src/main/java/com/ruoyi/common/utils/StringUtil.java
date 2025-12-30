package com.ruoyi.common.utils;

/**
 * 字符串处理工具类
 *
 * @author ruoyi
 */
public class StringUtil {

    /**
     * 去掉字符串开头的```json和结尾的```
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String removeJsonCodeBlock(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String result = str;
        // 去掉开头的```json
        if (result.startsWith("```json")) {
            result = result.substring(7);
        } else if (result.startsWith("```")) {
            result = result.substring(3);
        }

        // 去掉结尾的```
        if (result.endsWith("```")) {
            result = result.substring(0, result.length() - 3);
        }

        // 去掉首尾空格
        result = result.trim();

        return result;
    }


}