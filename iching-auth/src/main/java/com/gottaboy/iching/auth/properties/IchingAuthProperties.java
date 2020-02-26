package com.gottaboy.iching.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:iching-auth.properties"})
@ConfigurationProperties(prefix = "iching.auth")
public class IchingAuthProperties {

    private IchingClientsProperties[] clients = {};
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    // 免认证路径
    private String anonUrl;
    /**
     * 验证码配置
     */
    private IchingValidateCodeProperties code = new IchingValidateCodeProperties();
}
