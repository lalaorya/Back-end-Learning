package com.hhj.serviceclient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceClient2Application.class, args);
    }

}
