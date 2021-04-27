package com.hhj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class RestController {


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getUser(){
        return "GET";
    }

    // 上面那样写有些麻烦，因此SPRING给我们提供了几个新注解
   @PostMapping("/user")
    public String saveUser(){
        return "post";
    }

    @PutMapping("/user")
    public String putUser(){
        return "put";
    }

    @DeleteMapping("/user")
    public String deleteUser(){
        return "delete";
    }
}
