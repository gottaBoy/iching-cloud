package com.gottaboy.iching.common.configure;

import com.google.common.net.HttpHeaders;
import com.gottaboy.iching.common.entity.constant.IchingConstant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;

/**
 * OAuth2 Feign配置
 * hystrix:
 *   shareSecurityContext: true
 * @author gottaboy
 */
public class IchingOAuth2FeignConfigure {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            // 请求头中添加 Gateway Token
            String zuulToken = new String(Base64Utils.encode(IchingConstant.GATEWAY_TOKEN_VALUE.getBytes()));
            requestTemplate.header(IchingConstant.GATEWAY_TOKEN_HEADER, zuulToken);
            // 请求头中添加原请求头中的 Token
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, IchingConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
            }
        };
    }
}
