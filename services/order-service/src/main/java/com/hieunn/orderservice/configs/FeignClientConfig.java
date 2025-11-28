package com.hieunn.orderservice.configs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(basePackages = "com.hieunn.orderservice.clients")
public class FeignClientConfig {
}
