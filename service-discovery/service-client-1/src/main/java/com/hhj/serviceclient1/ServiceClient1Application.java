package com.hhj.serviceclient1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class
ServiceClient1Application {
    RestTemplate

    public static void main(String[] args) {
        SpringApplication.run(ServiceClient1Application.class, args);
    }

}
