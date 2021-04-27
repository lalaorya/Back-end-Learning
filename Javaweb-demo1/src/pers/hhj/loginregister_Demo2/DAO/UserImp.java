package pers.hhj.loginregister_Demo2.DAO;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import pers.hhj.loginregister_Demo.DAO.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserImp {
    public User login(User loginUser){
        //获取链接
        try {
            Connection con;
            con = pers.hhj.loginregister_Demo.DAO.JDBCUtils.getCon();

            String sql="select username,password from user where username=? and password=?";
            QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
            User user=queryRunner.query(sql,new BeanHandler<User>(User.class),loginUser.getUsername(),loginUser.getPassword());

            return user;

        } catch (SQLException throwables) {
            return null;
        }

    }
}
