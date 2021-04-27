package com.hhj.service;

import org.junit.Test;

//测试类名必须遵循规范：原类名后增加Test后缀
public class StudentServiceImplTest {
//    方法名也必须遵循规范：方法名前面加test前缀
//    不然使用mvn test命令编译运行时不会运行测试类

//    idea会报错，因为没有添加Junit jar包，但是在pom.xml添加依赖后可以使用mvn test进行单元测试
    @Test
    public void testPrintStudent(){
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.printStudent();
    }
}
