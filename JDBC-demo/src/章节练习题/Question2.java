package �½���ϰ��;

import day02_ʹ��PreparedStatement�����ݿ����crud����.PreparedStatementDemo;

import java.util.List;
import java.util.Scanner;
/**
 * @program: JDBCѧϰԴ��
 * @description: �½�ϰ��2������鿴�ʼ�.md
 * @author: HHJ
 * @created: 2020/09/10 20:17
 */
public class Question2 {
    public static void addStudent() {
        PreparedStatementDemo psd = new PreparedStatementDemo();
        while (true) {
            System.out.println("�������վ�:");
            Scanner sc = new Scanner(System.in);
            System.out.print("�ļ�or����:");
            int type = sc.nextInt();
            System.out.print("���֤��:");
            String idcard = sc.next();
            System.out.print("׼��֤��:");
            String examcrad = sc.next();
            System.out.print("ѧ������:");
            String name = sc.next();
            System.out.print("����:");
            String location = sc.next();
            System.out.print("�ɼ�:");
            int grade = sc.nextInt();
            String sql = "insert into cetexamstudent (Type,IDCard,ExamCard,StudentName,Location,Grade) values (?,?,?,?,?,?)";
            if (psd.updateMethod(sql,type, idcard,examcrad,name,location,grade) > 0)
                System.out.println("��Ϣ¼��ɹ�");
            else
                System.out.println("¼��ʧ��");
            System.out.println("------------------");
        }
    }
    public static void querybyIdOrExamCard(){
        System.out.println("��������Ҫ������ͣ�\n a:׼��֤�� \n b:���֤��");
        Scanner sc=new Scanner(System.in);
        String selection=sc.next();
        PreparedStatementDemo pst=new PreparedStatementDemo();
        if("a".equalsIgnoreCase(selection)){
            System.out.println("������׼��֤�ţ�");
            String examCard=sc.next();
            String sql="select FlowID flowID,Type type,ExamCard examCard,StudentName studentName,Location location,Grade grade from cetexamstudent where ExamCard=?";
            List<CETStudent> list=pst.queryMethod(CETStudent.class,sql,examCard);
            if(list.size()==0)
                System.out.println("���޴���");
            else
                list.forEach(System.out::println);
        }
        else if("b".equalsIgnoreCase(selection)){
            System.out.println("���������֤�ţ�");
            String IDCard=sc.next();
            String sql="select Type type,ExamCard examCard,StudentName studentName,Location location,Grade grade from CETexamstudent where IDCard=?";
            List<CETStudent> list=pst.queryMethod(CETStudent.class,sql,IDCard);
//            if(list.size()==0)
//                System.out.println("���޴���");
//            else
                list.forEach(System.out::println);
        }
        else{
            System.out.println("�������");
        }
    }
}
