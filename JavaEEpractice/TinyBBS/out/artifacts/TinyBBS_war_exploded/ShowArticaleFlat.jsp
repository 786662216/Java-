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
    int pageSize = 3;
    String strPageNo = request.getParameter("pageNo");
    int pageNo = 1;
    if(strPageNo == null || strPageNo.equals("")){
        pageNo = 1;

    }else{
        try {
            pageNo = Integer.parseInt(strPageNo.trim());
        } catch (NumberFormatException e){
            pageNo = 1;
        }
        if(pageNo <= 0) pageNo = 1;
    }


    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
    Connection conn = DriverManager.getConnection(url);

    Statement stmtCount = conn.createStatement();
    ResultSet rsCount = stmtCount.executeQuery("SELECT count(*) from artical WHERE pid = 0");
    rsCount.next();
    int totalRecords = rsCount.getInt(1);

    int totalPages = totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1 ;
    if (pageNo > totalPages){
        pageNo = totalPages;
    }

    int startPos = (pageNo - 1) * pageSize;


    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from artical where pid = 0 ORDER by pdate desc limit " + startPos + "," + pageSize);

%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="Post.jsp">发表新帖</a>
<table border="1">

    <%
        while(rs.next()){
    %>

    <tr>
        <td>
            <%=rs.getString("title") %>
        </td>
    </tr>


    <%
        }
    %>

    <%
        rs.close();
        stmt.close();
        conn.close();
    %>
</table>
共<%=totalPages%>页 第<%=pageNo%>页
<a href="ShowArticaleFlat.jsp?pageNo=<%=pageNo-1%>">上一页</a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="ShowArticaleFlat.jsp?pageNo=<%=pageNo+1%>">下一页</a>

<form name="form1" action="ShowArticaleFlat.jsp">
    <select name="pageNo" onchange="document.form1.submit()">
        <%
            for (int i = 1;i <= totalPages;i++){
        %>
        <option
                value=<%=i%> <%=(pageNo == i) ? "selected " : ""%>>第<%=i%>页
        </option>
        <%
            }
        %>
    </select>
</form>

<from name="form2" action="ShowArticaleFlat.jsp">
    <input type=text size=4 name="pageNo" value=<%=pageNo%>/>
    <input type="submit" value="go" />
</from>

</body>
</html>
