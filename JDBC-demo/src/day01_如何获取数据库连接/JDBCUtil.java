package day01_��λ�ȡ���ݿ�����;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: JDBCѧϰԴ��
 * @description: ����һ��JDBC����Mysql�Ĺ����࣬������������
 *                 1. ����Mysql���ݿ⣬�������Ӷ���
 *                 2. �رղ��ͷ�Connection��PreparedStatement�������Դ
 * @author: HHJ
 * @created: 2020/09/09 16:09
 */
public class JDBCUtil {
    public static Connection getConnection() throws Exception{
        //����������Ϣ����ȡ�ĸ�Ԫ��
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user=pros.getProperty("user");
        String password=pros.getProperty("password");
//        String url=pros.getProperty("url"); ��ȡ������Ϣ�����ܶ�ȡ���ģ��������
        String url="jdbc:mysql://localhost:3306/��ѧ����?serverTimezone=GMT%2B8";
        String driverClass=pros.getProperty("driverClass");

        //����Driver�ࡢͬʱ��������
        Class.forName(driverClass);

        //��ȡ���ӡ�
        Connection con= DriverManager.getConnection(url,user,password);
        return con;
    }


    /**
     * �ر�������
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
    // ��������
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
