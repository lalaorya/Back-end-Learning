package day02_ʹ��PreparedStatement�����ݿ����crud����;

import day01_��λ�ȡ���ݿ�����.Student;

import java.util.List;

/**
 * ������
 * hhj
 */
public class Test {
    public static void main(String args[]){
        PreparedStatementDemo pst=new PreparedStatementDemo();
        //pst.InsertMethod();
        //����ͨ�÷���
        //pst.updateMethod("update ѧ����Ϣ�� set ����=? where ����=?",18,"����");
        //pst.stuQuery();
        // ִ�н����Student{stu='06001', name='����', class1='null', major='null', age=33, birthday=null}
        //���Ե����ѯͨ�÷���
        //pst.queryForStudent("select ѧ�� stu,���� name,�༶ class1 from ѧ����Ϣ�� where ѧ��=?","00001");//ע�⣬sql���Ҫȡ�������������������������ͬ
        //���Բ�ѯͨ�÷���
        List<Student> list=pst.queryMethod(Student.class,"select ѧ�� stu,���� name,�༶ class1 from ѧ����Ϣ�� where �༶ = ?","18��");
        list.forEach(System.out::println);
    }
}
