package com.hhj.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ModelController {

    // 请求需要认证
    @RequiresAuthentication
    @RequestMapping("/saveAttr")
    public String saveToResq(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
//        map.put("name","zhangsan");
        System.out.println(111);
        model.addAttribute("age",11);
//        httpServletRequest.setAttribute("inter","basketball");
//        httpServletRequest.getRequestDispatcher("/user").forward(httpServletRequest,httpServletResponse);
        return "success";

    }

}
