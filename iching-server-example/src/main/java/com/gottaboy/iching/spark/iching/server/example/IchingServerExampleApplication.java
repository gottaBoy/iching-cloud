package com.gottaboy.iching.spark.iching.server.example;

import com.gottaboy.iching.common.annotation.EnableIchingAuthExceptionHandler;
import com.gottaboy.iching.common.annotation.IchingCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableIchingAuthExceptionHandler
@IchingCloudApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IchingServerExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(IchingServerExampleApplication.class, args);
    }

}
