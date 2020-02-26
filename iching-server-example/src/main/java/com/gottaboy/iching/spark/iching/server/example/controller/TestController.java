package com.gottaboy.iching.spark.iching.server.example.controller;

import com.gottaboy.iching.spark.iching.server.example.service.IHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private IHelloService helloService;

    @GetMapping("hello")
    public String hello(String name){
        log.info("Feign调用 iching-server-system 的 /hello 服务");
        return this.helloService.hello(name);
    }
}
