package com.hhj.service.impl;

import com.hhj.domain.Student;
import com.hhj.mapper.StudentMapper;
import com.hhj.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student getStudentById(int id) {
        Student student = studentMapper.getStudentById(id);
        return student;
    }

    @Override
    public int insertStudent(Student student) {
        int student1 = studentMapper.insertStudent(student);
        return student1;
    }


}
