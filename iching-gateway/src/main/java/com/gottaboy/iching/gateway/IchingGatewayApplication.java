package com.gottaboy.iching.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
//@EnableIchingServerProtect
@SpringBootApplication
public class IchingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IchingGatewayApplication.class, args);
	}

}
