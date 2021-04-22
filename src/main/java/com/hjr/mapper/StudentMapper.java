package com.hjr.mapper;

import com.hjr.been.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    List<Student> findAllStudent();

    @Select("select * from student where student_class_id = #{classId}")
    List<Student> findStudentByClassId(Integer classId);

    @Select("select * from student where student_login_name = #{loginName}")
    Student findStudentByLoginName(String loginName);

    @Update("update student set student_name = #{studentName}, " +
            "student_phone = #{studentPhone}, " +
            "student_wechat = #{studentWechat}, " +
            "student_qq = #{studentQQ}, " +
            "student_birthday = #{studentBirthday}, " +
            "student_height = #{studentHeight}, " +
            "student_weight = #{studentWeight}, " +
            "student_gender = #{studentGender} " +
            "where student_id = #{studentId}")
    void updateStudent(Student student);

    @Insert("insert into student " +
            "(student_login_name, student_password, student_name, student_phone, " +
            "student_wechat, student_qq, student_birthday, student_height, " +
            "student_weight, student_gender, is_student_delete, student_class_id) " +
            "values (#{studentLoginName}, #{studentPassword}, #{studentName}, #{studentPhone}, " +
            "#{studentWechat}, #{studentQQ}, #{studentBirthday}, #{studentHeight}, " +
            "#{studentWeight}, #{studentGender}, #{isStudentDelete}, #{studentClassId})")
    void insertIntoStudent(String studentLoginName, String studentPassword, String studentName, String studentPhone,
                           String studentWechat, String studentQQ, LocalDate studentBirthday, String studentHeight,
                           String studentWeight, Integer studentGender, Boolean isStudentDelete, Integer studentClassId);
}
