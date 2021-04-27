package day04_��������;

import day01_��λ�ȡ���ݿ�����.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @program: JDBCѧϰԴ��
 * @description:JDBC����������
 *              ��Ϊdelete��update��������;���һ�β���������¼������
 *              ����������������ָ������������
 *              ������Ҫ�õ�����������
 *                  addBatch(String)    �����Ҫ���������sql���
 *                  executeBatch()  ִ�л����е�SQL���
 *                  clearBatch  ��ջ����е�����
 * @author: HHJ
 * @created: 2020/09/12 13:07
 */

/**
 * �������������Ȳ�ִ��sql��䣬�ܵ�һ��������ִ�У��������Ч��
 */
public class BatchMethod {
    public void batchMethod1() throws Exception{
        Connection con= JDBCUtil.getConnection();
        String sql="insert into ѧ���ɼ���(ѧ��,����) values (?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        for(int i=1;i<50000;i++){
            ps.setString(1,Integer.parseInt("6152")+i+"");
            ps.setString(2,"��ʱ_"+i);

            ps.addBatch();
            if(i%500==0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        JDBCUtil.closeResource(con,ps);
    }

    /**
     * ����1���Ż���
     * ��Ϊpreparedstatement��Ĭ��ִ��һ��sql����ύһ�ν������˷���1Ҫ�ύ10000��
     * ������ǿ������ò��Զ��ύ���ȵ�sqlȫ��ִ�������ύ
     * @throws Exception
     */
    public void batchMethod2() throws Exception{
        Connection con= JDBCUtil.getConnection();
        //���ò��Զ��ύִ������
        con.setAutoCommit(false);
        String sql="insert into ѧ���ɼ���(ѧ��,����) values (?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        for(int i=1;i<5000;i++){
            ps.setString(1,Integer.parseInt("00001")+i+"");
            ps.setString(2,"��ʱ_"+i);

            ps.addBatch();
            if(i%500==0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        //ȫ��ִ�������ύ
        con.commit();
        JDBCUtil.closeResource(con,ps);
    }
}
