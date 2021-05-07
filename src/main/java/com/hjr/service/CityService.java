package com.hjr.service;

import com.hjr.been.City;
import com.hjr.mapper.CityMapper;
import com.hjr.util.CityRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityService {

    private static final String CITY_MAP_REDIS_KEY_PREFIX = "city_map_by_province_id_";

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityRedisUtil cityRedisUtil;

    public List<City> findCityByProvinceId(Integer provinceId) {
        if (cityRedisUtil.hasKey(CITY_MAP_REDIS_KEY_PREFIX + provinceId.toString())) {
            log.info("Find City List in Redis");
            return cityRedisUtil.getCityList(CITY_MAP_REDIS_KEY_PREFIX + provinceId.toString());
        }
        else {
            List<City> cityList = cityMapper.findCityByProvinceId(provinceId);
            cityRedisUtil.setCityListById(CITY_MAP_REDIS_KEY_PREFIX + provinceId.toString(), cityList);
            log.info("Find City List in Database");
            return cityList;
        }
    }

    public City findCityById(Integer cityId) {
        int provinceId = (cityId / 10000) * 10000;

        if (cityRedisUtil.hasKey(CITY_MAP_REDIS_KEY_PREFIX + Integer.toString(provinceId))) {
            log.info("Find City in Redis, cityId: " + cityId.toString());
            return cityRedisUtil.getCityById(CITY_MAP_REDIS_KEY_PREFIX + Integer.toString(provinceId), cityId);
        }
        else {
            List<City> cityList = cityMapper.findCityByProvinceId(provinceId);
            cityRedisUtil.setCityListById(CITY_MAP_REDIS_KEY_PREFIX + Integer.toString(provinceId), cityList);

            List<City> cityCollect = cityList.stream()
                    .filter(city -> city.getCityId().equals(cityId))
                    .collect(Collectors.toList());

            if (cityCollect.isEmpty()) {
                log.warn("Can Not Find City in Database, cityId: " + cityId.toString());
                return null;
            }
            else {
                log.info("Find City in Database, cityId: " + cityId.toString());
                return cityCollect.get(0);
            }
        }
    }
}
