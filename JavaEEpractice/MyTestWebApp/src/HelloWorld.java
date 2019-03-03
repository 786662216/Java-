import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {//继承HTTPServlet类

    //重写各种http动词方法
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        connDB conn = new connDB();
        ResultSet rs = null;
        try {
            rs = conn.connect();
            while(rs.next()) {
                response.getWriter().write("<h1>" + "id:" + rs.getInt("id") + "  username:" + rs.getString("name") + "  password:" + rs.getString("pwd") + "</h1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();
    }
}

class connDB {

    public static final String URL = "jdbc:mysql://localhost:3306/db_student";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    Connection conn = null;
    Statement stmt = null;

    ResultSet connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tb_admin");

        return rs;
    }
}
