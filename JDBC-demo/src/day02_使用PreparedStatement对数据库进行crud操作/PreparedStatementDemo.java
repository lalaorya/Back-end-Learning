package day02_使用PreparedStatement对数据库进行crud操作;

import day01_如何获取数据库连接.JDBCUtil;
import day01_如何获取数据库连接.Student;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

    /**
     PreparedStatement 接口是 Statement 的子接口，它表示一条预编译过的 SQL 语句。
     PreparedStatement接口为什么不用拼串和没有SQL注入问题呢？
     这是因为PreparedStatement接口传入的sql语句参数可以用“？”代替，后面再调用setxxx方法来设置这些参数
     PreparedStatement预编译的sql语句会被编译器缓存下来，因此当同一sql语句被多次执行时，只需要编译一次，大大提高了效率
     */
public class PreparedStatementDemo {
    /**
     * 向表中插入一条数据
     * 这里的获取数据库连接和关闭资源我们都是自己写的，比较麻烦。
     * 所以我们把获取数据库连接和关闭资源的方法封装到JDBCUtil类里面，以后都是直接调用这个类的静态方法，这里提醒一下
     * version 1.0
     */
    public void InsertMethod(){
        PreparedStatement ps=null;
        Connection con=null;
        try {
            //加载配置信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            //读取配置信息
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            // String url = pros.getProperty("url");
            String url = "jdbc:mysql://localhost:3306/教学管理?serverTimezone=GMT%2B8";
            String driverClass = pros.getProperty("driverClass");
            //加载驱动
            Class.forName(driverClass);
            //获取链接
            con = DriverManager.getConnection(url, user, password);
            //预编译SQL语句，返回PreparedStatement对象
            String sql = "insert into 学生信息表(学号,姓名,年龄) values (?,?,?)";
            ps = con.prepareStatement(sql);
            //填充占位符
            ps.setString(1, "00001");
            ps.setString(2, "张三");
            ps.setInt(3, 22);

            //执行SQL语句
            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {//关闭资源
            try{
                if(ps !=null)
                    ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try{
                if(con !=null)
                    con.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }

    }

    /**
     * 对Mysql数据库的表更新数据的通用方法
     * 更新数据库不需要返回结果，只需要执行sql语句就行，比较简单
     * @param sql
     * @param args
     * 最终版
     */
    public int updateMethod(String sql,Object ... args){//这是可变形参，因为不确定sql语句的占位符有多少
        Connection con=null;
        PreparedStatement ps=null;
        try{
            con= JDBCUtil.getConnection();
            //预编译sql语句
            ps=con.prepareStatement(sql);
            //填充占位符
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //执行sql语句
            return ps.executeUpdate();//返回更新的记录数，>0说明执行成功

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(con,ps);
        }
        return 0;
    }

    /**
     * 查询学生信息表表的方法
     * version 1.0 简单版，sql语句直接在里面定义了，因此只能使用这条的sql语句，且只能查询学生表
     * 后面会写一个通用的查询方法
     */
    public void stuQuery(){
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs= null;//数据库查询的结果集
        try {
            // 获取链接
            con = JDBCUtil.getConnection();

            //预编译sql
            String sql = "select * from 学生信息表 where 学号=?";
            ps = con.prepareStatement(sql);

            //填充占位符
            ps.setObject(1, "06152");

            //执行并返回结果集对象
            rs = ps.executeQuery();
            /**
             * next():boolean next()
             * 判断下一指针是否有数据，有返回true
             * 如果有，指针下移到下一个元素
             */
            Student s;//查询的结果使用一个学生类对象保存起来
            while (rs.next()) {
                String stu=rs.getString(1);
                String name = rs.getString(2);
                String class1=rs.getString(3);
                String major=rs.getString(4);
                int age=rs.getInt(5);
                Date birthday= rs.getDate(6);
                s=new Student(stu,name,class1,major,age,birthday);
                System.out.println(s.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(con,ps);
        }

    }

    /**
     * 针对学生信息表表的查询方法，运用了ORM编程思想、反射
     * 这个方法仅对【学生信息表】可用，因为不同表的属性字段不同，要定义不同的orm类
     * @param sql
     * @param args
     * @version 针对学生信息表的通用版
     */
    public Student queryForStudent(String sql,Object ... args){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //获取链接
            con = JDBCUtil.getConnection();
            //预编译SQL语句
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行sql，返回结果集
            rs = ps.executeQuery();
            //获取结果集的元数据集
            //ResultsetMetaData 可用于获取关于 ResultSet 对象中列的类型和属性信息。
            ResultSetMetaData rsmd = ps.getMetaData();

            //获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                Student s = new Student();
                for (int i = 0; i < columnCount; i++) {
                    //获取每一列的数据
                    Object columnVal = rs.getObject(i + 1);
                    //将数据赋值进ORM对象
                    //1. 获取别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //2. 使用反射为对象的对应字段赋值·
                    Field field = Student.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(s, columnVal);
                }
                return s;

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(con,ps,rs);
        }
        return null;
    }

    /**
     * 针对多个表的sql查询语句，返回查询记录的对象集合
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @version 通用版+最终版
     */
    public <T> List<T> queryMethod(Class<T> clazz, String sql, Object ... args){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //获取链接
            con = JDBCUtil.getConnection();
            //预编译SQL语句
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行sql，返回结果集
            rs = ps.executeQuery();
            //获取结果集的元数据集
            //ResultsetMetaData 可用于获取关于 ResultSet 对象中列的类型和属性信息。
            ResultSetMetaData rsmd = ps.getMetaData();

            //获取列数
            int columnCount = rsmd.getColumnCount();
            //创建集合
            ArrayList<T> list=new ArrayList<>();
            while (rs.next()) {//每行
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {//每行的列
                    //获取每一列的数据
                    Object columnVal = rs.getObject(i + 1);
                    //将数据赋值进ORM对象
                    //1. 获取别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //2. 使用反射为对象的对应字段赋值·
                    Field field = Student.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                list.add(t);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(con,ps,rs);
        }
        return null;
    }


}
