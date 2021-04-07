package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Student;
import com.hjr.service.AdminService;
import com.hjr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, HttpServletResponse response) {
        Student student = studentService.findStudentByLoginName(username);
        if (student != null && student.getStudentPassword().equals(password)) {
            Cookie studentCookie = new Cookie("student_login_name", username);
            session.setAttribute("student", student);
            response.addCookie(studentCookie);

            return "redirect:/student";
        }

        Admin admin = adminService.findAdminByLoginName(username);
        if (admin != null && admin.getAdminPassword().equals(password)) {
            Cookie adminCookie = new Cookie("admin_login_name", username);
            session.setAttribute("admin", admin);
            response.addCookie(adminCookie);

            return "redirect:/admin";
        }

        return "fail";
    }
}
