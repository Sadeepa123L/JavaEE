import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

        Customer customer = new Customer(id, name, address);
        customers.add(customer);

        resp.getWriter().println("Customer added successfully");
    }

    // READ (Get All or Get by ID)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id == null) {
            // Get All
            for (Customer c : customers) {
                resp.getWriter().println(c.getId() + " - " + c.getName() + " - " + c.getAddress());
            }
        } else {
            // Get by ID
            for (Customer c : customers) {
                if (c.getId().equals(id)) {
                    resp.getWriter().println(c.getId() + " - " + c.getName() + " - " + c.getAddress());
                    return;
                }
            }
            resp.getWriter().println("Customer not found");
        }
    }

    // UPDATE
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                if (name != null) c.setName(name);
                if (address != null) c.setAddress(address);

                resp.getWriter().println("Customer updated successfully");
                return;
            }
        }

        resp.getWriter().println("Customer not found for update");
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                customers.remove(c);
                resp.getWriter().println("Customer deleted successfully");
                return;
            }
        }

        resp.getWriter().println("Customer not found for deletion");
    }
}