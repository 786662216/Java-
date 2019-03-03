<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/6
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String strid = request.getParameter("id");
    int id = Integer.parseInt(strid);


    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from artical where id = " + id);

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    if(rs.next()){
%>
<table border="1">
    <tr>
        <td>ID</td>
        <td><%=rs.getInt("id")%></td>
    </tr>
    <tr>
        <td>Title</td>
        <td><%=rs.getString("title")%></td>
    </tr>
    <tr>
        <td>Content</td>
        <td><%=rs.getString("cont")%></td>
    </tr>
</table>

<a href="Reply.jsp?id=<%=rs.getInt("id")%>&rootid=<%=rs.getInt("rootid")%>">回复</a>
<%
    }
    rs.close();
    stmt.close();
    conn.close();
%>
</body>
</html>
