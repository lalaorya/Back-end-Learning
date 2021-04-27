package com.hhj.shiro;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hhj.common.Result;
import com.hhj.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    // 这个接手过来的servletRequest应该本来就是HttpServletRequest，向下强制转型
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 获取jwt
        String jwt = request.getHeader("Authorization");
        if(jwt==null){
            return null;
        }

        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if(jwt==null){
            return true;
        }
        // 校验jwt
        Claims claimByToken = jwtUtils.getClaimByToken(jwt);
        if(claimByToken==null||jwtUtils.isTokenExpired(claimByToken.getExpiration())){
            throw new ExpiredCredentialsException("token已失效，重新登陆");
        }



        return executeLogin(servletRequest,servletResponse);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse response1 = (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result fail = Result.fail(throwable.getMessage());
        try {
            response1.getWriter().print(JSONUtil.toJsonStr(fail));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return false;
    }
}
