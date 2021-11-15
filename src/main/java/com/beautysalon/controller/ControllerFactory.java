package com.beautysalon.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    Map<String, Controller> controllerMap = new HashMap<>();

    {
        controllerMap.put("GET/login", new ShowPageController("/login.html"));
        controllerMap.put("GET/service", new ServiceController());
        controllerMap.put("POST/login", new LoginController());
        controllerMap.put("POST/register", new RegisterController());
    }

    public Controller getController(String address){
        return controllerMap.get(address);
    }
}
