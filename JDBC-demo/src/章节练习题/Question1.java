package 章节练习题;

import day02_使用PreparedStatement对数据库进行crud操作.PreparedStatementDemo;

import java.util.Scanner;

/**
 * @program: JDBC学习源码
 * @description: 章节习题1
 * @author: HHJ
 * @created: 2020/09/10 19:45
 */
// 从控制台向数据库的表【学生成绩表】插入数据

public class Question1 {
    public static void insertTOstu(){
        while(true) {
            System.out.println("请输入收据:");
            Scanner sc = new Scanner(System.in);
            System.out.print("学号:");
            String stu = sc.nextLine();
            System.out.print("姓名:");
            String name = sc.nextLine();
            System.out.print("绩点:");
            double cpa = sc.nextDouble();
            String sql = "insert into 学生成绩表 values (?,?,?)";
            PreparedStatementDemo psd = new PreparedStatementDemo();
            if(psd.updateMethod(sql, stu, name, cpa)>0)
                System.out.println("添加成功");
            else{
                System.out.println("fail");
            }

        }



    }
}
