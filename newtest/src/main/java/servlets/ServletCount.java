package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/")
public class ServletCount extends HttpServlet {

    private static final String DB_USER = "test";
    private static final String DB_PASSWORD = "test";
    private static final String DB_URL = "jdbc:postgresql://185.27.193.111:37402/test";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           Class.forName("org.postgresql.Driver");
           Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
           Statement statement = connection.createStatement();
           String sql = "SELECT * FROM tableforcount";
           ResultSet result = statement.executeQuery(sql);
           response.setContentType("text/html; charset=utf-8");
           while(result.next()) {
               int count = result.getInt("webcount") + 1;
               request.setAttribute("count", count);
               request.getRequestDispatcher("WEB-INF/jsp/PageCount.jsp").forward(request, response);
               String sqlUpdate = "UPDATE tableforcount SET webcount = " + count + "";
               int numberRow = statement.executeUpdate(sqlUpdate);
           }
           result.close();
           statement.close();
           connection.close();
       } catch (SQLException e) {
            e.printStackTrace();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}
