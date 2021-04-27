package pers.hhj.day02_Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 使用获取请求头里面的referer属性值编写一个简单的防盗链
 */
@WebServlet ("/requestDemo2")
public class RequestDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("referer");
        if(referer!=null) {
            if(referer.contains("/web_learning")){
                System.out.println("路径正常，可以继续");
            }else{
                System.out.println("来源路径非法");
            }
        }
    }
}
