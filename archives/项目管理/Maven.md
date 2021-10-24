Maven🤺

1. 环境变量的配置

   > 添加MAVEN_HOME变量，value为appech-maven解压得位置
   >
   > path变量添加appech-mavenx下的bin目录位置的记录

2. 本地仓库配置

   > 配置setting.xml
   >
   > <img src="https://i.loli.net/2021/01/04/oWBzEQXhNRrA34q.png" alt="image-20201127200448074" style="zoom:67%;" />

3. 远程仓库配置（若在本地仓库找不到jar包，就去远程仓库down下来到本地仓库

4. Maven项目的标准目录结构

   > <img src="https://i.loli.net/2021/01/04/BIhYQ5uWeHXbrjP.png" alt="image-20201127223031325" style="zoom:67%;" />
   
5. 核心配置文件pom.xml

   > ```xml
   > <?xml version="1.0" encoding="UTF-8"?>
   > <project xmlns="http://maven.apache.org/POM/4.0.0"
   >          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   >          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   >     <modelVersion>4.0.0</modelVersion>
   > 
   > <!--    模块名称-->
   >     <groupId>com.hhj.maven-demo</groupId>
   > <!--    项目名称-->
   >     <artifactId>maven-demo</artifactId>
   >     <version>1.0-SNAPSHOT</version>
   > 
   > <!--    指定项目类型jar/war/pom,会影响mvn打包方式-->
   >     <packaging>jar</packaging>
   > <!--    添加项目依赖-->
   >     <dependencies>
   >         <!-- https://mvnrepository.com/artifact/junit/junit -->
   >         <dependency>
   >             <groupId>junit</groupId>
   >             <artifactId>junit</artifactId>
   >             <version>4.13</version>
   >             <scope>test</scope>
   >         </dependency>
   > 
   >     </dependencies>
   > </project>
   > ```
   >
   > 说白了，每个依赖就是一个jar包，保存在本地仓库中，我们可以通过这些jar包来调用我们需要的api，maven会帮助我们管理它，我们只需要在使用的时候进行配置即可。
   >
   > 同理，我们自己的项目也可以打包成一个jar包，通过mvn package命令打包成.jar文件，再通过mvn install命令把jar包放到本地仓库中，位置就在本地仓库下的groupId标签下。
   >
   > 我们可以通过同样的方式来引入我们项目自己的jar包，在多个模块(每个模块都有一个pom.xml）中实现复用。
   >
   > ![image-20201128000121751](https://i.loli.net/2021/01/04/rjXzidZpqBgmsKe.png)
   >
   > ---

6. maven常用命令

   > - mvn compile
   >
   >   编译整个项目，生成target目录
   >
   > - mvn test
   >
   >   编译测试目录，在target目录下生成test-classes目录
   >
   > - mvn clean
   >
   >   删除target目录
   >
   > - mvn package
   >
   >   打包整个项目，生成 .jar 文件
   >
   > - mvn install
   >
   >   根据pom.xml将项目jar包和其他配置信息复制到本地仓库(安装jar包，作为新的依赖

7. jar包依赖优先查找顺序

   > 本地仓库 to 私服 to 公共仓库(setting.xml配置的阿里云) to 中央仓库(maven-resposity)

8. idea中maven的配置

   > <img src="https://i.loli.net/2021/01/04/JDVHef4aNICMTSK.png" alt="image-20201128100037328" style="zoom:50%;" />

9. 父子依赖/依赖传递

10. scope生命周期

    > compile(默认值):测试 \ 编译 \ 运行 \ 打包 这个依赖都会存在
    >
    > provided:只在测试\ 编译 运行阶段存在,不会被打包
    >
    > test:只在测试阶段存在

