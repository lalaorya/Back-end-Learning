## 分布式session的四种实现方案

传统的session由服务器端生成并存储，但是当应用进行集群部署的时候，因为客户端有可能访问到不同的服务器，如何保证不同服务器上session信息能够共享呢？本篇将介绍四种实现分布式session的方案

## 1. cookie

也就是不使用session，把信息存在cookie，使用cookie进行客户端和服务器之间的通信。缺点是存在安全隐患、且cookie大小有限制

## 2. seesion复制

session复制的原理是在同一个局域网里面通过发送广播来同步session，配置简单，一般用于小型应用。缺点是一旦服务器多了，并发上来了，session需要同步的数据量就大了，需要将其他服务器上的session全部同步到本服务器上，会带来一定的网路开销，在用户量特别大的时候，会出现内存不足的情况

## 3. nginx进行session绑定

集群部署无法共享session的原因就是由于负载均衡算法，每次客户端都有可能访问到不同的服务器。但是我们可以基于nginx的`ip-hash策略`，可以对客户端和服务器进行绑定，同一个客户端就只能访问该服务器，无论客户端发送多少次请求都被同一个服务器处理。缺点是容易造成单点故障，如果一台服务器宕机，该服务器上的所有session都会丢失，可靠性不高。

## 4. 基于redis存储

这是最容易想到的方案也是最流行的方案，使用外部的缓存设备来共享 Session，避免单个服务器节点挂掉而影响服务，共享数据都会放到外部缓存容器中如redis中

实现细节：

- 新增Filter，拦截请求，包装HttpServletRequest

- 改写getSession方法，从redis存储中获取session数据，返回自定义的HttpSession实现

- 在生成新Session后，写入sessionid到cookie中

  所有服务器的session信息都存储到了同一个Redis集群中，即所有的服务都将 Session 的信息存储到 Redis 集群中，无论是对 Session 的注销、更新都会同步到集群中，达到了 Session 共享的目的

## 参考

- [Redis实现分布式Session](https://jasonkayzk.github.io/2020/02/10/Redis%E5%AE%9E%E7%8E%B0%E5%88%86%E5%B8%83%E5%BC%8FSession/)

- [分布式Session一致性的4种解决方案](https://segmentfault.com/a/1190000022404396)



