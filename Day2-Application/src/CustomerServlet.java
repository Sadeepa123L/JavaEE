import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    private static final List<Customer> customers = new ArrayList<>();

    // CREATE
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp","root",
                     "Sadeepa@2003");
             String sql = "INSERT INTO customer (id, name, address) VALUES (?, ?, ?)";
             PreparedStatement ps = connection.prepareStatement(sql);
             ps.setInt(1, Integer.parseInt(id));
             ps.setString(2, name);
             ps.setString(3, address);
             int rows = ps.executeUpdate();
             if (rows > 0) {
                 resp.getWriter().println("Customer successfully added");
             }else {
                 resp.getWriter().println("Customer not added");
             }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ (Get All or Get by ID)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp","root",
                    "Sadeepa@2003");
           if(id==null){
               String sql = "SELECT * FROM customer";
               PreparedStatement ps = connection.prepareStatement(sql);
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                  Integer cid = Integer.valueOf(rs.getString("id"));
                  String name = rs.getString("name");
                  String address = rs.getString("address");
                  resp.getWriter().println(cid+" "+name+" "+address);
               }
           }else {
               String sql = "SELECT * FROM customer WHERE id = ?";
               PreparedStatement ps = connection.prepareStatement(sql);
               ps.setInt(1, Integer.parseInt(id));
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                   Integer cid = Integer.valueOf(rs.getString("id"));
                   String name = rs.getString("name");
                   String address = rs.getString("address");
                   resp.getWriter().println(cid+" "+name+" "+address);
               }
           }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp","root",
                    "Sadeepa@2003");
            String sql = "UPDATE customer SET name = ?, address = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, Integer.parseInt(id));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                resp.getWriter().println("Customer successfully updated");
            }else {
                resp.getWriter().println("Customer not updated");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp","root",
                    "Sadeepa@2003");
            String sql = "DELETE FROM customer WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                resp.getWriter().println("Customer successfully deleted");
            }else {
                resp.getWriter().println("Customer not deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}