package day01_如何获取数据库连接;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: JDBC学习源码
 * @description: 这是一个JDBC连接Mysql的工具类，有两个方法、
 *                 1. 连接Mysql数据库，返回连接对象
 *                 2. 关闭并释放Connection和PreparedStatement对象的资源
 * @author: HHJ
 * @created: 2020/09/09 16:09
 */
public class JDBCUtil {
    public static Connection getConnection() throws Exception{
        //加载配置信息，获取四个元素
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user=pros.getProperty("user");
        String password=pros.getProperty("password");
//        String url=pros.getProperty("url"); 读取配置信息好像不能读取中文，后续解决
        String url="jdbc:mysql://localhost:3306/教学管理?serverTimezone=GMT%2B8";
        String driverClass=pros.getProperty("driverClass");

        //加载Driver类、同时加载驱动
        Class.forName(driverClass);

        //获取链接、
        Connection con= DriverManager.getConnection(url,user,password);
        return con;
    }


    /**
     * 关闭流对象
     * @param con
     * @param ps
     */
    public static void closeResource(Connection con, Statement ps){
        try {
            if (ps != null)
                ps.close();
        } catch(SQLException e){
                e.printStackTrace();
        }
        try{
            if(con!=null)
                con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // 方法重载
    public static void closeResource(Connection con, Statement ps, ResultSet rs){
        try {
            if (ps != null)
                ps.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(rs!=null)
                rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(con!=null)
                con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
