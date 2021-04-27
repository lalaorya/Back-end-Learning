### json-Notes

1. 概念

   > *Json*是什么？*JSON* 指的是 JavaScript 对象表示法（*J*ava*S*cript *O*bject *N*otation）
   >
   > 类似于XML，是一种存储和传输数据的文件格式。但是相比XML，它体积更小，传输更快，也更加容易被JS解析。
   >
   > 因此，现在前后端的数据交互常用JSON进行传输。

2. 语法

   - 定义

     > 👉JSON数据是由键值对构成的。
     >
     > - 键：必须是字符串，可以用引号引起来，也可以不用
     >
     > - 值：可以是数字/字符串(必须用引号)/布尔类型/数组/json对象/null
     >
     >   **因为值的数据类型很多，因此JSON对象可以表示的数据也更加丰富(疯狂套娃)**
     >
     > 👉使用{}花括号定义json对象
     >
     > 👉键值对之间用 , 逗号分隔
     >
     > 👉数组和原生js一样，用[]保存

     看几个小案例👇

     ```html
     <!DOCTYPE html>
     <html lang="en">
     <head>
         <meta charset="UTF-8">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <title>Document</title>
     </head>
     <body>
         <script>
             var person={
                 name:"zhangsan",
                 age:22,
                 gender:true,
                 address:{
                     "省份":"广东",
                     "地级市":"广州",
                     "区":"海珠区"
                 },
                 work:["程序员","搬运工"]
             }
     
             alert(person)
         </script>
     </body>
     </html>
     ```

     ```html
     <!DOCTYPE html>
     <html lang="en">
     <head>
         <meta charset="UTF-8">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <title>Document</title>
     </head>
     <body>
         <script>
             var persons={
                 "persons":[{
                     name:"zhangsan",
                     age:22,
                     gender:true
                 },{
                     name:"zhangsan",
                     age:22,
                     gender:true
                 },{
                     name:"zhangsan",
                     age:22,
                     gender:true
                 }]
             }
     
             alert(person)
         </script>
     </body>
     </html>
     ```

   - json的解析(获取值)

     > 两种方式
     >
     > - json对象.key    不加引号
     > - json对象[key]     必须加引号
     >
     > ```html
     > <!DOCTYPE html>
     > <html lang="en">
     > <head>
     >     <meta charset="UTF-8">
     >     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     >     <title>Document</title>
     > </head>
     > <body>
     >     <script>
     >         var person={
     >             name:"zhangsan",
     >             age:22,
     >             gender:true,
     >             address:{
     >                 "省份":"广东",
     >                 "地级市":"广州",
     >                 "区":"海珠区"
     >             },
     >             work:["程序员","搬运工"]
     >         }
     > 
     >         alert(person);
     >         //获取json的值
     >         alert(person.name);
     >         alert(person["name"]);
     >         alert(person.work[0]);
     >         //遍历
     >         for(var key in person){
     >             alert(person[key]);//这个key默认是字符串，所以只能用这种方式取值，不能用person.key
     >         }
     >     </script>
     > </body>
     > </html>
     > ```
     >
     > ```html
     > <!DOCTYPE html>
     > <html lang="en">
     > <head>
     >     <meta charset="UTF-8">
     >     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     >     <title>Document</title>
     > </head>
     > <body>
     >     <script>
     >         var persons={
     >             "persons":[{
     >                 name:"zhangsan",
     >                 age:22,
     >                 gender:true
     >             },{
     >                 name:"zhangsan",
     >                 age:22,
     >                 gender:true
     >             },{
     >                 name:"zhangsan",
     >                 age:22,
     >                 gender:true
     >             }],
     > 
     >             "persons2":[{
     >                 name:"zhangsan",
     >                 age:22,
     >                 gender:true
     >             },{
     >                 name:"zhangsan",
     >                 age:22,
     >                 gender:true
     >             },{
     >                 name:"zhangsan",
     >                 age:22,
     >                 gender:true
     >             }]
     >         }
     > 
     >         alert(persons)
     > 
     >         //遍历json
     >         for(var key in persons){
     >             var arr=persons[key];
     >             for(var i=0;i<arr.length;i++){
     >                 for(var key in arr[i]){
     >                     alert(arr[i][key]);
     >                 }
     >             }
     >         }
     >     </script>
     > </body>
     > </html>
     > ```
     >
   
3. JSON和Java对象的序列化和反序列化

   - 使用的是JackSon解析器API

     ```java
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
     
     ```

     **运行结果👇**

     <img src="../../../Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20201112192347502.png" alt="image-20201112192347502" style="zoom:80%;" />

