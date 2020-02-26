package com.gottaboy.iching.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author gottaboy
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:iching-gateway.properties"})
@ConfigurationProperties(prefix = "iching.gateway")
public class IchingGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;
}
