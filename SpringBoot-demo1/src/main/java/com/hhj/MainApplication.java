package com.hhj;

import com.hhj.config.MyConf;
import com.hhj.domain.Car;
import com.hhj.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;

@SpringBootApplication(scanBasePackages = "com.hhj")

public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(MainApplication.class, args);
        String[] beanDefinitionNames = application.getBeanDefinitionNames();
        for(String s:beanDefinitionNames) {
            System.out.println(s);
        }

    //        ------------------
        MyConf myConf = application.getBean("myConf", MyConf.class);
        User user = myConf.user();
        User user1 = myConf.user();
        System.out.println(user==user1);
        User user2 = application.getBean("user", User.class);
        System.out.println(user==user2);


        Car car = application.getBean("car", Car.class);
        System.out.println(car);

        System.out.println('z'-'a');


    }


}
