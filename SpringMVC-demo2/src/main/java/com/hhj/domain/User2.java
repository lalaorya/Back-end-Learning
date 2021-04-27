package com.hhj.domain;

public class User2 {
    private String uname;
    private String age;

    @Override
    public String toString() {
        return "User2{" +
                "uname='" + uname + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public User2(String uname, String age) {
        this.uname = uname;
        this.age = age;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
