package day06_DA0;

import day01_如何获取数据库连接.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBC学习源码
 * @description:  抽象方法，封装了对数据库的通用操作
 * @author: HHJ
 * @created: 2020/09/16 22:50
 */
public class BaseDAO {
    /**
     * 更新通用操作--只针对返回一条记录的更新--考虑事务
     * @param con
     * @param sql
     * @param args
     * @return
     */
    public int update(Connection con,String sql,Object... args){
        PreparedStatement ps=null;
        try{
            //预编译SQL语句
            ps=con.prepareStatement(sql);
            //填充占位符
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(null,ps);
        }
        return 0;
    }

    /**
     * 返回一条记录(以对象形式）
     * 考虑上事务
     * @param con
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T query(Connection con,Class<T> clazz,String sql,Object... args){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //预编译SQL语句
            ps=con.prepareStatement(sql);
            //填充占位符、
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs=ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd=rs.getMetaData();
            //获取列数
            int columnCount=rsmd.getColumnCount();
            if(rs.next()){
                T t=clazz.newInstance();
                //获取结果集第一行(只要一行)的每一列数据
                for(int i=0;i<columnCount;i++){
                    //获取列值
                    Object columnValue=rs.getObject(i+1);
                    //获取该列的字段名
                    String columnLable=rsmd.getColumnLabel(i+1);
                    //给对象t赋值
                    Field field=clazz.getDeclaredField(columnLable);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(null,ps,rs);
        }
        return null;
    }

    /**
     * 通用的查询操作，用于返回数据表中的多条记录构成的集合
     * （考虑上事务）
     */
    public <T> List<T> queryForList(Connection conn, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            // 创建集合对象
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列:给t对象指定的属性赋值
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);

                    // 获取每个列的列名
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                list.add(t);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * 用于查询特殊SQL语句的方法，返回一个确定类型的值，不再是集合或是对象
     * 比如这条SQL语句：select count(学号) from 学生信息表;
     * 就应该用这个方法查询
     * @param con
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public <E> E queryForValue(Connection con,String sql,Object... args){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=con.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs=ps.executeQuery();
            if(rs.next()){
                return (E)rs.getObject(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(null,ps,rs);
        }
        return null;
    }


}
