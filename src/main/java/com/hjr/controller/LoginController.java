package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Student;
import com.hjr.service.AdminService;
import com.hjr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        Student student = studentService.findStudentByLoginName(username);
        if (student != null && student.getStudentPassword().equals(password)) {
            session.setAttribute("student", student);

            return "redirect:/student";
        }

        Admin admin = adminService.findAdminByLoginName(username);
        if (admin != null && admin.getAdminPassword().equals(password)) {
            session.setAttribute("admin", admin);

            return "redirect:/admin";
        }

        return "redirect:/";
    }
}
