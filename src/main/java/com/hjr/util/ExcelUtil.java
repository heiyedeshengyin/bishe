package com.hjr.util;

import com.hjr.been.Checked;
import com.hjr.been.Student;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUtil {

    private ExcelUtil() {}

    public static XSSFWorkbook checkedListToExcel(List<Checked> checkedList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("checkedList");
        sheet.setColumnWidth(0, 22 * 256);
        sheet.setColumnWidth(1, 10 * 256);

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("签到时间");
        headerRow.createCell(1).setCellValue("签到体温");

        for (int i = 0; i < checkedList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            Checked checked = checkedList.get(i);
            row.createCell(0).setCellValue(checked.getCheckedTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
            row.createCell(1).setCellValue(checked.getCheckedTemperature() + "摄氏度");
        }

        return workbook;
    }

    public static XSSFWorkbook studentListToExcel(List<Student> studentList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("studentList");
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 11 * 256);
        sheet.setColumnWidth(4, 14 * 256);
        sheet.setColumnWidth(5, 12 * 256);
        sheet.setColumnWidth(6, 12 * 256);
        sheet.setColumnWidth(7, 8 * 256);

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("学生姓名");
        headerRow.createCell(1).setCellValue("学生电话");
        headerRow.createCell(2).setCellValue("学生微信");
        headerRow.createCell(3).setCellValue("学生QQ");
        headerRow.createCell(4).setCellValue("学生生日");
        headerRow.createCell(5).setCellValue("学生身高(cm)");
        headerRow.createCell(6).setCellValue("学生体重(kg)");
        headerRow.createCell(7).setCellValue("学生性别");

        for (int i = 0; i < studentList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            Student student = studentList.get(i);
            row.createCell(0).setCellValue(student.getStudentName());
            row.createCell(1).setCellValue(student.getStudentPhone());
            row.createCell(2).setCellValue(student.getStudentWechat());
            row.createCell(3).setCellValue(student.getStudentQQ());
            row.createCell(4).setCellValue(student.getStudentBirthday().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
            row.createCell(5).setCellValue(student.getStudentHeight() + "cm");
            row.createCell(6).setCellValue(student.getStudentWeight() + "kg");
            if (student.getStudentGender() == 1) {
                row.createCell(7).setCellValue("男");
            }
            else if (student.getStudentGender() == 2) {
                row.createCell(7).setCellValue("女");
            }
            else {
                row.createCell(7).setCellValue("不确定");
            }
        }

        return workbook;
    }
}
