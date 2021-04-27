package pers.hhj.loginregister_Demo2.Servlet;

import pers.hhj.loginregister_Demo.DAO.User;
import pers.hhj.loginregister_Demo.DAO.UserImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet ("/loginServlet2")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        // 获取验证码
        String checkcode = req.getParameter("checkcode");
        // 判断验证码是否正确
        HttpSession session = req.getSession();
        String checkCode_session = (String)session.getAttribute("checkCode");
        // 保证验证码一次可用
        session.removeAttribute("checkCode");
        if(checkcode.equalsIgnoreCase(checkCode_session)){
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User loginUser = new User(username, password);
            UserImp userImp = new UserImp();
            User result = userImp.login(loginUser);
            if(result==null)    req.getRequestDispatcher("/failServlet2").forward(req,resp);
            else{

                req.setAttribute("username",result.getUsername());
                req.getRequestDispatcher("/successServlet2").forward(req,resp);
            }
        }else{//验证码错误
            resp.getWriter().write("验证码错误");
        }



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
