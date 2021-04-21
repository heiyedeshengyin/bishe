package com.hjr.controller;

import com.hjr.been.Checked;
import com.hjr.been.Student;
import com.hjr.service.CheckedService;
import com.hjr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private CheckedService checkedService;

    @Autowired
    private StudentService studentService;

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

    @PostMapping("/updatestudentinfo")
    public String updatestudentinfo(HttpSession session, String studentName, String studentPhone,
                                    String studentWechat, String studentQQ, LocalDate studentBirthday,
                                    String studentHeight, String studentWeight, Integer studentGender) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            Student updateStudent = new Student();
            updateStudent.setStudentId(student.getStudentId());
            updateStudent.setStudentLoginName(student.getStudentLoginName());
            updateStudent.setStudentPassword(student.getStudentPassword());
            updateStudent.setStudentName(studentName);
            updateStudent.setStudentPhone(studentPhone);
            updateStudent.setStudentWechat(studentWechat);
            updateStudent.setStudentQQ(studentQQ);
            updateStudent.setStudentBirthday(studentBirthday);
            updateStudent.setStudentHeight(studentHeight);
            updateStudent.setStudentWeight(studentWeight);
            updateStudent.setStudentGender(studentGender);
            updateStudent.setIsStudentDelete(student.getIsStudentDelete());
            updateStudent.setStudentClassId(student.getStudentClassId());

            studentService.updateStudent(updateStudent);
            session.setAttribute("student", updateStudent);

            return "redirect:/studentinfo";
        }
        else {
            return "fail";
        }
    }
}
