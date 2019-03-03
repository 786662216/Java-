<%--
  Created by IntelliJ IDEA.
  User: 78666
  Date: 2018/10/6
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%!
    private void del(Connection conn,int id){

        Statement stmt = null;
        ResultSet rs = null;

        try{
            stmt = conn.createStatement();
            String sql = "select * from artical where pid = " + id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                del(conn,rs.getInt("id"));
            }
            stmt.executeUpdate("delete from artical WHERE id = " + id);


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
    String admin = (String)session.getAttribute("admin");
    if(admin == null || !admin.equals("true")){
        out.println("nonononono");
        return;
    }
%>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    int pid = Integer.parseInt(request.getParameter("pid"));

    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    conn.setAutoCommit(false);

    del(conn,id);

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT count(*) from artical WHERE pid = " + pid);
    rs.next();
    int count = rs.getInt(1);
    rs.close();
    stmt.close();

    if(count <= 0){
        Statement stmtupdate = conn.createStatement();
        stmtupdate.executeUpdate("UPDATE artical SET isleaf = 0 WHERE id = " + pid);
        stmtupdate.close();
    }

    conn.commit();
    conn.setAutoCommit(true);
    conn.close();

    response.sendRedirect("ShowArticaleTree.jsp");
%>

