<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/6
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    int rootid = Integer.parseInt(request.getParameter("rootid"));


%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="ReplyOK.jsp" method="post">
    <input type="hidden" name="id" value="<%=id%>">
    <input type="hidden" name="rootid" value="<%=rootid%>">
    <table border="1">
        <tr>
            <td>
                <input type="text" name="title" size="80">
            </td>
        </tr>
        <tr>
            <td>
                <textarea cols="80" rows="12" name="cont"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
