package day08_DbUtils的使用.更新update;

import java.sql.SQLException;

/**
 * @program: JDBC学习源码
 * @description:
 * @author: HHJ
 * @created: 2020/10/05 16:11
 */
public class text {
    public  static void main(String args[]) throws SQLException {
        String sql="insert into 学生信息表(学号,姓名,班级) values (?,?,?)";
        Object[] args1=new Object[]{"00010","郭靖","18计一"};
        Object[] args2=new Object[]{"00011","郭襄","18计二"};
//        testUpdate.updatedemo(sql,"00010","郭靖","18计一");
        testUpdate.updatedemo2(sql,args1,sql,args2);
    }
}
