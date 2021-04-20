package com.hjr.mapper;

import com.hjr.been.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    List<Student> findAllStudent();

    @Select("select * from student where student_login_name = #{loginName}")
    Student findStudentByLoginName(String loginName);

    @Update("update student set student_name = #{studentName}, student_phone = #{studentPhone}, student_wechat = #{studentWechat}, student_qq = #{studentQQ}, student_birthday = #{studentBirthday}, student_height = #{studentHeight}, student_weight = #{studentWeight}, student_gender = #{studentGender} where student_id = #{studentId}")
    void updateStudent(Student student);
}
