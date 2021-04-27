## Springboot-Notes

### 1. Springboot

​	可查看此文章[spring和springboot](https://www.yuque.com/atguigu/springboot/na3pfd)

​	重点要理解微服务和分布式的概念

- 微服务
- 分布式

### 2. Springboot的官方文档

- 文档地址：https://spring.io/projects/spring-boot#learn

  可自行选择版本，建议选择稳定版

- 文档架构

  <img src="https://i.loli.net/2021/04/15/idyeEs6qNQgVTwo.png" alt="image-20210308135728199" style="zoom:50%;" />

### 3. springboot2系统要求

- jdk1.8+
- maven3.3+

需要在maven的conf目录下配置settings.xml文件。添加以下两项，修改中央仓库位置为阿里云，jdk默认为1.8

![image-20210308140926014](https://i.loli.net/2021/04/15/oErvcI2bpjlF6SG.png)

```xml
<mirrors>
      <mirror>
        <id>nexus-aliyun</id>
        <mirrorOf>central</mirrorOf>
        <name>Nexus aliyun</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
      </mirror>
  </mirrors>
 
  <profiles>
         <profile>
              <id>jdk-1.8</id>
              <activation>
                <activeByDefault>true</activeByDefault>
                <jdk>1.8</jdk>
              </activation>
              <properties>
                <maven.compiler.source>1.8</maven.compiler.source>
                <maven.compiler.target>1.8</maven.compiler.target>
                <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
              </properties>
         </profile>
  </profiles>
```

### 4. Springboot快速入门

- 创建普通maven工程，引入springboot依赖

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.hhj</groupId>
      <artifactId>Springboot-learnNote</artifactId>
      <version>1.0-SNAPSHOT</version>
      <packaging>jar</packaging>
  
  <!--    springboot项目的根依赖，包含了许多核心基础的包
          使用parent标签，表示它的父依赖是这个，可以直接使用父亲的jar包-->
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>2.3.4.RELEASE</version>
      </parent>
  
  
  <!--    如果要创建web应用，需要引入此依赖-->
  <!--    该依赖中集成了tomcat等-->
      <dependencies>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
  
      </dependencies>
  
  
  </project>
  ```

- 创建主程序/入口

  ```java
  package com.hhj;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  // 标记这是一个springboot应用
  @SpringBootApplication
  public class MainApplication {
      public static void main(String[] args) {
          SpringApplication.run(MainApplication.class,args);
      }
  }
  ```

- 编写控制器

  ```java
  package com.hhj.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.ResponseBody;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  //@RestController=@ResponseBody+@Controller,表示当前类是一个控制器并且返回的数据直接输出给浏览器，不是页面跳转
  public class HelloController {
  
      @RequestMapping("/hello")
      public String hello(){
          return "HELLO SPRINGBOOT";
      }
  }
  ```

- 运行主程序

  ![image-20210308145535302](https://i.loli.net/2021/04/15/mXINu2bgkVKwxYS.png)

此外，Springboot还可以帮我们简化配置和部署

- 简化配置

  创建application.properties文件，配置应用程序的公用属性。

  上面提到的spirngboot的官方文档里面有详细说明，配置文件名字也是从此而来，不能改变。[application properties说明](https://docs.spring.io/spring-boot/docs/2.4.3/reference/html/appendix-application-properties.html#common-application-properties)

  ![image-20210308150130735](https://i.loli.net/2021/04/15/dYTLGARoWb6Fphx.png)

  ```properties
  #配置tomcat服务器端口号
  server.port=8880
  ```

- 简化部署

  以前ssm部署项目的原理是把项目打成war包，放到tomcat服务器上。springboot可以直接打成jar包，jar包中集成了tomcat服务器，因此可以在服务器本地使用`java -jar`命令直接运行

  先添加maven插件

  ```xml
   <build>
          <plugins>
              <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
              </plugin>
          </plugins>
  </build>
  ```

  maven install打成jar包

  命令行运行

  <img src="https://i.loli.net/2021/04/15/mxI3tHPQfAELzv9.png" alt="image-20210308153343952" style="zoom: 200%;" />

  > 可能出现的问题：
  >
  > <img src="https://i.loli.net/2021/04/15/Oy8SawxfzBANZhd.png" alt="image-20210308153156538" style="zoom: 150%;" />
  >
  > 运行jar包部署服务器时出现此错误
  >
  > 原因是安装完插件的第一次打包要用上面的install，不能用下面的install
  >
  > <img src="https://i.loli.net/2021/04/15/ndMGoh9C6QvHKg2.png" alt="image-20210308153445586" style="zoom:80%;" />

### 5. Springboot自动装配的原理

#### ① Springboot是如何管理依赖的

前面在学习ssm的时候，要导入一大堆jar包，但是springboot只需要引入一个父依赖以及场景启动器就可以构建web程序，它是怎么实现的呢？

我们先来看看springboot的父依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.4.RELEASE</version>
</parent>
```

1. 点击spring-boot-starter-parent，我们可以看到他也有父依赖

   ![image-20210308192432684](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308192432684.png)

2. 再点进去它的父依赖

   ![image-20210308192539629](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308192539629.png)

   我们可以看到这个坐标定义了很多属性，这些属性基本涵盖了我们需要使用的依赖的版本号。所以后面我们再导入其他场景启动器时不需要写版本号，因为版本号都定义在了这里

3. 所以说，spring-boot-starter-parent依赖并没有实际的jar包可以导入，它的作用是定义各组件的版本号

   ![image-20210308192928825](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308192928825.png)

   web场景启动器的版本号就定义在了这里

4. 看完spring-boot-starter-parent，我们接看来看spring-boot-starter-web，首先来看我们这个项目的maven依赖图

   <img src="https://i.loli.net/2021/04/15/jiFwATdLcflatPu.png" alt="image-20210308193145299" style="zoom:150%;" />

   这个图中没有关于spring-boot-starter-parent的信息，也侧面印证了我们上面的分析

5. 来看spring-boot-starter-web的代码

   ![image-20210308193357401](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308193357401.png)

   可以看到web启动器集成了springmvc、tomcat、springcontext等

   只要引入starter启动器，这个场景的所有常规需要的依赖我们都自动引入

   > SpringBoot所有支持的场景
   > https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter
   > `见到的  *-spring-boot-starter： 第三方为我们提供的简化开发的场景启动器。`

   6. 如何修改默认的版本号

      在当前项目的pom.xml文件中重写相应属性

      ```xml
      1、查看spring-boot-dependencies里面规定当前依赖的版本 用的 key。
      2、在当前项目里面重写配置
      比如修改mysql的版本号
      <properties>
           <mysql.version>5.1.43</mysql.version>
      </properties>
      ```

#### ② 自动装配原理

1. Springboot程序是这样启动的...

   ```java
   @SpringBootApplication
   public class MainApplication {
       public static void main(String[] args) {
           SpringApplication.run(MainApplication.class,args);
       }
   }
   ```

   这个方法返回的实际上是一个IOC容器ApplicationContext，springboot根据我们配置的依赖pom.xml动态的为我们创建并填充IOC容器

   我们先来看看这个IOC容器中装配了哪些bean

   ```java
   @SpringBootApplication
   public class MainApplication {
       public static void main(String[] args) {
           ConfigurableApplicationContext application = SpringApplication.run(MainApplication.class, args);
           String[] beanDefinitionNames = application.getBeanDefinitionNames();
           for(String s:beanDefinitionNames) {
               System.out.println(s);
           }
       }
   }
   ```

   ```
   org.springframework.context.annotation.internalConfigurationAnnotationProcessor
   org.springframework.context.annotation.internalAutowiredAnnotationProcessor
   org.springframework.context.annotation.internalCommonAnnotationProcessor
   org.springframework.context.event.internalEventListenerProcessor
   org.springframework.context.event.internalEventListenerFactory
   mainApplication
   org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory
   helloController
   org.springframework.boot.autoconfigure.AutoConfigurationPackages
   org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
   propertySourcesPlaceholderConfigurer
   org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration$TomcatWebSocketConfiguration
   websocketServletWebServerCustomizer
   org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration
   org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration$EmbeddedTomcat
   tomcatServletWebServerFactory
   org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
   servletWebServerFactoryCustomizer
   tomcatServletWebServerFactoryCustomizer
   org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor
   ............等等
   ```

   可以看到我们在springmvc中用到的dispathServlet、viewResolver、multipartResolver以及我们的helloController都被添加到该容器供springboot调用，这也就是为什么我们什么都没有配置，也可以使用mvc的组件。springboot都帮我们自动装配好了所有web开发常用的组件。

2. 还有一个问题：springboot是如何知道扫描哪些包的

   前面学习spring以及springmvc的时候，都要使用一个`<context:component-scan base-package="com.hhj"/>`来决定扫描哪些包，springboot又是如何实现的呢

   springboot有默认包扫描规则，默认扫描主程序所在包及其下面的所有子包

   也可以修改默认默认扫描包

   - 通过`@SpringBootApplication(scanBasePackages = "com.hhj")`

   - 通过`@ComponentScan(basePackages = "com.hhj")`加上另外两个注解替代@SpringBootApplication

     ```java
     @SpringBootApplication
     等同于
     @SpringBootConfiguration
     @EnableAutoConfiguration
     @ComponentScan("com.hhj")
     ```

   实际上，配置文件中的值最终都是映射到IOC容器中的某一个对象上的，springboot按需加载对象

### 6. 底层常用注解

#### ① @Configuration

- 标记当前类为配置类，用法和spring中的该注解一样

- 有一个重要属性`@Configuration(proxyBeanMethods = true)`,不写默认为true


  proxyBeanMethods表示@bean注解下调用该方法的方法是

  - Full模式(proxyBeanMethods = true)【保证每个@Bean方法被调用多少次返回的组件都是单实例的】

  - Lite模式(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】

  一般情况下要调成false，因为如果是true的话，spring每次向IOC中添加该bean前都会检查以下IOC容器中有没有该bean，如何有，返回容器中的bean，不重新创建。这样虽然保证了单例，但是会降低Springboot的性能。

  但是如果组件内部有依赖则必须使用Full模式，可以保证是同一个bean。其他默认是否Lite模式。

类似的添加bean的注解还有@Bean、@Compone、@Controller、@Service、@Respository

#### ②  @Import

- 只能再类上进行注解`@Import({User.class,DBHelper.class})`

  作用是使用无参构造函数在IOC上创建这两个类的bean，bean的默认名字就是全类名

#### ③ @Conditional

​	这是条件装配注解，只能满足Conditional指定的条件，采用注入IOC容器

​	它的实现注解：

![img](https://i.loli.net/2021/04/15/hIS92aUHb5KFuWE.png)

- 可以用在类上，表示当前类的所有@Bean注解下的方法都有此条件

- 也可以用在@Bean方法上，仅表示但钱方法有限制

  ```java
  @Bean
  @ConditionalOnBean(name = "user")
  public Dog dog(){
      return  new Dog("tom",5);
  }
  ```

#### ④ @ImportResource

​	这个注解的作用和xml注解中的component scan一样，都是实现注解配置和xml配置的结合

​	使用该注解可以将路径下的spring配置文件中的bean添加到同一个IOC容器

​	`@ImportResouce("classpath:spring.xml")`,一般用于配置类

#### ⑤ 关于绑定根配置文件的注解

​	把springboot的properties配置文件的信息绑定到JavaBean实体类

​	有两种种实现方式

配置文件：

![image-20210308225948314](https://i.loli.net/2021/04/15/7H3agnryifJtKzZ.png)

实体类：

![image-20210308230012312](https://i.loli.net/2021/04/15/iYdOQ4RsymF2Mrl.png)

- 在要绑定的实体类上使用@ConfigurationProperties

  ```java
  @Component
  @ConfigurationProperties(prefix = "mycar")
  public class Car {
      private String band;
      private double price;
      ...
  }
  ```

- 配置类@EnableConfigurationProperties+实体类@ConfigurationProperties

### 7. 深入了解Springboot自动装配的原理

<img src="https://i.loli.net/2021/04/15/q2ebf1u6kMGDQVT.png" alt="就看这张图" style="zoom:200%;" />

springboot应用的入口就是@SpringbootApplication所在的类，所以说这个注解到底在底层为我们做了什么？

- 首先看他的架构图

  ![image-20210309101147836](https://i.loli.net/2021/04/15/U8WRjcA7ubMwmrv.png)

  核心就是这三个注解

  ```java
  @SpringBootConfiguration
  @EnableAutoConfiguration
  @ComponentScan(
      excludeFilters = {@Filter(
      type = FilterType.CUSTOM,
      classes = {TypeExcludeFilter.class}
  ), @Filter(
      type = FilterType.CUSTOM,
      classes = {AutoConfigurationExcludeFilter.class}
  )}
  )
  ```

- 分析@SpringBootConfiguration

  ```java
  @Configuration
  public @interface SpringBootConfiguration {
      @AliasFor(
          annotation = Configuration.class
      )
      boolean proxyBeanMethods() default true;
  }
  ```

  表示当前类是一个spring配置类，没有涉及自动装配

- 分析@ComponentScan

  这个说过很多次了，指定扫描的包

- **分析@EnableAutoConfiguration**

  同样是一个合成注解

  ```java
  @AutoConfigurationPackage
  @Import({AutoConfigurationImportSelector.class})
  public @interface EnableAutoConfiguration {
      String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";
  
      Class<?>[] exclude() default {};
  
      String[] excludeName() default {};
  }
  ```

  - 分析@AutoConfigurationPackage

    ![image-20210309102153797](https://i.loli.net/2021/04/15/l5f2LY3UqbgvZpD.png)

    看来这个Resistrar类就是关键了，看看这个类的源码并调试

    ![image-20210309103033567](https://i.loli.net/2021/04/15/PNlm6tUB35ZnIka.png)
    
    使用debug可以看到metadata就是我们的mainapplication
    
    <img src="https://i.loli.net/2021/04/15/ANpxlmMoHOUtuj7.png" alt="image-20210309103348310" style="zoom: 80%;" />
    
    springboot通过metadata信息得到主类所在的包名，封装到数组后进行传参。**这也是自动扫描默认包的底层实现**
  
  - 分析`@Import({AutoConfigurationImportSelector.class})`
  
    ![image-20210309103958237](https://i.loli.net/2021/04/15/SMd1EsBkH3WV9OK.png)
  
    selectImports方法调用getAutoConfigurationEntry方法
  
    <img src="https://i.loli.net/2021/04/15/xpiNrcZhVPGakT4.png" alt="image-20210309104042793" style="zoom:200%;" />
  
    通过该方法给IOC容器批量导入一批自动配置的组件。
  
  总结：`@EnableAutoConfiguration`是关键(启用自动配置)，内部实际上就去加载`META-INF/spring.factories`文件的信息，然后筛选出以`EnableAutoConfiguration`为key的数据，加载到IOC容器中，实现自动配置功能！
  
  > Springboot自动配置的总结：
  >
  > - SpringBoot先加载所有的自动配置类  xxxxxAutoConfiguration
  > - 每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。xxxxProperties里面拿。xxxProperties和配置文件进行了绑定
  > - 生效的配置类就会给容器中装配很多组件
  > - 只要容器中有这些组件，相当于这些功能就有了
  > - 定制化配置
  >
  > - - 用户直接自己@Bean替换底层的组件
  >   - 用户去看这个组件是获取的配置文件什么值就去修改。
  >
  > **xxxxxAutoConfiguration ---> 组件  --->** **xxxxProperties里面拿值  ----> application.properties**
  >
  > 我的总结：总的来说就是通过配置类：先加载全部配置类，但是并不全部生效，按照条件生效。各个配置类的信息都是从xxxproperties里面拿的，而xxxProperties又和我们的根配置文件绑定。因此，添加或者修改注解可以通过自己编写的@Bean添加，也可通过修改配置文件。一般是第二个，方便又快捷。

### 8. springboot开发web应用的思路

- 引入场景启动器

  > https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter

- 查看自动配置了什么组件

  - 自己分析，引入场景对应的自动配置一般都生效了，组件都注入了
  - 配置文件中`debug=true`开启自动配置报告，可查看自动配置了什么组件

- 配置是否需要修改

  - 参考文件修改

    > https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties

  - 自己分析。xxxxProperties绑定了配置文件的哪些。

- 自定义加入或者替换组件@Bean....

### 9. Lombok插件

```xml
<dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
</dependency>
```

```java
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Dog {
    private String name;
    private int age;
}
```

```java
package com.hhj.controller;

@RestController
@Slf4j
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        log.info("简化日志开发");
        return "HELLO SPRINGBOOT";
    }
}

```

### 10. 热部署工具devtools

```xml
 <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <optional>true</optional>
</dependency>
```

spring-boot-devtools 是一个为开发者服务的一个模块，其中最重要的功能就是热部署。

当我们修改了classpath下的文件（包括类文件、属性文件、页面等）时，会重新启动应用（由于其采用的双类加载器机制，这个启动会非常快）。

### 

### 11. web开发

#### ① 访问静态资源

​	Springboot约定，静态资源放在类路径下，包名可以是`/static /public /resources /META-INF/resource`

​	访问时直接当前项目根路径+静态资源名即可，不需要具体的目录

​	![image-20210309210704077](https://i.loli.net/2021/04/15/VHjioyJbvYICArf.png)

- 原理：静态资源的映射url是/**，请求进来的时候，先去找controller看看有没有匹配的，不能处理的请求在交给静态资源处理器

- 关于静态资源的配置

  ```yaml
  spring:
    mvc:
      static-path-pattern: /res/**
      # 访问静态资源的前缀必须是这个，即使resources下没有这个目录
      # 但是如何配置了这个，欢迎页面和logo图无法正常显示，和源码有关，被写死
    web:
      resources:
        static-locations: [classpath:/public2]
        # 配置这个的话静态资源就必须放在这个目录下，原先的默认目录就不能用了，被替代了
        # 这是一个数组，可以重新配置多个静态资源目录
  ```

- 欢迎页支持

  静态资源路径下的`index.html`就是欢迎页，

- 自定义Favicon

  `favicon.ico`放在静态资源目录下即可

- 静态资源配置自动装配的源码解析

  > 以后补 
  >
  > 具体实现在![image-20210309230905243](https://i.loli.net/2021/04/15/qg48fS3LuvCW9sn.png)

#### ② Springboot与Rest风格

​	Rest风格我们都知道，url改变了，并且可以根据不同的请求方式(POST GET PUSH DELETE) 执行不同的控制器。

- 以前：/getUser 获取用户  */deleteUser* 删除用户   */editUser*  *修改用户*    */saveUser* *保存用户*

- 现在： /user   *GET获取用户*   *DELETE删除用户*   *PUT*修改用户    *POST保存用户*

  但是因为浏览器只能发送post和get请求，因此我们要配置一个核心的Filter：HiddenHttpMethodFilter，将POST/GET请求改成隐藏的请求方式

  -  配置

    ```yaml
    spring:
      mvc:
        hiddenmethod:
          filter:
            enabled: true
    ```

  - 用法

    ```html
    测试REST风格；
    <form action="/user" method="get">
        <input value="REST-GET 提交" type="submit"/>
    </form>
    <form action="/user" method="post">
        <input value="REST-POST 提交" type="submit"/>
    </form>
    <form action="/user" method="post">
        <input name="_method" type="hidden" value="delete"/>
        <input name="_m" type="hidden" value="delete"/>
        <input value="REST-DELETE 提交" type="submit"/>
    </form>
    <form action="/user" method="post">
        <input name="_method" type="hidden" value="PUT"/>
        <input value="REST-PUT 提交" type="submit"/>
    </form>
    ```

    表单提交要这么写，表现上是post，实际上还有一个type='hidden'，真正的提交方式是value

    ```java
    package com.hhj.controller;
    
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
    ```

  - 怎么修改固定的`_method`

    ```java
    @Configuration
    public class MyConfig {
    
        //自定义filter
        @Bean
        public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
            HiddenHttpMethodFilter methodFilter = new HiddenHttpMethodFilter();
            methodFilter.setMethodParam("_m");
            return methodFilter;
        }
    }
    ```

#### ③ springboot接收请求常用注解

- @PathVariable：绑定**Restful**风格url的占位符
- @RequestHeader：绑定请求头信息
- @RequestParam：绑定具体key的请求参数
- @CookieValue：绑定具体key的cookie
- @RequestBody：绑定post方法的所有参数
- @RequestAttribute：绑定request请求域中的参数

`Restful的请求路径"car/3/owner/lisi?age=18&inters=basketball&inters=game"`

怎么提取这个的请求参数呢？首先我们要知道哪些是请求参数

> 控制器地址映射`@GetMapping("/car/{id}/owner/{username}")`
>
> 因此我们可以知道传递过来的参数有：`3 & lisi & age=18 & inters=basketball & inters=game`
>
> 也可以写成这样：`id=3 & username=lisi & age=18 & inters=basketball & inters=game`
>
> 因为Rest风格的url把参数的key值隐藏起来了，这也是它的一个优点

```html
<a href="car/3/owner/lisi?age=18&inters=basketball&inters=game">car/{id}/owner/{username}</a>
```

```java
@GetMapping("/car/{id}/owner/{username}")
public Map<String,Object> getCar(@PathVariable("id") Integer id,
                                 @PathVariable("username") String name,
                                 @PathVariable Map<String,String> pv,
                                 @RequestHeader("User-Agent") String userAgent,
                                 @RequestHeader Map<String,String> header,
                                 @RequestParam("age") Integer age,
                                 @RequestParam("inters") List<String> inters,
                                 @RequestParam Map<String,String> params,
                                 @CookieValue("_ga") String _ga,
                                 @CookieValue("_ga") Cookie cookie){
    Map<String,Object> map = new HashMap<>();

    map.put("id",id);
    map.put("name",name);
    map.put("pv",pv);
    map.put("userAgent",userAgent);
    map.put("headers",header);
    map.put("age",age);
    map.put("inters",inters);
    map.put("params",params);
    map.put("_ga",_ga);
    System.out.println(cookie.getName()+"===>"+cookie.getValue());
    return map;
}
```

测试@RequestBody

```html
<form action="/save" method="post">
    测试@RequestBody获取数据 <br/>
    用户名：<input name="userName"/> <br>
    邮箱：<input name="email"/>
    <input type="submit" value="提交"/>
</form>
```

```java
@PostMapping("/save")
public Map postMethod(@RequestBody String content){
    Map<String,Object> map = new HashMap<>();
    map.put("content",content);
    return map;
}
```

**总结**

> springboot接收参数其实只有三个方式：基本类型、实体类、map

- 关于request域中存值取值的三种方法

  ```java
  map<String,String>
  Model model
HttpRequest req
  ```
  

#### ④ thymeleaf模板引擎

#### ⑤ 拦截器

类似于Servlet开发的过滤器，要想自定义拦截器，必须实现`HandlerInterceptor`接口。

创建一个拦截器

```java
public class MyInterceptor implements HandlerInterceptor {

    // 执行控制器方法之前执行该方法
    // return true 放行 否则不调用控制器方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    // 控制器方法执行后执行该方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 页面渲染后执行该方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```

注册拦截器(实现接口，重写方法)

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // 实现该接口 重写该方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")  //所有请求都被拦截包括静态资源
                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**",
                        "/js/**","/aa/**"); //放行的请求

    }
}
```

> 拦截器和过滤器的区别：
>
> - 实现的功能都差不多
>
> - 过滤器是ServletAPI原生的东西，可以任意使用，但是它不由spring容器管理，也就是不需要添加到spring容器中
> - 拦截器是springmvc的东西，必须添加到容器中才能生效
> - 它们的实现都是创建自己的拦截器并进行注册
>   - 注册可以在xml中配置
>   - 也可以使用配置类的方式
> - 拦截器的原理是拦截器链

#### ⑥ 文件上传

- 表单要添加enctype属性

```html
测试文件上传
<form action="fileLoad" method="POST" enctype="multipart/form-data">
    单个文件上传：<input type="file" name="file"/>
    多个文件上传:<input type="file" name="files"/>
    <input type="submit" value="提交"/>
</form>
```

```java
@RestController
// 文件上传
@Slf4j
public class FileLoadController {

    @PostMapping("/fileLoad")
    public String fileLoad(@RequestPart("file")MultipartFile file,@RequestPart("files") MultipartFile[] files) throws IOException {
        log.info("单个文件：{}\n多个文件：{}",file.getOriginalFilename(),files.length);

        if(!file.isEmpty()){
            // 保存到本地或者服务器
            String filename = file.getOriginalFilename();
            file.transferTo(new File("D:\\"+filename));
        }

        if(files.length>0){
            for(MultipartFile a:files){
                String filename = a.getOriginalFilename();
                a.transferTo(new File("D:\\"+filename));
            }
        }

        return "上传成功";
    }

}
```

#### ⑦ 错误处理

springboot会自动识别当前类目录下的/template/error文件夹，出错时根据文件名返回特定的页面

- 比如404，返回404.html
- 500,返回500.html

#### ⑧ WEB原生API：Servlet、Filter以及Listen的嵌入

- 在配置类使用注解@ServletComponentScan，把原生servlet组件放在那个包下

- 使用RegistrationBean

  ```java
  @Configuration
  public class MyRegistConfig {
  
      @Bean
      public ServletRegistrationBean myServlet(){
          MyServlet myServlet = new MyServlet();
  
          return new ServletRegistrationBean(myServlet,"/my","/my02");
      }
  
  
      @Bean
      public FilterRegistrationBean myFilter(){
  
          MyFilter myFilter = new MyFilter();
  //        return new FilterRegistrationBean(myFilter,myServlet());
          FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
          filterRegistrationBean.setUrlPatterns(Arrays.asList("/my","/css/*"));
          return filterRegistrationBean;
      }
  
      @Bean
      public ServletListenerRegistrationBean myListener(){
          MySwervletContextListener mySwervletContextListener = new MySwervletContextListener();
          return new ServletListenerRegistrationBean(mySwervletContextListener);
      }
  }
  ```

- 注意点：

  > - DispatcherServlet的控制器方法和原生Servlet不是一个东西，一般的springmvc只用到一个Servlet，就是DispatcherServlet
  >
  > - 因为上面那点，所以springIOC的拦截器对原生Servlet不起作用
  >
  >   <img src="https://i.loli.net/2021/04/15/6eSZrtuQVFDOAHd.png" alt="image.png" style="zoom:67%;" />
  >
  > - DispatherServlet对应的配置项时`spring.mvc`

#### ⑨ 配置springboot服务器

​	Springboot默认支持的服务器有Tomcat, Jetty, or Undertow

​	加载服务器的原理是：

- springboot发现当前应用是web应用
- 创建一个web版本的IOC容器，查找匹配对应的webserver工厂TomcatServletWebServerFactory, JettyServletWebServerFactory, or UndertowServletWebServerFactory
- 判断系统导入了那个web服务器的包，springboot-starter默认导入tomcat包
- 创建web服务器，启动
- 配置服务器的配置项时`server.`

  如何切换服务器？更改默认导入的包即可

  ```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
  ```

导入其他服务器的starter依赖

```xml
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-undertow</artifactId>
```
#### ⑩ 如何定制Springboot

定制化的套路一般是：**场景starter** - xxxxAutoConfiguration - 导入xxx组件 - 绑定xxxProperties -- **绑定配置文件项** 

但是springboot已经帮我们作了大部分的工作，所以我们一般只需要修改配置文件。但是也有一些定制化不能靠修改配置文件实现，需要像容器中添加bean，比如拦截器。

常见的定制化操作：

- 修改配置文件

- 编写自定义的配置类 xxxConfiguration+@Bean替换、增加容器中默认组件

- web开发时我们一般编写一个配置类实现 WebMvcConfigurer 接口即可定制化web功能

  使用@Bean给容器中再扩展一些组件

### 12. 数据访问

#### ① 快速入门

- 需要引入场景选择器

  ```xml
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-jdbc</artifactId>
          </dependency>
  ```

  这个是sprng提供给我们的，里面集成了jdbcTemplate、HikariDataSource等，但是没有数据库驱动，因为spingobot不知道我么们使用的是哪个数据库，所以还需要引入mysql驱动

  ```xml
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
          </dependency>
  ```

- 配置默认的HikariDataSurce数据连接池

  如果没有自己添加数据库连接池，springboot默认使用这个

  ```yaml
  spring:  
    datasource:
      url: jdbc:mysql://localhost:3306/sbtest
      username: root
      password: 123abc
      driver-class-name: com.mysql.cj.jdbc.Driver
  ```

- 使用JdbcTemplate进行简单测试

  ```java
  public class SpringbootApplicationMain {
      public static void main(String[] args) {
          ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplicationMain.class, args);
  
          JdbcTemplate bean = run.getBean(JdbcTemplate.class);
          Long aLong = bean.queryForObject("select count(*) from student", Long.class);
          System.out.println(aLong);
  
      }
  ```

#### ② 使用Druid数据连接池

```xml
<dependency>
     <groupId>com.alibaba</groupId>
     <artifactId>druid</artifactId>
     <version>1.1.17</version>
</dependency>
```

切换数据库连接池有两个方式：一是自己定义连接池并导入容器，二是找场景启动器，让它帮住我们自动配置

- 自定义

  ```java
  @Configuration
  public class DruidConfig {
  
      // 默认的自动配置是判断容器中没有才会配@ConditionalOnMissingBean(DataSource.class)
      //    @ConfigurationProperties("spring.datasource")
      @Bean
      @ConfigurationProperties(prefix = "spring.datasource")
      public DataSource dataSource(){
          DruidDataSource druidDataSource = new DruidDataSource();
          // druidDataSource.setUrl();
          // ...可以直接和配置文件绑定，不用这样写
                  //加入监控功能
          druidDataSource.setFilters("stat,wall");
          return druidDataSource;
      }
  }
  ```

  ![image-20210312231640042](https://i.loli.net/2021/04/15/8U6p5osYhwcNj9r.png)

  添加内置监控页面，该页面是一个Servlet，因此要使用之间那种方式为spring容器添加一个servlet

  ```java
      @Bean
      public ServletRegistrationBean addStatViewServlet(){
          ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
          // 可以设置登录用户民和密码
  //        registrationBean.setUrlMappings(Arrays.asList("/*"));
          registrationBean.addInitParameter("loginUsername","root");
          registrationBean.addInitParameter("loginPassword","root");
  
          return registrationBean;
  
      }
  ```

  添加和Spring关联的监控，是一个Filter过滤器

  ```java
  //    @Bean
      public FilterRegistrationBean webStatFilter(){
          WebStatFilter webStatFilter = new WebStatFilter();
  
          FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(webStatFilter);
          filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
          filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
  
          return filterRegistrationBean;
      }
  ```

#### ③ 整合Mybatis

  导入start

  ```xml
  <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.1.3</version>
  </dependency>
  ```

  配置springboot配置文件,只需要配置全局配置文件和mapper映射文件的位置即可

  ```yaml
  # 配置mybatis
  mybatis:
    config-location: classpath:mybatis/mybatis-config.xml
    mapper-locations: classpath:mybatis/mapper/*
  ```

- 使用xml配置查询数据库

  - 编写dao层mapper接口

    ```java
    @Mapper
    // 只有带有@Mapper注解的接口才会使用sqlsessionfatory创建对应的mapper,调用方法
    // 而且该mapper才会被注入到IOC容器中
    public interface StudentMapper {
    
        public Student getStudentById(int id);
    }
    ```

    底层接口添加mapper注解，然后该mapper会被自动注入到IOC容器中，不需要自己`sqlsession.getMapper(Student.class)`

  - 编写mapper接口映射的xml文件

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.hhj.mapper.StudentMapper">
        <select id="getStudentById" resultType="student">
        select * from student where id = #{id}
        </select>
    </mapper>
    ```

  - 编写service层接口和实现类

    ```java
    @Service
    public class StudentServiceImpl implements StudentService {
    
        @Autowired
        StudentMapper studentMapper;
    
        @Override
        public Student getStudentById(int id) {
            Student student = studentMapper.getStudentById(id);
            return student;
        }
    }
    ```

  - controller层调用

    ```java
    @RestController
    public class SqlController {
    
        @Autowired
        StudentService studentService;
    
        @GetMapping("/sql/{id}")
        public Student getUserById(@PathVariable("id") int id){
            Student studentById = studentService.getStudentById(id);
            return studentById;
        }
    }
    ```

  - 结果展示

    ![image-20210313110644504](https://i.loli.net/2021/03/13/8LMtr4kAynXe2Qf.png)

- 使用注解查询数据库

  注解查询和xml查询不同的是有可能不需要写映射的mapper文件，在接口方法上直接使用`@Select` `@Insert`等查询数据库即可，不过最佳实战是简单方法使用注解方式查询，复杂方法建议还是使用mapper映射

  ```java
  @Insert("insert into student(stu,name) values (#{stu},#{name})")
  public int insertStudent(Student student);
  ```

#### ④ 整合Mybatis-plus

​	Mybatis-plus官网：https://baomidou.com/

​	Mybatis-plus是对Mybatis的增强，有了mybatisplus，你甚至都不用写mapper映射文件，常用的增删改查方法也不用写，直接调用即可，真的很强大

```xml
<dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-boot-starter</artifactId>
      <version>3.4.1</version>
</dependency>
```

- 自动配置了映射xxxmaper，只需要直接使用即可

- mapperLocations 自动配置好的。有默认值。classpath:/mapper/\*.xml；任意包的类路径下的所有mapper文件夹下任意路径下的所有xml都是sql映射文件。建议以后sql映射文件，放在 mapper下

- 只需要mapper继承baseMapper即可使用

**快速入门**

- Mapper类

  ```java
  // Mybati plus的mapper接口
  @Mapper
  public interface UserMapper extends BaseMapper<User> {
  
      // 直接重写该方法即可调用
      @Override
      Integer selectCount(Wrapper<User> queryWrapper);
  
      @Override
      List<User> selectList(Wrapper<User> queryWrapper);
  }
  ```

- Service类同样逻辑调用该方法

- Controller类同样逻辑

- 测试结果

  ![image-20210313205301732](https://i.loli.net/2021/04/15/3lvkfBXuwSqjiV7.png)

- BaseMapper提供的常见方法

  <img src="https://i.loli.net/2021/04/15/nwivEsVlGD1rQjA.png" alt="image-20210313205422838" style="zoom:67%;" />

Mybatis-plus实现分页查询

#### ⑤ 整合Redis



未完待续~~







