package com.hjr.service;

import com.hjr.been.Admin;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@DisplayName("AdminService Test")
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    @DisplayName("AdminService findAllAdmin Test")
    public void testFindAllAdmin() {
        List<Admin> adminList = adminService.findAllAdmin();
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }

    @Test
    @DisplayName("AdminService findIdByLoginName Test")
    public void testFindIdByLoginName() {
        Integer id = adminService.findIdByLoginName("admin2");

        System.out.println(id);
    }

    @Test
    @DisplayName("AdminService findAdminByLoginName Test")
    public void testFindAdminByLoginName() {
        Admin admin = adminService.findAdminByLoginName("admin");
        System.out.println(admin);
    }

    @Test
    @DisplayName("AdminService updateAdmin Test")
    @Disabled
    public void testUpdateAdmin() {
        Admin admin = adminService.findAdminByLoginName("admin");
        admin.setAdminQQ("124353467");
        adminService.updateAdmin(admin);
    }

    @Test
    @DisplayName("AdminService insertIntoAdmin Test")
    @Disabled
    public void testInsertIntoAdmin() {
        Admin admin = new Admin();
        admin.setAdminLoginName("admin3");
        admin.setAdminPassword("admin3");
        admin.setAdminName("admin3");
        admin.setAdminPhone("12542135642");
        admin.setAdminWechat("12f43");
        admin.setAdminQQ("2432313342");
        admin.setAdminBirthday(LocalDate.of(1995, 3, 3));
        admin.setAdminGender(2);
        admin.setIsAdminDelete(false);
        admin.setAdminClassId(5);

        adminService.insertIntoAdmin(admin);
    }
}
