package pers.hhj.day09_json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDemo {

    public static void main(String[] args) throws JsonProcessingException {
        User user1 = new User("zhangsan", "123");
        User user2 = new User("llisi", "345");

//        创建Jackson的核心对象
        ObjectMapper objectMapper = new ObjectMapper();
        /***
         * Java对象转JSON：(序列化
         * 两大常用方法：
         *      1. writeValue(参数1 , obj)
         *          参数1：
         *              File对象。把obj对象转化为json字符串并保持到指定文件
         *              输出流对象。.........保存到输出流(字节输出流/字符输出流)中
         *          obj对象：
         *      2. writeValueAsString(参数1)    输出成字符串
         *          参数1：
         *              可以是对象(例如：学生对象
         *              List对象
         *              Map对象
         *
         *  JSON转Java对象：(反序列化
         *      只需要把write改为read即可
         *      ReadValue(json字符串,对象.class)
         */

        String user1Json = objectMapper.writeValueAsString(user1);
        System.out.println(user1Json);

        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        String listJson = objectMapper.writeValueAsString(list);
        System.out.println(listJson);

        Map userHashMap = new HashMap();
        userHashMap.put("zhangsan","123");
        userHashMap.put("lisi","111");
        String mapJson = objectMapper.writeValueAsString(userHashMap);
        System.out.println(mapJson);

    }
}
