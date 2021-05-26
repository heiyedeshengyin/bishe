package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.District;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DistrictRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setDistrictListById(String key, List<District> districtList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> districtMapById = districtList.stream()
                .collect(Collectors.toMap(district -> district.getDistrictId().toString(),
                        new Function<District, String>() {
                            @Override
                            @SneakyThrows(JsonProcessingException.class)
                            public String apply(District district) {
                                return objectMapper.writeValueAsString(district);
                            }
                        }));

        hashOperations.putAll(key, districtMapById);
    }

    public void setRiskyDistrictList(String key, List<District> districtList) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();

        List<String> districtJsonList = districtList.stream()
                .map(new Function<District, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(District district) {
                        return objectMapper.writeValueAsString(district);
                    }
                })
                .collect(Collectors.toList());

        listOperations.leftPushAll(key, districtJsonList);
    }

    public District getDistrictById(String key, Integer districtId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String districtJson = hashOperations.get(key, districtId.toString());
        District district = null;

        try {
            district = objectMapper.readValue(districtJson, District.class);
        }
        catch (JsonProcessingException e) {
            log.warn("Can not read district object from Redis! Key: " + key, e);
        }

        return district;
    }

    public List<District> getDistrictList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> districtJsonList = hashOperations.values(key);

        return districtJsonList.stream()
                .map(new Function<String, District>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public District apply(String json) {
                        return objectMapper.readValue(json, District.class);
                    }
                })
                .sorted(Comparator.comparingInt(District::getDistrictId))
                .collect(Collectors.toList());
    }

    public List<District> getRiskyDistrictList(String key) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();

        List<String> districtJsonList = listOperations.range(key, 0, -1);

        if (districtJsonList != null) {
            return districtJsonList.stream()
                    .map(new Function<String, District>() {
                        @Override
                        @SneakyThrows(JsonProcessingException.class)
                        public District apply(String json) {
                            return objectMapper.readValue(json, District.class);
                        }
                    })
                    .sorted(Comparator.comparingInt(District::getDistrictId))
                    .collect(Collectors.toList());
        }
        else {
            return null;
        }
    }
}
