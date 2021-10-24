### 1. 什么是SpringMVC

SpringMVC是SpringFramework的一个子项目，专门用于简化web应用的开发，它天然的嵌入了Spring框架的IOC、AOP概念，是基于请求-响应模型进行编程的。

简单来说，SpringMVC就是一个简易的框架，用来帮助我们开发webapp

### 2. SpringMVC的体系架构和执行原理

<img src="https://i.loli.net/2021/03/19/cntEpH97S1ozBRL.png" alt="image-20210301182438392" style="zoom:67%;" />

> Springmvc采用模块化开发，具体分为前端控制器、处理器映射器、处理器适配器、视图解析器，每个模块执行特定的功能，最终处理浏览器的请求并响应视图。
>
> 首先，浏览器发送请求被DispatcherServlet拦截，DispatcherServlet根据URL找到controller中相应的处理器Hanlder，返回处理器链HandlerChain。HandlerChain是一个数组，包含该请求的拦截器和处理器。
>
> DispatcherServlet接收到HandlerChain后，为其选择一个适配器HandlerAdapter，适配器依次执行拦截器和处理器的方法，最终返回ModelAndView。
>
> DispatcherServlet把返回的ModelAndView交给视图解析器ViewResolver，视图解析器根据ModelAndView解析出一个视图对象View，DispatcherServlet把视图中的数据渲染到html中，完成响应

### 3. SpringMVC和Struts2的优劣分析

- 共同点：都是表现层框架，基于MVC开发模型，底层都是ServletAPI，他们处理请求的核心机制都是前端控制器
- 不同点：SpringMVC的入口是一个Servlet，Struts2的入口是一个Filter

### 4. SpringMVC入门程序

**SpringMVC的核心就只有三个：**

- 配置web.xml文件，包含前端控制器DisptcherServlet和其他的拦截器Filter
- 配置springmvc.xml，其实也就是配置DisptcherServlet。这个xml文件是一个springxml文件，也就是说DisptcherServlet内部有一个IOC容器，负责管理和维护bean
- 编写controller控制器，也就是实际要执行的代码

首先看一下我的目录结构

<img src="https://i.loli.net/2021/03/19/aiSgE7XKAvoBR92.png" alt="image-20210302181827407" style="zoom:50%;" />

1. 导入maven依赖`pom.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>com.hhj</groupId>
       <artifactId>SpringMVC_Learn</artifactId>
       <version>1.0-SNAPSHOT</version>
   
       <dependencies>
           <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>5.3.1</version>
           </dependency>
           
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>5.1.5.RELEASE</version>
   
       </dependencies>
   </project>
   ```

2. web.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">
   
       <!--配置前端控制器-->
       <servlet>
           <servlet-name>springmvc</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
           <init-param>
               <param-name>contextConfigLocation</param-name>
               <param-value>classpath:springmvc.xml</param-value>
           </init-param>
   		<!--正数代表服务器启动创建该Servlet-->
           <load-on-startup>1</load-on-startup>
       </servlet>
   
       <servlet-mapping>
           <servlet-name>springmvc</servlet-name>
           <url-pattern>/</url-pattern>
       </servlet-mapping>
   </web-app>
   ```

3. springmvc.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/mvc
          https://www.springframework.org/schema/mvc/spring-mvc.xsd">
   
   	<!--开启注解扫描-->
       <context:component-scan base-package="com.hhj"/>
   
   <!--    下面这个配置可以开启Springmvc注解支持，并且帮助我们自动配置处理器映射器、适配器和视图解析器
           后面是用于开启自定义类型转换器-->
       <mvc:annotation-driven conversion-service="conversionService"/>
   
       <mvc:default-servlet-handler/>
   	
   	<!--配置视图解析器，springmvc会根据处理器返回的字符串找到相应视图-->
       <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/WEB-INF/page/"/>
           <property name="suffix" value=".jsp"/>
       </bean>
   
   </beans>
   ```

4. Controller控制器代码编写

   ```java
   package com.hhj.controller;
   
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   // 标志它是一个处理器
   @Controller
   // 一级目录
   @RequestMapping("/he")
   public class HelloController  {
       // 请求路径注解
       @RequestMapping("/hello")
       public String hello(){
           System.out.println("hellomvc");
           // 返回success视图，路径为视图解析器配置的路径，去那个包下匹配查找
           return "success";
       }
   }
   ```

简单来说，就是配置前端控制器，编写处理器代码，扫描RequestMapping注解，找到当前url对应的方法，执行即可

