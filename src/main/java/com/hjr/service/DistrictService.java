package com.hjr.service;

import com.hjr.been.District;
import com.hjr.mapper.DistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    public List<District> findDistrictByCityId(Integer cityId) {
        return districtMapper.findDistrictByCityId(cityId);
    }

    public District findDistrictById(Integer districtId) {
        return districtMapper.findDistrictById(districtId);
    }
}
