package day06_DA0;

import day07_数据库连接池.JDBCUtils;
import day01_如何获取数据库连接.Student;

import java.sql.Connection;

/**
 * @program: JDBC学习源码
 * @description: StudentDAOImpl的测试类
 * @author: HHJ
 * @created: 2020/09/17 15:41
 */
public class Test {
    public static void main(String[] args) throws Exception{
        StudentDAOImpl sdi=new StudentDAOImpl();
        Connection con= JDBCUtils.getConnection3();
//        sdi.insert(con,new Student("00001","张三","18计算机1班","计算机科学与技术",20,new Date(23423455L)));
//        sdi.insert(con,new Student("00002","李四","18计算机2班","计算机科学与技术",22,new Date(24425355L)));
//        sdi.insert(con,new Student("00003","王五","18软工1班","软件工程",22,new Date(31123455L)));
//        sdi.insert(con,new Student("00004","赵六","18电商1班","电子商务",23,new Date(33423455L)));
//        sdi.insert(con,new Student("00005","邓七","18计算机1班","计算机科学与技术",20,new Date(29423455L)));
        Student s=sdi.getStudentByID(con,"00001");
        System.out.println(sdi.getCount(con).toString());
        System.out.println(s);

    }
}
