package com.gottaboy.iching.spark.iching.server.example.service;

import com.gottaboy.iching.common.entity.constant.IchingServerConstant;
import com.gottaboy.iching.spark.iching.server.example.service.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = IchingServerConstant.ICHING_SERVER_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {

    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}