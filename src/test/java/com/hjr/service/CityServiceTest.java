package com.hjr.service;

import com.hjr.been.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("CityService Test")
public class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Test
    @DisplayName("CityService findCityByProvinceId Test")
    public void testFindCityByProvinceId() {
        List<City> cityList = cityService.findCityByProvinceId(420000);
        for (City city : cityList) {
            System.out.println(city);
        }
    }
}
