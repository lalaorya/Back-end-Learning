package pers.hhj.day06_Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(urlPatterns = {"/index.jsp"},dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class FilterDemo2 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter2 come");
        chain.doFilter(req, resp);
        System.out.println("Filter2 go");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
