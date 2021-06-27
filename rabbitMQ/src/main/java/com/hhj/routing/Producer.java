package com.hhj.routing;

import com.hhj.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

    //交换机名称
    static final String DIRECT_EXCHAGE = "direct_exchange";
    //队列名称 打印
    static final String DIRECT_QUEUE_PRINT = "direct_queue_print";
    //队列名称
    static final String DIRECT_QUEUE_SAVE = "direct_queue_save";

    public static void main(String[] args) throws Exception {

        //创建连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        /**
         * 声明交换机
         * 参数1：交换机名称
         * 参数2：交换机类型，fanout、topic、direct、headers
         */
//        route模式的交换机类型是direct直连
        channel.exchangeDeclare(DIRECT_EXCHAGE, BuiltinExchangeType.DIRECT);

        // 声明（创建）队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         */
        channel.queueDeclare(DIRECT_QUEUE_PRINT, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_SAVE, true, false, false, null);

        // 队列绑定交换机
        // 绑定时队列要声明它的route key
        channel.queueBind(DIRECT_QUEUE_PRINT, DIRECT_EXCHAGE, "info");
        channel.queueBind(DIRECT_QUEUE_PRINT, DIRECT_EXCHAGE, "error");
        channel.queueBind(DIRECT_QUEUE_PRINT, DIRECT_EXCHAGE, "warning");

        channel.queueBind(DIRECT_QUEUE_SAVE, DIRECT_EXCHAGE, "error");

        // 发送信息
        String message = "日志级别：error；日志信息：*****" ;
        /**
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：消息其它属性
         * 参数4：消息内容
         */
        channel.basicPublish(DIRECT_EXCHAGE, "error", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 发送信息
        message = "日志级别：info；日志信息：*****" ;
        channel.basicPublish(DIRECT_EXCHAGE, "info", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 关闭资源
        channel.close();
        connection.close();
    }


}
