package lk.ijse;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class DBConnection implements ServletContextListener {

    @Override
    public void contextInitialized(jakarta.servlet.ServletContextEvent sce) {
       ServletContext context = sce.getServletContext();
       BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/pos73");
        ds.setUsername("root");
        ds.setPassword("Sadeepa@2003");
        ds.setInitialSize(50);
        ds.setMaxTotal(100);

        context.setAttribute("datasource", ds);
    }
}
