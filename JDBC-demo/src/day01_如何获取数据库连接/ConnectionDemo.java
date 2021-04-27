package day01_如何获取数据库连接;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionDemo {
    //方式一
    public void getConnection(){
        try {
            //获取接口实例化对象(多态)
            Driver driver1 = new com.mysql.jdbc.Driver();

            //提供url：协议:子协议:子名称:数据库名称
            String url = "jdbc:mysql://localhost:3306/教学管理";

            //使用Properties封装用户名和密码
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "123abc");

            System.out.println(driver1.acceptsURL(url));//判断url是否可用，可省略
            Connection con = driver1.connect(url, info);//获取连接
            System.out.println(con);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //方式二
    //方式一中使用了微软公司提供的驱动程序，就是line 14的构造方法。
    //在这里我们通过Java中的反射机制使得该程序不出现第三方的api，增强代码的可移植性
    public void getConnection2(){
        try {
            //利用反射实例化Driver接口
            String className="com.mysql.jdbc.Driver";
            Class cla=Class.forName(className);
            Driver driver2=(Driver)cla.newInstance();

            //提供url：协议:子协议:子名称:数据库名称
            String url = "jdbc:mysql://localhost:3306/教学管理";

            //使用Properties封装用户名和密码
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "123abc");

            System.out.println(driver2.acceptsURL(url));//判断url是否可用，可省略
            Connection con = driver2.connect(url, info);//获取连接
            System.out.println(con);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //方式三
    //使用DriverManager实现数据库的连接（4个要素
    public void getConnection3(){
        try {
            //利用反射实例化Driver接口
            String className="com.mysql.jdbc.Driver";
            Class cla=Class.forName(className);
            Driver driver3=(Driver)cla.newInstance();

            //提供url、user、password
            String url = "jdbc:mysql://localhost:3306/教学管理";
            String user="root";
            String password="123abc";

            //注册驱动
            DriverManager.registerDriver(driver3);

            //获取连接
            Connection con=DriverManager.getConnection(url,user,password);
            System.out.println(con);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //方式四
    //方式三中的利用反射实例化接口还有优化的空间，怎么优化呢？看代码
    public void getConnection4(){
        try {
            //提供url、user、password
            String url = "jdbc:mysql://localhost:3306/教学管理";
            String user="root";
            String password="123abc";
            String className="com.mysql.jdbc.Driver";
            //
            Class.forName(className);
            /*这行代码可以实例化Driver并注册驱动。为什么呢
            因为SQLServerDriver类中的静态代码块已经帮我们实现了这些过程
            static {
                try {
                    DriverManager.registerDriver(new SQLServerDriver());
                } catch (SQLException var1) {
                 if (drLogger.isLoggable(Level.FINER) && Util.IsActivityTraceOn()) {
                drLogger.finer("Error registering driver: " + var1);
            }
            }
            利用反射机制时加载了类，因为这个静态代码块也被执行了，自动实例化了Driver并注册

             */

            //获取连接
            Connection con=DriverManager.getConnection(url,user,password);
            System.out.println(con);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //方式五（Final版本、
    //把四要素写入一个配置文件，加载类时从配置文件中读取信息
    //好处是实现了代码和数据的分析，如果要访问其他的数据库或使用其他驱动，不需要修改代码，只要修改配置信息
    public void getConnection5()    throws  Exception{
        //加载配置信息
        InputStream is=ConnectionDemo.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros=new Properties();
        pros.load(is);
        //读取配置信息
        String user=pros.getProperty("user");
        String password=pros.getProperty("password");
        String url=pros.getProperty("url");
        System.out.println(url);
        String driverClass=pros.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        //获取链接
        Connection con=DriverManager.getConnection(url,user,password);
        System.out.println(con);


    }



}
