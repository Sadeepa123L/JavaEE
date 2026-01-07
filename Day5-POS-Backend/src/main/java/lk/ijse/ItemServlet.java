package lk.ijse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
