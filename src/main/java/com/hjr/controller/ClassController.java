package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Class;
import com.hjr.been.Student;
import com.hjr.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ClassController {

    @Autowired
    private ClassService classService;

    @RequestMapping("/classinfo")
    public String classinfo(HttpServletRequest request, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            Integer studentClassId = student.getStudentClassId();
            Class classById = classService.findClassById(studentClassId);
            request.setAttribute("class", classById);
            request.setAttribute("sessionFlag", "student");

            return "classinfo";
        }

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            Integer studentClassId = admin.getAdminClassId();
            Class classByid = classService.findClassById(studentClassId);
            request.setAttribute("class", classByid);
            request.setAttribute("sessionFlag", "admin");

            return "classinfo";
        }

        return "redirect:/";
    }
}
