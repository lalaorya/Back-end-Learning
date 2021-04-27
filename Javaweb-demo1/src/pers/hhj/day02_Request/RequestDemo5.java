package pers.hhj.day02_Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/requestDemo5")
public class RequestDemo5 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("requestDemo5在此");
        // 存储数据到request域中
        req.setAttribute("name","zhangsan");

        // 请求并转发
        req.getRequestDispatcher("/requestDemo6").forward(req,resp);

    }

    /**
     *  - void setAttribute(String name,Object obj):键值对方式存储数据
     *  - Object getAttribute(String name):通过key得到value
     *  - void removeAttribute(String name):移除域中键值对数据
     */
}
