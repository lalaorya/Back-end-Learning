package com.hhj.config;


import ch.qos.logback.core.db.DBHelper;
import com.hhj.domain.Dog;
import com.hhj.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Configuration
//3、proxyBeanMethods：代理bean的方法
//        *      Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
//        *      Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
//        *      组件依赖必须使用Full模式默认。其他默认是否Lite模式
@Configuration(proxyBeanMethods = true)
@Import({User.class, DBHelper.class})
public class MyConf {
    @Autowired
    Dog mydog;

    @Bean
    public User user(){
        return new User("hhj",mydog);
    }

    @Bean
    @ConditionalOnBean(name = "user")
    public Dog dog(){
        return  new Dog("tom",5);
    }
}
