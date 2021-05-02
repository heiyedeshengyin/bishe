package com.hjr.util;

import com.hjr.been.Checked;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckedExcelUtil {

    private CheckedExcelUtil() {}

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
            row.createCell(0).setCellValue(checkedList.get(i).getCheckedTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
            row.createCell(1).setCellValue(checkedList.get(i).getCheckedTemperature() + "摄氏度");
        }

        return workbook;
    }
}
