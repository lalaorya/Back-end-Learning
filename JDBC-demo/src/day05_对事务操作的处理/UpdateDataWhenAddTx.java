package day05_对事务操作的处理;

import day01_如何获取数据库连接.JDBCUtil;
import day01_如何获取数据库连接.Student;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBC学习源码
 * @description: 对数据库更新进行事务处理时的操作方法
 * 在查询或更新中加入事务操作要怎么做：
 *      * 1. 多条SQL语句共用一个连接，因为这样才可以一起提交或者回滚。
 *      *      但是之前写的update或者query方法都是每个sql语句新建一个连接，因此要对这些方法进行升级
 *      * 2. 在执行SQL语句前要设置不自动提交
 *      * 3. 执行结束要记得提交
 *      * 4. 要是出现异常要进行回滚
 * @author: HHJ
 * @created: 2020/09/15 15:24
 */
public class UpdateDataWhenAddTx {
    Connection con = null;

    /**
     * 事务的测试类-更新操作
     */
    public void updateWithTX(){
        try {
            con = JDBCUtil.getConnection();
            //设置不自动提交
            con.setAutoCommit(false);
            //插入王五的同时插入赵六
            updateMethod(con, "insert into 学生信息表(学号,姓名,班级,专业)values(?,?,?,?)" ,"00006","张三","18金融学1班","金融学");
            //模拟网络异常
            //System.out.println(10/0);
            //插入赵六
            updateMethod(con, "insert into 学生信息表(学号,姓名,班级,专业)values(?,?,?,?)","00007","李四","18会计1班","会计");

            System.out.println("事务测试成功");
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            //走到这里说明出错了，要回滚数据
            try {
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }finally {
            //关闭连接
            //完成事务后，设置自动提交，(特别是针对数据库连接池的时候，以后会讲
            try{
                con.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
            JDBCUtil.closeResource(con,null);
        }
    }

    /**
     * 事务的测试-查询操作
     * 不做了，看后面的DAO类
     */
    public void queryTxTest(){

    }

    /**
     * 涉及事务的更新操作，多了一个参数。在查询完之后不能关闭连接。因为关闭连接会自动提交结果
     * 所以多条SQL语句要用同一个连接
     * @param con
     * @param sql
     * @param args
     */
    public void updateMethod(Connection con,String sql, Object ... args){//事务操作要使用同一个连接，因此不能关闭连接
        PreparedStatement ps=null;
        try{
            //预编译sql语句
            ps=con.prepareStatement(sql);
            //填充占位符
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //执行sql
            ps.execute();//返回更新的记录数，>0说明执行成功

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(null,ps);
        }
    }

    /**
     * 涉及事务的查询操作
     * 事务的查询并不需要对数据进行回滚啥的，因为它根本就没有改动数据
     * 涉及事务的查询需要考虑隔离级别的问题，具体查看笔记
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryMethod(Connection con,Class<T> clazz, String sql, Object ... args){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
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
            JDBCUtil.closeResource(null,ps,rs);
        }
        return null;
    }
}
