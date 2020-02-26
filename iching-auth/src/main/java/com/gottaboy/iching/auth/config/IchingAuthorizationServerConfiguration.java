package com.gottaboy.iching.auth.config;

import com.gottaboy.iching.auth.service.impl.IchingUserDetailService;
import com.gottaboy.iching.auth.properties.IchingAuthProperties;
import com.gottaboy.iching.auth.properties.IchingClientsProperties;
import com.gottaboy.iching.auth.service.impl.RedisClientDetailsService;
import com.gottaboy.iching.auth.translator.IchingWebResponseExceptionTranslator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class IchingAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private IchingUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        //配置两个客户端,一个用于password认证一个用于client认证
////        password 方案一：明文存储，用于测试，不能用于生产
////        String finalSecret = "123456";
////        password 方案二：用 BCrypt 对密码编码
////        String finalSecret = new BCryptPasswordEncoder().encode("123456");
//        // password 方案三：支持多种编码，通过密码的前缀区分编码方式
//        String finalSecret = "{bcrypt}"+new BCryptPasswordEncoder().encode("123456");
//        clients.inMemory().withClient("client_1")
//                .resourceIds(DemoConstants.DEMO_RESOURCE_ID)
//                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("select")
//                .authorities("client")
//                .secret(finalSecret)
//                .and().withClient("client_2")
//                .resourceIds(DemoConstants.DEMO_RESOURCE_ID)
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("select")
//                .authorities("client")
//                .secret(finalSecret);
//    }

    @Autowired
    private IchingWebResponseExceptionTranslator exceptionTranslator;

    @Override
    @SuppressWarnings("all")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())
                .exceptionTranslator(exceptionTranslator);
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds());
        tokenServices.setRefreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds());
        return tokenServices;
    }

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        //允许表单认证
//        oauthServer.allowFormAuthenticationForClients();
//    }

//    @Bean
//    protected UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
//        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
//        return manager;
//    }

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;
    @Autowired
    private IchingAuthProperties authProperties;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////        redisClientDetailsService.setPasswordEncoder(passwordEncoder);
////        clients.withClientDetails(redisClientDetailsService);
//        //Basic aWNoaW5nOjEyMzQ1Ng==
//        clients.inMemory()
//                .withClient("iching")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("all");
        IchingClientsProperties[] clientsArray = authProperties.getClients();
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(clientsArray)) {
            for (IchingClientsProperties client : clientsArray) {
                if (StringUtils.isBlank(client.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())) {
                    throw new Exception("secret不能为空");
                }
                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(), ",");
                builder.withClient(client.getClient())
                        .secret(passwordEncoder.encode(client.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(client.getScope());
            }
        }
    }
}
