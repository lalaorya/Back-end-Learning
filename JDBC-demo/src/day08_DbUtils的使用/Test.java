package day08_DbUtils��ʹ��;

/**
 * @program: JDBCѧϰԴ��
 * @description:
 * @author: HHJ
 * @created: 2020/09/26 11:17
 */
public class Test {
    public static void main(String[] args) throws Exception{

//        Connection con=null;
//        try{
//            con = JDBCUtils.getConnection1();
//            String sql="insert into ѧ����Ϣ��(ѧ��,����,�༶) values (?,?,?)";
//            System.out.println(QueryRunner_Demo.query_update(con,sql,"00008","��ʮ","18�������2��"));
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            JDBCUtil.closeResource(con,null);
//        }


        //���Բ�ѯһ����¼
        QueryRunner_Demo.query_instance();

        //���Բ�ѯ������¼
        QueryRunner_Demo.query_instanceList();

        //���Բ�ѯ����ֵ
        QueryRunner_Demo.query_speVal();


    }
}
