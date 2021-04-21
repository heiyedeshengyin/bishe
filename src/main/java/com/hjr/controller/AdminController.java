package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Checked;
import com.hjr.been.Student;
import com.hjr.service.AdminService;
import com.hjr.service.CheckedService;
import com.hjr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CheckedService checkedService;

    @Autowired
    private AdminService adminService;

    @RequestMapping
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

    @RequestMapping("/checkedlist")
    public String checkedlist(HttpServletRequest request, @RequestParam("id") Integer studentId) {
        List<Checked> checkedList = checkedService.findCheckedByStudentId(studentId);
        request.setAttribute("checkedList", checkedList);

        return "history";
    }

    @RequestMapping("/info")
    public String info() {
        return "admininfo";
    }

    @PostMapping("/update")
    public String update(HttpSession session, String adminName, String adminPhone, String adminWechat,
                         String adminQQ, LocalDate adminBirthday, Integer adminGender) {
        Admin admin = (Admin) session.getAttribute("admin");

        Admin updateAdmin = new Admin();
        updateAdmin.setAdminId(admin.getAdminId());
        updateAdmin.setAdminLoginName(admin.getAdminLoginName());
        updateAdmin.setAdminPassword(admin.getAdminPassword());
        updateAdmin.setAdminName(adminName);
        updateAdmin.setAdminPhone(adminPhone);
        updateAdmin.setAdminWechat(adminWechat);
        updateAdmin.setAdminQQ(adminQQ);
        updateAdmin.setAdminBirthday(adminBirthday);
        updateAdmin.setAdminGender(adminGender);
        updateAdmin.setIsAdminDelete(admin.getIsAdminDelete());
        updateAdmin.setAdminClassId(admin.getAdminClassId());

        adminService.updateAdmin(updateAdmin);
        session.setAttribute("admin", updateAdmin);

        return "redirect:/admin/info";
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("admin");

        return "redirect:/";
    }
}
