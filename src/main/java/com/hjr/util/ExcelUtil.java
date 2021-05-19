package com.hjr.util;

import com.hjr.been.Checked;
import com.hjr.been.Student;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUtil {

    private ExcelUtil() {}

    public static XSSFWorkbook checkedListToExcel(List<Checked> checkedList, List<String> checkedLocateList, String studentName) {
        //创建Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建一个工作簿
        XSSFSheet sheet = workbook.createSheet("checkedList");

        //设置每一列的宽度
        sheet.setColumnWidth(0, 22 * 256);
        sheet.setColumnWidth(1, 14 * 256);
        sheet.setColumnWidth(2, 21 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(4, 22 * 256);
        sheet.setColumnWidth(5, 22 * 256);

        //创建标题行
        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(studentName + "的签到记录");

        //设置标题居中
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置标题的字体
        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("等线");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setColor(new XSSFColor(new byte[]{(byte) 68, (byte) 114, (byte) 196}, new DefaultIndexedColorMap()));

        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        //合并标题的单元格
        CellRangeAddress titleCellAddresses = new CellRangeAddress(0, 0, 0, 4);
        sheet.addMergedRegion(titleCellAddresses);

        //创建表头行
        XSSFRow headerRow = sheet.createRow(1);
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, new DefaultIndexedColorMap()));

        //设置表头行每一格的内容
        XSSFCell checkedTimeHeaderCell = headerRow.createCell(0);
        checkedTimeHeaderCell.setCellValue("签到时间");
        checkedTimeHeaderCell.setCellStyle(headerStyle);

        XSSFCell checkedIsFeverHeaderCell = headerRow.createCell(1);
        checkedIsFeverHeaderCell.setCellValue("当天健康状况");
        checkedIsFeverHeaderCell.setCellStyle(headerStyle);

        XSSFCell checkedIsContactHeaderCell = headerRow.createCell(2);
        checkedIsContactHeaderCell.setCellValue("当天是否接触新冠患者");
        checkedIsContactHeaderCell.setCellStyle(headerStyle);

        XSSFCell checkedTemperatureHeaderCell = headerRow.createCell(3);
        checkedTemperatureHeaderCell.setCellValue("当天体温");
        checkedTemperatureHeaderCell.setCellStyle(headerStyle);

        XSSFCell checkedDistrictHeaderCell = headerRow.createCell(4);
        checkedDistrictHeaderCell.setCellValue("当天签到地");
        checkedDistrictHeaderCell.setCellStyle(headerStyle);

        //创建表体行的两种样式
        XSSFCellStyle oddValueStyle = workbook.createCellStyle();
        oddValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        oddValueStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 208, (byte) 206, (byte) 206}, new DefaultIndexedColorMap()));

        XSSFCellStyle evenValueStyle = workbook.createCellStyle();
        evenValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        evenValueStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 226, (byte) 239, (byte) 218}, new DefaultIndexedColorMap()));

        //设置表体的内容
        for (int i = 0; i < checkedList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 2);
            Checked checked = checkedList.get(i);

            XSSFCell checkedTimeValueCell = row.createCell(0);
            checkedTimeValueCell.setCellValue(checked.getCheckedTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));

            XSSFCell checkedIsFeverValueCell = row.createCell(1);
            checkedIsFeverValueCell.setCellValue(checked.getCheckedIsFever() ? "有发烧咳嗽症状" : "健康");

            XSSFCell checkedIsContactValueCell = row.createCell(2);
            checkedIsContactValueCell.setCellValue(checked.getCheckedIsContact() ? "是" : "否");

            XSSFCell checkedTemperatureValueCell = row.createCell(3);
            checkedTemperatureValueCell.setCellValue(checked.getCheckedTemperature() + "摄氏度");

            XSSFCell checkedDistrictValueCell = row.createCell(4);
            checkedDistrictValueCell.setCellValue(checkedLocateList.get(i));

            if ((i + 1) % 2 == 1) {
                checkedTimeValueCell.setCellStyle(oddValueStyle);
                checkedIsFeverValueCell.setCellStyle(oddValueStyle);
                checkedIsContactValueCell.setCellStyle(oddValueStyle);
                checkedTemperatureValueCell.setCellStyle(oddValueStyle);
                checkedDistrictValueCell.setCellStyle(oddValueStyle);
            }
            else {
                checkedTimeValueCell.setCellStyle(evenValueStyle);
                checkedIsFeverValueCell.setCellStyle(evenValueStyle);
                checkedIsContactValueCell.setCellStyle(evenValueStyle);
                checkedTemperatureValueCell.setCellStyle(evenValueStyle);
                checkedDistrictValueCell.setCellStyle(evenValueStyle);
            }
        }

        //设置文档生成时间
        XSSFCellStyle generationTimeHeaderStyle = workbook.createCellStyle();
        generationTimeHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        generationTimeHeaderStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 244, (byte) 176, (byte) 132}, new DefaultIndexedColorMap()));

        XSSFCell generationTimeHeaderCell = headerRow.createCell(5);
        generationTimeHeaderCell.setCellValue("生成时间");
        generationTimeHeaderCell.setCellStyle(generationTimeHeaderStyle);

        XSSFCellStyle generationTimeValueStyle = workbook.createCellStyle();
        generationTimeValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        generationTimeValueStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 252, (byte) 228, (byte) 214}, new DefaultIndexedColorMap()));

        XSSFCell generationTimeValueCell = sheet.getRow(2).createCell(5);
        generationTimeValueCell.setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        generationTimeValueCell.setCellStyle(generationTimeValueStyle);

        return workbook;
    }

    public static XSSFWorkbook studentListToExcel(List<Student> studentList, String adminName) {
        //创建Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建一个工作簿
        XSSFSheet sheet = workbook.createSheet("studentList");

        //设置每一列的宽度
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 11 * 256);
        sheet.setColumnWidth(4, 14 * 256);
        sheet.setColumnWidth(5, 12 * 256);
        sheet.setColumnWidth(6, 12 * 256);
        sheet.setColumnWidth(7, 8 * 256);
        sheet.setColumnWidth(8, 22 * 256);

        //创建标题行
        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(adminName + "的学生信息");

        //设置标题居中
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置标题的字体
        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("等线");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setColor(new XSSFColor(new byte[]{(byte) 68, (byte) 114, (byte) 196}, new DefaultIndexedColorMap()));

        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        //合并标题的单元格
        CellRangeAddress titleCellAddresses = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(titleCellAddresses);

        //创建表头行
        XSSFRow headerRow = sheet.createRow(1);
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, new DefaultIndexedColorMap()));

        //设置表头行每一格的内容
        XSSFCell studentNameHeaderCell = headerRow.createCell(0);
        studentNameHeaderCell.setCellValue("学生姓名");
        studentNameHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentPhoneHeaderCell = headerRow.createCell(1);
        studentPhoneHeaderCell.setCellValue("学生电话");
        studentPhoneHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentWechatHeaderCell = headerRow.createCell(2);
        studentWechatHeaderCell.setCellValue("学生微信");
        studentWechatHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentQQHeaderCell = headerRow.createCell(3);
        studentQQHeaderCell.setCellValue("学生QQ");
        studentQQHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentBirthdayHeaderCell = headerRow.createCell(4);
        studentBirthdayHeaderCell.setCellValue("学生生日");
        studentBirthdayHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentHeightHeaderCell = headerRow.createCell(5);
        studentHeightHeaderCell.setCellValue("学生身高(cm)");
        studentHeightHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentWeightHeaderCell = headerRow.createCell(6);
        studentWeightHeaderCell.setCellValue("学生体重(kg)");
        studentWeightHeaderCell.setCellStyle(headerStyle);

        XSSFCell studentGenderHeaderCell = headerRow.createCell(7);
        studentGenderHeaderCell.setCellValue("学生性别");
        studentGenderHeaderCell.setCellStyle(headerStyle);

        //创建表体行的两种样式
        XSSFCellStyle oddValueStyle = workbook.createCellStyle();
        oddValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        oddValueStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 208, (byte) 206, (byte) 206}, new DefaultIndexedColorMap()));

        XSSFCellStyle evenValueStyle = workbook.createCellStyle();
        evenValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        evenValueStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 226, (byte) 239, (byte) 218}, new DefaultIndexedColorMap()));

        //设置表体的内容
        for (int i = 0; i < studentList.size(); i++) {
            XSSFRow valueRow = sheet.createRow(i + 2);
            Student student = studentList.get(i);

            XSSFCell studentNameValueCell = valueRow.createCell(0);
            studentNameValueCell.setCellValue(student.getStudentName());

            XSSFCell studentPhoneValueCell = valueRow.createCell(1);
            studentPhoneValueCell.setCellValue(student.getStudentPhone());

            XSSFCell studentWechatValueCell = valueRow.createCell(2);
            studentWechatValueCell.setCellValue(student.getStudentWechat());

            XSSFCell studentQQValueCell = valueRow.createCell(3);
            studentQQValueCell.setCellValue(student.getStudentQQ());

            XSSFCell studentBirthdayValueCell = valueRow.createCell(4);
            studentBirthdayValueCell.setCellValue(student.getStudentBirthday().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));

            XSSFCell studentHeightValueCell = valueRow.createCell(5);
            studentHeightValueCell.setCellValue(student.getStudentHeight());

            XSSFCell studentWeightValueCell = valueRow.createCell(6);
            studentWeightValueCell.setCellValue(student.getStudentWeight());

            XSSFCell studentGenderValueCell = valueRow.createCell(7);
            if (student.getStudentGender() == 1) {
                studentGenderValueCell.setCellValue("男");
            }
            else if (student.getStudentGender() == 2) {
                studentGenderValueCell.setCellValue("女");
            }
            else {
                studentGenderValueCell.setCellValue("不确定");
            }

            if ((i + 1) % 2 == 1) {
                studentNameValueCell.setCellStyle(oddValueStyle);
                studentPhoneValueCell.setCellStyle(oddValueStyle);
                studentWechatValueCell.setCellStyle(oddValueStyle);
                studentQQValueCell.setCellStyle(oddValueStyle);
                studentBirthdayValueCell.setCellStyle(oddValueStyle);
                studentHeightValueCell.setCellStyle(oddValueStyle);
                studentWeightValueCell.setCellStyle(oddValueStyle);
                studentGenderValueCell.setCellStyle(oddValueStyle);
            }
            else {
                studentNameValueCell.setCellStyle(evenValueStyle);
                studentPhoneValueCell.setCellStyle(evenValueStyle);
                studentWechatValueCell.setCellStyle(evenValueStyle);
                studentQQValueCell.setCellStyle(evenValueStyle);
                studentBirthdayValueCell.setCellStyle(evenValueStyle);
                studentHeightValueCell.setCellStyle(evenValueStyle);
                studentWeightValueCell.setCellStyle(evenValueStyle);
                studentGenderValueCell.setCellStyle(evenValueStyle);
            }
        }

        //设置文档生成时间
        XSSFCellStyle generationTimeHeaderStyle = workbook.createCellStyle();
        generationTimeHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        generationTimeHeaderStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 244, (byte) 176, (byte) 132}, new DefaultIndexedColorMap()));

        XSSFCell generationTimeHeaderCell = headerRow.createCell(8);
        generationTimeHeaderCell.setCellValue("生成时间");
        generationTimeHeaderCell.setCellStyle(generationTimeHeaderStyle);

        XSSFCellStyle generationTimeValueStyle = workbook.createCellStyle();
        generationTimeValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        generationTimeValueStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 252, (byte) 228, (byte) 214}, new DefaultIndexedColorMap()));

        XSSFCell generationTimeValueCell = sheet.getRow(2).createCell(8);
        generationTimeValueCell.setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        generationTimeValueCell.setCellStyle(generationTimeValueStyle);

        return workbook;
    }
}
