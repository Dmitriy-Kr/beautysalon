package com.beautysalon.controller;

import com.beautysalon.dto.ServicePageDto;
import com.beautysalon.service.SalonService;
import com.beautysalon.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        SalonService salonService = new SalonService();
        HttpSession session = null;
        List<ServicePageDto> serviceList = null;
        String sort = request.getParameter("sort");
        String filter = request.getParameter("filter");
        boolean isFilteredServiceList = false;
        boolean firstVisitOrRefresh = sort == null && filter == null;

        if (firstVisitOrRefresh) {
            try {
                serviceList = salonService.findAll();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            serviceList.sort((o1, o2) -> o1.getServiceName().compareTo(o2.getServiceName()));
        } else {

            session = request.getSession(false);

            if (session == null) {
                try {
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            serviceList = (List<ServicePageDto>) session.getAttribute("serviceList");

            if (sort != null) {
                if (sort.equalsIgnoreCase("surname")) {
                    serviceList.sort(Comparator.comparing(ServicePageDto::getEmployeeSurname));
                }

                if (sort.equalsIgnoreCase("rating")) {
                    serviceList.sort((o1, o2) -> Double.compare(o2.getEmployeeRating(), o1.getEmployeeRating()));
                }
            }

            if (filter != null) {
                if (filter.equals("service")) {
                    String serviceName = request.getParameter("name");
                    serviceList = serviceList.stream()
                            .filter(servicePageDto -> servicePageDto.getServiceName().equals(serviceName))
                            .collect(Collectors.toList());
                }

                if (filter.equals("employee")) {
                    String employeeName = request.getParameter("name");
                    String employeeSurname = request.getParameter("surname");
                    serviceList = serviceList.stream()
                            .filter(servicePageDto -> servicePageDto.getEmployeeName().equals(employeeName)
                                    && servicePageDto.getEmployeeSurname().equals(employeeSurname))
                            .collect(Collectors.toList());
                }
                isFilteredServiceList = true;
            }
        }

        if (session == null) {
            session = request.getSession(true);
        }

        session.setAttribute("serviceList", serviceList);
        session.setAttribute("isFilteredServiceList", isFilteredServiceList);
        return "/WEB-INF/jsp/service.jsp";
    }
}
