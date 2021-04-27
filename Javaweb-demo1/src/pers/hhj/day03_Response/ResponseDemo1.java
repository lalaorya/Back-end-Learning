package pers.hhj.day03_Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/responseDemo1")
public class ResponseDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("1111");
        // 重定向，自动跳转到responseDemo2
        // 通过设置响应行状态码为302，以为响应头的location
        resp.setStatus(302);
        resp.setHeader("location","/web_learning/responseDemo2");

        // 一种简单的重定向方法，原理就是上面的，只不过封装了起来
        resp.sendRedirect("/web_learning/responseDemo2");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
