package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 *
 * @author ruoyi
 */
@Configuration
public class CorsConfig {

    @Autowired
    private CorsProperties corsProperties;

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 如果未启用跨域，则使用默认配置
        if (corsProperties.getEnabled() != null && !corsProperties.getEnabled()) {
            config.addAllowedOriginPattern("*");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.setAllowCredentials(true);
        } else {
            // 设置访问源地址
            if (!CollectionUtils.isEmpty(corsProperties.getAllowedOrigins())) {
                for (String origin : corsProperties.getAllowedOrigins()) {
                    config.addAllowedOriginPattern(origin);
                }
            } else {
                config.addAllowedOriginPattern("*");
            }

            // 设置访问源请求头
            if (!CollectionUtils.isEmpty(corsProperties.getAllowedHeaders())) {
                for (String header : corsProperties.getAllowedHeaders()) {
                    config.addAllowedHeader(header);
                }
            } else {
                config.addAllowedHeader("*");
            }

            // 设置访问源请求方法
            if (!CollectionUtils.isEmpty(corsProperties.getAllowedMethods())) {
                for (String method : corsProperties.getAllowedMethods()) {
                    config.addAllowedMethod(method);
                }
            } else {
                config.addAllowedMethod("*");
            }

            // 设置是否允许携带认证信息
            if (corsProperties.getAllowCredentials() != null) {
                config.setAllowCredentials(corsProperties.getAllowCredentials());
            } else {
                config.setAllowCredentials(true);
            }
        }

        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}