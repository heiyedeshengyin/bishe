package com.hjr.mapper;

import com.hjr.been.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollegeMapper {

    @Select("select * from college")
    List<College> findAllCollege();

    @Select("select * from college where college_id = #{collegeId}")
    College findCollegeById(Integer collegeId);
}
