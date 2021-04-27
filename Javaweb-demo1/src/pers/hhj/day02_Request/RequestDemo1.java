package pers.hhj.day02_Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet ("/resdemo1")
public class RequestDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取请求方式
        System.out.println(req.getMethod());
        // 2.获取虚拟目录
        System.out.println(req.getContextPath());
        // 3.获取Servlet路径
        System.out.println(req.getServletPath());
        // 4.获取post方式请求参数
        System.out.println(req.getQueryString());
        // 5.获取URI(短，没有http://虚拟目录) 以及URL
        System.out.println(req.getRequestURI());
        System.out.println(req.getRequestURL());
        // 6.获取协议和版本
        System.out.println(req.getProtocol());
        // 7.获取IP地址
        System.out.println(req.getRemoteAddr());

        // 8.获取请求头数据

        // 获取所有的请求请求头名称，返回E
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            // 通过请求头的名称key获取它的value
            String value = req.getHeader(name);
            System.out.println(name+"--------"+value);
        }

    }
}
