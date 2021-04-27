package day07_数据库连接池;

import java.sql.Connection;

/**
 * @program: JDBC学习源码
 * @description:
 * @author: HHJ
 * @created: 2020/09/19 23:13
 */
public class Test {
    public static void main(String args[])throws Exception {
//        Connection con=C3P0Demo.getConnection2();
//        System.out.println(con);
//
//        con=DBCPDemo.getConnection2();
//        System.out.println(con);

        Connection con=DruidDemo.getConnection1();
        System.out.println((con));
    }
}
