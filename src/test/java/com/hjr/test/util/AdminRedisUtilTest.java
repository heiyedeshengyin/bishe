package com.hjr.test.util;

import com.hjr.been.Admin;
import com.hjr.mapper.AdminMapper;
import com.hjr.util.AdminRedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
@DisplayName("AdminRedisUtil Test")
public class AdminRedisUtilTest {

    @Autowired
    private AdminRedisUtil adminRedisUtil;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    @DisplayName("AdminRedisUtil setAdminListByLoginName Test")
    public void testSetAdminListByLoginName() {
        List<Admin> adminList = adminMapper.findAllAdmin();
        adminRedisUtil.setAdminListByLoginName("admin_list_test", adminList, Duration.ofMinutes(3));
    }

    @Test
    @DisplayName("AdminRedisUtil getAdminList Test")
    public void testGetAdminList() {
        List<Admin> adminList = adminRedisUtil.getAdminList("admin_list_test");
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }
}
