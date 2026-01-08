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

@WebServlet(urlPatterns = "/api/v1/orders")
public class OrderServlet extends HttpServlet {

    BasicDataSource ds;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ds = (BasicDataSource) servletContext.getAttribute("datasource");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String orderId = json.get("orderId").getAsString();
        String customerId = json.get("customerId").getAsString();
        String itemId = json.get("itemId").getAsString();
        int qty = json.get("qty").getAsInt();

        try {
            Connection connection = ds.getConnection();
            String sql = "INSERT INTO orders VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, orderId);
            ps.setString(2, customerId);
            ps.setString(3, itemId);
            ps.setInt(4, qty);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                resp.getWriter().println("Order Saved Successfully");
            } else {
                resp.getWriter().println("Order Save Failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String orderId = json.get("orderId").getAsString();
        String customerId = json.get("customerId").getAsString();
        String itemId = json.get("itemId").getAsString();
        int qty = json.get("qty").getAsInt();

        try {
            Connection connection = ds.getConnection();
            String sql = "UPDATE orders SET customer_id=?, item_id=?, qty=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, customerId);
            ps.setString(2, itemId);
            ps.setInt(3, qty);
            ps.setString(4, orderId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                resp.getWriter().println("Order Updated Successfully");
            } else {
                resp.getWriter().println("Order Update Failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderId = req.getParameter("orderId");

        try {
            Connection connection = ds.getConnection();
            String sql = "DELETE FROM orders WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, orderId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                resp.getWriter().println("Order Deleted Successfully");
            } else {
                resp.getWriter().println("Order Delete Failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            JsonArray orderList = new JsonArray();
            Connection connection = ds.getConnection();
            String sql = "SELECT * FROM orders";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JsonObject order = new JsonObject();
                order.addProperty("orderId", rs.getString("id"));
                order.addProperty("customerId", rs.getString("customer_id"));
                order.addProperty("itemId", rs.getString("item_id"));
                order.addProperty("qty", rs.getInt("qty"));

                orderList.add(order);
            }

            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().println(orderList.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
