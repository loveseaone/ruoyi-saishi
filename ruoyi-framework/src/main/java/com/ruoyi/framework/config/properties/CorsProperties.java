package com.ruoyi.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 跨域配置属性
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    /**
     * 是否开启跨域支持
     */
    private Boolean enabled;

    /**
     * 允许的域名列表
     */
    private List<String> allowedOrigins;

    /**
     * 允许的请求头
     */
    private List<String> allowedHeaders;

    /**
     * 允许的请求方法
     */
    private List<String> allowedMethods;

    /**
     * 是否允许携带认证信息
     */
    private Boolean allowCredentials;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }
}