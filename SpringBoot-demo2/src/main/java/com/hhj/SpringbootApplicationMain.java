package com.hhj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringbootApplicationMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplicationMain.class, args);


        JdbcTemplate bean = run.getBean(JdbcTemplate.class);

        Long aLong = bean.queryForObject("select count(*) from student", Long.class);
        System.out.println(bean.getDataSource().getClass().getName());
        System.out.println(aLong);

        //-----------------------------------------------------
        


    }
}
