<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/5
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String admin = (String)session.getAttribute("admin");
    if(admin != null && admin.equals("true")){
        login = true;
    }

%>

<%!
    String str = "";
    boolean login = false;
    private void tree (Connection conn,int id,int level){

        Statement stmt = null;
        ResultSet rs = null;
        String preStr = "";

        for(int i = 0;i < level;i++){
            preStr += "----";
        }

        try{
            stmt = conn.createStatement();
            String sql = "select * from artical where pid = " + id;
            rs = stmt.executeQuery(sql);
            String strLogin = "";
            while(rs.next()){
                if(login){
                    strLogin = "<td><a href='Delete.jsp?id=" + rs.getInt("id") + "&pid=" + rs.getInt("pid") + "'>删除</a>";
                }
                str +=  "<tr><td>" + rs.getInt("id") + "</td><td>" +
                        preStr + "<a href='ShowArticaleDetail.jsp?id=" + rs.getInt("id") + "'>" +
                        rs.getString("title") + "</a></td>" +
                        strLogin +
                        "</td></tr>";
                if(rs.getInt("isleaf") != 0){
                    tree(conn,rs.getInt("id"),level + 1);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if(rs != null){
                    rs.close();
                    rs = null;
                }
                if(stmt != null){
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
%>

<%
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from artical where pid = 0");
    String strLogin = "";
    while (rs.next()){
        if(login){
            strLogin = "<td><a href='Delete.jsp?id=" + rs.getInt("id") + "&pid=" + rs.getInt("pid") + "'>删除</a>";
        }
        str +=  "<tr><td>" + rs.getInt("id") + "</td><td>" +
                "<a href='ShowArticaleDetail.jsp?id=" + rs.getInt("id") + "'>" +
                rs.getString("title") + "</a></td><td>" +
                strLogin +
                "</td></tr>";
        if(rs.getInt("isleaf") != 0){
            tree(conn,rs.getInt("id"),1);
        }
    }
    rs.close();
    stmt.close();
    conn.close();
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="Post.jsp">发表新帖</a>
    <table border="1">
        <%= str %>
        <%
            str = "";
            login = false;
        %>
    </table>
</body>
</html>
