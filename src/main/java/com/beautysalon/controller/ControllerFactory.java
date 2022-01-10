package com.beautysalon.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    Map<String, Controller> controllerMap = new HashMap<>();

    {
        controllerMap.put("GET/login", new ShowPageController("/login.html"));
        controllerMap.put("GET/service", new ServiceController());
        controllerMap.put("GET/orderings", new OrderingsController());
        controllerMap.put("GET/order", new ShowPageController("/WEB-INF/jsp/order.jsp"));
        controllerMap.put("POST/login", new LoginController());
        controllerMap.put("POST/register", new RegisterController());
        controllerMap.put("POST/order", new OrderController());
    }

    public Controller getController(String address){
        return controllerMap.get(address);
    }
}
