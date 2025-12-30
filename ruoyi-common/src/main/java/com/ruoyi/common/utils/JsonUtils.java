package com.ruoyi.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * 提供JSON格式化、解析等相关功能
 *
 * @author ruoyi
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将字符串格式化为标准的JSON格式
     *
     * @param jsonString 原始JSON字符串
     * @return 格式化后的标准JSON字符串
     * @throws JsonProcessingException 当JSON处理异常时抛出
     */
    public static String formatJson(String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }

    /**
     * 将字符串格式化为压缩的JSON格式（无换行和空格）
     *
     * @param jsonString 原始JSON字符串
     * @return 压缩后的JSON字符串
     * @throws JsonProcessingException 当JSON处理异常时抛出
     */
    public static String compressJson(String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return objectMapper.writeValueAsString(jsonNode);
    }

    /**
     * 验证字符串是否为有效的JSON格式
     *
     * @param jsonString 待验证的JSON字符串
     * @return 是否为有效的JSON格式
     */
    public static boolean isValidJson(String jsonString) {
        try {
            objectMapper.readTree(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 待转换的对象
     * @return JSON字符串
     * @throws JsonProcessingException 当JSON处理异常时抛出
     */
    public static String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param jsonString JSON字符串
     * @param clazz      目标类型
     * @param <T>        泛型类型
     * @return 转换后的对象
     * @throws JsonProcessingException 当JSON处理异常时抛出
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, clazz);
    }
}