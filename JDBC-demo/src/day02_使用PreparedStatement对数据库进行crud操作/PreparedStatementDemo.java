package day02_ʹ��PreparedStatement�����ݿ����crud����;

import day01_��λ�ȡ���ݿ�����.JDBCUtil;
import day01_��λ�ȡ���ݿ�����.Student;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

    /**
     PreparedStatement �ӿ��� Statement ���ӽӿڣ�����ʾһ��Ԥ������� SQL ��䡣
     PreparedStatement�ӿ�Ϊʲô����ƴ����û��SQLע�������أ�
     ������ΪPreparedStatement�ӿڴ����sql�����������á��������棬�����ٵ���setxxx������������Щ����
     PreparedStatementԤ�����sql���ᱻ������������������˵�ͬһsql��䱻���ִ��ʱ��ֻ��Ҫ����һ�Σ���������Ч��
     */
public class PreparedStatementDemo {
    /**
     * ����в���һ������
     * ����Ļ�ȡ���ݿ����Ӻ͹ر���Դ���Ƕ����Լ�д�ģ��Ƚ��鷳��
     * �������ǰѻ�ȡ���ݿ����Ӻ͹ر���Դ�ķ�����װ��JDBCUtil�����棬�Ժ���ֱ�ӵ��������ľ�̬��������������һ��
     * version 1.0
     */
    public void InsertMethod(){
        PreparedStatement ps=null;
        Connection con=null;
        try {
            //����������Ϣ
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            //��ȡ������Ϣ
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            // String url = pros.getProperty("url");
            String url = "jdbc:mysql://localhost:3306/��ѧ����?serverTimezone=GMT%2B8";
            String driverClass = pros.getProperty("driverClass");
            //��������
            Class.forName(driverClass);
            //��ȡ����
            con = DriverManager.getConnection(url, user, password);
            //Ԥ����SQL��䣬����PreparedStatement����
            String sql = "insert into ѧ����Ϣ��(ѧ��,����,����) values (?,?,?)";
            ps = con.prepareStatement(sql);
            //���ռλ��
            ps.setString(1, "00001");
            ps.setString(2, "����");
            ps.setInt(3, 22);

            //ִ��SQL���
            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {//�ر���Դ
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
     * ��Mysql���ݿ�ı�������ݵ�ͨ�÷���
     * �������ݿⲻ��Ҫ���ؽ����ֻ��Ҫִ��sql�����У��Ƚϼ�
     * @param sql
     * @param args
     * ���հ�
     */
    public int updateMethod(String sql,Object ... args){//���ǿɱ��βΣ���Ϊ��ȷ��sql����ռλ���ж���
        Connection con=null;
        PreparedStatement ps=null;
        try{
            con= JDBCUtil.getConnection();
            //Ԥ����sql���
            ps=con.prepareStatement(sql);
            //���ռλ��
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //ִ��sql���
            return ps.executeUpdate();//���ظ��µļ�¼����>0˵��ִ�гɹ�

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(con,ps);
        }
        return 0;
    }

    /**
     * ��ѯѧ����Ϣ���ķ���
     * version 1.0 �򵥰棬sql���ֱ�������涨���ˣ����ֻ��ʹ��������sql��䣬��ֻ�ܲ�ѯѧ����
     * �����дһ��ͨ�õĲ�ѯ����
     */
    public void stuQuery(){
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs= null;//���ݿ��ѯ�Ľ����
        try {
            // ��ȡ����
            con = JDBCUtil.getConnection();

            //Ԥ����sql
            String sql = "select * from ѧ����Ϣ�� where ѧ��=?";
            ps = con.prepareStatement(sql);

            //���ռλ��
            ps.setObject(1, "06152");

            //ִ�в����ؽ��������
            rs = ps.executeQuery();
            /**
             * next():boolean next()
             * �ж���һָ���Ƿ������ݣ��з���true
             * ����У�ָ�����Ƶ���һ��Ԫ��
             */
            Student s;//��ѯ�Ľ��ʹ��һ��ѧ������󱣴�����
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
     * ���ѧ����Ϣ���Ĳ�ѯ������������ORM���˼�롢����
     * ����������ԡ�ѧ����Ϣ�����ã���Ϊ��ͬ��������ֶβ�ͬ��Ҫ���岻ͬ��orm��
     * @param sql
     * @param args
     * @version ���ѧ����Ϣ���ͨ�ð�
     */
    public Student queryForStudent(String sql,Object ... args){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //��ȡ����
            con = JDBCUtil.getConnection();
            //Ԥ����SQL���
            ps = con.prepareStatement(sql);
            //���ռλ��
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //ִ��sql�����ؽ����
            rs = ps.executeQuery();
            //��ȡ�������Ԫ���ݼ�
            //ResultsetMetaData �����ڻ�ȡ���� ResultSet �������е����ͺ�������Ϣ��
            ResultSetMetaData rsmd = ps.getMetaData();

            //��ȡ����
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                Student s = new Student();
                for (int i = 0; i < columnCount; i++) {
                    //��ȡÿһ�е�����
                    Object columnVal = rs.getObject(i + 1);
                    //�����ݸ�ֵ��ORM����
                    //1. ��ȡ����
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //2. ʹ�÷���Ϊ����Ķ�Ӧ�ֶθ�ֵ��
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
     * ��Զ�����sql��ѯ��䣬���ز�ѯ��¼�Ķ��󼯺�
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @version ͨ�ð�+���հ�
     */
    public <T> List<T> queryMethod(Class<T> clazz, String sql, Object ... args){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //��ȡ����
            con = JDBCUtil.getConnection();
            //Ԥ����SQL���
            ps = con.prepareStatement(sql);
            //���ռλ��
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //ִ��sql�����ؽ����
            rs = ps.executeQuery();
            //��ȡ�������Ԫ���ݼ�
            //ResultsetMetaData �����ڻ�ȡ���� ResultSet �������е����ͺ�������Ϣ��
            ResultSetMetaData rsmd = ps.getMetaData();

            //��ȡ����
            int columnCount = rsmd.getColumnCount();
            //��������
            ArrayList<T> list=new ArrayList<>();
            while (rs.next()) {//ÿ��
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {//ÿ�е���
                    //��ȡÿһ�е�����
                    Object columnVal = rs.getObject(i + 1);
                    //�����ݸ�ֵ��ORM����
                    //1. ��ȡ����
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //2. ʹ�÷���Ϊ����Ķ�Ӧ�ֶθ�ֵ��
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
