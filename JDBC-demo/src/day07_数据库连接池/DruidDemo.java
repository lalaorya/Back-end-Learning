package day07_数据库连接池;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @program: JDBC学习源码
 * @description: 阿里巴巴德鲁伊数据库连接池使用Demo
 * @author: HHJ
 * @created: 2020/09/20 13:24
 */
public class DruidDemo {
    public static Connection getConnection1() throws Exception{
        //调用方法配置基本信息的方法这里就不演示了，直接使用配置文件的方式
        Properties pros=new Properties();
        FileInputStream fis=new FileInputStream(new File("src/druid.properties"));

        pros.load(fis);

        //创建数据库连接池
        DataSource source= DruidDataSourceFactory.createDataSource(pros);
        //获取链接
        return source.getConnection();

    }
}
