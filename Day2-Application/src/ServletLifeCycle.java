import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/slc")
public class ServletLifeCycle extends HttpServlet {
    @Override
    public void init(){
        System.out.println("Servlet init");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet doGet");
    }

    @Override
    public void destroy(){
        System.out.println("Servlet destroy");
    }
}
