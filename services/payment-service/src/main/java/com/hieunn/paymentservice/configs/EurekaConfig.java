package com.hieunn.paymentservice.configs;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableDiscoveryClient
public class EurekaConfig {
}
