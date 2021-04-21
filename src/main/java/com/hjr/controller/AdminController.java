package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Student;
import com.hjr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/studentlist")
    public String studentlist(HttpServletRequest request, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Student> studentList = studentService.findStudentByClassId(admin.getAdminClassId());
        request.setAttribute("studentList", studentList);

        return "studentlist";
    }
}
