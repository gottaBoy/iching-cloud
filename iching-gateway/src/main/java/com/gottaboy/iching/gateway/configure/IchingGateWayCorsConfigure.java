package com.gottaboy.iching.gateway.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域处理
 */
@Configuration
public class IchingGateWayCorsConfigure {

    /**
     * setAllowCredentials(true)表示允许cookie跨域；
     * addAllowedHeader(CorsConfiguration.ALL)表示请求头部允许携带任何内容；
     * addAllowedOrigin(CorsConfiguration.ALL)表示允许任何来源；
     * addAllowedMethod(CorsConfiguration.ALL)表示允许任何HTTP方法
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}