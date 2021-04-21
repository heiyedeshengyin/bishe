package com.hjr.mapper;

import com.hjr.been.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from admin")
    List<Admin> findAllAdmin();

    @Select("select * from admin where admin_login_name = #{loginName}")
    Admin findAdminByLoginName(String loginName);

    @Update("update admin set admin_name = #{adminName}, admin_phone = #{adminPhone}, admin_wechat = #{adminWechat}, admin_qq = #{adminQQ}, admin_birthday = #{adminBirthday}, admin_gender = #{adminGender} where admin_id = #{adminId}")
    void updateAdmin(Admin admin);
}
