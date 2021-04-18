package com.hjr.mapper;

import com.hjr.been.Checked;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckedMapper {

    @Select("select * from checked")
    List<Checked> findAllChecked();

    @Insert("insert into checked (checked_time, checked_temperature, is_checked_delete, checked_student_id) values (#{checkedTime}, #{checkedTemperature}, #{isCheckedDelete}, #{checkedStudentId})")
    void insertChecked(Checked checked);
}
