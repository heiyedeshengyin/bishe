package com.hjr.controller;

import com.hjr.been.Checked;
import com.hjr.been.Province;
import com.hjr.been.Student;
import com.hjr.service.CheckedService;
import com.hjr.service.DistrictService;
import com.hjr.service.ProvinceService;
import com.hjr.service.StudentService;
import com.hjr.util.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CheckedService checkedService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @RequestMapping
    public String student() {
        return "student";
    }

    @RequestMapping("/checked")
    public String checked(HttpServletRequest request) {
        List<Province> provinceList = provinceService.findAllProvince();
        request.setAttribute("provinceList", provinceList);

        return "check";
    }

    @PostMapping("/check")
    public String check(Integer checkedIsFever, Integer checkedIsContact, String checkedTemperature,
                        Integer checkedDistrictId, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        Checked checked = new Checked();
        checked.setCheckedId(0);
        checked.setCheckedTime(LocalDateTime.now());
        checked.setCheckedIsFever(checkedIsFever != 0);
        checked.setCheckedIsContact(checkedIsContact != 0);
        checked.setCheckedTemperature(checkedTemperature);
        checked.setCheckedDistrictId(checkedDistrictId);
        checked.setIsCheckedDelete(false);
        checked.setCheckedStudentId(student.getStudentId());

        checkedService.insertChecked(checked);

        return "redirect:/student/history";
    }

    @RequestMapping("/info")
    public String info() {
        return "studentinfo";
    }

    @RequestMapping("/history")
    public String history(HttpServletRequest request, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        List<Checked> checkedList = checkedService.findCheckedByStudentId(student.getStudentId());
        List<String> checkedTimeFormatList = checkedList.stream()
                .map(checked -> checked.getCheckedTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")))
                .collect(Collectors.toList());
        List<String> checkedLocateStringList = checkedList.stream()
                .map(checked -> districtService.districtToString(checked.getCheckedDistrictId()))
                .collect(Collectors.toList());

        request.setAttribute("checkedList", checkedList);
        request.setAttribute("checkedTimeFormatList", checkedTimeFormatList);
        request.setAttribute("checkedLocateStringList", checkedLocateStringList);
        request.setAttribute("sessionFlag", "student");

        return "history";
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        List<Checked> checkedList = checkedService.findCheckedByStudentId(student.getStudentId());
        String fileName = student.getStudentName() + "-签到记录.xlsx";
        String headerName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + headerName);
        XSSFWorkbook workbook = ExcelUtil.checkedListToExcel(checkedList, student.getStudentName());

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

    @PostMapping("/update")
    public String update(HttpSession session, String studentName, String studentPhone,
                                    String studentWechat, String studentQQ, LocalDate studentBirthday,
                                    String studentHeight, String studentWeight, Integer studentGender) {
        Student student = (Student) session.getAttribute("student");

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

        return "redirect:/student/info";
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("student");

        return "redirect:/";
    }
}
