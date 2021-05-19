package com.hjr.controller;

import com.hjr.been.City;
import com.hjr.been.District;
import com.hjr.service.CityService;
import com.hjr.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locate")
public class LocationController {

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @PostMapping("/city")
    public List<City> city(@RequestParam("provinceid") Integer provinceId) {
        return cityService.findCityByProvinceId(provinceId);
    }

    @PostMapping("/district")
    public List<District> district(@RequestParam("cityid") Integer cityId) {
        return districtService.findDistrictByCityId(cityId);
    }
}
