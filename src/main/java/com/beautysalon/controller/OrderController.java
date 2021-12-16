package com.beautysalon.controller;

import com.beautysalon.entity.*;
import com.beautysalon.security.SecureUser;
import com.beautysalon.service.OrderingService;
import com.beautysalon.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class OrderController implements Controller {
    private OrderingService orderingService = new OrderingService();
    private Ordering ordering;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long serviceId = Long.parseLong(request.getParameter("serviceId"));
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        String orderDate = request.getParameter("order_date");
        LocalDateTime localDateTime = null;


        HttpSession httpSession = request.getSession();
        SecureUser secureUser = (SecureUser) httpSession.getAttribute("secureUser");
        long clientId = 0;
        if (secureUser.getRole().equals(RoleEnum.CLIENT)) {
            clientId = secureUser.getAccountId();
        } else {
            System.out.println("OrderController secureUser role: " + secureUser.getRole());
            return "/WEB-INF/jsp/error.jsp";
        }

        System.out.println("OrderController serviceId: " + serviceId);
        System.out.println("OrderController employeeId: " + employeeId);
        System.out.println("OrderController clientId: " + clientId);
        System.out.println("OrderController orderDate: " + orderDate);

        localDateTime = LocalDateTime.parse(orderDate);


        System.out.println("OrderController localDateTime: " + localDateTime);

        ordering = createOrdering(localDateTime, serviceId, employeeId, clientId);
        try {
            orderingService.create(ordering);
        } catch (ServiceException e) {
            e.printStackTrace();
            return "/WEB-INF/jsp/error.jsp";
        }

        request.setAttribute("ordering", ordering);

        return "/WEB-INF/jsp/orderOk.jsp";
    }

    private Ordering createOrdering(LocalDateTime localDateTime,
                                    long serviceId,
                                    long employeeId,
                                    long clientId) {

        return new Ordering().setCreateTime(localDateTime)
                .setService(new Service().setId(serviceId))
                .setEmployee(new Employee().setId(employeeId))
                .setClient(new Client().setId(clientId))
                .setStatus(StatusEnum.ACTIVE);
    }
}
