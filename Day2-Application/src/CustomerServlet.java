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
    List<Customer> customerList = new ArrayList<Customer>();

   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("doPost");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
       System.out.println(id+name+address);
       customerList.add(new Customer(id, name, address));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.getContentType();
       for(Customer c:customerList){
           System.out.println(
                 "<tr>"+"<td>" + c.getId() + "</td>"+
                  "<td>" + c.getName() + "</td>"+
                  " <td>" + c.getAddress() + "</td>"+
                 "</tr>"
           );
       }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        for(Customer c:customerList){
            if(c.getId().equals(id)){
                c.setName(name);
                c.setAddress(address);
                break;
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        customerList.removeIf(customer -> customer.getId().equals(id));
    }
}