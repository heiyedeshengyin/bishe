package com.hjr.mapper;

import com.hjr.been.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from admin")
    List<Admin> findAllAdmin();

    @Select("select * from admin where admin_login_name = #{loginName}")
    Admin findAdminByLoginName(String loginName);

    @Update("update admin set admin_name = #{adminName}, " +
            "admin_phone = #{adminPhone}, " +
            "admin_wechat = #{adminWechat}, " +
            "admin_qq = #{adminQQ}, " +
            "admin_birthday = #{adminBirthday}, " +
            "admin_gender = #{adminGender} " +
            "where admin_id = #{adminId}")
    void updateAdmin(Admin admin);

    @Insert("insert into admin (admin_login_name, admin_password, admin_name, " +
            "admin_phone, admin_wechat, admin_qq, admin_birthday, " +
            "admin_gender, is_admin_delete, admin_class_id) " +
            "values (#{adminLoginName}, #{adminPassword}, #{adminName}, " +
            "#{adminPhone}, #{adminWechat}, #{adminQQ}, #{adminBirthday}, " +
            "#{adminGender}, #{isAdminDelete}, #{adminClassId})")
    void insertIntoAdmin(String adminLoginName, String adminPassword, String adminName,
                         String adminPhone, String adminWechat, String adminQQ, LocalDate adminBirthday,
                         Integer adminGender, Boolean isAdminDelete, Integer adminClassId);
}
