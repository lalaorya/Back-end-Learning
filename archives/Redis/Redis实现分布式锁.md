# Redis实现分布式锁

## 为什么需要分布式锁

引入经典的秒杀情景，100件商品供客户抢。如果是单机版的话，我们使用synchronized 或者 lock 都可以实现线程安全。但是如果多个服务器的话，synchronized 和 lock 就不管用了（废话，怎么可能管用，都不在同一段代码了）。

分布式锁就是被设计出来实现多个服务器的线程安全。

很容易想到的方案是把共享变量（锁）抽取出来放在一个公共的数据库里（Redis、Memchhed）里，所有的服务器通过这个公共的资源实现数据的一致性，防止超卖。

## 具体实现

分布式锁的实现方式有：Memchched分布式锁、Redis分布式锁、Zookeeper分布式锁，这里我们以Redis分布式锁为例，Redis分布式锁也是现在使用得最多的

### 1. 思路

- setnx加锁

  > setnx是实现分布式的核心，意思是只有当前key不存在才返回1，当前key存在返回0
  >
  > 这个key就是我们的“锁”，只有线程获得锁才能继续执行，执行完del这个key相当于解锁操作。这个就是redis实现分布式锁的核心，怎么样，很好理解吧

- del解锁

### 2. 第一个问题：锁无法被释放

试想一下，如果你执行完set命令服务器宕机了，来不及del解锁，那么这个锁永远无法被释放，其他线程无法执行。

解决方法是key必须设置一个超时时间，即使没有被显示释放，也在超时后自动释放。

redis为我们提供了这个命令设置超时时间

> - expire key ttl	秒为单位
> - pexpire key ttl    毫秒为单位
> - expireat key timestamp
> - pexpireat key timestamp

因此加锁的操作变成：

```java
setnx lock 1
expire lock 10
```

但是这两个操作不保证原子性（Redis单条操作保证原子性），如果加完锁还没设置过期时间服务器就宕机了，同样会导致死锁，因此加锁整个操作必须保证原子性。

redis提供了set+过期时间的原子操作

```java
set lock 1 EX 10 NX
// 最终的加锁命令
```

### 3. 第二个问题：错误释放锁

第二个问题，如果线程执行时间超过TTL，当前锁被自动销毁

但是等线程执行完了，原来的del方法还会执行，它就会去执行解锁操作，把其他线程占用的锁给del了，这会产生非常严重的问题

```java
String REDIS_Lock="lock";
String value=1;
try{
    redisUtil.setLockDistribute(REDIS_LOCK,1,10);
    ......业务逻辑
        
}finally{
    // 这个操作有可能会误删锁
    redisUtil.del(REDIS_LOCK);
}
```

解决方案是key的value不再是默认的了

```java
String REDIS_Lock="lock";
String value=UUID.randomUUID().toString()+Thread.currentThread().getname();
try{
    redisUtil.setLockDistribute(REDIS_LOCK,value,10);
    ......业务逻辑
        
}finally{
    // 先判断后删除
    if(redisUtil.get(REDIS_LOCK).equals(value)){
        redisUtil.del(REDIS_LOCK);
    }
    
}
```

这样写其实还有个问题，判断和删除无法保证原子性，还是有可能误删。因此解锁我们使用lua脚本来保证原子性：工具类有实现lua脚本的方法。

```lua
//lua脚本删除key原子操作
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

(解锁操作也可用redis事务来保证原子性，应付面试，实战还是lua脚本)

### 4. 第三个问题：超时解锁导致并发

加锁和解锁操作我们都搞定了，但是还有一个问题：如果你的线程执行时间超过ttl过期时间，锁还是被释放了，其他线程可以和此线程并发执行，这是我们并不想看到的。

因此我们要为ttl延时

我们可以让获得锁的线程开启一个**守护线程**，用来给快要过期的锁“续航”。

![image-20210502230844016](https://i.loli.net/2021/05/02/q7JvE2pPZkRXm6D.png)

### 5. 集群环境下可能出现的问题

redis集群环境，多个master，多个slave的情况下：

当主节点挂掉时，从节点会取而代之，但客户端无明显感知。当客户端 A 成功加锁，指令还未同步，此时主节点挂掉，从节点提升为主节点，新的主节点没有锁的数据，当客户端 B 加锁时就会成功。

也就是主结点加了锁就宕机了，从节点还没同步，当该从节点提升为主节点时就会出错。

![image-20210502231255950](https://i.loli.net/2021/05/02/U2F8KksEvXqStdZ.png)

解决方案我也不清楚....以后碰到再找资料

## 开源框架Redisson

上面的流程如果手写的话会要人老命，开源框架Redisson帮我们摆平一切，现在用得十分多

直接上代码：

```java
// 注入redisson
public Redisson redisson(){
    Config config=new Config();
    config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
    return Redisson.create(config);

}
```

```java
@Autowired
Redisson son;

String REDIS_Lock="lock";
String value=UUID.randomUUID().toString()+Thread.currentThread().getname();
RLock lock=son.getLock();
try{
    lock.lock();
    ......业务逻辑
        
}finally{
   	lock.unlock();
}

// 这段代码会解决上述三个问题，集群环境下redis分布式锁的实现
```

## 结语

分布式锁看起来难其实原理还是很简单的，没事多看看官方文档，讲得挺细致的

## 参考

- [分布式锁的实现之 redis 篇](https://xiaomi-info.github.io/2019/12/17/redis-distributed-lock/)
- [漫画：什么是分布式锁？](https://mp.weixin.qq.com/s/8fdBKAyHZrfHmSajXT_dnA)
- [[通俗讲解*分布式锁*，看完不懂算作者输](https://zhuanlan.zhihu.com/p/72896771)](https://www.zhihu.com/search?type=content&q=%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81)
- [尚硅谷周阳-大厂面试题第二季](https://www.bilibili.com/video/BV1Hy4y1B78T?p=51)

