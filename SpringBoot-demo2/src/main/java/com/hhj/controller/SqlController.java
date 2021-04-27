package com.hhj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhj.domain.Student;
import com.hhj.domain.User;
import com.hhj.service.StudentService;
import com.hhj.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SqlController {

    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;

    @GetMapping("/sql/{id}")
    @ApiOperation("根据id查询用户")
    public Student getUserById(@PathVariable("id") int id){
        System.out.println(id);
        Student studentById = studentService.getStudentById(id);
        return studentById;
    }

    @GetMapping("/sql/insert/{stu}/{name}")
    public int insertStudent(@PathVariable("stu")int stu,@PathVariable("name") String name){
        Student student = new Student();
        student.setStu(stu);
        student.setName(name);
        int student1 = studentService.insertStudent(student);
        return student1;
    }

    @GetMapping("sql/count")
    public int totalNum(){
        Integer integer = userService.selectCount(null);
        return integer;
    }

    @GetMapping("sql/userList")
    public List<User> findAll(){
        List<User> users = userService.selectList(null);
        return users;
    }

    @GetMapping("/sql/page/{id}")
    public Page<User> selectUserPage(@PathVariable("id") int id){
        //构造分页参数
        // 第几页 每页几个
        Page<User> page = new Page<User>(id, 2);
        userService.selectUserPage(page);
        return page;
    }
}
