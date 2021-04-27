package day07_数据库连接池;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @program: JDBC学习源码
 * @description: 使用数据库连接池获取连接的方法
 * @author: HHJ
 * @created: 2020/09/20 09:50
 */
public class JDBCUtils {
    //放到成员位置，保证只有一个数据库连接池
    private static ComboPooledDataSource cpds=new ComboPooledDataSource("helloc3p0");
    //c3p0获取数据库连接的方式
    public static Connection getConnection1() throws Exception{
        return cpds.getConnection();

    }
    public static DataSource getC3p0DataSource(){
        return cpds;
    }

    //使用静态代码块的方式创建的dbcp数据库连接池
    private static DataSource source=null;
    static{
        try {
            Properties pros = new Properties();

            FileInputStream fis = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(fis);

            //创建对象
            source = BasicDataSourceFactory.createDataSource(pros);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //dbcp连接池返回链接
    public static Connection getConnection2() throws Exception{
        return source.getConnection();
    }

    //使用静态代码块创建druid数据库连接池
    private static DataSource source2=null;
    static {
        try {
            Properties pros = new Properties();

            FileInputStream fis = new FileInputStream(new File("src/druid.properties"));
            pros.load(fis);
            //下面这行代码是因为配置文件无法读取中文教学管理数据库，因此我们自己设置url
            //如果解决了可以去掉
            pros.setProperty("url","jdbc:mysql://localhost:3306/教学管理?serverTimezone=GMT%2B8");

            source2 = DruidDataSourceFactory.createDataSource(pros);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //druid连接池返回链接
    public static Connection getConnection3() throws Exception{
        return source2.getConnection();
    }
    //返回数据库连接池
    public static DataSource getDruidDataSource(){
        return source2;
    }


    //使用dbutils.jar中提供的DbUtils类关闭资源
    public static void closeResource(Connection con, PreparedStatement ps, ResultSet rs){
        DbUtils.closeQuietly(con,ps,rs);
        //具体实现和我们以前写的一摸一样
    }
}
