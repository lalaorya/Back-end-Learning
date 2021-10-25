

## MyBaits-Note🏃‍♀️🏃‍♀️

#### 1. MyBaits的优势

- 为什么我们不用Hibernate?

  > ![image-20201203172556092](https://i.loli.net/2021/01/04/wtIsCPlLO9kxWqc.png)
  >
  > 大白话:Hibernate的设计思想是把javaBean和数据库表进行全映射,具体是如何操作数据库的全部进行了"黑箱"处理. 使得我们无法对SQL语句进行优化. (其实是可以优化的,但是你需要深入学习Hibernate的底层,代价太大了)
  >
  > 再来说说全映射,它是把数据库表中的全部记录都映射到javabean,但是有时候我们其实只需要某列的记录,不需要全部地记录,不适用于我们具体的业务需求

- Mybaits的优点

  > ![image-20201203172536050](https://i.loli.net/2021/01/04/qQc4VSGtzwRvyL3.png)
  >
  > 大白话: 把SQL语句抽离成配置文件,使得我们可以对SQL语句进行优化,更灵活的从数据库中查找记录


#### 2. mybatis是如何使用的？

- 根配置文件，用于加载连接数据库的基本信息(driver、url、password等)以及注册其他SQLMapper文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/table_mybatis?serverTimezone=GMT%2B8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="123abc"/>
              </dataSource>
          </environment>
      </environments>
  
      <mappers>
  <!--        注册一映射的sqlmapper文件-->
          <mapper resource="UserMapper.xml"/>
      </mappers>
  </configuration>
  ```

- SQLMapper配置文件

  > 一般为每张表一个配置文件，每个配置文件可以定义多条SQL语句。通过namespace(命名空间)来区分每个不同的配置文件，通过select--id来区分每一条不同的sql语句。
  >
  > 这两个坐标即可确定唯一的一条sql语句

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="UserMapper">
      <select id="getUserById" resultType="com.hhj.domain.User">
          select * from table_user where id = #{id}
      </select>
  </mapper>
  ```

- 测试单元

  ```java
  package com.hhj.day01;
  
  import com.hhj.domain.User;
  import org.apache.ibatis.io.Resources;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;
  import org.apache.ibatis.session.SqlSessionFactoryBuilder;
  import org.junit.jupiter.api.Test;
  
  import java.io.IOException;
  import java.io.InputStream;
  
  public class HelloMybatis {
  
      @Test
      public void findById() throws IOException {
          //1. 通过mybatis下的Resouces工具类快速加载配置文件
          InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
          //2. 创建sql会话工厂类
          SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
          //3. 建立会话()
          SqlSession sqlSession = sessionFactory.openSession();
          //4. 根据UserMapper.xml的命名空间选择执行哪条sql
          User user = sqlSession.selectOne("UserMapper.getUserById", 17);
  
          sqlSession.close();
  
          System.out.println(user);
      }
  }
  ```

#### 3. 接口化编程

- 设计思想

  - 给每一张表创建一个操作接口，接口中定义了各种针对此表记录进行增删改查的抽象方法。
  - 将该接口与具体的Mapper配置文件进行全映射。具体是通过配置文件的namespace等于全接口名
  - 将该接口的每个抽象方法与Mapper配置文件的select标签进行全映射。具体是通过配置文件的id等于方法名

  通过这种设计，我们就可以更加规范严谨、逻辑清晰的去操作数据库中的每一个表了。和之前学习的dao、daoImpl类似，但是好处就是我们不用去想如何实现的，因为mybaits已经帮我们实现了(黑箱处理)，我们只需要确定sql语句以及返回值类型即可。

- UserMapper接口

  ```java
  package com.hhj.day01;
  
  import com.hhj.domain.User;
  
  public interface UserMapper {
      //定义方法
      public User getUserById(int id);
  }
  ```

- UserMapper配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <!--命名空间要与操作此表的接口的全类名一致,实现配置文件与接口的映射，把具体实现黑箱处理。我们只需要写好sql，给定返回值类型即可-->
  <mapper namespace="com.hhj.day01.UserMapper">
  	<!--与方法名对应-->
      <select id="getUserById" resultType="com.hhj.domain.User">
          select * from table_user where id = #{id}
      </select>
  </mapper>
  ```

- 测试单元

  通过sqlSession会话获取接口实现类，通过实现类调用方法执行配置文件中对应的sql语句

  mapper.getClass是代理对象，也就是说内部是通过代理对象的方式实现的

  ```java
  @Test
      public void test() throws IOException {
          InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
          SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
          SqlSession sqlSession = sessionFactory.openSession();
          try{
              // 获取UserMapper的实现类对象---重点！！
              UserMapper mapper = sqlSession.getMapper(UserMapper.class);
              
              User user = mapper.getUserById(17);
              System.out.println(user);
              System.out.println(mapper.getClass());
          }finally {
              sqlSession.close();
          }
      }
  ```

- 谈谈sqlSession

  > 本质上就是一个Connection，非线程安全的，每此使用都要重新创建

#### 4. 全局配置文件mybatis-config.xml的配置

- properties标签：引入外部配置文件，不常用。用的时候查一波即可

  <img src="https://i.loli.net/2021/04/08/N7dW8DoZkcYFae5.png" alt="image-20201204220200059" style="zoom:67%;" />

- environments和environment标签 --- 重点

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  <!--    dafault属性选择使用不同的环境(不同的数据库-->
      <environments default="development">
  
  <!--        每个environment都代表一个数据库环境，id是它的唯一标识-->
          <environment id="development">
  <!--            transactionManager是事务管理器，有JDBC和MANAGED两种，也支持使用自定义的事务管理器-->
              <transactionManager type="JDBC"/>
  <!--            type属性执行数据连接池的类型：UNPOOLED(不使用连接池)|POOLED(使用默认的连接池)|JNDI(使用这个连接池，不了解)|也可以使用自定义的数据库连接池-->
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/table_mybatis?serverTimezone=GMT%2B8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="123abc"/>
              </dataSource>
          </environment>
          
  <!--        //测试用连接池-->
          <environment id="test">
              <transactionManager type="JDBC"></transactionManager>
              <dataSource type="UNPOOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/table_mybatis?serverTimezone=GMT%2B8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="123abc"/>
              </dataSource>
          </environment>
      </environments>
  
      <mappers>
  <!--        注册一映射的sql语句-->
          <mapper resource="UserMapper.xml"/>
      </mappers>
  </configuration>
  ```

  其实这个也没啥内容，也就是对数据库的使用配置，需要配置事务管理器、连接池类型，你可以使用Mybatis提供的，也可以自己定义(实现java提供的接口，然后在cofignation.class注册即可）

  自定义事务管理器需要实现 [JdbcTransactionFactory] 接口，自定义数据库连接池需要实现 [DataSourceFactory] 接口

- typeAliases标签----给全类名起别名

  ```xml
   <!--    给domain类型起别名，在写返回值类型的时候就可以使用别名，不用写全类名-->
      <typeAliases>
          <!--        默认别名是类名的首字母小写，也可以使用alias属性更改-->
          <typeAlias type="com.hhj.domain.User"/>
          ！！！！没有默认，必须指定alias属性，不然会报错！！！
          <typeAlias type="com.hhj.domain.User" alias="user"/>
  <!--        也可以为整个domain包按默认方式起别名-->
  <!--        <typeAlias type="com.hhj.domain"/>-->
  <!--        最后一种方式是在具体实体类添加注解方式起别名，优先级最高-->
      </typeAliases>
  ```

  ```java
  @Alias("userr")
  public class User {
      private int id;
      private String username;
      private String password;
      ...
  }
  ```

  在测试的时候还出现了这样的一个错误，觉得需要注意一下

  > 元素类型为 "configuration" 的内容必须匹配 "(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)"。

  configuration标签中子标签的顺序有规定

  ⭕mybatis还对非自定义的java内置的数据类型(int、double、list、set...)起了别名，具体查阅：https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases

- plugins插件----非重点

  对sql语句执行的某个方法进行拦截

- typeHandlers

  类型处理器，把从数据库中取出来的值已合适的方式转换为Java类型

- databaseIdProvider----重点

  数据库厂商标识，因为不同的数据库执行sql的过程是不同的，因此可以配置数据库厂商标识，在select标签定义sql时使用databaseId属性指定使用什么数据库执行sql语句

  ```XML
    <!--        数据库厂商标识,起别名-->
      <databaseIdProvider type="DB_VENDOR">
  <!--        给不同的数据库起别名，指定数据库时使用别名-->
          <property name="SQL Server" value="sqlserver"/>
          <property name="MySQL" value="mysql"/>
          <property name="Oracle" value="oracle"/>
      </databaseIdProvider>
  ```
  
  ```XML
  <select id="getUserById" resultType="userr" databaseId="mysql">
          select * from table_user where id = #{id}
  </select>
  ```
  
  ps：使用哪个数据库执行sql时，你的environments就必须dafault那个environment
  
- mapper标签

  有两个方式注册SQL语句，一是通过设置resource属性值为UserMapper此类配置文件，根据配置文件中的信息注册SQL语句，也就是我们之前一直使用的方式。二是通过设置class属性值为dao接口的全类名，为dao接口的每个方法添加select注解，定义sql语句，以这种方式来注册sql语句

  ```xml
    <mappers>
  <!--        注册一映射的sql语句-->
        <mapper resource="UserMapper.xml"/>
  
  <!--        基于注解-->
          <mapper class="com.hhj.domain.User"/>
      </mappers>
  ```
  
  ```java
  //    基于注解的放肆，不用写UserMapper文件，但是还是需要在config文件注册(class属性)
      @Select("select * from table_user where name=#{name}")
      public User getUserByName(String name);
  ```

#### 5. 映射文件的配置

- insert标签--增

  对数据库表进行增加操作时因为有自增序列的存在比较特殊，我们并不知道数据库表中下一个id是多少，因此无法在javabean类中指定自增序列id的值。

  mybatis也考虑到了这一点，因此在insert标签中添加了userGeneratedKeys属性和keyProperty属性来帮助我们解决这个问题

  最初版本，无法实现自增序列的赋值

  ```xml
  <insert id="addUser">
          insert into table_user(username,password,email) values (#{username},#{password},#{email}) </insert>
  ```
  
  v2
  
  ```xml
  <!--useGeneratedKeys：是否使用自增序列    keyProperty：自增属性的属性名 -->
  <insert id="addUser" useGeneratedKeys="true" keyProperty="id">
          insert into table_user(username,password,email) values (#{username},#{password},#{email})
  </insert>
  ```
  
  ⭕对于不支持自增序列的数据库，如oracle，可以使用这种解决方式：
  
  ```xml
  	<!-- 
  	获取非自增主键的值：
  		Oracle不支持自增；Oracle使用序列来模拟自增；
  		每次插入的数据的主键是从序列中拿到的值；如何获取到这个值；
  	 -->
  	<insert id="addEmp" databaseId="oracle">
  		<!-- 
  		keyProperty:查出的主键值封装给javaBean的哪个属性
  		order="BEFORE":当前sql在插入sql之前运行
  			   AFTER：当前sql在插入sql之后运行
  		resultType:查出的数据的返回值类型
  		
  		BEFORE运行顺序：
  			先运行selectKey查询id的sql；查出id值封装给javaBean的id属性
  			在运行插入的sql；就可以取出id属性对应的值
  		AFTER运行顺序：
  			先运行插入的sql（从序列中取出新值作为id）；
  			再运行selectKey查询id的sql；
  		 -->
  		<selectKey keyProperty="id" order="BEFORE" resultType="Integer">
  			<!-- 编写查询主键的sql语句 -->
  			<!-- BEFORE-->
  			select EMPLOYEES_SEQ.nextval from dual 
  			<!-- AFTER：
  			 select EMPLOYEES_SEQ.currval from dual -->
  		</selectKey>
  		
  		<!-- 插入时的主键是从序列中拿到的 -->
  		<!-- BEFORE:-->
  		insert into employees(EMPLOYEE_ID,LAST_NAME,EMAIL) 
  		values(#{id},#{lastName},#{email<!-- ,jdbcType=NULL -->}) 
  		<!-- AFTER：
  		insert into employees(EMPLOYEE_ID,LAST_NAME,EMAIL) 
  		values(employees_seq.nextval,#{lastName},#{email}) -->
  	</insert>
  ```
  
- 参数获取问题

  当方法要求输入多个参数时(之前写的都是一个参数的），如：

  ```java
  //    多个参数的方法
      public User getUserByUsernameAndPassword(String username,String password);
  ```
  在select、insert等标签写sql的时候怎么添加参数呢？原来只有一个参数时，参数名可以随便写，因为肯定是对应他。
  
  mybatis默认把多个参数设置成**map**数据结构，key值是从param1~paramn，value就是你传入的参数。因此你可以这样写sql
  
  ```xml
  <!--    多个参数时使用命令参数-->
    <select id="getUserByUsernameAndPassword" resultType="user">
        select * from table_user where username=#{param1} and password=#{param2}
    </select>
  ```
  
    但是写params1啥的不清晰，不能见名知意。所以可以通过param注解的方式给每个形参添加别名
  
  ```java
  	  public User getUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
  ```
  
  ```xml
    <select id="getUserByUsernameAndPassword" resultType="user">
            select * from table_user where username=#{username} and password=#{password}
    </select>
  ```

  其实，当你传入bean对象时，bean对象也被封装为map，key值就是bean类的属性名，因此我们可以直接使用属性名取出对应参数。

  ⭕综上，针对多个参数传递和设置我们有以下几种分析：

  - 如果多个参数正好是我们业务逻辑的数据模型，可以直接传入pojo(bean类)，#{属性名}取出传入的pojo属性值
  
  - 如果多个参数不是业务逻辑的数据模型，为了方便，我们可以自己封装map传入，#{key}取出map中对应的值
  
  - 如果多个参数不是业务逻辑中的数据模型，但是经常使用(如分页对象page)，我们可以封装一个TO数据传输对象，#{属性名}取出TO属性值
  
  - 如果多个参数被封装成一个Collection(List|Set)类型、或者数据类型时，会被特殊处理，当然也会被封装到map中，但是它们的key不再是param1，而是：
  
    - Collection：collection
    - List：list
    - 数组：array
    
    
    #{list[0]},#{array[0]}这般取值

  ⭕ 参数获取时#{}与${}的区别

  - #{}：是以预编译的形式，将参数设置到sql语句，PreparedStatement，防止sql注入
  
    #{}会在拼接时加上“”，因此只能拼接我们要的参数，而${}不会，直接加，因此什么都能拼，所以有可能有SQL注入的问题
  
  - ${}：sql字符串拼接的方式，statement，会有安全问题
  
    使用哪个？在不能使用占位符预编译的地方(分表、排序）可以使用${}字符串拼接
  
    ```sql
    select * from user_{index} where ....
    select * from user order by ${name} ${order}
    select * from user where ${column} = #{value}
    ```
  
    也就是说，${}会被传入参数直接替换，#{}会被?代替。
  
- select标签

  >  查询永远都是数据库进行得最多的东西，因此查询标签select有很多很重要的属性！！👈

  - resultMap属性

    自定义某个javaBean的封装规则，就是指定某一个映射到javabean的哪个属性。当数据库列名与属性名不一致且不符合驼峰语法时，都需要使用resultMap对结果进行封装。

    和作用resultType差不多，设置返回值映射，但是resultType的封装不是自定义的，同时resultMap可以支持更复杂的查询结果封装。

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
    <mapper namespace="com.hhj.day02.StudentMapper">
        <resultMap id="getStudentById" type="student">
    <!--        使用id标签定义主键底层会有优化-->
            <id column="id1" property="id"/>
    <!--        其他普通列使用result标签封装，column：列名，property：对于属性名-->
            <result column="name1" property="name"/>
            <result column="gender1" property="gender"/>
            <result column="age1" property="age"/>
            <result column="major1" property="major"/>
        </resultMap>
        
        <!-- public Student getStudentById(int id); -->
        <select id="getStudentById" resultMap="getStudentById">
            select * from table_student where id1=#{id}
        </select>
        
    </mapper>
    ```

    ⭕ 使用resultMap进行多表关联查询

    1. 可以使用表名.属性名方式，缺点是多表间有相同列时会出错以及无法处理复杂的关联查询(属性是对象)

    ```xml
    <select id="getStudentById1" resultMap="getStudentById">
        <!--表的连接-->
         select * from table_student s,table_major m where s.id=m.id and s.id=#{id}
    </select>
    ```

    ```xml
    <resultMap id="getStudentById" type="student">
            <id column="id" property="id"/>
            <result column="name" property="name"/>
            <result column="gender" property="gender"/>
            <result column="age" property="age"/>
    		<result column="major" property="major.id"/>
    		<result column="majorName" property="major.majorName"/>
    </resultMap>
    ```

    ​	这种方法的改进版是使用association标签对对象属性进行封装|分步查询

    ```xml
    <resultMap id="getStudentById2" type="student">
          
            <id column="id" property="id"/>
            <result column="name" property="name"/>
            <result column="gender" property="gender"/>
            <result column="age" property="age"/>
            <!--1. 当关联属性是一个对象时，可以使用association来进行关联查询封装结果-->
            <association property="major" javaType="com.hhj.domain.Major">
                <id column="major" property="id"/>-->
                <result column="majorName" property="majorName"/>
            </association>
    
    		<!--2. 也可以分部查询，第一步的输出作为第二步的输入-->
            <association property="major" column="major" select="com.hhj.day02.MajorMapper.getMajorById" fetchType="lazy">
            </association>
    
        </resultMap>
    ```

    2. 属性是list集合时，使用collection标签

       collection封装

       collection分步查询

    3. dis ...鉴别器

- update

- insert

- delete

#### 6. 动态SQL

动态SQL就是使用OGNL语法对SQL语句添加一些动态的逻辑判断，使我们能够动态的生成SQL语句，以适应我们复杂的业务逻辑。

其中使用的标签有if、choose(when、otherwise)、trim(where,set)、foreach

1. if标签

   需求是根据传入的student对象，返回数据库中的记录

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   
   <mapper namespace="com.hhj.day03.StudentMapperDynamicSQL">
       <select id="getStudentByConditionIf" resultType="com.hhj.domain.Student">
           <!--如果这样写的话问题很大,传入的student、对象必须每一项都不为空，我们想要的是哪个不为空就拼接哪个-->
           select * from table_student where
           <!--id=#{id} and name=#{name} and gender=#{gender} and age=#{age} and major=#{major.id}-->
           
           <!--要和test属性配合使用，这里的id是传进来的参数id-->
           <if test="id!=null">id=#{id}</if>
           <if test="name!=null and name.trim() != &quot;&quot;">
               and name=#{name}
           </if>
           <!--不仅可以调用函数，还可以自动进行类型转化-->
           <if test="gender==0 or gender==1">
               and gender=#{gender}
           </if>
           <if test="age!=null">
               and age=#{age}
           </if>
           <if test="major!=null">
               and major=#{major.id}
           </if>
       </select>
   </mapper>
   ```

   使用if标签根据传入的参数进行判断，动态修改sql语句。但是这样写的话会有一个小问题：当id为null时，直接就是where and....了，sql语法出错(sql多个多余的前缀或者后缀）。解决的办法有三个：

   - .....  where 1=1 ,在前面加个恒等式，所有动态sql片段都加and，第一个也不例外

   - where不写，其他全部加在 where 标签里面(其实所有用到where的地方都要这样写)。这种写法只能去掉前缀，不能去掉后缀。

     所以要介绍trim标签，专门用来解决前缀后缀问题

2. trim

   专门用于SQL语句字符串前缀后缀等字符串的截取

   ```xml
   <select id="getStudentByConditionTrim" resultType="com.hhj.domain.Student">
           select * from table_student
           <!-- 后面多出的and或者or where标签不能解决
   	 	prefix="":前缀：trim标签体中是整个字符串拼串 后的结果。
   	 			prefix给拼串后的整个字符串加一个前缀
   	 	prefixOverrides="":
   	 			前缀覆盖： 去掉整个字符串前面多余的字符
   	 	suffix="":后缀
   	 			suffix给拼串后的整个字符串加一个后缀
   	 	suffixOverrides=""
   	 			后缀覆盖：去掉整个字符串后面多余的字符
   
   	 	-->
           <trim prefix="where" prefixOverrides="and">
               <if test="id!=null">id=#{id}</if>
               <if test="name!=null and name.trim() != &quot;&quot;">
                   and name=#{name}
               </if>
               <if test="gender==0 or gender==1">
                   and gender=#{gender}
               </if>
               <if test="age!=null">
                   and age=#{age}
               </if>
               <if test="major!=null">
                   and major=#{major.id}
               </if>
           </trim>
   
       </select>
   ```

   trim其实就四个属性：拼接完成的sql前面或后面 加或清楚 字符串

   - set标签

     用于update语句，功能和where一样，特殊点是它能够清楚后缀逗号

     ```xml
     <!--set测试-->
         <update id="updateByConditionSet">
             update table_student
             <set>
                 <if test="name!=null and name.trim() != &quot;&quot;">
                     name=#{name},
                 </if>
                 <if test="gender==0 or gender==1">
                     gender=#{gender},
                 </if>
                 <if test="age!=null">
                     age=#{age},
                 </if>
                 <if test="major!=null">
                     major=#{major.id}
                 </if>
             </set>
             <where>
                 id=#{id}
             </where>
             
             <!--上面这个代码也可以用trim改，前面加set，后面清除后缀逗号-->
         </update>
     ```

3. choose--when

   相当于java的switch-case，只能选择一个

   ```xml
   <!--    where&#45;&#45;choose测试   相当于java的switch-case，选择一个-->
       <select id="getStudentByConditionIf" resultType="com.hhj.domain.Student">
   
           select * from table_student
           <where>
               <choose>
                   <when test="id!=null">
                       id=#{id}
                   </when>
                   <when test="name!=null and name.trim() != &quot;&quot;">
                       name=#{name}
                   </when>
                   <when test="gender==0 or gender==1">
                       gender=#{gender}
                   </when>
                   <when test="age!=null">
                       age=#{age}
                   </when>
                   <otherwise>
                       major=1
                   </otherwise>
               </choose>
           </where>
       </select>
   ```

4. foreach

   一般用于in查询、批量操作

   ```xml
   <!--    测试in查询-->
       <select id="findStudentForEach" resultType="com.hhj.domain.Student">
           select * from table_student where id in
           <foreach collection="list" item="item" open="(" close=")" separator=",">
               #{item}
           </foreach>
           <!--
   	 		collection：指定要遍历的集合：
   	 			        list类型的参数会特殊处理封装在map中，map的key就叫list
   	 		item：将当前遍历出的元素赋值给指定的变量
   	 		separator:每个元素之间的分隔符
   	 		open：遍历出所有结果拼接一个开始的字符
   	 		close:遍历出所有结果拼接一个结束的字符
   	 		index:索引。遍历list的时候是index就是索引，item就是当前值
   	 				      遍历map的时候index表示的就是map的key，item就是map的值
   
   	 		#{变量名}就能取出变量的值也就是当前遍历出的元素
   	 	  -->
       </select>
   
   <!--    批量插入，有两种方式：
               1. 使用特殊的mysql语法insert into talbe values (..),(..),(..)，每次执行一条sql。但是这种只要批量插入才有这种语法
               2. 对整条sql进行foreach，每次执行多条sql，默认不支持，要开启配置：数据库连接属性allowMultiQueries=true；
                   若是要批量更新删除，只能使用第二种了-->
   
   <!--    public long insertForEach(List<Student> list);-->
       <insert id="insertForEach">
           insert into table_student values
           <foreach collection="list" item="item" separator=",">
               (null,#{item.name},#{item.gender},#{item.age},#{item.major.id})
           </foreach>
   
       </insert>
   
   <!--    第二种方法-->
       <insert id="insertForEach">
           <foreach collection="list" item="item" separator=",">
               insert into table_student values
               (null,#{item.name},#{item.gender},#{item.age},#{item.major.id})
           </foreach>
   
       </insert>
   ```

5. 两个内置函数

   > _parameter:代表整个参数
   >
   > _databaseId:代表当前数据库的别名

6. bind标签

   > 把参数值和变量绑定起来，没啥用

7. include标签

   > 把s常用ql语句的片段抽取出来，实现复用

#### 7. 缓存

https://tech.meituan.com/2018/01/19/mybatis-cache.html

mybatis的缓存本质就是一个内存中的map结构，当查询相同的SQL时，把缓存中的数据返回，不用再去查询数据库，减少数据库的读写压力。

缓存分为一级缓存和二级缓存。

一级缓存又叫SqlSession缓存，每s一次数据库会话都有自己独立的一级缓存。它里面存储的数据也只有在当前会话才能共享。mabatis默认开启一级缓存。

二级缓存又叫namaspance缓存，只要是同一个mapper对象，尽管是不同会话，都能共享缓存数据。一般情况下，每张表都有自己的二级缓存。二级缓存需要在mapper文件中进行配置才能开启。

mybatis查询数据时默认先查二级缓存，再查一级缓存，在调用jdbc查询数据库。一级缓存中的数据只有在当前会话关闭后才会被写入二级缓存。也就是说，二级缓存中的数据都来自一级缓存。



----

关于mybatis的学习先到这里为止，后续等学完Spring再来补充

  

  

  

  

  

  

