package com.gottaboy.iching.auth.config;

import com.gottaboy.iching.auth.service.impl.IchingUserDetailService;
import com.gottaboy.iching.auth.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html
 * http://localhost:8001/oauth/token?username=iching_2&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
 * http://localhost:8001/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
 */
@EnableWebSecurity
public class IchingSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder());
//    }

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////        password 方案一：明文存储，用于测试，不能用于生产
////        String finalPassword = "123456";
////        password 方案二：用 BCrypt 对密码编码
////        String finalPassword = bCryptPasswordEncoder.encode("123456");
//        // password 方案三：支持多种编码，通过密码的前缀区分编码方式
//        String finalPassword = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("iching_1").password(finalPassword).authorities("USER").build());
//        manager.createUser(User.withUsername("iching_2").password(finalPassword).authorities("USER").build());
//        return manager;
//    }

    /**
     * springboot2.0 删除了原来的 plainTextPasswordEncoder
     * https://docs.spring.io/spring-security/site/docs/5.0.4.RELEASE/reference/htmlsingle/#10.3.2 DelegatingPasswordEncoder
     *
     */


    // password 方案一：明文存储，用于测试，不能用于生产
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    // password 方案二：用 BCrypt 对密码编码
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // password 方案三：支持多种编码，通过密码的前缀区分编码方式,推荐
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    //
//    /**
//     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
//     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("iching_1").password("123456").authorities("USER")
//                .and()
//                .withUser("iching_2").password("123456").authorities("USER");
//    }

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .requestMatchers()
            .antMatchers("/oauth/**")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/**").authenticated()
            .and()
            .csrf().disable();
        // @formatter:on
    }

    @Autowired
    private IchingUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
}