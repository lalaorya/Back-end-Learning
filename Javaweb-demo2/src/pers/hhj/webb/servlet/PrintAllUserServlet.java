package pers.hhj.webb.servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import pers.hhj.domain.User;
import pers.hhj.service.UserService;
import pers.hhj.service.impl.UserServiceImpl;
import pers.hhj.util.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/printAllUserServlet")
public class PrintAllUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json;charset=utf-8");
//        查询所有用户
        UserServiceImpl userService = new UserServiceImpl();
        List<User> allUser = userService.getAllUser();
        System.out.println(allUser);
//        封装成json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),allUser);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
