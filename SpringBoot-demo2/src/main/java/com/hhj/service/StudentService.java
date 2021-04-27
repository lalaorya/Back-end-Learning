package com.hhj.service;

import com.hhj.domain.Student;

public interface StudentService {
    public Student getStudentById(int id);

    public int insertStudent(Student student);
}
