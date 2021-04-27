package pers.hhj.test;

import pers.hhj.domain.User;
import pers.hhj.service.UserService;
import pers.hhj.service.impl.UserServiceImpl;

import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();
        List<User> allUser = userService.getAllUser();
        System.out.println(allUser);
    }
}
