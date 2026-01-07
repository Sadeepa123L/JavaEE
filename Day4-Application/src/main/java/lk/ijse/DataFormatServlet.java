package lk.ijse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/data-format")
public class DataFormatServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contentType= request.getContentType();
        System.out.println("contentType:"+contentType);

//        query parameters
//        System.out.println(request.getParameter("id"));
//        System.out.println(request.getParameter("name"));

//        path variables

//        x-www.form-urlencoded
//           System.out.println(request.getParameter("id"));
//           System.out.println(request.getParameter("name"));

//        Read the file
            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();

//            Create a directory
            File uploaddir = new File(fileName);
            uploaddir.mkdir();
    }
}
