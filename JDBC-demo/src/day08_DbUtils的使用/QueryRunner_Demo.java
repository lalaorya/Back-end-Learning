package day08_DbUtils的使用;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import day07_数据库连接池.JDBCUtils;
import day01_如何获取数据库连接.JDBCUtil;
import day01_如何获取数据库连接.Student;

import java.sql.Connection;
import java.util.List;

/**
 * @program: JDBC学习源码
 * @description: DbUtiil包中用于查询、更新数据表的封装类
 * @author: HHJ
 * @created: 2020/09/26 11:03
 */
public class QueryRunner_Demo {

    //通过queryrunner实现更新操作
    public static int query_update(Connection con, String sql, Object... args) throws Exception {
        //queryRunner是DbUtiil的一个类，有query和update方法
        QueryRunner qr = new QueryRunner();

        return qr.update(con, sql, args);


    }

    /**
     * 通过queryrunner实现查询操作
     */
    public static void query_instance() {
        Connection con = null;
        try {
            QueryRunner qr = new QueryRunner();

            con = JDBCUtils.getConnection1();

            //查询结果保存在一个类里面
            //也可以查询多条记录保持在List、map、array里面，需要使用不同的实现类
            BeanHandler<Student> handler = new BeanHandler<>(Student.class);
            String sql = "select 学号 stu,姓名 name,班级 class1 from 学生信息表 where 学号=?";
            Student s = qr.query(con, sql, handler, "00001");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(con, null);
        }


    }

    /**
     * 通过queryrunner实现查询操作
     * 多条记录
     */
    public static void query_instanceList() {
        Connection con = null;
        try {
            QueryRunner qr = new QueryRunner();

            con = JDBCUtils.getConnection1();

            //查询结果保存在一个集合里面
            BeanListHandler<Student> handler = new BeanListHandler<>(Student.class);
            String sql = "select 学号 stu,姓名 name,班级 class1,年龄 age from 学生信息表 where 年龄>?";
            List<Student> list = qr.query(con, sql, handler, 18);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(con, null);
        }


    }

    /**
     * 还可以把结果存在数组里面等，按需求使用不是的ResultSetHandler实现类
     */

    /**
     * 通过queryrunner实现查询特殊值
     * 多条记录
     */
    public static void query_speVal() {
        Connection con = null;
        try {
            QueryRunner qr = new QueryRunner();

            con = JDBCUtils.getConnection1();

            //特殊值查询使用scalarhandler实现类
            ScalarHandler handler=new ScalarHandler();
            String sql = "select count(*) from 学生信息表";
            long count =(long) qr.query(con, sql, handler);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(con, null,null);
        }


    }


    /**
     * 要是有特殊需求可以自己使用匿名对象重写实现ResultSetHandler接口
     *
     * 过程大概是这个：
     *     ResultSetHandler<Student> handler=new ResultSetHandler<Student>() {
     *         @Override
     *         //重写handle方法，
     *         //它的返回值就是query函数的返回值
     *         //其他接口的具体实现类也是这样搞的
     *         public Student handle(ResultSet resultSet) throws SQLException {
     *             return null;
     *         }
     *
     *         qr.query(..,handle,...)
     *     }
     */


}