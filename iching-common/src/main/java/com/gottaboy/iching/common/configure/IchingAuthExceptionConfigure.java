package com.gottaboy.iching.common.configure;

import com.gottaboy.iching.common.handler.IchingAccessDeniedHandler;
import com.gottaboy.iching.common.handler.IchingAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 异常翻译配置
 *
 * @author gottaboy
 */
public class IchingAuthExceptionConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public IchingAccessDeniedHandler accessDeniedHandler() {
        return new IchingAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public IchingAuthExceptionEntryPoint authenticationEntryPoint() {
        return new IchingAuthExceptionEntryPoint();
    }
}
