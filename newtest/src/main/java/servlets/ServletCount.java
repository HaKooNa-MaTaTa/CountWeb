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
           Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           String sql = "SELECT webcount FROM tableforcount";
           ResultSet result = statement.executeQuery(sql);
           result.first();
           int count = result.getInt("webcount") + 1;
           //Для проверки
           System.out.println(count);
           request.setAttribute("count", count);
           request.getRequestDispatcher("WEB-INF/jsp/PageCount.jsp").forward(request, response);
           result.close();
           statement.close();
           Statement newStatement = connection.createStatement();
           String sqlUpdate = "UPDATE tableforcount SET webcount = " + count + "";
           int numberRow = newStatement.executeUpdate(sqlUpdate);
           newStatement.close();
           connection.close();
       } catch (SQLException e) {
            e.printStackTrace();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}
