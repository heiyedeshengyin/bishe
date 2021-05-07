package com.hjr.mapper;

import com.hjr.been.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProvinceMapper {

    @Select("select * from province")
    List<Province> findAllProvince();

    @Select("select * from province where province_id = #{provinceId}")
    Province findProvinceById(Integer provinceId);
}
