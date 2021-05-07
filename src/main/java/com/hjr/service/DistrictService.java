package com.hjr.service;

import com.hjr.been.District;
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
}
