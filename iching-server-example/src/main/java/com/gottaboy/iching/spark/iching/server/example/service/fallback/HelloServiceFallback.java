package com.gottaboy.iching.spark.iching.server.example.service.fallback;

import com.gottaboy.iching.spark.iching.server.example.service.IHelloService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * feign:
 *   hystrix:
 *     enabled: true
 */
@Slf4j
@Component
public class HelloServiceFallback implements FallbackFactory<IHelloService> {
    @Override
    public IHelloService create(Throwable throwable) {
        return new IHelloService() {
            @Override
            public String hello(String name) {
                log.error("调用iching-server-system服务出错", throwable);
                return "调用出错";
            }
        };
    }
}