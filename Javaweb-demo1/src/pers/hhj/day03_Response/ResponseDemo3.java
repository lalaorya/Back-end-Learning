package pers.hhj.day03_Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/responseDemo3")
public class ResponseDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取流对象之前，设置编码
        //resp.setCharacterEncoding("UTF-8");
        //让浏览器以utf-8的编码表解码
        //但是实际上，下面这个方式不仅可以告诉浏览器的解码方式，话可以设置resp对象的编码。因此上面那行可以注释掉了
        //resp.setHeader("content-type","text/html;charset=UTF-8");

        //这样还不是最简单的.....有一个专门的方法用于设置编解码方式。所以上面那个也可以注释了，我们用这个
        resp.setContentType("text/html;charset=UTF-8");

        //获取字符输出流
        PrintWriter pw = resp.getWriter();
        pw.write("你好hello");//这里也可以写入html文档，浏览器会自动解析


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
