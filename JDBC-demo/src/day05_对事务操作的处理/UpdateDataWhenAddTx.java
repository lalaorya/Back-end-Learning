package day05_����������Ĵ���;

import day01_��λ�ȡ���ݿ�����.JDBCUtil;
import day01_��λ�ȡ���ݿ�����.Student;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBCѧϰԴ��
 * @description: �����ݿ���½���������ʱ�Ĳ�������
 * �ڲ�ѯ������м����������Ҫ��ô����
 *      * 1. ����SQL��乲��һ�����ӣ���Ϊ�����ſ���һ���ύ���߻ع���
 *      *      ����֮ǰд��update����query��������ÿ��sql����½�һ�����ӣ����Ҫ����Щ������������
 *      * 2. ��ִ��SQL���ǰҪ���ò��Զ��ύ
 *      * 3. ִ�н���Ҫ�ǵ��ύ
 *      * 4. Ҫ�ǳ����쳣Ҫ���лع�
 * @author: HHJ
 * @created: 2020/09/15 15:24
 */
public class UpdateDataWhenAddTx {
    Connection con = null;

    /**
     * ����Ĳ�����-���²���
     */
    public void updateWithTX(){
        try {
            con = JDBCUtil.getConnection();
            //���ò��Զ��ύ
            con.setAutoCommit(false);
            //���������ͬʱ��������
            updateMethod(con, "insert into ѧ����Ϣ��(ѧ��,����,�༶,רҵ)values(?,?,?,?)" ,"00006","����","18����ѧ1��","����ѧ");
            //ģ�������쳣
            //System.out.println(10/0);
            //��������
            updateMethod(con, "insert into ѧ����Ϣ��(ѧ��,����,�༶,רҵ)values(?,?,?,?)","00007","����","18���1��","���");

            System.out.println("������Գɹ�");
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            //�ߵ�����˵�������ˣ�Ҫ�ع�����
            try {
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }finally {
            //�ر�����
            //�������������Զ��ύ��(�ر���������ݿ����ӳص�ʱ���Ժ�ὲ
            try{
                con.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
            JDBCUtil.closeResource(con,null);
        }
    }

    /**
     * ����Ĳ���-��ѯ����
     * �����ˣ��������DAO��
     */
    public void queryTxTest(){

    }

    /**
     * �漰����ĸ��²���������һ���������ڲ�ѯ��֮���ܹر����ӡ���Ϊ�ر����ӻ��Զ��ύ���
     * ���Զ���SQL���Ҫ��ͬһ������
     * @param con
     * @param sql
     * @param args
     */
    public void updateMethod(Connection con,String sql, Object ... args){//�������Ҫʹ��ͬһ�����ӣ���˲��ܹر�����
        PreparedStatement ps=null;
        try{
            //Ԥ����sql���
            ps=con.prepareStatement(sql);
            //���ռλ��
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //ִ��sql
            ps.execute();//���ظ��µļ�¼����>0˵��ִ�гɹ�

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(null,ps);
        }
    }

    /**
     * �漰����Ĳ�ѯ����
     * ����Ĳ�ѯ������Ҫ�����ݽ��лع�ɶ�ģ���Ϊ��������û�иĶ�����
     * �漰����Ĳ�ѯ��Ҫ���Ǹ��뼶������⣬����鿴�ʼ�
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryMethod(Connection con,Class<T> clazz, String sql, Object ... args){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //Ԥ����SQL���
            ps = con.prepareStatement(sql);
            //���ռλ��
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //ִ��sql�����ؽ����
            rs = ps.executeQuery();
            //��ȡ�������Ԫ���ݼ�
            //ResultsetMetaData �����ڻ�ȡ���� ResultSet �������е����ͺ�������Ϣ��
            ResultSetMetaData rsmd = ps.getMetaData();

            //��ȡ����
            int columnCount = rsmd.getColumnCount();
            //��������
            ArrayList<T> list=new ArrayList<>();
            while (rs.next()) {//ÿ��
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {//ÿ�е���
                    //��ȡÿһ�е�����
                    Object columnVal = rs.getObject(i + 1);
                    //�����ݸ�ֵ��ORM����
                    //1. ��ȡ����
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //2. ʹ�÷���Ϊ����Ķ�Ӧ�ֶθ�ֵ��
                    Field field = Student.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                list.add(t);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(null,ps,rs);
        }
        return null;
    }
}
