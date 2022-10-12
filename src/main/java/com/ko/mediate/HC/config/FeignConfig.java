package com.ko.mediate.HC.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.ko.mediate.HC")
public class FeignConfig {

}
