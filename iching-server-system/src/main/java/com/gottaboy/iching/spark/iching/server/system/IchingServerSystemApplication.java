package com.gottaboy.iching.spark.iching.server.system;

import com.gottaboy.iching.common.annotation.IchingCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@IchingCloudApplication
@EnableTransactionManagement
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableIchingAuthExceptionHandler
//@EnableIchingServerProtect
@MapperScan("com.gottaboy.iching.server.system.mapper")
public class IchingServerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(IchingServerSystemApplication.class, args);
	}

}
