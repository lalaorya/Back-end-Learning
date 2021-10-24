# SQL调优的流程

SQL调优无论在实际工作中还是在面试过程中都是考查的重点，在文章中我将从慢查询分析和索引两个角度来介绍SQL调优的一点思路。

## 慢查询分析

SQL优化并不是漫无目的一条条去 `explain` SQL语句，需要具备针对性，所以我们只需要对执行时间超出我们预期的SQL进行针对的调整，那么慢查询分析日志就是帮我们快速定位超预期执行时间的SQL的一个有力工具。

### 打开慢查询分析开关

打开慢查询分析日志的开关的两种方式：1.修改配置文件，2.设置参数。我们下面使用设置参数的方式来演示：

- 登入MySQL

- 查看慢查询日志分析文件的配置信息

  ```SQL
  show variables like 'slow_query%';
  ```

- 结果如下

  ```SQL
  +---------------------+-------------------------------------------------+
  | Variable_name       | Value                                           |
  +---------------------+-------------------------------------------------+
  | slow_query_log      | OFF                                             |
  | slow_query_log_file | /usr/local/mysql/data/TechBird-Macbook-slow.log |
  +---------------------+-------------------------------------------------+
  2 rows in set (0.00 sec)
  ```

  把`slow_query_log` 的值 OFF 设置为 ON，即打开慢查询分析日志记录。记录的日志文件会保存在`slow_query_log_file` 的 Value 所指向的路径文件中，如果需要也可以对其进行修改。

  ```SQL
  set global slow_query_log = 'ON';
  ```

### 修改配置

修改默认慢SQL的时间，默认是10s，太长了，一般设置为100毫秒

```SQL
set global long_query_time = 1;
```

### 查看慢查询分析的日志文件

构造一个慢的SQL测试语句并执行

```sql
select sleep(3);
```

通过查看慢查询分析的日志文件，我们可以了解项目中哪些SQL超出了我们设置的预期执行时间，然后针对其进行优化：

```SQL
tail /var/lib/mysql/TechBird-Macbook-slow.log
```

```SQL
# User@Host: root[root] @ localhost [] Id:  419
# Query_time: 3.005275 Lock_time: 0.000000 Rows_sent: 1 Rows_examined: 0
SET timestamp=1617260355;
select sleep(3);
```

通过查看日志，可以准确的定位出是这条`select sleep(3);`执行超出了预期时间，之后就可以针对此SQL进行优化，一般是对索引进行调优：尽量使用覆盖索引，联合索引，减少回表次数，小心索引失效而进行全表扫描等

## 索引

### 使用explain对SQL语句进行分析

我这里有一张存储用户信息的演示表 tab_user：

```SQL
create table `tab_user`
(
   `uid`                 int(11) not null,
   `username`            varchar(255) default null,
   `password`            varchar(255) default null,
   `name`                varchar(255) default null,   
    primary key (`uid`) using btree,
	  key 'index_name' ('name'),
    key 'index_uid_name' ('uid','name')
);
```

现在我们来看一条简单查询语句的执行计划：

```SQL
explain select uid,name from tab_user where uid='1';
```

![image-20210907170349455](https://i.loli.net/2021/09/07/bej2gAzRXhoq64u.png)

对于执行计划，参数有 possible_keys 字段表示可能用到的索引，key 字段表示实际用的索引，key_len 表示索引的长度，rows 表示扫描的数据行数，filtered表示覆盖率%。Extra表示额外的信息说明。

**这其中需要重点关注type字段**，表示数据扫描类型，也就是描述了找到所需数据时使用的扫描方式是什么，常见扫描类型的执行效率从低到高的顺序为（**考虑到查询效率问题，全表扫描和全索引扫描要尽量避免**）

- ALL（全表扫描）
- index（全索引扫描）
- range（索引范围扫描）
- ref（非唯一索引扫描）
- eq_ref（唯一索引扫描）
- const（结果只有一条的主键或唯一索引扫描）

### 索引失效的情况

- 索引列使用了函数，因为这种情况必扫描所有索引并回表，代价高于全表扫描
- 使用了不确定查询其后面的索引会失效，如范围查询 `>、<`、`not in `、`not exist`、`like`（a%这种还是有可能走索引，%a%、%a就不可能走索引
- 不符合最左匹配原则
- 字符串不加单引号导致隐形类型转换
- 使用了or关键字
- Mysql自己分析之后觉得走全表更快