package pers.hhj.test;

import pers.hhj.dao.impl.UserDAOImpl;
import pers.hhj.domain.User;

import java.util.List;

public class daotest {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        List<User> allUser = userDAO.getAllUser();
        System.out.println(allUser);

    }
}
