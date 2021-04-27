package day07_���ݿ����ӳ�;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @program: JDBCѧϰԴ��
 * @description: DBCP���ӳ���Ҫ����jar��
 * @author: HHJ
 * @created: 2020/09/20 10:00
 */
public class DBCPDemo {
    //��ʽһ�����Ƽ�
    public static Connection getConnection1() throws Exception {
        //DBCPʵ������������ӿ�
        //������һ�����ӳ�
        BasicDataSource source = new BasicDataSource();
        //���û�����Ϣ
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/��ѧ����?serverTimezone=GMT%2B8");
        source.setUsername("root");
        source.setPassword("123abc");
        //���������������漰���ݿ����ӳع������Ϣ
        //........
        return source.getConnection();
    }


    //��ʽ����ʹ�������ļ�
    public static Connection getConnection2() throws Exception{
        Properties pros=new Properties();
        //��ʽһ��ʹ������������������ļ�
        //InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("dbpc.properties");

        //��ʽ��
        FileInputStream fis=new FileInputStream(new File("src/dbcp.properties"));
        pros.load(fis);
        DataSource source=BasicDataSourceFactory.createDataSource(pros);
        return source.getConnection();
    }

}