> RequestMapping注解
>
> - 作用:建立请求URL与处理方法之间的一一对应，可以用在类上，也可以用在方法上，用在类上为一级目录，用在方法上为二级目录
> - 属性
>   - path/value：指定请求路径的url
>   - method：指定该方法的请求方法，其他方法请求时会报错
>   - params：指定请求参数的条件
>   - headers：指定发送的请求中必须包含的请求头
>
> **使用示例**：`@RequestMapping(value="/hello",method=RequestMethod.POST,params={"username","password"})`

### 5. SpringMVC的请求参数绑定

这个是重中之重，如何把前端携带的数据提取出来并且封装到相应的数据结构，交给业务层处理

绑定的方式说白了，其实只有一种，就是绑定到相应处理方法的形式参数上，但是这个形式参数的类型可以有多种：基本类型或者实体类。下面我们来一一演示如何绑定。

#### ① 绑定到基本类型

```html
<%--请求参数绑定--%>
<a href="form?username=hehe&password=123">请求参数绑定</a>

```

```java
@RequestMapping("form1")
    // ① 通过形式参数绑定请求参数
    // <a href="form"?username=hehe&password=123">请求参数绑定</a>
    public String params(String username,String password){
        System.out.println(username+"---"+password);

        return "success";
    }
```

#### ② 绑定到实体类

```html
<%--把数据封装Account类中--%>
<form action="form2" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    金额：<input type="text" name="money" /><br/>
    用户姓名：<input type="text" name="user.uname" /><br/>
    用户年龄：<input type="text" name="user.age" /><br/>
    <input type="submit" value="提交" />
</form>
```

```java
@RequestMapping("form2")
    public String params2(Account account){
        System.out.println(account);
        return "success";
    }
```

#### ③ 绑定到集合中

```html
<%--把数据封装Account类中，类中存在list和map的集合--%>
<form action="form3" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    金额：<input type="text" name="money" /><br/>

<%--    集合可以这样存--%>
    用户姓名：<input type="text" name="list[0].uname" /><br/>
    用户年龄：<input type="text" name="list[0].age" /><br/>

    用户姓名：<input type="text" name="map['one'].uname" /><br/>
    用户年龄：<input type="text" name="map['one'].age" /><br/>
    <input type="submit" value="提交" />
</form>
```

主要是html的写法有所不同，绑定list用list[0]，绑定map可以用map["key"]

#### ④ 获取Servlet中的原生API，即Request和Respose

```java
@RequestMapping("form2")
    public String params2(Request request,Respose respose){
        System.out.println("修改形参即可使用之前那些个方法");
        return "success";
    }
```

### 6. SpringMVC的常用注解

#### ① RequestParam

@RequestParam可以将请求的参数绑定到该变量，这样就名字不同也能绑定了。如何没有这个注解，形参的名字和请求的参数名就必须相同

required属性可以指定该属性是否必须携带

```java
 public String params(@RequestParam(value = "username",required = true) String username, String password){
        System.out.println(username+"---"+password);

        return "success";
    }
```

#### ② RequestBody

获取请求体的内容，将请求体绑定到String字符串/实体类

```java
@RequestMapping("form2")
    public String params2(Account account, @RequestBody String body){
        System.out.println(account);
        System.out.println(body);
        return "success";
    }
```

#### ③ PathVariable

这个注解很重要，用的多

讲这个注解之前要先介绍一下Restful风格 

> RESTful架构是目前最流行的一种互联网软件架构，是一种设计风格而不是标准。Restful风格的优点有简洁、安全等，它的核心就是对URL的扩展，每一个URL代表一种资源。
>
> 我们先来具体看下RESTful风格的url,比如我要查询商品信息，那么
>
> 非REST的url：http://…/queryGoods?id=1001&type=t01
>
> REST的url: http://…/t01/goods/1001
>
> 可以看到使用Restful架构的url间接，参数直接通过url传给服务器，不用暴露过多的后端代码，起到安全的作用。此外，同一个url以不同的请求方式(PUT/GET/DELETE/PUT)请求服务器时，也会执行不同的方法，得到不同的响应。
>
> 总而言之，Restful风格就是对URL与后端处理器一一对应的另外一种编程风格，这种风格使我们的开发更加清晰。
>
> **restful的示例(同一个URL不同请求方式响应完全不同)**
>
> ```
> /account/1   HTTP GET     得到id = 1的account 
> /account/1   HTTP DELETE  删除id = 1的account 
> /account/1   HTTP PUT     更新id = 1的account
> /account/1   HTTP POST    新增account
> ```

