package day08_DbUtils��ʹ��.����update;

import java.sql.SQLException;

/**
 * @program: JDBCѧϰԴ��
 * @description:
 * @author: HHJ
 * @created: 2020/10/05 16:11
 */
public class text {
    public  static void main(String args[]) throws SQLException {
        String sql="insert into ѧ����Ϣ��(ѧ��,����,�༶) values (?,?,?)";
        Object[] args1=new Object[]{"00010","����","18��һ"};
        Object[] args2=new Object[]{"00011","����","18�ƶ�"};
//        testUpdate.updatedemo(sql,"00010","����","18��һ");
        testUpdate.updatedemo2(sql,args1,sql,args2);
    }
}
