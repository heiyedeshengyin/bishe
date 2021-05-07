package com.hjr.service;

import com.hjr.been.Province;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("ProvinceService Test")
public class ProvinceServiceTest {

    @Autowired
    private ProvinceService provinceService;

    @Test
    @DisplayName("ProvinceService findAllProvince Test")
    public void testFindAllProvince() {
        List<Province> provinceList = provinceService.findAllProvince();
        for (Province province : provinceList) {
            System.out.println(province);
        }
    }

    @Test
    @DisplayName("ProvinceService findProvinceById Test")
    public void testFindProvinceById() {
        Province province = provinceService.findProvinceById(420000);
        System.out.println(province);
    }
}
