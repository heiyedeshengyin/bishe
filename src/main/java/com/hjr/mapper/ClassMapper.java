package com.hjr.mapper;

import com.hjr.been.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("select * from class")
    List<Class> findAllClass();
}
