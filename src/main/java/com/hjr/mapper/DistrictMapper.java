package com.hjr.mapper;

import com.hjr.been.District;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DistrictMapper {

    @Select("select * from district where district_city_id = #{cityId}")
    List<District> findDistrictByCityId(Integer cityId);

    @Select("select * from district where district_id = #{districtId}")
    District findDistrictById(Integer districtId);

    @Select("select * from district where district_is_risky = true")
    List<District> findRiskyDistrict();

    @Update("update district set district_is_risky = #{isRisky} where district_id = #{districtId}")
    void updateDistrictRisky(Integer districtId, Boolean isRisky);
}
