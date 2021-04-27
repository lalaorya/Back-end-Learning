package pers.hhj.service;

import pers.hhj.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 返回所有用户信息
     * @return
     */
    List<User> getAllUser();
}
