package day07_���ݿ����ӳ�;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

/**
 * @program: JDBCѧϰԴ��
 * @description: c3p0��ȡ���ݿ����ӵ����ַ�ʽ
 * @author: HHJ
 * @created: 2020/09/19 22:49
 */
public class C3P0Demo {
    //��ʽһ
    public static Connection getConnection1()throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/��ѧ����?serverTimezone=GMT%2B8");
        cpds.setUser("root");
        cpds.setPassword("123abc");

        Connection con=cpds.getConnection();
        return con;
    }
    //��ʽ�� ͨ���Զ���ȡ�����ļ��ķ�ʽ
    public static Connection getConnection2() throws Exception {
        ComboPooledDataSource cpds=new ComboPooledDataSource("helloc3p0");
        Connection con=cpds.getConnection();
        //�ر����ݿ����ӳأ�һ�㲻��
        //DataSources.destroy(cpds);
        return con;
    }


}
