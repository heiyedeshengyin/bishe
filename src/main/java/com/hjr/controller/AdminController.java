package com.hjr.controller;

import com.hjr.been.Admin;
import com.hjr.been.Checked;
import com.hjr.been.Student;
import com.hjr.service.AdminService;
import com.hjr.service.CheckedService;
import com.hjr.service.StudentService;
import com.hjr.util.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, Long> todayCheckedMap = new HashMap<>();
        for (Student student : studentList) {
            Checked lastChecked = checkedService.findLastCheckedByStudentId(student.getStudentId());
            if (lastChecked != null) {
                LocalDateTime lastCheckedTime = lastChecked.getCheckedTime();
                LocalDateTime now = LocalDateTime.now();
                Duration between = Duration.between(lastCheckedTime, now);
                todayCheckedMap.put(student.getStudentId(), between.toHours());
            }
            else {
                todayCheckedMap.put(student.getStudentId(), -1L);
            }
        }
        request.setAttribute("studentList", studentList);
        request.setAttribute("todayCheckedMap", todayCheckedMap);

        return "studentlist";
    }

    @RequestMapping("/downloadstudentlist")
    public void downloadstudentlist(HttpServletResponse response, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");

        List<Student> studentList = studentService.findStudentByClassId(admin.getAdminClassId());
        String fileName = admin.getAdminName() + "的学生信息.xlsx";
        String headerName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + headerName);
        XSSFWorkbook workbook = ExcelUtil.studentListToExcel(studentList);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/checkedlist")
    public String checkedlist(HttpServletRequest request, @RequestParam("id") Integer studentId, @RequestParam("name") String studentName) {
        List<Checked> checkedList = checkedService.findCheckedByStudentId(studentId);
        request.setAttribute("checkedList", checkedList);
        request.setAttribute("studentId", studentId);
        request.setAttribute("studentName", studentName);
        request.setAttribute("sessionFlag", "admin");

        return "history";
    }

    @RequestMapping("/downloadcheckedlist")
    public void downloadcheckedlist(HttpServletResponse response, @RequestParam("id") Integer studentId, @RequestParam("name") String studentName) {
        List<Checked> checkedList = checkedService.findCheckedByStudentId(studentId);
        String fileName = studentName + "-签到记录.xlsx";
        String headerName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + headerName);
        XSSFWorkbook workbook = ExcelUtil.checkedListToExcel(checkedList);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
