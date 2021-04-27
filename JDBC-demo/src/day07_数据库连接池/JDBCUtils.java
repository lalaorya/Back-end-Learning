package day07_���ݿ����ӳ�;

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
 * @program: JDBCѧϰԴ��
 * @description: ʹ�����ݿ����ӳػ�ȡ���ӵķ���
 * @author: HHJ
 * @created: 2020/09/20 09:50
 */
public class JDBCUtils {
    //�ŵ���Աλ�ã���ֻ֤��һ�����ݿ����ӳ�
    private static ComboPooledDataSource cpds=new ComboPooledDataSource("helloc3p0");
    //c3p0��ȡ���ݿ����ӵķ�ʽ
    public static Connection getConnection1() throws Exception{
        return cpds.getConnection();

    }
    public static DataSource getC3p0DataSource(){
        return cpds;
    }

    //ʹ�þ�̬�����ķ�ʽ������dbcp���ݿ����ӳ�
    private static DataSource source=null;
    static{
        try {
            Properties pros = new Properties();

            FileInputStream fis = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(fis);

            //��������
            source = BasicDataSourceFactory.createDataSource(pros);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //dbcp���ӳط�������
    public static Connection getConnection2() throws Exception{
        return source.getConnection();
    }

    //ʹ�þ�̬����鴴��druid���ݿ����ӳ�
    private static DataSource source2=null;
    static {
        try {
            Properties pros = new Properties();

            FileInputStream fis = new FileInputStream(new File("src/druid.properties"));
            pros.load(fis);
            //�������д�������Ϊ�����ļ��޷���ȡ���Ľ�ѧ�������ݿ⣬��������Լ�����url
            //�������˿���ȥ��
            pros.setProperty("url","jdbc:mysql://localhost:3306/��ѧ����?serverTimezone=GMT%2B8");

            source2 = DruidDataSourceFactory.createDataSource(pros);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //druid���ӳط�������
    public static Connection getConnection3() throws Exception{
        return source2.getConnection();
    }
    //�������ݿ����ӳ�
    public static DataSource getDruidDataSource(){
        return source2;
    }


    //ʹ��dbutils.jar���ṩ��DbUtils��ر���Դ
    public static void closeResource(Connection con, PreparedStatement ps, ResultSet rs){
        DbUtils.closeQuietly(con,ps,rs);
        //����ʵ�ֺ�������ǰд��һ��һ��
    }
}
