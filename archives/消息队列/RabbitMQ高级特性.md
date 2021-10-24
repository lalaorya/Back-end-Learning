## RabbitMQ高级特性

### 消费端限流

在并发量高的情况下，如秒杀、抢票等场景，如果不对 RabbitMQ 推送信息给消费者的速度进行限流的话，很容易打崩某个系统

![image-20210519175151290](https://i.loli.net/2021/05/20/GspqR5e1Ocdv8Wz.png)

RabbitMQ 的限流机制：

- 确保消费端 ack 机制为手动签到
- 如果限流数为1，表示消费端每次只能从mq拉去一条消息来消费，直到手动确认消费完毕后，才会继续拉去下一条信息。使用这种机制来对消费端进行限流

具体配置：

```yaml
spring:
  rabbitmq:
#    配置消费端
    listener:
      simple:
        acknowledge-mode: manual
        prefetch: 1000
```

### 消息存活时间TTL

RabbitMQ允许您为**某个消息**和**队列中的所有消息**设置TTL（生存时间）。如果两者都设置了，则以时间短的为准。

过期的消息不会立刻从队列中被清除，而是等到它轮询到队头的时候判断它是否过期，过期再清除。有点类似于Redis的惰性清理。

#### 为队列设置过期时间

参数是：`x-message-ttl`

```JAVA
@Configuration
public class RabbitMQConfig {
    private static final String SB_TOPIC_QUEUE="sb_topic_queue1";

    // 声明队列
    @Bean
    public Queue queue1(){
        Queue build = QueueBuilder.durable(SB_TOPIC_QUEUE).build();
        build.addArgument("x-message-ttl",3000);
        return  build;
    }
}

```

#### 为单个消息设置过期时间

```JAVA
    @Test
    public void testTTLOneMessage(){
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(8000));
                return message;
            }
        };
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("sb_topic_exchange","user2.info",(Object) "这个消息的ttl为8s",messagePostProcessor);
        }

    }
```





