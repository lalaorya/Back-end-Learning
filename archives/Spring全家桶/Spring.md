### 1. 如何解决类之间的依赖(解耦)

**类与类耦合的解决过程：**

- 反射(Class.forname().instance)

  可以直接创建对象，编译器不出错，减少类与类之间的依赖

  <img src="https://i.loli.net/2021/03/19/W4HFAubgypinm9a.png" alt="image-20201216221903869" style="zoom: 150%;" />

- 配置文件的方式(类加载器读取配置文件+反射创建对象）

  类之间的耦合依旧严重，因为有new其他类的对象。我们想要的是即使某个接口的实现类没写，编译依旧不报错。

- 工厂模式(配置文件+反射)

  通过工厂类创建对象并返回。最初的工厂模式是多例的

- 改进的工厂模式(单例)

  map实现。把每个类的实现对象保持在map中，实现单例。
  
  <img src="https://i.loli.net/2021/03/19/AQXdtG8LcMuWS6g.png" alt="image-20201216222543190" style="zoom: 150%;" />

这就是工厂模式解耦的思路，spring也是使用这个设计模式。

### 2. Spring的体系结构

<img src="https://i.loli.net/2021/03/19/kAnvjF4WzCdDeQJ.png" alt="image-20201217200456861" style="zoom: 150%;" />

### 3. IOC容器

#### ① 概念

Ioc—Inversion of Control，即“控制反转”，不是什么技术，而是一种设计思想。

上面说到类与类之间的耦合问题，Spring解耦的方式就是通过IOC容器，把设计好的类交给容器，容器会帮你自动管理好对象的创建、生命周期等。因此你不需要自己创建对象，只需要从容器中取出你要用的对象，减少了类与类之间耦合（或者说把类与类之间的依赖转移到类与容器的依赖）。至于控制反转是什么意思，就是说之前创建管理对象都是类本身在完成，现在全部交给IOC容器完成，对类的控制权出现了转移，我们就叫做控制反转。详细请看开涛老师对IOC容器的介绍👇

[详细查看此文章--IOC基础](https://www.iteye.com/blog/jinnianshilongnian-1413846)

- IOC容器创建与使用

  ```xml
  <!--通过配置文件创建IOC容器-->
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--把对象的创建交给spring来管理-->
      <bean id="userService" class="service.impl.UserServiceImpl"></bean>
  
  </beans>
  ```

  ```java
  package web.servlet;
  
  import org.springframework.beans.factory.BeanFactory;
  import org.springframework.beans.factory.xml.XmlBeanFactory;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import org.springframework.core.io.ClassPathResource;
  import service.UserService;
  import service.impl.UserServiceImpl;
  
  public class UserServlet {
      /**
       * 获取spring的Ioc核心容器，并根据id获取对象
       *
       * ApplicationContext的三个常用实现类：
       *      ClassPathXmlApplicationContext：它可以加载类路径下的配置文件，要求配置文件必须在类路径下。不在的话，加载不了。(更常用)
       *      FileSystemXmlApplicationContext：它可以加载磁盘任意路径下的配置文件(必须有访问权限）
       *
       *      AnnotationConfigApplicationContext：它是用于读取注解创建容器的，是明天的内容。
       *
       * 核心容器的两个接口引发出的问题：
       *  ApplicationContext:     单例对象适用              采用此接口
       *      它在构建核心容器时，创建对象采取的策略是采用立即加载的方式。也就是说，只要一读取完配置文件马上就创建配置文件中配置的对象。
       *
       *  BeanFactory:            多例对象使用
       *      它在构建核心容器时，创建对象采取的策略是采用延迟加载的方式。也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。
       * @param args
       */
      public static void main(String[] args) {
          /**
           * 原来的方式，new对象
           */
          UserService userService = new UserServiceImpl();
          userService.getUserById(1);
          System.out.println("--------------------------------------------------------------------");
  
          /**
           * ----------1. 通过applicationContext接口获取实例化对象-------------
           */
  		// 加载容器
          ApplicationContext ac = new ClassPathXmlApplicationContext("bean-config.xml");
          /**
           * 方式一：填配置文件中的id获取实例化对象
           * 方式二：eeee 好像也是，不过加个参数不需要强转
           */
          UserService userService1=(UserService)ac.getBean("userService");
          userService1=ac.getBean("userService",UserService.class);
          userService1.getUserById(1);
  
          /**
           * ----------2. 通过Beanfactory(底层接口）接口获取实例化对象--------------------
           */
          ClassPathResource classPathResource = new ClassPathResource("bean-config.xml");
          BeanFactory xmlBeanFactory = new XmlBeanFactory(classPathResource);
          UserService userService2=(UserService)xmlBeanFactory.getBean("userService");
          userService2=xmlBeanFactory.getBean("userService",UserService.class);
          userService2.getUserById(1);
      }
  }
  
  ```

IOC容器的代表就是Beans模块下的**BeanFactory**接口(懒汉式），它是容器最底层的接口，是所有Ioc容器的父接口，提供了Ioc容器最基本的功能

<img src="https://i.loli.net/2021/03/19/ERbxA1N6UzVhgdq.png" alt="image-20201217220622946" style="zoom: 150%;" />

BeanFactory接口的常用的实现类有*XmlBeanFactory*

但是我们一般不用BeanFactory接口，功能太少不够强大，我们一般使用它的子接口**ApplicationContext**（饿汉式），常用的实现类有：**ClassPathXmlApplicationContext**、**FileSystemXmlApplicationContext**

下面是BeanFactory和ApplicationContext接口之间的继承关系图：

<img src="https://i.loli.net/2021/03/19/H8GbCO7UMFKlLXA.png" alt="image-20201217221516923" style="zoom:80%;" />

- 创建Bean的三种方式（构造方法 | 普通方法 | 静态方法）

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
          <!--创建Bean的三种方式 -->
          <!-- 第一种方式：使用默认构造函数创建。
                  在spring的配置文件中使用bean标签在容器中添加该bean。
                  采用的就是默认构造函数创建bean对象，此时如果类中没有默认构造函数，则对象无法创建。-->
  
          <bean id="userService" class="service.impl.UserServiceImpl"></bean>
          
  <!--         第二种方式： 使用(普通工厂中的)普通方法创建对象（使用某个类中的方法创建对象，并存入spring容器）-->
          <bean id="userServiceFactory" class="service.impl.UserServiceImpl"></bean>
          <bean id="userService2" factory-bean="userServiceFactory" factory-method="getInstance"></bean>
  
  <!--         第三种方式：使用(工厂)中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入spring容器)-->
          <bean id="userService3" class="service.impl.UserServiceImpl" factory-method="getInstance2"></bean>
  
  </beans>
  ```

- Bean的细节（生命周期、作用范围（单例 | 多例 | ...））

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
  
  <!--            bean的作用范围调整-->
  <!--            bean标签的scope属性：-->
  <!--                作用：用于指定bean的作用范围-->
  <!--                取值： 常用的就是单例的和多例的-->
  <!--                    singleton：单例的（默认值）-->
  <!--                    prototype：多例的-->
  <!--                    request：作用于web应用的请求范围-->
  <!--                    session：作用于web应用的会话范围-->
  <!--                    global-session：作用于集群环境的会话范围（全局会话范围），当不是集群环境时，它就是session-->
          <bean id="userService4" class="service.impl.UserServiceImpl" scope="prototype"></bean>
      
          <!-- bean对象的生命周期
                  单例对象
                      出生：当容器创建时对象出生
                      活着：只要容器还在，对象一直活着
                      死亡：容器销毁，对象消亡
                      总结：单例对象的生命周期和容器相同
                  多例对象
                      出生：当我们使用对象时spring框架为我们创建
                      活着：对象只要是在使用过程中就一直活着。
                      死亡：当对象长时间不用，且没有别的对象引用时，由Java的垃圾回收器回收
           -->
          <bean id="userService5" class="service.impl.UserServiceImpl"
                scope="singleton" init-method="init" destroy-method="destroy"></bean>
  
  </beans>
  ```

说完控制反转，再来说说**依赖注入DI—Dependency Injection**

> 首先，一个类的依赖是什么？可以是常量、外部文件、其他Bean对象等。体现在一个类中可以是类的成员变量、构造方法的参数等、set方法的参数。
>
> ![image-20201220232013654](https://i.loli.net/2021/01/25/R2OFpQUXny8xLvN.png)
>
> 注入依赖的方法有构造方法注入(constructor-arg标签）以及set方法注入（property标签）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

<!--    ==============依赖注入===================-->
    <!-- spring中的依赖注入
       依赖注入：
           Dependency Injection
       IOC的作用：
           降低程序间的耦合（依赖关系）
       依赖关系的管理：
           以后都交给spring来维护
       在当前类需要用到其他类的对象，由spring为我们提供，我们只需要在配置文件中说明
       依赖关系的维护：
           就称之为依赖注入。
        依赖注入：
           能注入的数据：有三类
               基本类型和String
               其他bean类型（在配置文件中或者注解配置过的bean）
               复杂类型/集合类型
            注入的方式：有三种
               第一种：使用构造函数提供
               第二种：使用set方法提供
               第三种：使用注解提供（明天的内容）
    -->

    <!--  ================1 构造方法注入数据(DI)=================-->
    <!--构造函数注入：
        使用的标签:constructor-arg
        标签出现的位置：bean标签的内部
        标签中的属性
            type：用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型
            index：用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
            name：用于指定给构造函数中指定名称的参数赋值                                        常用的
            =============以上三个用于指定给构造函数中哪个参数赋值===============================
            value：用于提供基本类型和String类型的数据
            ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象

        优势：
            在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功。
        弊端：
            改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
    -->
    <bean id="date" class="java.util.Date"></bean>
    <bean id="userService6" class="service.impl.UserServiceImpl">
        <constructor-arg index="0" value="hhj"></constructor-arg>
        <constructor-arg name="age" value="21"></constructor-arg>
        <constructor-arg ref="date"></constructor-arg>
    </bean>

    <!-- =================2 set方法注入=================        更常用的方式
        涉及的标签：property
        出现的位置：bean标签的内部
        标签的属性
            name：用于指定注入时所调用的set方法名称
            value：用于提供基本类型和String类型的数据
            ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象
        优势：
            创建对象时没有明确的限制，可以直接使用默认构造函数
        弊端：
            如果有某个成员必须有值，则获取对象是有可能set方法没有执行。
    -->
    <bean id="userService7" class="service.impl.UserServiceImpl">
        <property name="name" value="hhhhjjjj" ></property>
        <property name="age" value="21"></property>
        <property name="birthday" ref="date"></property>
    </bean>


    <!-- ==================3 复杂类型的注入/集合类型的注入(同样依靠set方法)=======================
        用于给List结构集合注入的标签：
            list array set
        用于个Map结构集合注入的标签:
            map  props
        结构相同，标签可以互换
        所以只需要记住两个list-value和map-entry
    -->
    <bean id="userService8" class="service.impl.UserServiceImpl">
        <property name="strings">
            <set>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </set>
        </property>

        <property name="list">
            <array>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </array>
        </property>

        <property name="set">
            <list>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </list>
        </property>

        <property name="map">
            <props>
                <prop key="testC">ccc</prop>
                <prop key="testD">ddd</prop>
            </props>
        </property>

        <property name="properties">
            <map>
                <entry key="testA" value="aaa"></entry>
                <entry key="testB">
                    <value>BBB</value>
                </entry>
            </map>
        </property>
    </bean>
</beans>
```

总的来说，依赖注入就是注入类的成员变量，注入的方式有两种，一种是通过set方法，一种是通过构造函数。依赖注入只是一种装配对象的手段，设计的类结构才是基础，如果设计的类结构不支持依赖注入，Spring IoC容器也注入不了任何东西，从而从根本上说**“如何设计好类结构才是关键，依赖注入只是一种装配对象手段”。**（设计好类结构的意思是不是把类需要的属性如其他类的对象**放在成员位置**，在创建对象的时候加以赋值，而不是在方法内部new一个新对象，以此来解决类与类之间的依赖以及耦合？目前的理解是这样)

[依赖注入DI的配置使用](https://www.iteye.com/blog/jinnianshilongnian-1415277)

#### ② 循环依赖

循环依赖就是循环引用，就是两个或多个Bean相互之间的持有对方，比如CircleA引用CircleB，CircleB引用CircleC，CircleC引用CircleA，则它们最终反映为一个环。死循环直到内存遗传报错。此处不是循环调用，循环调用是方法之间的环调用。

循环依赖是类结构不合理与Spring容器特性结合产生的问题，是无法解决的，只能在设计类结构的时候去避免。

<img src="https://i.loli.net/2021/03/19/25cXJjsV9rpInqt.png" alt="image-20201221082901190" style="zoom: 150%;" />

Spring容器循环依赖包括构造器循环依赖和setter循环依赖，那Spring容器如何解决循环依赖呢？**首先来看构造器循环依赖。**

Spring容器会将每一个正在创建的 Bean 标识符放在一个**“当前创建Bean池”**中，Bean标识符在创建过程中将一直保持在这个池中，因此如果在创建Bean过程中发现自己已经在“当前创建Bean池”里时将抛出`BeanCurrentlyInCreationException`异常表示循环依赖；而对于创建完毕的Bean将从“当前创建Bean池”中清除掉。

<img src="https://i.loli.net/2021/03/19/lZLFBNCdYXg5ybm.png" alt="image-20201221083419998"  />

大白话：也就是说，Spring在读取配置文件时，读到beanA，就新建了一个A对象，并把它放入当前创建Bean池，此时A对象已经创建了，只是属性还没赋值。赋值时发现需要创建B对象，然后就递归调用创建B对象，把B对象放入Bean池。发现B对象依赖C对象，又递归调用创建C对象，把C对象放入Bean池。重点来了，发现C对象依赖A对象，递归调用创建A对象放入Bean池，但是此时Bean池中已经有A对象了，Spring报错`BeanCurrentlyInCreationException`。也就是上图的1--4冲突。

**Spring正是通过这种设计解决构造器循环依赖，其实没解决，只是发生循环依赖时候会抛出异常，让你改进。**

**接着来看set方式注入产生循环依赖**

使用set方法注入创建对象的方式与构造函数创建有所不同，它是先创建好所有对象放入Bean池，再为创建好的对象添加属性(注入依赖)。

<img src="https://i.loli.net/2021/03/19/HoguiIrZBtGXqDm.png" alt="image-20201221085126675" style="zoom: 150%;" />

因此，使用set方法创建对象(单例模式下)，不会产生循环依赖。

但是在多例模式下(每次调用创建一个对象)，又会产生循环依赖。因为对于“prototype”作用域Bean，Spring容器无法完成依赖注入，因为“prototype”作用域的Bean，Spring容器不进行缓存，因此无法提前暴露一个创建中的Bean。

大白话：多例模式下，A依赖B的对象，但是此使B对象还没被使用，故没有对象，从而报错`BeanCurrentlyInCreationException`

总结

> Spring中一个bean依赖于另一个bean的时候，可以通过构造器注入或setter注入，其中setter注入又可以分为单例和多例(prototype)。
> 1.如果是构造注入或多例模式的setter注入的时候，循环依赖是解决不了的；
> 2.如果是单例setter注入则可以解决掉循环依赖问题。原因在于Spring是先将Bean对象实例化之后再设置对象属性的而不是一次性将bean对象连同属性一起构造的。

[更多关于DI的知识](https://www.iteye.com/blog/jinnianshilongnian-1415461)

### 4. Spring的注解开发

#### ① xml文件中的配置

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    
<!--    告知spring要扫描哪个包-->
    <context:component-scan base-package="com.hhj"></context:component-scan>

</beans>
```

#### ② 九大注解

1. **用于创建对象(@Component | @Controller | @Service | @Repository)**

   ```java
   @Component(value = "userService")
   @Component("userService")
   @Component //不写默认value=类名(首字母小写)
   ```

   Component后面三个注解本质上与Component一摸一样的，是Spring为适应mvc架构提供的三种注解，使我们的代码结构更加清晰

   ```markdown
   @Controller 表现层
   @Service 业务层
   @Repository 持久层
   ```

2. **用于注入数据的**

   1. **@Autowired**

      >- 自动按照类型注入，相当于xml配置中使用propery标签进行依赖注入
      >  - 如果Spring容器中没有匹配的类型，会抛出错误
      >  - 如果Spring容器中有多个匹配时，使用**变量**名称查找bean对象，找得到注入成功，找不到报错
      >- 只能注入bean类型的对象
      >- 使用Autowired注解时，set方法就不是必须的

      ```java
      @Autowired
      private UserDao userDao1;
      @Autowired
      public UserService(UserDao userDao){}
      @Autowired
      public setUserDao1(UserDao userDao){}
      // 可以用在成员变量、构造方法、set方法等等任何方法上
      ```

      如果Autowired找不到匹配的bean，一般情况下会抛出错误。但是可以通过设置@Autowired(required = false)让Spring跳过没有匹配的属性，这个时候该属性就属于未装配的状况null。

   2. **@Qualifier**

      > 按照类型注入的基础上再按照名称注入，必须和一起Autowired使用，用于帮Autowired指定注入bean的id

      ```java
      @Autowired
      @Qualifier(value = "userDao2")
      private UserDao userDao1;
      ```

   3. **@Resource**

      > 直接按照bean的id注入，综合了前两个注解
      >
      > 使用的属性是name，不是value！！同样只能注入bean类型

      ```java
      @Resource(name = "userDao2")
      private UserDao userDao2;
      ```

   4. **@Value**

      > 用于注入基本类型和String类型（集合类型数据只能使用xml配置的方式注入)
      >
      > 使用的属性是value，可以使用Spring中的EL表达式：$(表达式)

      ```java
      @Value(value = "hhj")
      private String name;
      
      @Value(value = "22")
      private Integer age;
      ```

3. **用于改变作用范围的(@Scope)**

   ```java
   @Scope("prototype")
   //@Scope("singleton")
   public class UserServiceImpl implements UserService{
       @Value(value = "hhj")
       private String name;
   
       public UserServiceImpl() {}
   }
   ```

4. **和生命周期相关的(@PreDestroy | @PostConstruct)**

   > 用于指定初始化方法和销毁方法

5. **和IOC容器配置相关的(@Configuration | @ComponentScan)**

   > 注解开发中我们通过声明一个类为Spring配置类，对配置类进行注解配置。可以实现完全不需要bean.xml，可以说剩下的代码就是来取代xml配置的

   ```java
   // 标记当前类为Spring配置类
   @Configuration
   // 告知SpringIOC要扫描的包
   // @ComponentScan(value = "com.hhj")
   @ComponentScan(basePackages = "com.hhj")
   // 要是要扫描的包有多个的话，可以使用数组形式
   @ComponentScan(basePackages = {"com.hhj","com.hhj2"})
   public class SpringConfig {
   
   }
   ```

   关于Spring的配置类这里有几点需要注意：

   - 配置类就是Spring创建IOC容器的入口，Spring会根据配置类的配置信息去扫描某个包来创建IOC容器

   - 配置类可以有多个，它们之间的关系可以是兄弟关系，也可以是父子关系

     - 兄弟关系：多个配置类，使用AnnotationConfigApplicationContext创建对象是添加多个配置文件即可，像下面这样

       ```
       // 多个配置类的情况
       AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class,jdbcConfig.class);
       ```

     - 父子关系：一个主配置类，多个子配置类。具体实现是在主配置类通过@import注解来添加子配置类

       ```java
       @Configuration
       @Import(JdbcConfig.class)
       @ComponentScan(basePackages = "com.hhj")
       public class SpringConfig {    }
       ```

6. **和不能修改源码的bean对象相关的(@Bean)**

   > 有时候我们使用到其他人编写的工具类，如阿帕基的dbutils、jdbc相关的类时，因为我们无法修改它的源码，自然不能使用上面的方法把这些bean对象添加到我们的容器。
   >
   > Spring的@Bean注解可以让我们把当前方法的返回值添加到IOC容器，可以使用name属性指定key，不写默认为方法名，value为返回值(一般为bean对象)

   ```java
   // 标记当前类为Spring配置类
   @Configuration
   // 告知SpringIOC要扫描的包
   //@ComponentScan(value = "com.hhj")
   @ComponentScan(basePackages = "com.hhj")
   public class SpringConfig {
   
       private QueryRunner queryRunner;
   
       @Bean(name = "queryRunner")
       @Scope("prototype")
       // 当我们使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象。查找的方式和Autowired注解是一样的
       public QueryRunner getQueryRunner(DataSource dataSource){
           return new QueryRunner(dataSource);
       }
   
       @Bean(name = "dataSource")
       public DataSource getDataSource(){
           ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
           try {
               comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
               //还有url、name、password
           } catch (PropertyVetoException e) {
               e.printStackTrace();
           }
           return comboPooledDataSource;
   
       }
   
   
   }
   ```

7. **和读取配置类信息创建IOC容器相关的(annotationConfigApplicationContext实现类)**

   > 看完上面相关的代码，你或许会有个疑问：Spring怎么知道哪个类是配置类呢？不知道配置类就无法创建ioc容器
   >
   > 之前读取配置文件信息我们使用的一直都是 ClassPathXmlApplicationContext实现类，读取配置类信息我们使用的是`AnnotationConfigApplicationContext`

   ```java
   package com.hhj.web.servlet;
   
   public class UserServlet {
       public static void main(String[] args) {
   		
           AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
           UserService userService3 = annotationConfigApplicationContext.getBean("userServiceImpl", UserService.class);
           userService3.getUserById(1);
   		QueryRunner runner1 = annotationConfigApplicationContext.getBean("queryRunner", QueryRunner.class);
           QueryRunner runner2 = annotationConfigApplicationContext.getBean("queryRunner", QueryRunner.class);
   
           System.out.println((runner1==runner2)+"------");
       }
   }
   
   ```

8. **用于导入外部资源的注解(@PropertySource)**

9. 

10. 

### 5. Spring整合junit

#### ① 导入相关maven依赖

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.1.1.RELEASE</version>
    <scope>test</scope>
</dependency>
```

#### ② 测试类中创建IOC容器并测试

```java
import com.hhj.service.UserService;
import conf.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// ① 通过配置类创建容器
@ContextConfiguration(classes = SpringConfig.class)
// ② 通过xml配置文件创建容器
@ContextConfiguration(locations = "classpath:bean-config.xml")
public class SpringTestJunit {

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        userService.getUserById(1);
    }
}
```

### 6. AOP

面向切面编程，实现通用功能与类的解耦。

简单来说就是每个类中都有的必须实现的功能，比如安全、事务、日志等。AOP就是把通用的功能抽取出来再织入需要的类，不用每个类都写重复的代码。

#### ① AOP中的常用术语

1. 切面：就是被抽取的通用的功能，一般为一个类

2. 通知：定义了切面是什么以及何时使用

   ![image-20210223172150486](https://i.loli.net/2021/03/19/lnz83T2HgNyBpbo.png)

3. 连接点：程序能够应用通知的所有点

4. 切点：连接点有很多，但是连接点被织入通知后就变成了切点。没有被织入通知的连接点不是切点

5. 织入：把切面应用到目标对象并创建新的代理对象的过程。有三种方式织入

   ![image-20210223172739461](https://i.loli.net/2021/03/19/L1ye569vtjRl7xI.png)

#### ② 使用注解实现AOP

### 7. 异常处理









​    





