package com.hjr.mapper;

import com.hjr.been.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from admin")
    List<Admin> findAllAdmin();

    @Select("select * from admin where admin_login_name = #{loginName}")
    Admin findAdminByLoginName(String loginName);
}
