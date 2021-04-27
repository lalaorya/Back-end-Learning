package day01_如何获取数据库连接;

import java.util.Date;

/**
 * @program: JDBC学习源码
 * @description: ORM编程思想
 *                 一个数据表对应一个java类，表中一个字段对应java类的属性
 *                 这种思想再JDBC的查询中常用
 * @author: HHJ
 * @created: 2020/09/10 10:38
 */
public class Student {
    private String stu;
    private String name;
    private String class1;
    private String major;
    private int age;
    private Date birthday;
    public Student(){
        super();
    }
    public Student(String stu, String name, String class1, String major, int age, Date birthday) {
        this.stu = stu;
        this.name = name;
        this.class1 = class1;
        this.major = major;
        this.age = age;
        this.birthday = birthday;
    }

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stu='" + stu + '\'' +
                ", name='" + name + '\'' +
                ", class1='" + class1 + '\'' +
                ", major='" + major + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
