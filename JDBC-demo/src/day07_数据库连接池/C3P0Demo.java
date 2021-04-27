package day07_数据库连接池;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

/**
 * @program: JDBC学习源码
 * @description: c3p0获取数据库连接的两种方式
 * @author: HHJ
 * @created: 2020/09/19 22:49
 */
public class C3P0Demo {
    //方式一
    public static Connection getConnection1()throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/教学管理?serverTimezone=GMT%2B8");
        cpds.setUser("root");
        cpds.setPassword("123abc");

        Connection con=cpds.getConnection();
        return con;
    }
    //方式二 通过自动读取配置文件的方式
    public static Connection getConnection2() throws Exception {
        ComboPooledDataSource cpds=new ComboPooledDataSource("helloc3p0");
        Connection con=cpds.getConnection();
        //关闭数据库连接池，一般不用
        //DataSources.destroy(cpds);
        return con;
    }


}
