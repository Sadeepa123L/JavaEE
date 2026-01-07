package lk.ijse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/v1/customers")
public class CustomerServlet extends HttpServlet {

    BasicDataSource ds;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ds = (BasicDataSource) getServletContext().getAttribute("datasource");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String id = json.get("cid").getAsString();
        String name = json.get("cname").getAsString();
        String address = json.get("caddress").getAsString();

        try {
            Connection connection = ds.getConnection();
            String query = "INSERT INTO customer (id,name,address) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, address);

            int rowInserted = preparedStatement.executeUpdate();

            if (rowInserted > 0) {
                resp.getWriter().println("Customer Saved Successfully");
            } else {
                resp.getWriter().println("Customer Saved Failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String id = json.get("cid").getAsString();
        String name = json.get("cname").getAsString();
        String address = json.get("caddress").getAsString();

        try {
            Connection connection = ds.getConnection();
            String query = "UPDATE customer SET name=?, address=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                resp.getWriter().println("Customer Updated Successfully");
            } else {
                resp.getWriter().println("Customer Update Failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("cid");
        try {
            Connection connection=ds.getConnection();
            String query="delete from customer where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            int rowDeleted=preparedStatement.executeUpdate();
            if(rowDeleted>0){
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("Customer deleted successfully");
            }else {
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("Customer deleted Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {

        try {
            JsonArray customerList = new JsonArray();
            Connection connection = ds.getConnection();
            String query = "SELECT * FROM customer";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                JsonObject customer = new JsonObject();
                customer.addProperty("cid", id);
                customer.addProperty("cname", name);
                customer.addProperty("caddress", address);

                customerList.add(customer);
            }
            resp.getWriter().println(customerList.toString());
            resp.setContentType("application/json;charset=UTF-8");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {

        System.out.println("do options method called");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    }
}
