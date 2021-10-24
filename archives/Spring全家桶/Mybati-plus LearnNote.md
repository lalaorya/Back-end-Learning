### Mybati-plus LearnNote

#### 1. pojo常用注解及关键字

| 注解 \| 关键字 |                 说明                 |
| :------------: | :----------------------------------: |
|   @TableName   |    实体类的类名和数据库表名不一致    |
|    @TableId    | 实体类的主键名和数据库表主键名不一致 |
|  @TableFieId   |   实体类的成员名和表中字段名不一致   |
|   transient    | 使用该关键字可以修饰非表中的成员变量 |

```JAVA
@Data
@TableName("t_user")
public class User {
    @TableId("user_id")
    private Long id;
    @TableField("real_name")
    private String name;
    private Integer age;
    private String email;
    transient private Long managerId;
    private LocalDateTime createTime;
}
```

#### 2. 条件构造器查询

只需要创建queryWrapper对象，在对象中添加查询条件，最后把wrapper对象传给具体查询方法即可

- 模糊查询

  ```java
  /**
       * 查询名字中包含'雨'并且年龄小于40
       * where name like '%雨%' and age < 40
       */
      @Test
      public void selectByWrapper(){
          QueryWrapper<User> queryWrapper = new QueryWrapper<>();
          queryWrapper.like("name","雨").lt("age",40);
          List<User> userList = userMapper.selectList(queryWrapper);
          userList.forEach(System.out::println);
      }
  ```

3