@PathVaribale这个注解用于绑定url的占位符，url是支持占位符的，比如/account/{id}

```html
<a href="form5/777">绑定占位符</a>
```

```java
 // ④ 绑定占位符Restful风格
    @RequestMapping("form5/{id}")
    public String params4(@PathVariable("id") String param){
        System.out.println(param);
        return "success";
    }
```

#### ④ RequestHeader

用于获取请求头信息，value提供请求头名称，required是否必须包含此头

```java
// 请求头占位符
@RequestMapping("form5/{id}")
public String params4(@RequestHeader(value = "Accept-Language",required = true)String header){
    System.out.println(header);
    return "success";
}
```

#### ⑤ CookieValue

用于绑定请求体指定cookie名称的值，也就是说取出携带的cookie

```java
// Cookie
@RequestMapping("form5/{id}")
public String params4(@CookieValue
        (value = "JSESSIONID",required = false)String cookie){
    System.out.println(cookie);
    return "success";
}
```

#### ⑥ ModelAttribute

该注解相当于AOP编程中的前置通知，表示当前方法会在控制器的方法执行之前先执行，可以用于请求参数数据的处理，处理后得到的数据(保存在map中)可以返回给处理器参数使用

作用是将键值对添加到全局，所有注解了@RequestMapping的方法可获得此键值对

该注解的方法可以代返回值也可以不带返回值

- 带返回值：返回值被处理器形参接收

  ```java
  // 数据处理 前置通知 ModelAttribute
      // 1 带返回值
      @ModelAttribute
      public Account modelTest(String username,String password){
          Account account = new Account(username, password, null, null, null);
          return account;
      }
      访问此url需要的参数acount是上面返回的
      @RequestMapping("form3")
      public String params3(Account account){
          System.out.println(account);
  
          return "success";
      }
  ```

- 不带返回值：把需要传递的数据存进map中，处理器形参也是用此注解接收map中的数据

  ```java
  //    不带返回值
      @ModelAttribute
      public void modelTest2(String username, String password, Map<String,Account> map){
          Account account = new Account(username, password, null, null, null);
          map.put("user1",account);
      }
      @RequestMapping("form6")
      public String params6(@ModelAttribute(value = "user1")Account account){
          System.out.println(account);
  
          return "success";
      }
  ```

#### ⑦ SessionAttributes

 只能放在类上，用来实现当前controller的所有请求方法的数据共享，SessionAttibutes和原生Session是不一样，但是都可以用来实现请求之间的数据共享

> 官方解释：当用`@SessionAttribute`标注的`Controller`向其模型Model添加属性时，将根据该注解指定的名称/类型检查这些属性，**若匹配上了就顺带也会放进Session里**。匹配上的将一直放在`Sesson`中，直到你调用了`SessionStatus.setComplete()`方法。用户可以调用`SessionStatus.setComplete`来清除，**这个方法只是清除`SessionAttribute`里的参数，而不会应用于`Session`中的参数**。也就是说使用API自己放进Session内和使用`@SessionAttribute`注解放进去还是有些许差异的~

怎么使用SessionAttributes存取数据？

- 存：model中匹配的key，value会被存进SeesionAttributes

- 取：使用@ModelAttribute注解

  ​		使用model对象get方法

  ​		使用原生request的getAttribute

#### ⑦ SessionAttributes&ModelAttribute&Model

相当于session的域对象，实现多个请求之间的相互通信

若希望在多个请求之间共用数据，则可以在控制器类上标注一个 @SessionAttributes,配置需要在session中存放的数据范围，Spring MVC将存放在model中对应的数据暂存到HttpSession 中

```java
@Controller
@SessionAttributes(value = {"username","password"})
public class FormController {
    
    @RequestMapping("form7")
    public String params7(Model model){
        model.addAttribute("username","hhj");
        return "success";
    }
    
	@RequestMapping("form6")
    public String params6(@ModelAttribute(value = "user1")Account account,Model model){
        System.out.println(account);
        Object username = model.getAttribute("username");
        System.out.println(username.toString());
        return "success";
    }

}
```

### 7. SpringMVC响应数据和结果

#### ① 页面跳转(也就是请求转发，有request域对象)

Springmvc实现页面跳转有多种方式，最常用的有以下两种：

