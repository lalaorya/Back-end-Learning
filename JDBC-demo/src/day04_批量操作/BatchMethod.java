package day04_批量操作;

import day01_如何获取数据库连接.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @program: JDBC学习源码
 * @description:JDBC的批量操作
 *              因为delete、update操作本身就具有一次操作多条记录的特性
 *              因此这里的批量操作指的是批量插入
 *              这里需要用到三个方法：
 *                  addBatch(String)    添加需要批量处理的sql语句
 *                  executeBatch()  执行缓存中的SQL语句
 *                  clearBatch  清空缓存中的数据
 * @author: HHJ
 * @created: 2020/09/12 13:07
 */

/**
 * 批量操作就是先不执行sql语句，攒到一定数量在执行，可以提高效率
 */
public class BatchMethod {
    public void batchMethod1() throws Exception{
        Connection con= JDBCUtil.getConnection();
        String sql="insert into 学生成绩表(学号,姓名) values (?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        for(int i=1;i<50000;i++){
            ps.setString(1,Integer.parseInt("6152")+i+"");
            ps.setString(2,"临时_"+i);

            ps.addBatch();
            if(i%500==0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        JDBCUtil.closeResource(con,ps);
    }

    /**
     * 方法1的优化版
     * 因为preparedstatement是默认执行一条sql语句提交一次结果，因此方法1要提交10000次
     * 因此我们可以设置不自动提交，等到sql全部执行完在提交
     * @throws Exception
     */
    public void batchMethod2() throws Exception{
        Connection con= JDBCUtil.getConnection();
        //设置不自动提交执行数据
        con.setAutoCommit(false);
        String sql="insert into 学生成绩表(学号,姓名) values (?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        for(int i=1;i<5000;i++){
            ps.setString(1,Integer.parseInt("00001")+i+"");
            ps.setString(2,"临时_"+i);

            ps.addBatch();
            if(i%500==0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        //全部执行完再提交
        con.commit();
        JDBCUtil.closeResource(con,ps);
    }
}
