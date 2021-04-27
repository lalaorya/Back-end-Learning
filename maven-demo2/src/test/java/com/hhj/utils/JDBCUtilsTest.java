package com.hhj.utils;

import org.junit.Test;


public class JDBCUtilsTest {

    @Test
    public void testGetConnection(){
        JDBCUtils jdbcUtils=new JDBCUtils();
        jdbcUtils.getConnection();
    }

}
