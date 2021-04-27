package pers.hhj.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import pers.hhj.dao.UserDAO;
import pers.hhj.domain.User;
import pers.hhj.util.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * User接口的具体实现类
 */
public class UserDAOImpl implements UserDAO {

    @Override
    /**
     * 使用JDBC查询并返回数据
     */
    public List<User> getAllUser() {
        //获取连接
        try {
            //编写SQL
            String sql="select * from user2";
            System.out.println(JDBCUtils.getDataSource2());
//            System.out.println(JDBCUtils.getCon());
            QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource2());
            List<User> userList = queryRunner.query(sql, new BeanListHandler<User>(User.class));
//            System.out.println(userList);
            return userList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
