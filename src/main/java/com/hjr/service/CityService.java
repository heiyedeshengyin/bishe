package com.hjr.service;

import com.hjr.been.City;
import com.hjr.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public List<City> findCityByProvinceId(Integer provinceId) {
        return cityMapper.findCityByProvinceId(provinceId);
    }

    public City findCityById(Integer cityId) {
        return cityMapper.findCityById(cityId);
    }
}
