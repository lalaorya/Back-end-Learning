package day06_DA0;

import day01_��λ�ȡ���ݿ�����.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBCѧϰԴ��
 * @description:  ���󷽷�����װ�˶����ݿ��ͨ�ò���
 * @author: HHJ
 * @created: 2020/09/16 22:50
 */
public class BaseDAO {
    /**
     * ����ͨ�ò���--ֻ��Է���һ����¼�ĸ���--��������
     * @param con
     * @param sql
     * @param args
     * @return
     */
    public int update(Connection con,String sql,Object... args){
        PreparedStatement ps=null;
        try{
            //Ԥ����SQL���
            ps=con.prepareStatement(sql);
            //���ռλ��
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
     * ����һ����¼(�Զ�����ʽ��
     * ����������
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
            //Ԥ����SQL���
            ps=con.prepareStatement(sql);
            //���ռλ����
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs=ps.executeQuery();
            //��ȡ�������Ԫ����
            ResultSetMetaData rsmd=rs.getMetaData();
            //��ȡ����
            int columnCount=rsmd.getColumnCount();
            if(rs.next()){
                T t=clazz.newInstance();
                //��ȡ�������һ��(ֻҪһ��)��ÿһ������
                for(int i=0;i<columnCount;i++){
                    //��ȡ��ֵ
                    Object columnValue=rs.getObject(i+1);
                    //��ȡ���е��ֶ���
                    String columnLable=rsmd.getColumnLabel(i+1);
                    //������t��ֵ
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
     * ͨ�õĲ�ѯ���������ڷ������ݱ��еĶ�����¼���ɵļ���
     * ������������
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
            // ��ȡ�������Ԫ���� :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // ͨ��ResultSetMetaData��ȡ������е�����
            int columnCount = rsmd.getColumnCount();
            // �������϶���
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = clazz.newInstance();
                // ��������һ�������е�ÿһ����:��t����ָ�������Ը�ֵ
                for (int i = 0; i < columnCount; i++) {
                    // ��ȡ��ֵ
                    Object columValue = rs.getObject(i + 1);

                    // ��ȡÿ���е�����
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // ��t����ָ����columnName���ԣ���ֵΪcolumValue��ͨ������
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
     * ���ڲ�ѯ����SQL���ķ���������һ��ȷ�����͵�ֵ�������Ǽ��ϻ��Ƕ���
     * ��������SQL��䣺select count(ѧ��) from ѧ����Ϣ��;
     * ��Ӧ�������������ѯ
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