- 直接返回字符串，springmvc.xml配置的视图解析器会根据前缀后缀匹配到要跳转的资源。

  我们之间一直用的就是这个方法，`return "success"`,请求转发的默认方式

- 返回ModelAndView进行页面跳转，原理和上面一样，也是根据视图解析器找到资源

  ```java
   /**
       * 返回ModelAndView
       * @return
       */
      @RequestMapping("form8")
      public ModelAndView testModelAndView(){
          // 创建ModelAndView对象
          ModelAndView mv = new ModelAndView();
          // 模拟从数据库中查询出User对象
          User user = new User();
          user.setUsername("小凤");
          user.setPassword("456");
          user.setAge(30);
          // 把user对象存储到mv对象中，也会把user对象存入到request对象
          mv.addObject("user",user);
          // 跳转到哪个页面
          mv.setViewName("success");
  
          return mv;
      }
  ```

#### ② 请求转发和重定向

- 请求转发
  - 可以通过原生Servlet API的request对象的`req.getRequestDispatcher("url").forward(req,resp)`进行转发。请注意，这里的url不再能通过视图解析器解析，因此要写绝对路径
  - `return "sucess"`或者`return "forward:/WEB-INF/page/success.jsp"`。这一个是默认格式，会通过视图解析器解析，路径不需要写全，第二个需要写全
- 重定向
  - 可以通过原生Servlet API的response对象的`resp.setRedirect(url)`进行转发。请注意，重定向的资源不能放在WEB-INF中，否则无法找到
  - return `“redirect:/index.jsp”`

#### ③ 使用ResponseBody注解响应数据(json/xml)

之间我们的响应一般都是返回一个字符串，然后跳转到对应的页面。但是自从有了ajax，我们后台返回的一般都是数据。

@ResponseBody一般用在方法上，也就是说添加了@ResponseBody注解的方法，返回值会通过Http响应主体直接发送给浏览器，默认情况下，使用该注解返回的数据只能是String，返回其他类型时会出现异常。要想返回json/xml格式的数据，要添加特定的转换器。

添加jsckson转换器，添加依赖后，Springmvc会自动应用该转换器

```xml
<dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.0</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-core</artifactId>
      <version>2.9.0</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-annotations</artifactId>
      <version>2.9.0</version>
    </dependency>
```
```xml
<!--springmvc.xml中配置前端控制器，哪些静态资源不拦截-->
<!--这样才不会拦截服务器中的js文件-->
    <mvc:resources location="/css/" mapping="/css/**"/>
    <mvc:resources location="/images/" mapping="/images/**"/>
    <mvc:resources location="/js/" mapping="/js/**"/>
```



```html
 <script>
        // 页面加载，绑定单击事件
        $(function(){
            $("#btn").click(function(){
                // alert("hello btn");
                // 发送ajax请求
                $.ajax({
                    // 编写json格式，设置属性和值
                    url:"resp1",
                    Accept:"application/json",
                    contentType:"application/json;charset=UTF-8",
                    data:'{"uname":"hehe","age":"12"}',
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        // data服务器端响应的json的数据，进行解析
                        // alert(data);
                        // alert(data.username);
                        // alert(data.password);
                        // alert(data.age);
                    }
                });

            });
        });

</script>
```

```java
@RequestMapping("resp1")
@ResponseBody
public User2 resp1(@RequestBody User2 user){
    user.setUname("11");
    user.setAge("11");
    return user;
}
```

### 8. 文件上传

需要添加两个jar包

```xml
<!--       文件上传 依赖-->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.4</version>
        </dependenc
```

```xml
  <!--配置文件解析器对象-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="10485760" />
    </bean>
```

```java
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
```

### 9. Springmvc的拦截器

其实就是如果服务器出错，一般情况下异常错误会一级一级向上返回，直到传到前端控制器，如果不做处理的化，错误信息会直接返回给浏览器，最终展示在页面上。

拦截器就是对异常情况的拦截，如果出现错误，会跳转至错误页面，这杨用户体验会比较好。

### 10. 记录一些问题

1. 遇到在maven项目中已经导入依赖，但是服务器运行时找不到需要的jar包

   > 解决方法：web下新建lib项目，新导入的依赖放进lib目录下，它不会自动导入
   >
   > ![image-20210305210954113](https://i.loli.net/2021/03/19/3CMaSDXioBlTZtH.png)

#### 11. SSM整合

mybatis -> spring -> springmvc

https://blog.csdn.net/qq_33524158/article/details/78360268

==🎈🎈🎈不足之处，留待日后更改==

