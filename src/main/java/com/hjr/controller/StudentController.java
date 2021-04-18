package com.hjr.controller;

import com.hjr.been.Checked;
import com.hjr.been.Student;
import com.hjr.service.CheckedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private CheckedService checkedService;

    @RequestMapping("/student")
    public String student(HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            return "student";
        }
        else {
            return "fail";
        }
    }

    @RequestMapping("/checkpage")
    public String checkpage() {
        return "check";
    }

    @RequestMapping("/check")
    public String check(String temperature, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            Checked checked = new Checked();
            checked.setCheckedId(0);
            checked.setCheckedTime(LocalDateTime.now());
            checked.setCheckedTemperature(temperature);
            checked.setIsCheckedDelete(false);
            checked.setCheckedStudentId(student.getStudentId());

            checkedService.insertChecked(checked);

            return "success";
        }
        else {
            return "fail";
        }
    }

    @RequestMapping("/studentinfo")
    public String studentinfo(HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            return "studentinfo";
        }
        else {
            return "fail";
        }
    }

    @RequestMapping("/checkhistory")
    public String checkhistory(HttpServletRequest request, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            List<Checked> checkedList = checkedService.findCheckedByStudentId(student.getStudentId());
            request.setAttribute("checkedList", checkedList);

            return "history";
        }
        else {
            return "fail";
        }
    }
}
