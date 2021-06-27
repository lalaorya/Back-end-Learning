package com.hhj.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // rabbitMQ其实和redis差不多，都是一个网络中间件
        factory.setHost("47.119.115.60");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("root");

        // 创建连接
        Connection connection = factory.newConnection();

        return connection;
    }
}
