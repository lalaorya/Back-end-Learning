package day07_���ݿ����ӳ�;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @program: JDBCѧϰԴ��
 * @description: ����Ͱ͵�³�����ݿ����ӳ�ʹ��Demo
 * @author: HHJ
 * @created: 2020/09/20 13:24
 */
public class DruidDemo {
    public static Connection getConnection1() throws Exception{
        //���÷������û�����Ϣ�ķ�������Ͳ���ʾ�ˣ�ֱ��ʹ�������ļ��ķ�ʽ
        Properties pros=new Properties();
        FileInputStream fis=new FileInputStream(new File("src/druid.properties"));

        pros.load(fis);

        //�������ݿ����ӳ�
        DataSource source= DruidDataSourceFactory.createDataSource(pros);
        //��ȡ����
        return source.getConnection();

    }
}
