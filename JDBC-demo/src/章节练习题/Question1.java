package �½���ϰ��;

import day02_ʹ��PreparedStatement�����ݿ����crud����.PreparedStatementDemo;

import java.util.Scanner;

/**
 * @program: JDBCѧϰԴ��
 * @description: �½�ϰ��1
 * @author: HHJ
 * @created: 2020/09/10 19:45
 */
// �ӿ���̨�����ݿ�ı�ѧ���ɼ�����������

public class Question1 {
    public static void insertTOstu(){
        while(true) {
            System.out.println("�������վ�:");
            Scanner sc = new Scanner(System.in);
            System.out.print("ѧ��:");
            String stu = sc.nextLine();
            System.out.print("����:");
            String name = sc.nextLine();
            System.out.print("����:");
            double cpa = sc.nextDouble();
            String sql = "insert into ѧ���ɼ��� values (?,?,?)";
            PreparedStatementDemo psd = new PreparedStatementDemo();
            if(psd.updateMethod(sql, stu, name, cpa)>0)
                System.out.println("��ӳɹ�");
            else{
                System.out.println("fail");
            }

        }



    }
}
