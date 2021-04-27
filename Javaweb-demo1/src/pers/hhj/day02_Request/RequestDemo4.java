package pers.hhj.day02_Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet ("/requestDemo4")
public class RequestDemo4 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码，防止post请求方式接收参数时中文乱码,以后都要写
        req.setCharacterEncoding("UTF-8");

        System.out.println(req.getParameter("username"));

        String[] sexes = req.getParameterValues("sex");
        for(String s:sexes){
            System.out.println(s);
        }

        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()){
            System.out.println(req.getParameter(parameterNames.nextElement()));
        }

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<String> strings = parameterMap.keySet();
        for(String s:strings) {
            String[] values = parameterMap.get(s);
            System.out.println(s);
            for(String s1:values){
                System.out.println(s1);
            }
            System.out.println("---------");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
