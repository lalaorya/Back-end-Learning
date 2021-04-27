package day01_��λ�ȡ���ݿ�����;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionDemo {
    //��ʽһ
    public void getConnection(){
        try {
            //��ȡ�ӿ�ʵ��������(��̬)
            Driver driver1 = new com.mysql.jdbc.Driver();

            //�ṩurl��Э��:��Э��:������:���ݿ�����
            String url = "jdbc:mysql://localhost:3306/��ѧ����";

            //ʹ��Properties��װ�û���������
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "123abc");

            System.out.println(driver1.acceptsURL(url));//�ж�url�Ƿ���ã���ʡ��
            Connection con = driver1.connect(url, info);//��ȡ����
            System.out.println(con);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //��ʽ��
    //��ʽһ��ʹ����΢��˾�ṩ���������򣬾���line 14�Ĺ��췽����
    //����������ͨ��Java�еķ������ʹ�øó��򲻳��ֵ�������api����ǿ����Ŀ���ֲ��
    public void getConnection2(){
        try {
            //���÷���ʵ����Driver�ӿ�
            String className="com.mysql.jdbc.Driver";
            Class cla=Class.forName(className);
            Driver driver2=(Driver)cla.newInstance();

            //�ṩurl��Э��:��Э��:������:���ݿ�����
            String url = "jdbc:mysql://localhost:3306/��ѧ����";

            //ʹ��Properties��װ�û���������
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "123abc");

            System.out.println(driver2.acceptsURL(url));//�ж�url�Ƿ���ã���ʡ��
            Connection con = driver2.connect(url, info);//��ȡ����
            System.out.println(con);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //��ʽ��
    //ʹ��DriverManagerʵ�����ݿ�����ӣ�4��Ҫ��
    public void getConnection3(){
        try {
            //���÷���ʵ����Driver�ӿ�
            String className="com.mysql.jdbc.Driver";
            Class cla=Class.forName(className);
            Driver driver3=(Driver)cla.newInstance();

            //�ṩurl��user��password
            String url = "jdbc:mysql://localhost:3306/��ѧ����";
            String user="root";
            String password="123abc";

            //ע������
            DriverManager.registerDriver(driver3);

            //��ȡ����
            Connection con=DriverManager.getConnection(url,user,password);
            System.out.println(con);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //��ʽ��
    //��ʽ���е����÷���ʵ�����ӿڻ����Ż��Ŀռ䣬��ô�Ż��أ�������
    public void getConnection4(){
        try {
            //�ṩurl��user��password
            String url = "jdbc:mysql://localhost:3306/��ѧ����";
            String user="root";
            String password="123abc";
            String className="com.mysql.jdbc.Driver";
            //
            Class.forName(className);
            /*���д������ʵ����Driver��ע��������Ϊʲô��
            ��ΪSQLServerDriver���еľ�̬������Ѿ�������ʵ������Щ����
            static {
                try {
                    DriverManager.registerDriver(new SQLServerDriver());
                } catch (SQLException var1) {
                 if (drLogger.isLoggable(Level.FINER) && Util.IsActivityTraceOn()) {
                drLogger.finer("Error registering driver: " + var1);
            }
            }
            ���÷������ʱ�������࣬��Ϊ�����̬�����Ҳ��ִ���ˣ��Զ�ʵ������Driver��ע��

             */

            //��ȡ����
            Connection con=DriverManager.getConnection(url,user,password);
            System.out.println(con);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //��ʽ�壨Final�汾��
    //����Ҫ��д��һ�������ļ���������ʱ�������ļ��ж�ȡ��Ϣ
    //�ô���ʵ���˴�������ݵķ��������Ҫ�������������ݿ��ʹ����������������Ҫ�޸Ĵ��룬ֻҪ�޸�������Ϣ
    public void getConnection5()    throws  Exception{
        //����������Ϣ
        InputStream is=ConnectionDemo.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros=new Properties();
        pros.load(is);
        //��ȡ������Ϣ
        String user=pros.getProperty("user");
        String password=pros.getProperty("password");
        String url=pros.getProperty("url");
        System.out.println(url);
        String driverClass=pros.getProperty("driverClass");
        //��������
        Class.forName(driverClass);
        //��ȡ����
        Connection con=DriverManager.getConnection(url,user,password);
        System.out.println(con);


    }



}
