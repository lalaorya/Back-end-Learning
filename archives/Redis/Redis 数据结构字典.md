## Redis 数据结构字典

## 引言

字典结构，又叫map映射或者hashTable散列表，是一种专门保存**键值对**的数据结构。对字典结构不熟悉的同学可以自己找资料了解，这里不多深入。

字典结构对于 Redis 意义重大

- 字典结构是 Redis 数据库的基层实现，增删改查操作都是构建在字典操作之上

  > 我们来看Redis数据库的定义：**Redis**是一个使用[ANSI C](https://zh.wikipedia.org/wiki/ANSI_C)编写的[开源](https://zh.wikipedia.org/wiki/开源)、支持[网络](https://zh.wikipedia.org/wiki/电脑网络)、基于[内存](https://zh.wikipedia.org/wiki/内存)、[分布式](https://zh.wikipedia.org/wiki/分布式缓存)、可选[持久性](https://zh.wikipedia.org/w/index.php?title=持久性_(数据库)&action=edit&redlink=1)的[**键值对存储数据库**](https://zh.wikipedia.org/wiki/键值-值数据库)。
  >
  > NOSQL 非关系型数据库的存储形式都是key-value类型，也就是字典类型。从这里足以窥见字典结构对于 Redis 的特殊意义。

- 散列表数据结构的底层实现

  散列表（map/dict）结构的命令：http://www.redis.cn/commands.html#hash

  ```shell
  127.0.0.1:6379> hset class 1 zhangsan
  (integer) 1
  127.0.0.1:6379> hset class 2 lisi
  (integer) 1
  127.0.0.1:6379> hset class 3 wangwu
  127.0.0.1:6379> hset class 1 zhangsan
  (integer) 1
  127.0.0.1:6379> hset class 2 lisi
  (integer) 1
  127.0.0.1:6379> hset class 3 wangwu
  ```

## Redis字典结构的实现

C语言并没有提供字典结构的实现，因此Redis自己构建了一个字典结构：

![image-20210522151911183](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210522151911183.png)

- 字典结构有两个哈希表，一般情况下字典只使用第一个哈希表。只有在对表1进行 rehash 的时候才会使用第二个哈希表
  - 哈希表有一个entry节点数组，用于保存key-value数据。每个entry结点还有一个 next 的字段指向另一个 entry 结点（上图中没有画出来），用于哈希冲突时的拉链法
  - size：数组大小
  - sizemask：数组大小掩码，用于计算索引值，固定为size-1
  - used：已有结点个数（已存储key-value结点的个数）
- type、privdata：多态字典相关
- rehashix：记录当前 rehash 的进度，如果没有在进行 rehash ，rehashix = -1

![image-20210522153919108](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210522153919108.png)

## hash算法和hash冲突

- 通过hash函数算出当前键对应的索引值，把数据存进哈希表
- hash冲突的解决是拉链表

## rehash

- 负载因子过大或过小（哈希表/数组的元素过多或过少都要进行rehash）
- rehash就是把表1的数据全部复制到表2，表2的大小有两种情况
  - 如果是扩充，表2的大小是第一个大于等于表1已使用空间*2的2的n次方。比如表1已使用空间为13，乘2是26，那么表2的大小为32，如果表1的大小为17，那么表2的大小为64
  - 如果是缩小，表2的大小是第一个大于等于表1已使用空间的2的n次方。比如表1已使用空间为13，那么表2的大小为16，如果表1的大小为17，那么表2的大小为32
- 复制过程要重新计算索引值，因此顺序等等会被重新打乱

rehash的时机：

1. 服务器目前没有在执行 BGSAVE 命令或者 BGREWRITEAOF 命令， 并且哈希表的负载因子大于等于 `1` ；
2. 服务器目前正在执行 BGSAVE 命令或者 BGREWRITEAOF 命令， 并且哈希表的负载因子大于等于 `5` ；（强制rehash）

```JAVA
// 负载因子 = 哈希表已保存节点数量 / 哈希表大小
load_factor = ht[0].used / ht[0].size
```



## 渐进式rehash

- 因为表1数据太多了，一次性rehash的话要浪费不少时间，rehash是慢慢来

- https://juejin.cn/post/6844904053227405325

  设置一个index索引，每执行一个redis命令时将index处的键值对rehash到table2

- 执行新增add命令时，添加到table2

- select 、del 、update表1表2都执行

- 这样下去table1迟早变成空表，标志rehash成功
