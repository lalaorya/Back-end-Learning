package com.hhj.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.hhj.domain.User;
import com.hhj.service.UserService;
import com.hhj.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.java2d.cmm.Profile;

@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    // 认证
    // 把收集到的信息封装成AuthorizationInfo，然后交给SecurityManager
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 授权
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user=userService.getUserById(Integer.parseInt(userId));

        if(user==null)  throw new UnknownAccountException("账户不存在");

        // 获取权限
//         if(user.getStatus()==-1) throw new LockedAccountException("账户已被锁定");
        MyPorfile myPorfile = new MyPorfile();
        BeanUtil.copyProperties(user,myPorfile);

        return  new SimpleAuthenticationInfo(myPorfile,jwtToken.getCredentials(),getName());
    }
}
