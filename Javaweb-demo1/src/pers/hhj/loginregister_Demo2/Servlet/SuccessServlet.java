package pers.hhj.loginregister_Demo2.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/successServlet2")
public class SuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object username = req.getAttribute("username");
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("欢迎"+username+"来到本站");
    }
}
