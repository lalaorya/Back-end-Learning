package day05_对事务操作的处理;

/**
 * @program: JDBC学习源码
 * @description:
 * @author: HHJ
 * @created: 2020/09/15 15:59
 */
public class TxTest {
    public static void main(String args[]){
        UpdateDataWhenAddTx udwt=new UpdateDataWhenAddTx();
        udwt.updateWithTX();
    }
}
