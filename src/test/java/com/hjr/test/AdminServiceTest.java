package com.hjr.test;

import com.hjr.been.Admin;
import com.hjr.service.AdminService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
