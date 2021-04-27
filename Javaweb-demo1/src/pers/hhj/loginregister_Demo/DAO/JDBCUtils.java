package pers.hhj.loginregister_Demo.DAO;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource source=null;
    static {
        Properties pros=new Properties();
        try {
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            
            pros.load(is);
            source= DruidDataSourceFactory.createDataSource(pros);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return source;
    }

   public static Connection getCon() throws SQLException {
       Connection connection = source.getConnection();
       return connection;
   }



}
