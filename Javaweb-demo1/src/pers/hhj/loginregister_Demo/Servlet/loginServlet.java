package pers.hhj.loginregister_Demo.Servlet;

import pers.hhj.loginregister_Demo.DAO.User;
import pers.hhj.loginregister_Demo.DAO.UserImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User loginUser = new User(username, password);
        UserImp userImp = new UserImp();
        User result = userImp.login(loginUser);
        if(result==null)    req.getRequestDispatcher("/failServlet").forward(req,resp);
        else{
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("username",result.getUsername());
            req.getRequestDispatcher("/successServlet").forward(req,resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
