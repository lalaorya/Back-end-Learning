package day06_DA0;

import day01_��λ�ȡ���ݿ�����.Student;

import java.sql.Connection;
import java.util.List;

/**
 * @program: JDBCѧϰԴ��
 * @description: StudentDAO��ʵ����
 * @author: HHJ
 * @created: 2020/09/17 15:16
 */
public class StudentDAOImpl extends BaseDAO implements StudentDAO{
    @Override
    public void insert(Connection con, Student s) {
        String sql="insert into ѧ����Ϣ�� values (?,?,?,?,?,?)";
        update(con,sql,s.getStu(),s.getName(),s.getClass1(),s.getMajor(),s.getAge(),s.getBirthday());
        System.out.println("��ӳɹ�");
    }

    @Override
    public void deleteByID(Connection con, String stuID) {
        String sql="delete from ѧ����Ϣ�� where ѧ��=?";
        update(con,sql,stuID);
        System.out.println("ɾ���ɹ�");
    }

    @Override
    public void update(Connection con, Student s) {
        String sql="update ѧ����Ϣ�� set ѧ��=?,����=?,�༶=?,רҵ=?,����=?,��������=? where ѧ��=?";
        update(con,sql,s.getStu(),s.getName(),s.getClass1(),s.getMajor(),s.getAge(),s.getBirthday(),s.getStu());
        System.out.println("���³ɹ�");
    }

    @Override
    public Student getStudentByID(Connection con, String stuID) {
        String sql="select ѧ�� stu,���� name,�༶ class1,רҵ major,���� age,�������� birthday from ѧ����Ϣ�� where ѧ��=?";
        return query(con,Student.class,sql,stuID);
    }

    @Override
    public List<Student> getAll(Connection con) {
        String sql="select ѧ�� stu,���� name,�༶ class1,רҵ major,���� age,�������� birthday from ѧ����Ϣ��";
        return queryForList(con,Student.class,sql);
    }

    @Override
    public Long getCount(Connection con) {
        String sql = "select count(*) from ѧ����Ϣ��";
        return queryForValue(con, sql);
    }
}
