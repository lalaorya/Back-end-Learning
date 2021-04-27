package pers.hhj.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource source1;
    static {
        try {
            Properties pros=new Properties();
//            这里要用绝对路径，不然servlet无法获取
            FileInputStream fis = new FileInputStream(new File("D:\\ideaProjects\\webDemo1\\src\\druid.properties"));
//            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("src/druid.properties");
//            InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("src/druid.properties");
            pros.load(fis);
            source1= DruidDataSourceFactory.createDataSource(pros);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource2(){
        return source1;
    }

    /**
     * 返回数据库连接
     * @return
     * @throws SQLException
     */
   public static Connection getCon() throws SQLException {
       Connection connection = source1.getConnection();
       return connection;
   }



}
