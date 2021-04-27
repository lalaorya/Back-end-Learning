package day02_使用PreparedStatement对数据库进行crud操作;

public class StatementDemo {
    /**
    Statement 是java定义的一个接口，用于执行静态SQL语句并返回它所生成的结果的对象
    但是呢？使用Statement执行SQL语句时会产生两个问题
    1. 存在拼串操作。这个问题源于接口的方法定义，无法解决，导致操作繁琐。后面介绍的preparedStatement子接口重写了该方法，能有效避免拼串问题
    2. 存在SQL注入问题。这个解释起来比较麻烦，产生原因是由于Statement接口的漏洞，使用错误的用户名密码也可访问数据库。这个问题是根本性问题，直接对Statement这个接口判了死刑

     PreparedStatement 的优点：
     1. 可以解决拼串问题和SQL诸如问题
     2. 可以操作Blob（图片、视频）类型的数据，而Statement做不到
     3. 因为它的SQL语句是预编译的，因为可以实现更高效的批量操作
     */
}
