package com.hjr.been;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Student {
    private Integer studentId;
    private String studentLoginName;
    private String studentPassword;

    private String studentName;
    private String studentPhone;
    private String studentWechat;
    private String studentQQ;
    private LocalDate studentBirthday;
    private String studentHeight;
    private String studentWeight;
    private Integer studentGender;

    private Boolean isStudentDelete;
    private Integer studentClassId;
}
