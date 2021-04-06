package com.hjr.been;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Admin {
    private Integer adminId;
    private String adminLoginName;
    private String adminPassword;

    private String adminName;
    private String adminPhone;
    private String adminWechat;
    private String adminQQ;
    private LocalDate adminBirthday;
    private Integer adminGender;

    private Boolean isAdminDelete;
    private Integer adminClassId;
}
