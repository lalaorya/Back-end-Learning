package day07_数据库连接池;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @program: JDBC学习源码
 * @description: DBCP连接池需要两个jar包
 * @author: HHJ
 * @created: 2020/09/20 10:00
 */
public class DBCPDemo {
    //方式一，不推荐
    public static Connection getConnection1() throws Exception {
        //DBCP实现了这个超级接口
        //创建了一个连接池
        BasicDataSource source = new BasicDataSource();
        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/教学管理?serverTimezone=GMT%2B8");
        source.setUsername("root");
        source.setPassword("123abc");
        //还可以设置其他涉及数据库连接池管理的信息
        //........
        return source.getConnection();
    }


    //方式二，使用配置文件
    public static Connection getConnection2() throws Exception{
        Properties pros=new Properties();
        //方式一：使用类加载器加载配置文件
        //InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("dbpc.properties");

        //方式二
        FileInputStream fis=new FileInputStream(new File("src/dbcp.properties"));
        pros.load(fis);
        DataSource source=BasicDataSourceFactory.createDataSource(pros);
        return source.getConnection();
    }

}
