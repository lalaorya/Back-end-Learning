package day08_DbUtils��ʹ��;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import day07_���ݿ����ӳ�.JDBCUtils;
import day01_��λ�ȡ���ݿ�����.JDBCUtil;
import day01_��λ�ȡ���ݿ�����.Student;

import java.sql.Connection;
import java.util.List;

/**
 * @program: JDBCѧϰԴ��
 * @description: DbUtiil�������ڲ�ѯ���������ݱ�ķ�װ��
 * @author: HHJ
 * @created: 2020/09/26 11:03
 */
public class QueryRunner_Demo {

    //ͨ��queryrunnerʵ�ָ��²���
    public static int query_update(Connection con, String sql, Object... args) throws Exception {
        //queryRunner��DbUtiil��һ���࣬��query��update����
        QueryRunner qr = new QueryRunner();

        return qr.update(con, sql, args);


    }

    /**
     * ͨ��queryrunnerʵ�ֲ�ѯ����
     */
    public static void query_instance() {
        Connection con = null;
        try {
            QueryRunner qr = new QueryRunner();

            con = JDBCUtils.getConnection1();

            //��ѯ���������һ��������
            //Ҳ���Բ�ѯ������¼������List��map��array���棬��Ҫʹ�ò�ͬ��ʵ����
            BeanHandler<Student> handler = new BeanHandler<>(Student.class);
            String sql = "select ѧ�� stu,���� name,�༶ class1 from ѧ����Ϣ�� where ѧ��=?";
            Student s = qr.query(con, sql, handler, "00001");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(con, null);
        }


    }

    /**
     * ͨ��queryrunnerʵ�ֲ�ѯ����
     * ������¼
     */
    public static void query_instanceList() {
        Connection con = null;
        try {
            QueryRunner qr = new QueryRunner();

            con = JDBCUtils.getConnection1();

            //��ѯ���������һ����������
            BeanListHandler<Student> handler = new BeanListHandler<>(Student.class);
            String sql = "select ѧ�� stu,���� name,�༶ class1,���� age from ѧ����Ϣ�� where ����>?";
            List<Student> list = qr.query(con, sql, handler, 18);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(con, null);
        }


    }

    /**
     * �����԰ѽ��������������ȣ�������ʹ�ò��ǵ�ResultSetHandlerʵ����
     */

    /**
     * ͨ��queryrunnerʵ�ֲ�ѯ����ֵ
     * ������¼
     */
    public static void query_speVal() {
        Connection con = null;
        try {
            QueryRunner qr = new QueryRunner();

            con = JDBCUtils.getConnection1();

            //����ֵ��ѯʹ��scalarhandlerʵ����
            ScalarHandler handler=new ScalarHandler();
            String sql = "select count(*) from ѧ����Ϣ��";
            long count =(long) qr.query(con, sql, handler);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(con, null,null);
        }


    }


    /**
     * Ҫ����������������Լ�ʹ������������дʵ��ResultSetHandler�ӿ�
     *
     * ���̴���������
     *     ResultSetHandler<Student> handler=new ResultSetHandler<Student>() {
     *         @Override
     *         //��дhandle������
     *         //���ķ���ֵ����query�����ķ���ֵ
     *         //�����ӿڵľ���ʵ����Ҳ���������
     *         public Student handle(ResultSet resultSet) throws SQLException {
     *             return null;
     *         }
     *
     *         qr.query(..,handle,...)
     *     }
     */


}