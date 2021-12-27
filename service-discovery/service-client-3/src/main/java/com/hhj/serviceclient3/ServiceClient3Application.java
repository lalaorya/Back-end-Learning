package com.hhj.serviceclient3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient    //微服务注册通用注解
public class ServiceClient3Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceClient3Application.class, args);
    }
}
