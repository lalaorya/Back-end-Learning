package com.hhj.controller;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//@RestController=@ResponseBody+@Controller,表示当前类是一个控制器并且返回的数据直接输出给浏览器，不是页面跳转
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        log.info("简化日志开发");
        return "HELLO SPRINGBOOT";
    }
}
