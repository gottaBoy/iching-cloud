package com.gottaboy.iching.spark.iching.server.system.configure;

import com.gottaboy.iching.common.entity.constant.EndpointConstant;
import com.gottaboy.iching.spark.iching.server.system.properties.IchingServerSystemProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IchingServerSystemResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Autowired
    private IchingServerSystemProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.headers().frameOptions().disable()
        .and().csrf().disable()
                .requestMatchers().antMatchers(EndpointConstant.ALL)
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers(EndpointConstant.ALL).authenticated()
                .and().httpBasic();
    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.authenticationEntryPoint(exceptionEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler);
//    }
}