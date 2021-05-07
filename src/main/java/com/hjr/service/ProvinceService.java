package com.hjr.service;

import com.hjr.been.Province;
import com.hjr.mapper.ProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    public List<Province> findAllProvince() {
        return provinceMapper.findAllProvince();
    }

    public Province findProvinceById(Integer provinceId) {
        return provinceMapper.findProvinceById(provinceId);
    }
}
