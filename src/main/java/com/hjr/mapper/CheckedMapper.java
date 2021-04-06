package com.hjr.mapper;

import com.hjr.been.Checked;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckedMapper {

    @Select("select * from checked")
    List<Checked> findAllChecked();
}
