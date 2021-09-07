# RabbitMQ高级特性二 死信队列与延迟队列

## 死信队列

### 引言

死信队列，英文缩写：DLX 。Dead Letter Exchange（死信交换机），其实应该叫做死信交换机才更恰当。

当消息成为Dead message后，可以被重新发送到另一个交换机，这个交换机就是DLX。

![image-20210519215444911](https://i.loli.net/2021/05/20/LZvsxqD8RBQ5rtS.png)

总结：其实死信队列就是一个普通的交换机，有些队列的消息成为死信后，（比如过期了或者队列满了）这些死信一般情况下是会被 RabbitMQ 清理的。但是你可以配置某个交换机为此队列的死信交换机，该队列的消息成为死信后会被重新发送到此 DLX 。至于怎么处理这个DLX中的死信就是看具体的业务场景了，DLX 中的信息可以被路由到新的队列。

### 消息成为死信的三种情况

- 队列长度到达限制，无法加入新的消息
- 消费者拒接消费消息，并且不重回队列。该信息会被清除并进入死信队列
- 原队列存在消息过期设置，消息到达超时时间未被消费

### 队列如何绑定 DLX 

- x-dead-letter-exchange	指定此队列的死信队列
- x-dead-letter-exchange    指定此队列向DLX发送死信的routing key，因为这个时候该队列相当于一个生产者，发送消息要指定routing key

实现效果：往一个普通队列添加消息，消息过期成为死信，进入死信队列。死信队列根据配置好的route key 路由到与它绑定的其他普通队列。

1. 声明死信队列（普通交换机）

   ```java
       // 声明死信交换机
       @Bean("deadExchange")
       public Exchange deadExchange(){
           return ExchangeBuilder.topicExchange("sb_dead_exchange").durable(true)
                   .autoDelete().build();
       }
   ```

2. 声明普通队列，配置它的DLX

   ```java
       // 声明普通队列，绑定死信队列
       @Bean
       public Queue queue3(){
           Queue build = QueueBuilder.durable("sb_dead_queue").build();
           build.addArgument("x-message-ttl",10000);
           build.addArgument("x-dead-letter-exchange","sb_dead_exchange");
           // 此时队列相当于生产者，因此要指定消息的routing key,死信队列可以更加routing key路由到其他队列
           build.addArgument("x-dead-letter-routing-key","user4.info");
           return  build;
       }
       // 绑定此队列和它的交换机
       @Bean
       public Binding exchangQueue3(@Qualifier("queue3")Queue queue,
                                    @Qualifier("topicExchange") Exchange exchange){
           return BindingBuilder.bind(queue).to(exchange).with("user3.#").noargs();
       }
   ```

3. 绑定死信队列和普通队列，死信队列中的消息会根据路由发送到其他队列

   ```JAVA
       // 绑定死信队列和普通队列
       @Bean
       public Binding exchangQueue4(@Qualifier("queue2")Queue queue,
                                    @Qualifier("deadExchange") Exchange exchange){
           return BindingBuilder.bind(queue).to(exchange).with("user4.#").noargs();
       }
   ```

   ## 延迟队列
   
   延迟队列，即消息进入队列后不会立即被消费，只有到达指定时间后，才会被消费。经典的应用场景是下单减库存。
   
   ![image-20210519222253075](https://i.loli.net/2021/05/20/eYtERdhxISGjsH4.png)
   
   预扣库存的模式下，我们下单会立刻减库存，但是超过支付时间还没支付的话该订单就会被取消，库存回滚。
   
   具体实现可以采用定时器的方式，定时检查当前时间与下单时间是否超过上限，比如设置为30min。但是多久执行一次定时任务是个问题，精度大（比如1s执行一次）的话数据库的压力十分大，精度小（比如2min执行一次）的话又会带来误差。
   
   现在采用的方式是采用延迟队列。但是RabbitMQ没有提供延迟队列的功能。但是我们能使用死信队列 + TTL 实现延迟队列。TTL时间为超时时间（如30min）。
   
   ![image-20210519224613611](https://i.loli.net/2021/05/20/YnvcJfW8LU1dGeg.png)
   
   如此设计库存系统就一定只能在30min后才能从队列中取出订单，间接实现了延迟队列
   
   

