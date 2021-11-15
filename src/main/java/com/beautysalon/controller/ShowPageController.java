package com.beautysalon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageController implements Controller{
    String pageName;

    public ShowPageController(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return pageName;
    }
}
