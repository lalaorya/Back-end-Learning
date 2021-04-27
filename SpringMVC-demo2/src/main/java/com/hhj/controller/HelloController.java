package com.hhj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/he")
public class HelloController  {

    @RequestMapping("/hello")

    public String hello(){
        System.out.println("hellomvc");
        return "success";
    }
}
