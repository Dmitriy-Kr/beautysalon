package com.beautysalon.server.filter;

import com.beautysalon.entity.RoleEnum;
import com.beautysalon.security.SecureUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@WebFilter("/beautysalon/*")
public class SecureFilter implements Filter {
    Map<String, Set<RoleEnum>> accessMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap = new HashMap<>();
        accessMap.put("/login", Set.of(RoleEnum.values()));
        accessMap.put("/service", Set.of(RoleEnum.values()));
        accessMap.put("/register", Set.of(RoleEnum.values()));
        accessMap.put("/order", Set.of(RoleEnum.CLIENT));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        SecureUser secureUser;
        HttpSession httpSession = httpServletRequest.getSession(true);
        secureUser = (SecureUser) httpSession.getAttribute("secureUser");

        String path = httpServletRequest.getPathInfo();

        if (secureUser == null) {
            secureUser = new SecureUser().setLogin("guest").setRole(RoleEnum.GUEST);
            httpSession.setAttribute("secureUser", secureUser);
        }

        System.out.println("Filter: " + path);

        if ("/logout".equals(path)) {
            httpSession.invalidate();
            httpServletResponse.sendRedirect("/index.jsp");
            System.out.println("After sendredirect");
        } else {
            if (accessMap.get(path).contains(secureUser.getRole())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpServletResponse.sendRedirect("login");
            }
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
