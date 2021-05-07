package com.hjr.service;

import com.hjr.been.District;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("DistrictService Test")
public class DistrictServiceTest {

    @Autowired
    private DistrictService districtService;

    @Test
    @DisplayName("DistrictService findDistrictByCityId Test")
    public void testFindDistrictByCityId() {
        List<District> districtList = districtService.findDistrictByCityId(420100);
        for (District district : districtList) {
            System.out.println(district);
        }
    }

    @Test
    @DisplayName("DistrictService findDistrictById Test")
    public void testFindDistrictById() {
        District district = districtService.findDistrictById(420112);
        System.out.println(district);
    }
}
