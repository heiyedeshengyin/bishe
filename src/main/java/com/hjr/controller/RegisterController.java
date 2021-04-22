package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Class;
import com.hjr.been.Student;
import com.hjr.service.AdminService;
import com.hjr.service.ClassService;
import com.hjr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @RequestMapping("/student")
    public String student(HttpServletRequest request) {
        List<Class> allClass = classService.findAllClass();
        request.setAttribute("allClass", allClass);

        return "registerstudent";
    }

    @RequestMapping("/admin")
    public String admin(HttpServletRequest request) {
        List<Class> allClass = classService.findAllClass();
        request.setAttribute("allClass", allClass);

        return "registeradmin";
    }

    @PostMapping("/insertstudent")
    public String insertstudent(String studentLoginName, String studentPassword, String studentName,
                                String studentPhone, String studentWechat, String studentQQ,
                                LocalDate studentBirthday, String studentHeight, String studentWeight,
                                Integer studentGender, Integer studentClassId, HttpSession session) {
        Student student = new Student();
        student.setStudentLoginName(studentLoginName);
        student.setStudentPassword(studentPassword);
        student.setStudentName(studentName);
        student.setStudentPhone(studentPhone);
        student.setStudentWechat(studentWechat);
        student.setStudentQQ(studentQQ);
        student.setStudentBirthday(studentBirthday);
        student.setStudentHeight(studentHeight);
        student.setStudentWeight(studentWeight);
        student.setStudentGender(studentGender);
        student.setIsStudentDelete(false);
        student.setStudentClassId(studentClassId);

        studentService.insertIntoStudent(student);
        Integer studentId = studentService.findIdByLoginName(studentLoginName);
        student.setStudentId(studentId);
        session.setAttribute("student", student);
        return "redirect:/register/success";
    }

    @PostMapping("/insertadmin")
    public String insertadmin(String adminLoginName, String adminPassword, String adminName,
                              String adminPhone, String adminWechat, String adminQQ,
                              LocalDate adminBirthday, Integer adminGender, Integer adminClassId, HttpSession session) {
        Admin admin = new Admin();
        admin.setAdminLoginName(adminLoginName);
        admin.setAdminPassword(adminPassword);
        admin.setAdminName(adminName);
        admin.setAdminPhone(adminPhone);
        admin.setAdminWechat(adminWechat);
        admin.setAdminQQ(adminQQ);
        admin.setAdminBirthday(adminBirthday);
        admin.setAdminGender(adminGender);
        admin.setIsAdminDelete(false);
        admin.setAdminClassId(adminClassId);

        adminService.insertIntoAdmin(admin);
        Integer adminId = adminService.findIdByLoginName(adminLoginName);
        admin.setAdminId(adminId);
        session.setAttribute("admin", admin);
        return "redirect:/register/success";
    }

    @RequestMapping("/success")
    public String success(HttpServletRequest request, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            request.setAttribute("sessionFlag", "student");
            return "registersuccess";
        }

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            request.setAttribute("sessionFlag", "admin");
            return "registersuccess";
        }

        return "redirect:/";
    }
}
