package com.gottaboy.iching.auth;

import com.gottaboy.iching.common.annotation.EnableIchingLettuceRedis;
import com.gottaboy.iching.common.annotation.EnableIchingServerProtect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableIchingLettuceRedis
@SpringBootApplication
@EnableIchingServerProtect
//@EnableIchingAuthExceptionHandler
@MapperScan("com.gottaboy.iching.auth.mapper")
public class IchingAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(IchingAuthApplication.class, args);
    }

}
