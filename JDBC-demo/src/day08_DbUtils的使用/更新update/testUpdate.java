package day08_DbUtils的使用.更新update;

import day07_数据库连接池.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: JDBC学习源码
 * @description: 使用queryrunner实现更新操作
 * @author: HHJ
 * @created: 2020/10/05 16:01
 */
public class testUpdate {
    //每个sql语句就是一个事务
    public static void updatedemo1(String sql,Object ... args)throws SQLException {
        //获取queryRunner类
        QueryRunner qr=new QueryRunner(JDBCUtils.getC3p0DataSource());
        qr.update(sql,args);
        //事务情况下的更新操作:
        //# 上面的queryrunner使用空参即可，不要使用数据库连接池作为参数。
        //需要链接的时候从连接池中取出来一个即可



    }

    //事务情况下的更新操作:
    //上面的queryrunner使用空参即可，不要使用数据库连接池作为参数。
    //需要链接的时候从连接池中取出来一个即可
    public static void updatedemo2(String sql,Object[] args1,String sql2,Object[] args2){
        QueryRunner qr=null;
        Connection con=null;
        try{
            qr=new QueryRunner();
            con=JDBCUtils.getConnection3();
            qr.update(con,sql,args1);
            qr.update(con,sql2,args2);

            System.out.println("事务提交成功");
            DbUtils.commitAndCloseQuietly(con);
        }catch (Exception e){
            DbUtils.rollbackAndCloseQuietly(con);
            e.printStackTrace();
        }

    }

}
