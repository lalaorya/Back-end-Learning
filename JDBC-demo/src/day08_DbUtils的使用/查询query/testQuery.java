package day08_DbUtils的使用.查询query;

import day01_如何获取数据库连接.Student;
import day07_数据库连接池.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @program: JDBC学习源码
 * @description:
 *      实现查询的关键接口是ResultSetHandler
 *
 *      八大实现类：
 *              ArrayHandler//结果集的第一行数据保存在Object数组中
 *              ArrayListHandler
 *
 *              BeanHandler//保存在类中，注意：类中属性名要和表的列名一致
 *              BeanListHandler
 *
 *              ColumnListHandler//获取某一列的数据，保存在List中
 *
 *              MapHandler//保持在map中。key：列名。value：该列的值
 *              MapListHandler
 *
 *              KeyedHandler
 *
 *              ScalarHandler//一行一列
 *
 *
 *
 * @author: HHJ
 * @created: 2020/10/05 17:03
 */
public class testQuery {
    static QueryRunner qr=new QueryRunner(JDBCUtils.getC3p0DataSource());
    public static void query_1() throws SQLException {
        String sql="select * from 学生信息表 where 学号=?";

        //QueryRunner qr=new QueryRunner(JDBCUtils.getC3p0DataSource());
        Object[] objects=qr.query(sql,new ArrayHandler(),"00001");
        System.out.println(Arrays.toString(objects));
    }

    public static void query_2() throws SQLException{
        String sql="select * from 学生信息表";
        List<Object[]> list=qr.query(sql,new ArrayListHandler());
        for(Object[] objects:list){
            System.out.println(Arrays.toString(objects));
        }
    }

    public static void query_3() throws SQLException{
        String sql="select 学号 stu,姓名 name from 学生信息表";//别名
        Student s=qr.query(sql,new BeanHandler<Student>(Student.class));

        System.out.println(s.toString());
    }

    public static void query_4() throws SQLException{
        String sql="select 学号 stu,姓名 name from 学生信息表";//别名
        List<Student> list=qr.query(sql,new BeanListHandler<Student>(Student.class));
        for(Student s:list){
            System.out.println(s.toString());
        }

    }

    public static void query_5() throws SQLException{
        String sql="select 姓名 from 学生信息表";
        List<Object> list=qr.query(sql,new ColumnListHandler<>("姓名"));

        System.out.println(list);

    }

    public static void query_6() throws SQLException{
        String sql="select * from 学生信息表";
        Map<String,Object> map =qr.query(sql,new MapHandler());

        System.out.println(map);

    }

    public static void query_7() throws SQLException{
        String sql="select * from 学生信息表";
        List<Map<String,Object>> list =qr.query(sql,new MapListHandler());

        for(Map<String,Object>  map:list){
            System.out.println(map);
        }

    }

    public static void query_8() throws SQLException{
        String sql="select * from 学生信息表";
        Map<Object,Map<String,Object>> list =qr.query(sql,new KeyedHandler<>());

        System.out.println(list);
    }

    public static void query_9() throws SQLException{
        String sql="select count(*) from 学生信息表";
        Object object =qr.query(sql,new ScalarHandler<>(1));

        System.out.println(object);

    }









}
