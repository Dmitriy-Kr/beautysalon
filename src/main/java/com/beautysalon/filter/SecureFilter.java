package com.beautysalon.filter;

import com.beautysalon.security.SecureRole;
import com.beautysalon.security.SecureUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@WebFilter("/beautysalon/*")
public class SecureFilter implements Filter {
    Map<String, Set<SecureRole>>  accessMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap = new HashMap<>();
        accessMap.put("/login", Set.of(SecureRole.values()));
        accessMap.put("/service", Set.of(SecureRole.values()));
        accessMap.put("/register", Set.of(SecureRole.values()));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        SecureUser secureUser;
        HttpSession httpSession = httpServletRequest.getSession(true);
        secureUser = (SecureUser) httpSession.getAttribute("secureUser");

        if (secureUser == null){
            secureUser = new SecureUser();
            secureUser.setLogin("guest").setRole(SecureRole.GUEST);
            httpSession.setAttribute("secureUser", secureUser);
        }

        System.out.println(httpServletRequest.getPathInfo());

        if (accessMap.get(httpServletRequest.getPathInfo()).contains(secureUser.getRole())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect("login");
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
