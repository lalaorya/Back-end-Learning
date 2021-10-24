## XML
1. 概念
	> Extensible Markup Language 可扩展的标记语言
	
	- 什么是可扩展：所有标签都是自定义的 
	- 功能
	  - 存储数据：存储配置文件
	  - 在网络中传输数据
	- xml和html的区别
	  - xml语法严格，html语法松散
	  - xml的所有标签都是自定义的
	  - xml是存储数据的，html是展示数据的
	
2. 语法

   - 基本语法：

     1. xml的第一行必须是文档声明（只能是第一行，不可以第一行空白，第二行声明文档
     2. xml文档中只能有一个根标签
     3. 属性值必须用单(双)引号括起来
     4. 除了自闭合标签，其他标签必须正确关闭

     **一个简单的xml文档案例：**

     ```xml
     <?xml version='1.0'?>
     
     <users>
     
         <user id='1'>
             <name>zhangsan</name>
             <age>23</age>
             <gender>male</gender>
         </user>
     
         <user id='2'>
             <name>zhangsan</name>
             <age>23</age>
             <gender>male</gender>
         </user>
     
     
     </users>
     ```

     **判断一个xml文档是否有语法错误，只需要在浏览器打开即可**

3. 组成元素

   1. 文档声明

      ```xml
      <?xml version='1.0' encoding="UTF-8" standalone="yes"?>
      
      <!-- version：版本号
      encoding：浏览器的解析引擎要使用的字符集
      standalone：是否依赖其他文件 -->
      ```

   2. 指令

      > 最开始是被设计用来引入css文件，操作标签样式的。
      >
      > 但是现在xml已经不用于展示数据了，因此这个指令了解一下即可

      ```xml
      <?xml-stylesheet type="text/css" href="a.css"?>
      ```

   3. 标签

   4. 属性

   5. 文本

      > 文本里面有一个CDATA区要注意，在该区域的数据会被原样展示，无需转义

      ```xml
      <code>
      	<![CDATA[a>b && b>c]]>
      </code>
      ```

4. 约束文档

   > 我们知道xml一般就是用来存放软件的配置信息。因此用户就可以通过编写xml文件来修改软件的默认配置。
   >
   > 但是，xml文件是一个可扩展的标记语言，标签都是自定义的。为了让软件(框架）能够正确的解析我们写的配置文件，必须对我们写的xml文件进行一些约束。这些约束就是配置文档。
   >
   > 程序员-->阅读-->约束文档-->编写配置文件
   >
   > 软件-->制定-->约束文文档-->读取配置文件
   >
   > 因此，对于框架的使用者-程序员的要求是：
   >
   > - 引入约束文档
   > - 编写配置文件

   1. DTD约束技术

      **引入dtd文档到xml文档中**

      - 内部方式(不用)

      - 外部方式

        1. 本地

           ```
           <!DOCTYPE 根标签名 SYSTEM "DTD文件的位置">
           ```

        2. 网络

           ```
           <!DOCTYPE 根标签名 PUBLIC "DTD文件名称(随意)" "DTD文件的位置">
           ```

      **一个小案例**

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      
      <!DOCTYPE students SYSTEM "./dtd/student.dtd">
      
      <!--要是不按照约束格式写会报错-->
      <students>
          <student number='s1'>
              <name>张三</name>
              <age>22</age>
              <sex>boy</sex>
          </student>
      
          <student number='s2'>
              <name>李四</name>
              <age>22</age>
              <sex>girl</sex>
          </student>
      
      </students>
      ```

      ```dtd
      <!ELEMENT students (student*) >
      <!ELEMENT student (name,age,sex)>
      <!ELEMENT name (#PCDATA)>
      <!ELEMENT age (#PCDATA)>
      <!ELEMENT sex (#PCDATA)>
      <!ATTLIST student number ID #REQUIRED>
      ```

   2. schema约束技术

      > schema是为了克服DTD的局限性。
      >
      > DTD缺乏对文档结构、元素、数据类型的全面描述。像上面那个例子，使用DTD只能约束文档的结构，但是你在age标签填1000也不会报错。可以看出DTD对文档的约束十分有限。
      >
      > 对于Schema，我们并不需要精通，只要能简单的看懂，并对配置文件进行初步修改即可。

      我们通过一个简单的案例来描述Schema(后缀位xsd)是如何编写的。

      ```scheme
      <?xml version="1.0"?>
      <xsd:schema xmlns="http://www.itcast.cn/xml"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              targetNamespace="http://www.itcast.cn/xml" elementFormDefault="qualified">
          <xsd:element name="students" type="studentsType"/>
          <xsd:complexType name="studentsType">
              <xsd:sequence>
                  <xsd:element name="student" type="studentType" minOccurs="0" maxOccurs="unbounded"/>
              </xsd:sequence>
          </xsd:complexType>
          <xsd:complexType name="studentType">
              <xsd:sequence>
                  <xsd:element name="name" type="xsd:string"/>
                  <xsd:element name="age" type="ageType" />
                  <xsd:element name="sex" type="sexType" />
              </xsd:sequence>
              <xsd:attribute name="number" type="numberType" use="required"/>
          </xsd:complexType>
          <xsd:simpleType name="sexType">
              <xsd:restriction base="xsd:string">
                  <xsd:enumeration value="male"/>
                  <xsd:enumeration value="female"/>
              </xsd:restriction>
          </xsd:simpleType>
          <xsd:simpleType name="ageType">
              <xsd:restriction base="xsd:integer">
                  <xsd:minInclusive value="0"/>
                  <xsd:maxInclusive value="256"/>
              </xsd:restriction>
          </xsd:simpleType>
          <xsd:simpleType name="numberType">
              <xsd:restriction base="xsd:string">
                  <xsd:pattern value="heima_\d{4}"/>
              </xsd:restriction>
          </xsd:simpleType>
      </xsd:schema>
      ```

      **重点：**

      - 当一个xml文档要引入多个.xsd约束文件，但是因为每个约束文件都相当于一个xml文件，它的标签是自定义的。因此为了避免混乱，每个

