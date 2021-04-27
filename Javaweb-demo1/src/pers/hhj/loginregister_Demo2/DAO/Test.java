package pers.hhj.loginregister_Demo2.DAO;

public class Test {
    public static void main(String[] args) {
        User hhj = new User("hhj", "123");
        UserImp userImp = new UserImp();
        System.out.println(userImp.login(hhj));
    }
}
