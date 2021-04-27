package com.hhj.mapper;

import com.hhj.domain.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;

@Mapper
// 只有带有@Mapper注解的接口才会使用sqlsessionfatory创建对应的mapper调用方法
// 而且该mapper才会被注入到IOC容器中
public interface StudentMapper {

    public Student getStudentById(int id);

    @Insert("insert into student(stu,name) values (#{stu},#{name})")
    public int insertStudent(Student student);
}
