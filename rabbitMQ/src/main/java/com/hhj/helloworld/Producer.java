package com.hhj.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 */

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // rabbitMQ其实和redis差不多，都是一个网络中间件
        factory.setHost("47.119.115.60");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("root");

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建chennel管道
        Channel channel = connection.createChannel();


        /**
         *       创建队列queue
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         */
        channel.queueDeclare("First_QUEUE",true,false,false,null);

        String message="Hello rabbitMQ";
        /**
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：消息其它属性
         * 参数4：消息内容
         */
        channel.basicPublish("","First_QUEUE",null,message.getBytes());
        System.out.println("已发送信息："+message);

        channel.close();
        connection.close();




    }

}
