package com.hjr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String admin(@CookieValue("admin_login_name") String adminLoginName, HttpSession session) {

        return "admin";
    }
}
