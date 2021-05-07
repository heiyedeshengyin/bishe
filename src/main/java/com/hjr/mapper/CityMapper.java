package com.hjr.mapper;

import com.hjr.been.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityMapper {

    @Select("select * from city where city_province_id = #{provinceId}")
    List<City> findCityByProvinceId(Integer provinceId);

    @Select("select * from city where city_id = #{cityId}")
    City findCityById(Integer cityId);
}
