package com.hjr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class StudentController {

    @RequestMapping("/student")
    public String student(@CookieValue("student_login_name") String studentLoginName, HttpSession session) {

        return "student";
    }
}
