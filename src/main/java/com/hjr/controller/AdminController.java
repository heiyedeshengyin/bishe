package com.hjr.controller;

import com.hjr.been.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String admin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            return "admin";
        }
        else {
            return "fail";
        }
    }
}