package com.beautysalon.listener;

import com.beautysalon.connectionpool.ConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

public class BeautySalonContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Properties properties = new Properties();
        System.out.println(servletContext.getInitParameter("db_driver"));
        properties.put("user", servletContext.getInitParameter("db_user"));
        properties.put("password", servletContext.getInitParameter("db_password"));
        String dbDriver = servletContext.getInitParameter("db_driver");
        String dbURL = servletContext.getInitParameter("db_URL");
        ConnectionPool.INSTANCE.init(dbDriver, dbURL, properties);
        System.out.println("ListenerInit");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.INSTANCE.destroyPool();
        System.out.println("ListenerDestroy");
    }
}
