package com.hieunn.paymentservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration(proxyBeanMethods = false)
@EnableRetry
public class GlobalRetryConfig {
}
