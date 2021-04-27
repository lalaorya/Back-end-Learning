<%--
  Created by IntelliJ IDEA.
  User: sang_Mu
  Date: 2021/3/1
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--请求参数绑定--%>
<a href="form?username=hehe&password=123">请求参数绑定</a>


<%--把数据封装Account类中--%>
<form action="form2" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    金额：<input type="text" name="money" /><br/>
    用户姓名：<input type="text" name="user.uname" /><br/>
    用户年龄：<input type="text" name="user.age" /><br/>
    <input type="submit" value="提交" />
</form>


<%--把数据封装Account类中，类中存在list和map的集合--%>
<form action="form3" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    金额：<input type="text" name="money" /><br/>

<%--    集合可以这样存--%>
    用户姓名：<input type="text" name="list[0].uname" /><br/>
    用户年龄：<input type="text" name="list[0].age" /><br/>

    用户姓名：<input type="text" name="map['one'].uname" /><br/>
    用户年龄：<input type="text" name="map['one'].age" /><br/>
    <input type="submit" value="提交" />
</form>


<%--自定义类型转换器：通过在xml中添加自定义类型转换器，可以把某个字段转为我们需要的数据格式进行封装
    比如日期格式2000-0-0 无法自动转换为Date格式，这个时候就需要我们设计自定义类型转换器--%>
<form action="form4" method="post">
    用户姓名：<input type="text" name="uname" /><br/>
    用户年龄：<input type="text" name="age" /><br/>
    用户生日：<input type="text" name="date" /><br/>
    <input type="submit" value="提交" />
</form>



<%--<a href="param/testServlet">Servlet原生的API</a>--%>

<a href="form5/777">绑定占位符</a>
<%--<a href="/form5/777">绑定占位符</a>   不能加个/，/指的是根目录，根目录是web，不加/是当前目录，当前目录是当前项目名称--%>

</body>
</html>

