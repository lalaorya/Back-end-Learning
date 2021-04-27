package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;

import java.util.*;

public class UserServiceImpl implements UserService {
    private String name;
    private Integer age;
    private Date birthday;

    private String[] strings;
    private List list;
    private Set set;
    private Map map;
    private Properties properties;

    public UserServiceImpl() {
    }

    public UserServiceImpl(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public void getUserById(int id) {
        UserDao userDao = new UserDaoImpl();
        userDao.getUserById(1);
        System.out.println(name+age+birthday);
        System.out.println(Arrays.toString(strings));
        System.out.println(list);
        System.out.println(set);
        System.out.println(map);
        System.out.println(properties);
    }

    public UserServiceImpl getInstance(){
        return new UserServiceImpl();
    }

    public static UserServiceImpl getInstance2(){
        return new UserServiceImpl();
    }

    public void init(){
        System.out.println("对象被创建了");
    }

    public void destroy(){
        System.out.println("对象被销毁了");
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
