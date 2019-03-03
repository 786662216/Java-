<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/6
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.omg.PortableInterceptor.SYSTEM_EXCEPTION" %>

<%
    request.setCharacterEncoding("UTF-8");

    String action = request.getParameter("action");
    if (action != null && action.equals("post")){

        String title = request.getParameter("title");
        String cont = request.getParameter("cont");

        cont = cont.replaceAll("\n","<br>");

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
        Connection conn = DriverManager.getConnection(url);

        conn.setAutoCommit(false);

        String sql = "insert into artical values (null,0,?,?,?,now(),0)";
        PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        Statement stmt = conn.createStatement();

        pstmt.setInt(1,-1);
        pstmt.setString(2,title);
        pstmt.setString(3,cont);
        pstmt.executeUpdate();

        ResultSet rsKey = pstmt.getGeneratedKeys();
        rsKey.next();
        int key = rsKey.getInt(1);
        rsKey.close();
        String sql2 = "UPDATE artical SET rootid = " + key + " where id = " + key;
        stmt.executeUpdate(sql2);

        conn.commit();
        conn.setAutoCommit(true);

        stmt.close();
        pstmt.close();
        conn.close();

        response.sendRedirect("ShowArticaleFlat.jsp");
    }
%>


<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="Post.jsp" method="post">
    <input type="hidden" name="action" value="post">
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
