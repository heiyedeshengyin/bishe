package com.hjr.mapper;

import com.hjr.been.District;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DistrictMapper {

    @Select("select * from district where district_city_id = #{cityId}")
    List<District> findDistrictByCityId(Integer cityId);

    @Select("select * from district where district_id = #{districtId}")
    District findDistrictById(Integer districtId);
}
