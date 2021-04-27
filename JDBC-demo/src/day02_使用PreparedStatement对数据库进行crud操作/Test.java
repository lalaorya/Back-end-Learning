package day02_使用PreparedStatement对数据库进行crud操作;

import day01_如何获取数据库连接.Student;

import java.util.List;

/**
 * 测试类
 * hhj
 */
public class Test {
    public static void main(String args[]){
        PreparedStatementDemo pst=new PreparedStatementDemo();
        //pst.InsertMethod();
        //测试通用方法
        //pst.updateMethod("update 学生信息表 set 年龄=? where 姓名=?",18,"张三");
        //pst.stuQuery();
        // 执行结果：Student{stu='06001', name='张三', class1='null', major='null', age=33, birthday=null}
        //测试单表查询通用方法
        //pst.queryForStudent("select 学号 stu,姓名 name,班级 class1 from 学生信息表 where 学号=?","00001");//注意，sql语句要取别名，别名和类的属性名称相同
        //测试查询通用方法
        List<Student> list=pst.queryMethod(Student.class,"select 学号 stu,姓名 name,班级 class1 from 学生信息表 where 班级 = ?","18软工");
        list.forEach(System.out::println);
    }
}
