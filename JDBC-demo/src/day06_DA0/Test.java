package day06_DA0;

import day07_���ݿ����ӳ�.JDBCUtils;
import day01_��λ�ȡ���ݿ�����.Student;

import java.sql.Connection;

/**
 * @program: JDBCѧϰԴ��
 * @description: StudentDAOImpl�Ĳ�����
 * @author: HHJ
 * @created: 2020/09/17 15:41
 */
public class Test {
    public static void main(String[] args) throws Exception{
        StudentDAOImpl sdi=new StudentDAOImpl();
        Connection con= JDBCUtils.getConnection3();
//        sdi.insert(con,new Student("00001","����","18�����1��","�������ѧ�뼼��",20,new Date(23423455L)));
//        sdi.insert(con,new Student("00002","����","18�����2��","�������ѧ�뼼��",22,new Date(24425355L)));
//        sdi.insert(con,new Student("00003","����","18��1��","�������",22,new Date(31123455L)));
//        sdi.insert(con,new Student("00004","����","18����1��","��������",23,new Date(33423455L)));
//        sdi.insert(con,new Student("00005","����","18�����1��","�������ѧ�뼼��",20,new Date(29423455L)));
        Student s=sdi.getStudentByID(con,"00001");
        System.out.println(sdi.getCount(con).toString());
        System.out.println(s);

    }
}
