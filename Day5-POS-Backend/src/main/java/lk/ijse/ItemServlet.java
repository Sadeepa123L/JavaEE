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

@WebServlet(urlPatterns = "/api/v1/items")
public class ItemServlet extends HttpServlet {

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

        String id = json.get("itemid").getAsString();
        String name = json.get("itemname").getAsString();
        Double price = json.get("itemprice").getAsDouble();

        try {
            Connection connection = ds.getConnection();
            String query = "INSERT INTO item (id,name,price) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);

            int rowInserted = preparedStatement.executeUpdate();

            if (rowInserted > 0) {
                resp.getWriter().println("Item Saved Successfully");
            } else {
                resp.getWriter().println("Item Saved Failed");
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

        String id = json.get("iteamid").getAsString();
        String name = json.get("itemname").getAsString();
        Double price = json.get("itemprice").getAsDouble();

        try {
            Connection connection = ds.getConnection();
            String query = "UPDATE item SET name=?, price=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                resp.getWriter().println("Item Updated Successfully");
            } else {
                resp.getWriter().println("Item Update Failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("itemid");
        try {
            Connection connection=ds.getConnection();
            String query="delete from item where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            int rowDeleted=preparedStatement.executeUpdate();
            if(rowDeleted>0){
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("Item deleted successfully");
            }else {
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("Item deleted Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {

        try {
            JsonArray itemList = new JsonArray();
            Connection connection = ds.getConnection();
            String query = "SELECT * FROM item";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("itemid");
                String name = resultSet.getString("itemname");
                Double price = resultSet.getDouble("itemprice");

                JsonObject item = new JsonObject();
                item.addProperty("itemid", id);
                item.addProperty("itemname", name);
                item.addProperty("itemprice", price);

                itemList.add(item);
            }
            resp.getWriter().println(itemList.toString());
            resp.setContentType("application/json;charset=UTF-8");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
