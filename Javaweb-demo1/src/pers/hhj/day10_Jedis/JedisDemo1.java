package pers.hhj.day10_Jedis;

import redis.clients.jedis.Jedis;

public class JedisDemo1 {
    public static void main(String[] args) {
        // 连接redis数据库
        Jedis jedis = new Jedis("192.168.118.128",6379);

        jedis.set("name","zhangsan");

    }
}
