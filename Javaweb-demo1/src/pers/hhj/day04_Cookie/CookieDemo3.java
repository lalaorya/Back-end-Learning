package pers.hhj.day04_Cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/cookieDemo3")
/**
 * 记住上次登陆时间的案例
 */
public class CookieDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        Cookie[] cookies = req.getCookies();
        boolean flag=true;
        if(cookies!=null&& cookies.length!=0){
            for(Cookie cookie:cookies){
                String name = cookie.getName();
                if(name.equals("last_time")){
                    String value = cookie.getValue();
                    resp.getWriter().write("欢迎你再次访问本站，您上次的访问时间是"+value);
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                    value=sdf.format(date);
                    cookie.setValue(value);
                    //保存一周
                    cookie.setMaxAge(60*60*24*7);
                    resp.addCookie(cookie);

                    flag=false;
                    break;
                }
            }
        }

        if(cookies==null||cookies.length==0||flag==true){
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            String value=sdf.format(date);
            Cookie cookie = new Cookie("last_time",value);
            resp.addCookie(cookie);
            resp.getWriter().write("欢迎首次访问本站");

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
