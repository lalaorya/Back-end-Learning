package pers.hhj.day01;

import javax.servlet.*;
import java.io.IOException;

public class ServletDemo2 implements Servlet {
    /**
     * 初始化方法，servlet被创建时执行，只执行一次
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    /**
     * 获取servlet的配置对象
     * @return
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 每次提供服务时执行service方法，执行多次
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    /**
     * 获取servlet的一些信息（版本，作者
     * @return
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 正常关闭时执行，执行一次
     */
    @Override
    public void destroy() {

    }
}
