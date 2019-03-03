<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/7
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="Login.jsp" method="post">
    用户名：<input type="text" name="uname">
    <br>
    密码：<input type="text" name="pwd">
    <br>
    <input type="submit" value="submit">
</form>
</body>
</html>
<%
    String username = request.getParameter("uname");
    String password = request.getParameter("pwd");

    if(username == null || !username.equals("admin")){
        out.println("用户名不对");
        return;
    }
    if(password == null || !password.equals("admin")){
        out.println("密码不对");
        return;
    }
    session.setAttribute("admin","true");
    response.sendRedirect("ShowArticaleTree.jsp");
%>