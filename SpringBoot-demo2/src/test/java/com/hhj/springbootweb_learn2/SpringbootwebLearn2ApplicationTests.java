package com.hhj.springbootweb_learn2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
class SpringbootwebLearn2ApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Test
    void contextLoads() {
        Long aLong = jdbcTemplate.queryForObject("select count(*) from student", Long.class);
        System.out.println(aLong);
    }

}
