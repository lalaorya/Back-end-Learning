package 章节练习题;

import day02_使用PreparedStatement对数据库进行crud操作.PreparedStatementDemo;

import java.util.List;
import java.util.Scanner;
/**
 * @program: JDBC学习源码
 * @description: 章节习题2，具体查看笔记.md
 * @author: HHJ
 * @created: 2020/09/10 20:17
 */
public class Question2 {
    public static void addStudent() {
        PreparedStatementDemo psd = new PreparedStatementDemo();
        while (true) {
            System.out.println("请输入收据:");
            Scanner sc = new Scanner(System.in);
            System.out.print("四级or六级:");
            int type = sc.nextInt();
            System.out.print("身份证号:");
            String idcard = sc.next();
            System.out.print("准考证号:");
            String examcrad = sc.next();
            System.out.print("学生姓名:");
            String name = sc.next();
            System.out.print("区域:");
            String location = sc.next();
            System.out.print("成绩:");
            int grade = sc.nextInt();
            String sql = "insert into cetexamstudent (Type,IDCard,ExamCard,StudentName,Location,Grade) values (?,?,?,?,?,?)";
            if (psd.updateMethod(sql,type, idcard,examcrad,name,location,grade) > 0)
                System.out.println("信息录入成功");
            else
                System.out.println("录入失败");
            System.out.println("------------------");
        }
    }
    public static void querybyIdOrExamCard(){
        System.out.println("请输入你要查的类型：\n a:准考证号 \n b:身份证号");
        Scanner sc=new Scanner(System.in);
        String selection=sc.next();
        PreparedStatementDemo pst=new PreparedStatementDemo();
        if("a".equalsIgnoreCase(selection)){
            System.out.println("请输入准考证号：");
            String examCard=sc.next();
            String sql="select FlowID flowID,Type type,ExamCard examCard,StudentName studentName,Location location,Grade grade from cetexamstudent where ExamCard=?";
            List<CETStudent> list=pst.queryMethod(CETStudent.class,sql,examCard);
            if(list.size()==0)
                System.out.println("查无此人");
            else
                list.forEach(System.out::println);
        }
        else if("b".equalsIgnoreCase(selection)){
            System.out.println("请输入身份证号：");
            String IDCard=sc.next();
            String sql="select Type type,ExamCard examCard,StudentName studentName,Location location,Grade grade from CETexamstudent where IDCard=?";
            List<CETStudent> list=pst.queryMethod(CETStudent.class,sql,IDCard);
//            if(list.size()==0)
//                System.out.println("查无此人");
//            else
                list.forEach(System.out::println);
        }
        else{
            System.out.println("输入错误");
        }
    }
}
