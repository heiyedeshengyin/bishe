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

    @Select("select * from checked where checked_student_id = #{studentId}")
    List<Checked> findCheckedByStudentId(Integer studentId);

    @Select("select * from checked where checked_student_id = #{studentId} and date(checked_time) = date(#{checkedDate})")
    List<Checked> findCheckedByStudentIdAndCheckedTime(String checkedDate, Integer studentId);

    @Select("select a.checked_id, a.checked_time, a.checked_is_fever, a.checked_is_contact, a.checked_temperature, a.checked_district_id, a.is_checked_delete, a.checked_student_id " +
            "from checked a, (select checked_student_id, max(checked_time) as checked_time from checked where checked_student_id = #{studentId}) b " +
            "where a.checked_student_id = b.checked_student_id and a.checked_time = b.checked_time")
    Checked findLastCheckedByStudentId(Integer studentId);

    @Insert("insert into checked " +
            "(checked_time, checked_is_fever, checked_is_contact, checked_temperature, checked_district_id, is_checked_delete, checked_student_id) " +
            "values " +
            "(#{checkedTime}, #{checkedIsFever}, #{checkedIsContact}, #{checkedTemperature}, #{checkedDistrictId}, #{isCheckedDelete}, #{checkedStudentId})")
    void insertChecked(Checked checked);
}
