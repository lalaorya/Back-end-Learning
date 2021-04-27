package pers.hhj.dao;

import pers.hhj.domain.User;

import java.util.List;

/**
 * 定义了针对User对象操作的抽象方法
 * service层调用这些基本方法完成具体的业务逻辑
 */
public interface UserDAO {
    /**
     * 返回所有用户的list集合
     * 不需要写public abstract，默认就是这个
     * @return
     */
    List<User> getAllUser();

}
