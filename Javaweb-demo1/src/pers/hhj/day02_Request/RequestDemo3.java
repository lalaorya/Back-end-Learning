package pers.hhj.day02_Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/requestDemo3")
public class RequestDemo3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取流对象
        BufferedReader reader = request.getReader();
        // 上面那个是字符流对象，还有一个获取字节流对象的方法getInputStream
        // 这个方法一般用于获取文件参数
        // 2.读取信息
        String s = null;
        while((s=reader.readLine())!=null){
            System.out.println(s);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
