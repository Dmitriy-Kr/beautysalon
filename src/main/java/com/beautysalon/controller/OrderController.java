package com.beautysalon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long serviceId = Long.parseLong(request.getParameter("serviceId"));

        return null;
    }
}
