package pers.hhj.day06_Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//通过WebFilter配置拦截路径,也就是要拦截哪些Servlet
//@WebFilter("/*")
public class FilterDemo1 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("访问服务器资源区执行的代码");
        //放行，让该请求去执行它的Servlet
        chain.doFilter(req, resp);
        System.out.println("响应服务器前执行的代码");

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
