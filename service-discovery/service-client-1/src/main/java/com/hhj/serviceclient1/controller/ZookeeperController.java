package com.hhj.serviceclient1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author virtual
 * @Date 2021/11/28 16:57
 */
@RestController
public class ZookeeperController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/zk")
    public String zk(){
        return "springcloud with zookeeper: " + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
