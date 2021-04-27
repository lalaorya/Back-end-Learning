package day08_DbUtils��ʹ��.����update;

import day07_���ݿ����ӳ�.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: JDBCѧϰԴ��
 * @description: ʹ��queryrunnerʵ�ָ��²���
 * @author: HHJ
 * @created: 2020/10/05 16:01
 */
public class testUpdate {
    //ÿ��sql������һ������
    public static void updatedemo1(String sql,Object ... args)throws SQLException {
        //��ȡqueryRunner��
        QueryRunner qr=new QueryRunner(JDBCUtils.getC3p0DataSource());
        qr.update(sql,args);
        //��������µĸ��²���:
        //# �����queryrunnerʹ�ÿղμ��ɣ���Ҫʹ�����ݿ����ӳ���Ϊ������
        //��Ҫ���ӵ�ʱ������ӳ���ȡ����һ������



    }

    //��������µĸ��²���:
    //�����queryrunnerʹ�ÿղμ��ɣ���Ҫʹ�����ݿ����ӳ���Ϊ������
    //��Ҫ���ӵ�ʱ������ӳ���ȡ����һ������
    public static void updatedemo2(String sql,Object[] args1,String sql2,Object[] args2){
        QueryRunner qr=null;
        Connection con=null;
        try{
            qr=new QueryRunner();
            con=JDBCUtils.getConnection3();
            qr.update(con,sql,args1);
            qr.update(con,sql2,args2);

            System.out.println("�����ύ�ɹ�");
            DbUtils.commitAndCloseQuietly(con);
        }catch (Exception e){
            DbUtils.rollbackAndCloseQuietly(con);
            e.printStackTrace();
        }

    }

}
