package pers.hhj.service.impl;

import pers.hhj.dao.UserDAO;
import pers.hhj.dao.impl.UserDAOImpl;
import pers.hhj.domain.User;
import pers.hhj.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUser() {
        UserDAO userDAO = new UserDAOImpl();
        List<User> allUser = userDAO.getAllUser();
        System.out.println(allUser);
        return  allUser;
    }
}
