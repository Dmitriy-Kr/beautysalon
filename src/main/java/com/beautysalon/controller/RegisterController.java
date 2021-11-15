package com.beautysalon.controller;

import com.beautysalon.entity.Account;
import com.beautysalon.entity.Client;
import com.beautysalon.entity.Role;
import com.beautysalon.entity.RoleEnum;
import com.beautysalon.security.SecureRole;
import com.beautysalon.security.SecureUser;
import com.beautysalon.service.AccountService;
import com.beautysalon.service.RegisterClientService;
import com.beautysalon.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class RegisterController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        boolean isCreated = false;
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        System.out.println("registerController: " + login + " " + password + " " + name + " " + surname);

        if (login == null || password == null || name == null || surname == null) {
            return "/WEB-INF/jsp/error.jsp";
        }

        if (login.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return "/register.html";
        }

        RegisterClientService registerClientService = new RegisterClientService();
        Client client = new Client();
        client.setAccount(new Account());
        client.getAccount().setLogin(login).setPassword(password).setRole(new Role(RoleEnum.CLIENT));
        client.setName(name).setSurname(surname);

        try {
            isCreated = registerClientService.create(client);
        } catch (ServiceException e) {
            e.printStackTrace();
            return "/WEB-INF/jsp/error.jsp";
        }

        if (isCreated) {
            request.getSession(true)
                    .setAttribute("secureUser", new SecureUser().setLogin(login).setRole(SecureRole.CLIENT));
            return "/WEB-INF/jsp/successfulregister.jsp";
        }
        return "/WEB-INF/jsp/error.jsp";
    }
}
