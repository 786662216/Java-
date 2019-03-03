<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/6
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    request.setCharacterEncoding("UTF-8");

    int id = Integer.parseInt(request.getParameter("id"));
    int rootid = Integer.parseInt(request.getParameter("rootid"));
    String title = request.getParameter("title");
    String cont = request.getParameter("cont");

    cont = cont.replaceAll("\n","<br>");

    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    conn.setAutoCommit(false);

    String sql = "insert into artical values (null,?,?,?,?,now(),0)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    Statement stmt = conn.createStatement();

    pstmt.setInt(1,id);
    pstmt.setInt(2,rootid);
    pstmt.setString(3,title);
    pstmt.setString(4,cont);
    pstmt.executeUpdate();

    stmt.executeUpdate("update artical set isleaf = 1 WHERE id = " + id);

    conn.commit();
    conn.setAutoCommit(true);

    stmt.close();
    pstmt.close();
    conn.close();

    response.sendRedirect("ShowArticaleTree.jsp");
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <font color="red" size="7">
        OK!
    </font>
</body>
</html>
