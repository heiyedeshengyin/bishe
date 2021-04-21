package com.hjr.service;

import com.hjr.been.Admin;
import com.hjr.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> findAllAdmin() {
        return adminMapper.findAllAdmin();
    }

    public Admin findAdminByLoginName(String loginName) {
        return adminMapper.findAdminByLoginName(loginName);
    }

    public void updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
    }
}
