package com.gottaboy.iching.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class IchingMonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IchingMonitorAdminApplication.class, args);
    }

}
