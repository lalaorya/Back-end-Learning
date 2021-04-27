package day08_DbUtils��ʹ��.��ѯquery;

import day01_��λ�ȡ���ݿ�����.Student;
import day07_���ݿ����ӳ�.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @program: JDBCѧϰԴ��
 * @description:
 *      ʵ�ֲ�ѯ�Ĺؼ��ӿ���ResultSetHandler
 *
 *      �˴�ʵ���ࣺ
 *              ArrayHandler//������ĵ�һ�����ݱ�����Object������
 *              ArrayListHandler
 *
 *              BeanHandler//���������У�ע�⣺����������Ҫ�ͱ������һ��
 *              BeanListHandler
 *
 *              ColumnListHandler//��ȡĳһ�е����ݣ�������List��
 *
 *              MapHandler//������map�С�key��������value�����е�ֵ
 *              MapListHandler
 *
 *              KeyedHandler
 *
 *              ScalarHandler//һ��һ��
 *
 *
 *
 * @author: HHJ
 * @created: 2020/10/05 17:03
 */
public class testQuery {
    static QueryRunner qr=new QueryRunner(JDBCUtils.getC3p0DataSource());
    public static void query_1() throws SQLException {
        String sql="select * from ѧ����Ϣ�� where ѧ��=?";

        //QueryRunner qr=new QueryRunner(JDBCUtils.getC3p0DataSource());
        Object[] objects=qr.query(sql,new ArrayHandler(),"00001");
        System.out.println(Arrays.toString(objects));
    }

    public static void query_2() throws SQLException{
        String sql="select * from ѧ����Ϣ��";
        List<Object[]> list=qr.query(sql,new ArrayListHandler());
        for(Object[] objects:list){
            System.out.println(Arrays.toString(objects));
        }
    }

    public static void query_3() throws SQLException{
        String sql="select ѧ�� stu,���� name from ѧ����Ϣ��";//����
        Student s=qr.query(sql,new BeanHandler<Student>(Student.class));

        System.out.println(s.toString());
    }

    public static void query_4() throws SQLException{
        String sql="select ѧ�� stu,���� name from ѧ����Ϣ��";//����
        List<Student> list=qr.query(sql,new BeanListHandler<Student>(Student.class));
        for(Student s:list){
            System.out.println(s.toString());
        }

    }

    public static void query_5() throws SQLException{
        String sql="select ���� from ѧ����Ϣ��";
        List<Object> list=qr.query(sql,new ColumnListHandler<>("����"));

        System.out.println(list);

    }

    public static void query_6() throws SQLException{
        String sql="select * from ѧ����Ϣ��";
        Map<String,Object> map =qr.query(sql,new MapHandler());

        System.out.println(map);

    }

    public static void query_7() throws SQLException{
        String sql="select * from ѧ����Ϣ��";
        List<Map<String,Object>> list =qr.query(sql,new MapListHandler());

        for(Map<String,Object>  map:list){
            System.out.println(map);
        }

    }

    public static void query_8() throws SQLException{
        String sql="select * from ѧ����Ϣ��";
        Map<Object,Map<String,Object>> list =qr.query(sql,new KeyedHandler<>());

        System.out.println(list);
    }

    public static void query_9() throws SQLException{
        String sql="select count(*) from ѧ����Ϣ��";
        Object object =qr.query(sql,new ScalarHandler<>(1));

        System.out.println(object);

    }









}
