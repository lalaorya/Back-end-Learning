## RabbitMQ安装与配置

### 安装erlang环境

RabbitMQ底层是Erlang语言，因此要先安装erlang环境，就像你要运行Java程序就必须先安装JRE/JDK。

这里使用RabbitMQ官方提供的脚本下载yum源

```shell
curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.rpm.sh | sudo bash

sudo yum install -y erlang
```

安装完成可以使用以下命令查看是否安装成功

```SHELL
erl -v

# 如果出现下面信息说明安装成功，这里安装的是OTP/22版本
Erlang/OTP 22 [erts-10.4.4] [source] [64-bit] [smp:2:2] [ds:2:2:10] [async-threads:1] [hipe] 
Eshell V10.4.4  (abort with ^G)
```

### 安装RabbitMQ

RabbitMQ和Erlang有版本兼容性要求，血泪教训，**一定要选择兼容的版本**，具体信息可参考官网

RabbitMQ Erlang版本要求：https://www.rabbitmq.com/which-erlang.html

因为我安装的Erlang的版本是OPT/22，所以RabbitMQ我选择3.8.8

![image-20210513222946678](https://i.loli.net/2021/05/13/Q5zyEN2HrvlZ4px.png)

安装RabbitMQ的方式有两种：

1. wget命令从 github 源安装，但是我尝试的时候失败了，大概因为要翻墙，云服务器不知道怎么配置

   ```shell
   wget https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.8/rabbitmq-server-3.8.8-1.el6.noarch.rpm
   
   sudo yum install -y rabbitmq-server-3.8.8-1.el6.noarch.rpm
   ```

   有条件的同学可以试一下，其实我更推荐下面这种

2. 从 github 下载 rabbitMQ 的 rpm 文件，使用远程连接工具把文件拉到 Linux，可以放到 home 目录下。在 home 目录下同样使用 yum 命令安装即可

   ```shell
   sudo yum install -y rabbitmq-server-3.8.8-1.el6.noarch.rpm
   ```

   github源地址：https://github.com/rabbitmq/rabbitmq-server/releases?after=v3.8.9

   ![image-20210513223752957](https://i.loli.net/2021/05/13/pOamCd1XY3TeMND.png)

安装完成后使用

```shell
rabbitmqctl status
```

可以查看是否安装成功，如果没有报错说明安装成功

![image-20210513224125448](https://i.loli.net/2021/05/13/oNs8I9wG6gy2uct.png)

最后以守护进程的方式运行 RabbitMQ 

```shell
rabbitmq-server -detached
```

### 基本配置

#### 开启 WEB 后台管理插件

```shell
rabbitmq-plugins enable rabbitmq_management
```

#### 创建运程登录用户

因为 rabbbitmq 的默认账号（账号guest，密码guest）只允许在本地登录，远程登录会失败，因此我们需要创建新用户并设置响应权限

1. 查看当前用户列表

   ```shell
   rabbitmqctl list_users;
   ```

2. 新增一个用户

   语法：`rabbitmqctl add_user {username} {password}`

   ```shell
   # 创建 root 用户
   rabbitmqctl add_user root root
   ```

3. 设置用户角色

   语法：`rabbitmqctl set_user_tags {username} {tag...}`

   ```shell
   # 设置root为超级管理员
   rabbitmqctl set_user_tags root administrator
   ```

4. 设置用户权限

   语法是 `rabbitmqctl set_permissions [-p vhost] {user}{conf}{write}{read}`
   rabbitmq默认的虚拟主机host为”/“

   ```shell
   rabbitmqctl set_permissions -p / root ".*" ".*" ".*"
   ```

如果你使用的是云服务器，你可能还需要设置安全组，开放 5672 & 15672 这两个端口（RabbitMQ server默认是5672端口，后台管理默认是25672端口）

做完上述操作，你应该可以远程访问 RabbitMQ 

![image-20210513225404717](https://i.loli.net/2021/05/13/VBv78NkZpMzj3Xr.png)

### 参考

[RabbitMQ的安装详解](https://pjmike.github.io/2018/08/08/RabbitMQ%E7%9A%84%E5%AE%89%E8%A3%85%E8%AF%A6%E8%A7%A3/)

[消息队列RabbitMQ（二）：安装与配置](https://www.vckai.com/xiao-xi-dui-lie-rabbitmq-yi-an-zhuang-yu-pei-zhi)

