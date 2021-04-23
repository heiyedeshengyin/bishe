package com.hjr.mapper;

import com.hjr.been.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("select * from class")
    List<Class> findAllClass();

    @Select("select * from class where class_id = #{classId}")
    Class findClassById(Integer classId);

    @Select("select * from class where class_college_id = #{colegeId}")
    List<Class> findClassByCollegeId(Integer collegeId);
}
