package com.gottaboy.iching.auth.config;

import com.gottaboy.iching.auth.properties.IchingAuthProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class IchingResourceServerConfigure extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private IchingAccessDeniedHandler accessDeniedHandler;
//    @Autowired
//    private IchingAuthExceptionEntryPoint exceptionEntryPoint;
    @Autowired
    private IchingAuthProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.csrf().disable()
                .authorizeRequests().antMatchers("/actuator/**").permitAll()
                .and()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();
    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        // resources.resourceId(DemoConstants.DEMO_RESOURCE_ID).stateless(true);
//        resources.authenticationEntryPoint(exceptionEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//            .and()
//            .requestMatchers().anyRequest()
//            .and()
//            .anonymous()
//            .and()
//            .authorizeRequests()
////                    .antMatchers("/index/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
//            .antMatchers("/index/**").authenticated();//配置order访问控制，必须认证过后才可以访问
//    // @formatter:on
//    }
}
