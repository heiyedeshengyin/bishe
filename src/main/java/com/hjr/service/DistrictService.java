package com.hjr.service;

import com.hjr.been.City;
import com.hjr.been.District;
import com.hjr.been.Province;
import com.hjr.mapper.DistrictMapper;
import com.hjr.util.DistrictRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DistrictService {

    private static final String DISTRICT_MAP_REDIS_KEY_PREFIX = "district_map_by_city_id_";

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private DistrictRedisUtil districtRedisUtil;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    public List<District> findDistrictByCityId(Integer cityId) {
        if (districtRedisUtil.hasKey(DISTRICT_MAP_REDIS_KEY_PREFIX + cityId.toString())) {
            log.info("Find District List in Redis");
            return districtRedisUtil.getDistrictList(DISTRICT_MAP_REDIS_KEY_PREFIX + cityId.toString());
        }
        else {
            List<District> districtList = districtMapper.findDistrictByCityId(cityId);
            districtRedisUtil.setDistrictListById(DISTRICT_MAP_REDIS_KEY_PREFIX + cityId.toString(), districtList);
            log.info("Find District List in Database");
            return districtList;
        }
    }

    public District findDistrictById(Integer districtId) {
        int cityId = (districtId / 100) * 100;

        if (districtRedisUtil.hasKey(DISTRICT_MAP_REDIS_KEY_PREFIX + Integer.toString(cityId))) {
            log.info("Find District in Redis, districtId: " + districtId.toString());
            return districtRedisUtil.getDistrictById(DISTRICT_MAP_REDIS_KEY_PREFIX + Integer.toString(cityId), districtId);
        }
        else {
            List<District> districtList = districtMapper.findDistrictByCityId(cityId);
            districtRedisUtil.setDistrictListById(DISTRICT_MAP_REDIS_KEY_PREFIX + Integer.toString(cityId), districtList);

            List<District> districtCollect = districtList.stream()
                    .filter(district -> district.getDistrictId().equals(districtId))
                    .collect(Collectors.toList());

            if (districtCollect.isEmpty()) {
                log.warn("Can Not Find District in Database, districtId: " + districtId.toString());
                return null;
            }
            else {
                log.info("Find District in Database, districtId: " + districtId.toString());
                return districtCollect.get(0);
            }
        }
    }

    public List<District> findRiskyDistrict() {
        if (districtRedisUtil.hasKey("district_risky_list")) {
            log.info("Find Risky District List in Redis");
            return districtRedisUtil.getRiskyDistrictList("district_risky_list");
        }
        else {
            List<District> riskyDistrictList = districtMapper.findRiskyDistrict();
            if (riskyDistrictList != null && !riskyDistrictList.isEmpty()) {
                districtRedisUtil.setRiskyDistrictList("district_risky_list", riskyDistrictList);
            }

            log.info("Find Risky District List in Database");
            return riskyDistrictList;
        }
    }

    public void updateDistrictRisky(Integer districtId, Boolean isRisky) {
        int cityId = (districtId / 100) * 100;
        districtMapper.updateDistrictRisky(districtId, isRisky);
        districtRedisUtil.deleteKey(DISTRICT_MAP_REDIS_KEY_PREFIX + Integer.toString(cityId));
        districtRedisUtil.deleteKey("district_risky_list");
    }

    public String districtToString(Integer districtId) {
        int cityId = (districtId / 100) * 100;
        int provinceId = (districtId / 10000) * 10000;

        District district = findDistrictById(districtId);
        City city = cityService.findCityById(cityId);
        Province province = provinceService.findProvinceById(provinceId);

        return province.getProvinceName() + " " + city.getCityName() + " " + district.getDistrictName();
    }
}
