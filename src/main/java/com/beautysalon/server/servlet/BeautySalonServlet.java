package com.beautysalon.server.servlet;

import com.beautysalon.controller.Controller;
import com.beautysalon.controller.ControllerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BeautySalonServlet extends HttpServlet {

    private ControllerFactory controllerFactory;

    @Override
    public void init() throws ServletException {
        controllerFactory = new ControllerFactory();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if(pathInfo == null) {
            resp.sendRedirect("beautysalon/main");
        }
        System.out.println("RequestURI: " + req.getRequestURI());
        Controller controller = controllerFactory.getController(req.getMethod() + pathInfo);
        String address = "";
        address = controller.execute(req, resp);
        System.out.println("address" + address);
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Controller controller = controllerFactory.getController(req.getMethod() + req.getPathInfo());
        String address = "";
        address = controller.execute(req, resp);
        System.out.println("Servlet path info: " + req.getPathInfo());
        System.out.println("Servlet address: " + address);

        req.getRequestDispatcher(address).forward(req, resp);
    }
}
