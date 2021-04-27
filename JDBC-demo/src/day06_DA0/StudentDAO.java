package day06_DA0;

import day01_��λ�ȡ���ݿ�����.Student;

import java.sql.Connection;
import java.util.List;
/**
 * �˽ӿ����ڹ淶���ѧ����Ϣ��ĳ��ò���
 * ����Ҫ�Ա���в������඼Ҫʵ�ָýӿ�
 */
public interface StudentDAO {
    /**
     * ��ѧ������S��ӵ����ݿ�
     */
    public void insert(Connection con, Student s);

    /**
     * ����ѧ��ɾ����¼
     */
    public void deleteByID(Connection con,String stuID);

    /**
     * ʹ���µ�s����ȥ�޸����ݱ���ָ���ļ�¼
     */
    public void update(Connection con,Student s);

    /**
     * ����ѧ�ŷ���ָ��ѧ����¼
     */
    public Student getStudentByID(Connection con,String stuID);

    /**
     * ��ѯѧ����Ϣ���е����м�¼
     */
    public List <Student> getAll(Connection con);

    /**
     * �������ݱ�ļ�¼��
     */
    public Long getCount(Connection con);
}
