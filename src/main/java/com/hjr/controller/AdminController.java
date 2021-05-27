package com.hjr.controller;

import com.hjr.been.*;
import com.hjr.service.*;
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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CheckedService checkedService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @RequestMapping
    public String admin() {
        return "admin";
    }

    @RequestMapping("/studentlist")
    public String studentlist(HttpServletRequest request, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Student> studentList = studentService.findStudentByClassId(admin.getAdminClassId());
        Map<Integer, Long> todayCheckedMap = new HashMap<>();

        int todayCheckedNum = 0;
        for (Student student : studentList) {
            Checked lastChecked = checkedService.findLastCheckedByStudentId(student.getStudentId());
            if (lastChecked != null) {
                LocalDateTime lastCheckedTime = lastChecked.getCheckedTime();
                LocalDateTime now = LocalDateTime.now();
                Duration between = Duration.between(lastCheckedTime, now);
                long deltaHours = between.toHours();
                todayCheckedMap.put(student.getStudentId(), deltaHours);
                if (deltaHours < 24L && deltaHours >= 0L) {
                    todayCheckedNum++;
                }

                String checkedTimeFormat = lastChecked.getCheckedTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 mm:HH:ss"));
                request.setAttribute("checkedTimeFormat", checkedTimeFormat);
            }
            else {
                todayCheckedMap.put(student.getStudentId(), -1L);
            }
        }
        request.setAttribute("studentList", studentList);
        request.setAttribute("todayCheckedMap", todayCheckedMap);
        request.setAttribute("studentNum", studentList.size());
        request.setAttribute("todayCheckedNum", todayCheckedNum);

        return "studentlist";
    }

    @RequestMapping("/downloadstudentlist")
    public void downloadstudentlist(HttpServletResponse response, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");

        List<Student> studentList = studentService.findStudentByClassId(admin.getAdminClassId());
        String fileName = admin.getAdminName() + "的学生信息.xlsx";
        String headerName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + headerName);
        XSSFWorkbook workbook = ExcelUtil.studentListToExcel(studentList, admin.getAdminName());

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
        List<String> checkedTimeFormatList = checkedList.stream()
                .map(checked -> checked.getCheckedTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")))
                .collect(Collectors.toList());
        List<String> checkedLocateStringList = checkedList.stream()
                .map(checked -> districtService.districtToString(checked.getCheckedDistrictId()))
                .collect(Collectors.toList());

        request.setAttribute("checkedList", checkedList);
        request.setAttribute("checkedTimeFormatList", checkedTimeFormatList);
        request.setAttribute("checkedLocateStringList", checkedLocateStringList);
        request.setAttribute("studentId", studentId);
        request.setAttribute("studentName", studentName);
        request.setAttribute("sessionFlag", "admin");

        return "history";
    }

    @RequestMapping("/downloadcheckedlist")
    public void downloadcheckedlist(HttpServletResponse response, @RequestParam("id") Integer studentId, @RequestParam("name") String studentName) {
        List<Checked> checkedList = checkedService.findCheckedByStudentId(studentId);
        List<String> checkedLocateStringList = checkedList.stream()
                .map(checked -> districtService.districtToString(checked.getCheckedDistrictId()))
                .collect(Collectors.toList());

        String fileName = studentName + "-签到记录.xlsx";
        String headerName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + headerName);
        XSSFWorkbook workbook = ExcelUtil.checkedListToExcel(checkedList, checkedLocateStringList, studentName);

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

    @RequestMapping("/riskydistrict")
    public String riskydistrict(HttpServletRequest request) {
        List<Province> provinceList = provinceService.findAllProvince();
        List<District> districtList = districtService.findRiskyDistrict();
        List<String> locateStringList = districtList.stream()
                .map(district -> districtService.districtToString(district.getDistrictId()))
                .collect(Collectors.toList());

        request.setAttribute("provinceList", provinceList);
        request.setAttribute("districtList", districtList);
        request.setAttribute("locateStringList", locateStringList);

        return "riskydistrict";
    }

    @PostMapping("/addrisky")
    public String addrisky(Integer districtId) {
        districtService.updateDistrictRisky(districtId, true);

        return "redirect:/admin/riskydistrict";
    }

    @PostMapping("/deleterisky")
    public String deleterisky(Integer districtId) {
        districtService.updateDistrictRisky(districtId, false);

        return "redirect:/admin/riskydistrict";
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("admin");

        return "redirect:/";
    }
}
