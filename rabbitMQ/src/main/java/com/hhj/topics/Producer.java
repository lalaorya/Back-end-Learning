package com.hhj.topics;

import com.hhj.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 需求：
 *  所有error级别的日志都要保存，所有 user 模块的日志都要保存
 *  所有日志都要打印
 */
public class Producer {

    //交换机名称
    static final String TOPIC_EXCHAGE = "topics_exchange";
    //队列名称 打印
    static final String TOPIC_QUEUE_PRINT = "topic_queue_print";
    //队列名称
    static final String TOPIC_QUEUE_SAVE = "topic_queue_save";

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
//        topics模式的交换机类型是topics 主题模式
        channel.exchangeDeclare(TOPIC_EXCHAGE, BuiltinExchangeType.TOPIC);

        // 声明（创建）队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         */
        channel.queueDeclare(TOPIC_QUEUE_PRINT, true, false, false, null);
        channel.queueDeclare(TOPIC_QUEUE_SAVE, true, false, false, null);

        // 队列绑定交换机
        // 绑定时队列要声明它的route key    通配符格式
        // 任何模块.级别都要打印
        channel.queueBind(TOPIC_QUEUE_PRINT,TOPIC_EXCHAGE, "#.info");
        channel.queueBind(TOPIC_QUEUE_PRINT,TOPIC_EXCHAGE, "#.error");
        channel.queueBind(TOPIC_QUEUE_PRINT,TOPIC_EXCHAGE, "#.warning");

        channel.queueBind(TOPIC_QUEUE_SAVE, TOPIC_EXCHAGE, "#.error");
        channel.queueBind(TOPIC_QUEUE_SAVE, TOPIC_EXCHAGE, "user.#");

        // 发送信息
        String message = "日志级别：info；所属模块：user 日志信息：*****" ;
        /**
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：消息其它属性
         * 参数4：消息内容
         */
        channel.basicPublish(TOPIC_EXCHAGE, "user.info", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 发送信息
        message = "日志级别：info；所属模块：order，日志信息：*****" ;
        channel.basicPublish(TOPIC_EXCHAGE, "order.info", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 发送信息
        message = "日志级别：error；所属模块：order，日志信息：*****" ;
        channel.basicPublish(TOPIC_EXCHAGE, "order.error", null, message.getBytes());
        System.out.println("已发送消息：" + message);


        // 关闭资源
        channel.close();
        connection.close();
    }


}
