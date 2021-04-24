package com.hjr.service;

import com.hjr.been.Admin;
import com.hjr.mapper.AdminMapper;
import com.hjr.util.AdminRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminService {

    private static final String ADMIN_MAP_REDIS_KEY = "admin_map_by_login_name";

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRedisUtil adminRedisUtil;

    public List<Admin> findAllAdmin() {
        if (adminRedisUtil.hasKey(ADMIN_MAP_REDIS_KEY)) {
            log.info("Find Admin in Redis");
            return adminRedisUtil.getAdminList(ADMIN_MAP_REDIS_KEY);
        }
        else {
            List<Admin> adminList = adminMapper.findAllAdmin();
            adminRedisUtil.setAdminListByLoginName(ADMIN_MAP_REDIS_KEY, adminList, Duration.ofDays(7));
            log.info("Find Admin in Database");
            return adminList;
        }
    }

    public Integer findIdByLoginName(String loginName) {
        if (adminRedisUtil.hasKey(ADMIN_MAP_REDIS_KEY)) {
            Admin admin = adminRedisUtil.getAdminByLoginName(ADMIN_MAP_REDIS_KEY, loginName);
            log.info("Find Admin in Redis");
            return admin.getAdminId();
        }
        else {
            List<Admin> adminList = adminMapper.findAllAdmin();
            adminRedisUtil.setAdminListByLoginName(ADMIN_MAP_REDIS_KEY, adminList, Duration.ofDays(7));
            List<Admin> adminCollect = adminList.stream()
                    .filter(admin -> admin.getAdminLoginName().equals(loginName))
                    .collect(Collectors.toList());

            log.info("Find Admin in Redis");
            return adminCollect.isEmpty() ? null : adminCollect.get(0).getAdminId();
        }
    }

    public Admin findAdminByLoginName(String loginName) {
        if (adminRedisUtil.hasKey(ADMIN_MAP_REDIS_KEY)) {
            log.info("Find Admin in Redis");
            return adminRedisUtil.getAdminByLoginName(ADMIN_MAP_REDIS_KEY, loginName);
        }
        else {
            List<Admin> adminList = adminMapper.findAllAdmin();
            adminRedisUtil.setAdminListByLoginName(ADMIN_MAP_REDIS_KEY, adminList, Duration.ofDays(7));
            List<Admin> adminCollect = adminList.stream()
                    .filter(admin -> admin.getAdminLoginName().equals(loginName))
                    .collect(Collectors.toList());

            log.info("Find Admin in Database");
            return adminCollect.isEmpty() ? null : adminCollect.get(0);
        }
    }

    public void updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
        adminRedisUtil.deleteKey(ADMIN_MAP_REDIS_KEY);
    }

    public void insertIntoAdmin(Admin admin) {
        adminMapper.insertIntoAdmin(admin.getAdminLoginName(), admin.getAdminPassword(), admin.getAdminName(),
                admin.getAdminPhone(), admin.getAdminWechat(), admin.getAdminQQ(), admin.getAdminBirthday(),
                admin.getAdminGender(), admin.getIsAdminDelete(), admin.getAdminClassId());
        adminRedisUtil.deleteKey(ADMIN_MAP_REDIS_KEY);
    }
}
