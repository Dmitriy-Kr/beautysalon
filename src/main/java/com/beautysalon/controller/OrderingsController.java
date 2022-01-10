package com.beautysalon.controller;

import com.beautysalon.entity.Ordering;
import com.beautysalon.service.OrderingService;
import com.beautysalon.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OrderingsController implements Controller {
    OrderingService orderingService = new OrderingService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Ordering> orderings = null;
        HttpSession session;

        try {
            orderings = orderingService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        session = request.getSession(false);
        session.setAttribute("orderings", orderings);

        return "/WEB-INF/jsp/orderings.jsp";
    }
}
