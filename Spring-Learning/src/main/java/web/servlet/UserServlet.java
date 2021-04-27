package web.servlet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import service.UserService;
import service.UserService2;
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

        ApplicationContext ac = new ClassPathXmlApplicationContext("bean-config.xml");
        /**
         * 方式一：填配置文件中的id获取实例化对象
         * 方式二：eeee 好像也是，不过加个参数不需要强转
         */
        UserService userService1=(UserService)ac.getBean("userService4");
        userService1=ac.getBean("userService4",UserService.class);
        UserService userService3=ac.getBean("userService4",UserService.class);
        System.out.println(userService1==userService3);
        userService1.getUserById(1);

        //------------------------
        UserService userService4=ac.getBean("userService5",UserService.class);
        UserService userService5 = ac.getBean("userService5", UserService.class);
        UserService2 userService2_1=(UserService2)userService5;
        userService2_1.delete();


        /**
         * ----------2. 通过Beanfactory(底层接口）接口获取实例化对象--------------------
         */
        ClassPathResource classPathResource = new ClassPathResource("bean-config.xml");
        BeanFactory xmlBeanFactory = new XmlBeanFactory(classPathResource);
        UserService userService2=(UserService)xmlBeanFactory.getBean("userService");
        userService2=xmlBeanFactory.getBean("userService",UserService.class);
        userService2.getUserById(1);


        //===================依赖注入=================
        UserService userService6=(UserService)ac.getBean("userService6");
        userService6.getUserById(1);
        System.out.println("--------****************------");

        UserService userService7=(UserService)ac.getBean("userService7");
        userService7.getUserById(1);
        System.out.println("----------      ---------------");

        UserService userService8=(UserService)ac.getBean("userService8");
        userService8.getUserById(1);



    }
}
