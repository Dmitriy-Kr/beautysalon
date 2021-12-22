package com.beautysalon.controller;

import com.beautysalon.entity.Account;
import com.beautysalon.entity.RoleEnum;
import com.beautysalon.security.SecureUser;
import com.beautysalon.service.AccountService;
import com.beautysalon.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginController implements Controller {
    private Map<RoleEnum, String> userURL = Map.of(
            RoleEnum.GUEST, "/index.jsp",
            RoleEnum.CLIENT, "/WEB-INF/jsp/account.jsp",
            RoleEnum.EMPLOYEE, "/WEB-INF/jsp/account.jsp",
            RoleEnum.ADMIN, "/WEB-INF/jsp/adminpage.jsp");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println("login controller: " + login + "   " + password);

        if (login == null || password == null) {
            return "/WEB-INF/jsp/error.jsp";
        }

        if (login.isEmpty() || password.isEmpty()) {
            return "/login.html";
        }

        AccountService accountService = new AccountService();
        Account account;

        try {
            account = accountService.findByValue(login);
        } catch (ServiceException e) {
            e.printStackTrace();
            return "/WEB-INF/jsp/error.jsp";
        }

        if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
            System.out.println("account role: " + account.getRole().getName().toString());
            request.getSession(true)
                    .setAttribute("secureUser",
                            new SecureUser()
                                    .setLogin(login)
                                    .setRole(account.getRole().getName())
                                    .setAccountId(account.getId()));

            request.setAttribute("account", account);
            System.out.println("Account: " + account.getLogin() + "  " + account.getPassword());

            return userURL.get(account.getRole().getName());
        }
        return "/WEB-INF/jsp/error.jsp";
    }
}
