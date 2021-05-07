package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.Province;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProvinceRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setProvinceListById(String key, List<Province> provinceList, Duration duration) {
        setProvinceListById(key, provinceList);
        redisTemplate.expire(key, duration);
    }

    public void setProvinceListById(String key, List<Province> provinceList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> provinceMapById = provinceList.stream()
                .collect(Collectors.toMap(province -> province.getProvinceId().toString(), new Function<Province, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(Province province) {
                        return objectMapper.writeValueAsString(province);
                    }
                }));

        hashOperations.putAll(key, provinceMapById);
    }

    public Province getProvinceById(String key, Integer provinceId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String provinceJson = hashOperations.get(key, provinceId.toString());
        Province province = null;

        if (provinceJson != null && !provinceJson.isEmpty()) {
            try {
                province = objectMapper.readValue(provinceJson, Province.class);
            }
            catch (JsonProcessingException e) {
                log.warn("Can not read province object from Redis! Key: " + key, e);
            }
        }

        return province;
    }

    public List<Province> getProvinceList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> provinceJsonList = hashOperations.values(key);

        return provinceJsonList.stream()
                .map(new Function<String, Province>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public Province apply(String json) {
                        return objectMapper.readValue(json, Province.class);
                    }
                })
                .sorted(Comparator.comparingInt(Province::getProvinceId))
                .collect(Collectors.toList());
    }
}
