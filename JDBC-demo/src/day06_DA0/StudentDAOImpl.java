package day06_DA0;

import day01_如何获取数据库连接.Student;

import java.sql.Connection;
import java.util.List;

/**
 * @program: JDBC学习源码
 * @description: StudentDAO的实现类
 * @author: HHJ
 * @created: 2020/09/17 15:16
 */
public class StudentDAOImpl extends BaseDAO implements StudentDAO{
    @Override
    public void insert(Connection con, Student s) {
        String sql="insert into 学生信息表 values (?,?,?,?,?,?)";
        update(con,sql,s.getStu(),s.getName(),s.getClass1(),s.getMajor(),s.getAge(),s.getBirthday());
        System.out.println("添加成功");
    }

    @Override
    public void deleteByID(Connection con, String stuID) {
        String sql="delete from 学生信息表 where 学号=?";
        update(con,sql,stuID);
        System.out.println("删除成功");
    }

    @Override
    public void update(Connection con, Student s) {
        String sql="update 学生信息表 set 学号=?,姓名=?,班级=?,专业=?,年龄=?,出生日期=? where 学号=?";
        update(con,sql,s.getStu(),s.getName(),s.getClass1(),s.getMajor(),s.getAge(),s.getBirthday(),s.getStu());
        System.out.println("更新成功");
    }

    @Override
    public Student getStudentByID(Connection con, String stuID) {
        String sql="select 学号 stu,姓名 name,班级 class1,专业 major,年龄 age,出生日期 birthday from 学生信息表 where 学号=?";
        return query(con,Student.class,sql,stuID);
    }

    @Override
    public List<Student> getAll(Connection con) {
        String sql="select 学号 stu,姓名 name,班级 class1,专业 major,年龄 age,出生日期 birthday from 学生信息表";
        return queryForList(con,Student.class,sql);
    }

    @Override
    public Long getCount(Connection con) {
        String sql = "select count(*) from 学生信息表";
        return queryForValue(con, sql);
    }
}
