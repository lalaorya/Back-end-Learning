package com.hhj.controller;

import com.hhj.domain.Account;
import com.hhj.domain.User;
import com.hhj.domain.User2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes(value = {"username","password"})
public class FormController {
    @RequestMapping("form1")
    // ① 通过形式参数绑定请求参数
    // <a href="form"?username=hehe&password=123">请求参数绑定</a>
    // @RequestParam可以将请求的参数绑定到该变量，这样就名字不同也能绑定了。如何没有这个注解，形参的名字和请求的参数名就必须相同
    public String params(@RequestParam(value = "username",required = true) String username, String password){
        System.out.println(username+"---"+password);

        return "success";
    }

    // ② 把形式参数绑定到实体类
    /*<%--把数据封装Account类中.name标签名必须与实体类名一致--%>
    <form action="param/saveAccount" method="post">
        姓名：<input type="text" name="username" /><br/>
        密码：<input type="text" name="password" /><br/>
        金额：<input type="text" name="money" /><br/>
        // 嵌套可以这样用
        用户姓名：<input type="text" name="user.uname" /><br/>
        用户年龄：<input type="text" name="user.age" /><br/>
        <input type="submit" value="提交" />
    </form>*/
    @RequestMapping("form2")
    public String params2(Account account, @RequestBody String body){
        System.out.println(account);

        return "success";
    }

    // ③ 参数绑定到set集合或者map集合
    @RequestMapping("form3")
    public String params3(Account account){
        System.out.println(account);

        return "success";
    }

    // ③ 自定义转换器
    @RequestMapping("form4")
    public String params4(User user){
        System.out.println(user);

        return "success";
    }

    // ④ 绑定占位符Restful风格, and 请求头 and Cookie
    @RequestMapping("form5/{id}")
    public String params4(@PathVariable("id") String param, @RequestHeader(value = "Accept-Language",required = true)String header,@CookieValue
            (value = "JSESSIONID",required = false)String cookie){
        System.out.println(param);
        System.out.println(header);
        System.out.println(cookie);
        return "success";
    }

    // 数据处理 前置通知 ModelAttribute
    // 1 带返回值
    @ModelAttribute
    public Account modelTest(String username,String password){
        Account account = new Account(username, password, null, null, null);
        return account;
    }
//    访问此url是ccount是上面返回的
//    @RequestMapping("form3")
//    public String params3(Account account){
//        System.out.println(account);
//
//        return "success";
//    }

//    不带返回值
    @ModelAttribute
    public void modelTest2(String username, String password, Map<String,Account> map,Model model){
        model.addAttribute("password",123);
        model.addAttribute("username",123456);
        Account account = new Account(username, password, null, null, null);
        map.put("user1",account);
    }

    @RequestMapping("form6")
    public String params6(@ModelAttribute(value = "user1")Account account,Model model,@ModelAttribute(value = "password")String password){
        System.out.println(account);
        Object username = model.getAttribute("username");
        System.out.println(username.toString()+password);

        return "success";
    }

    @RequestMapping("form7")
    public String params7(Model model){
        model.addAttribute("username","hhj");

        return "success";
    }

    // 响应json数据
    @RequestMapping("resp1")
    @ResponseBody
    public User2 resp1(@RequestBody User2 user){
        user.setUname("11");
        user.setAge("11");
        return user;
    }

    /**
     * SpringMVC文件上传
     * @return
     */
    @RequestMapping("fileupload2")
    public String fileuoload2(HttpServletRequest request, MultipartFile upload) throws Exception {
        System.out.println("springmvc文件上传...");

        // 使用fileupload组件完成文件上传
        // 上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        // 判断，该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            // 创建该文件夹
            file.mkdirs();
        }

        // 说明上传文件项
        // 获取上传文件的名称
        String filename = upload.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid+"_"+filename;
        // 完成文件上传
        upload.transferTo(new File(path,filename));

        return "success";
    }

}
