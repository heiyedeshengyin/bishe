package com.hjr.service;

import com.hjr.been.Province;
import com.hjr.mapper.ProvinceMapper;
import com.hjr.util.ProvinceRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProvinceService {

    private static final String PROVINCE_MAP_REDIS_KEY = "province_map_by_id";

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private ProvinceRedisUtil provinceRedisUtil;

    public List<Province> findAllProvince() {
        if (provinceRedisUtil.hasKey(PROVINCE_MAP_REDIS_KEY)) {
            log.info("Find Province List in Redis");
            return provinceRedisUtil.getProvinceList(PROVINCE_MAP_REDIS_KEY);
        }
        else {
            List<Province> provinceList = provinceMapper.findAllProvince();
            provinceRedisUtil.setProvinceListById(PROVINCE_MAP_REDIS_KEY, provinceList);
            log.info("Find Province List in Database");
            return provinceList;
        }
    }

    public Province findProvinceById(Integer provinceId) {
        if (provinceRedisUtil.hasKey(PROVINCE_MAP_REDIS_KEY)) {
            log.info("Find Province in Redis, provinceId: " + provinceId.toString());
            return provinceRedisUtil.getProvinceById(PROVINCE_MAP_REDIS_KEY, provinceId);
        }
        else {
            List<Province> provinceList = provinceMapper.findAllProvince();
            provinceRedisUtil.setProvinceListById(PROVINCE_MAP_REDIS_KEY, provinceList);

            List<Province> provinceCollect = provinceList.stream()
                    .filter(province -> province.getProvinceId().equals(provinceId))
                    .collect(Collectors.toList());

            if (provinceCollect.isEmpty()) {
                log.warn("Can Not Find Province in Database, provinceId: " + provinceId.toString());
                return null;
            }
            else {
                log.info("Find Province in Database, provinceId: " + provinceId.toString());
                return provinceCollect.get(0);
            }
        }
    }
}
