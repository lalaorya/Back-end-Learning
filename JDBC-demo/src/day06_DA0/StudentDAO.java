package day06_DA0;

import day01_如何获取数据库连接.Student;

import java.sql.Connection;
import java.util.List;
/**
 * 此接口用于规范针对学生信息表的常用操作
 * 所有要对表进行操作的类都要实现该接口
 */
public interface StudentDAO {
    /**
     * 将学生对象S添加到数据库
     */
    public void insert(Connection con, Student s);

    /**
     * 根据学号删除记录
     */
    public void deleteByID(Connection con,String stuID);

    /**
     * 使用新的s对象，去修改数据表中指定的记录
     */
    public void update(Connection con,Student s);

    /**
     * 根据学号返回指定学生记录
     */
    public Student getStudentByID(Connection con,String stuID);

    /**
     * 查询学生信息表中的所有记录
     */
    public List <Student> getAll(Connection con);

    /**
     * 返回数据表的记录数
     */
    public Long getCount(Connection con);
}
