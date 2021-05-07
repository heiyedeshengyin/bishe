package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.City;
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
public class CityRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setCityListById(String key, List<City> cityList, Duration duration) {
        setCityListById(key, cityList);
        redisTemplate.expire(key, duration);
    }

    public void setCityListById(String key, List<City> cityList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> cityMapById = cityList.stream()
                .collect(Collectors.toMap(city -> city.getCityId().toString(),
                        new Function<City, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(City city) {
                        return objectMapper.writeValueAsString(city);
                    }
                }));

        hashOperations.putAll(key, cityMapById);
    }

    public City getCityById(String key, Integer cityId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String cityJson = hashOperations.get(key, cityId.toString());
        City city = null;

        try {
            city = objectMapper.readValue(cityJson, City.class);
        }
        catch (JsonProcessingException e) {
            log.warn("Can not read city object from Redis! Key: " + key, e);
        }

        return city;
    }

    public List<City> getCityList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> cityJsonList = hashOperations.values(key);

        return cityJsonList.stream()
                .map(new Function<String, City>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public City apply(String json) {
                        return objectMapper.readValue(json, City.class);
                    }
                })
                .sorted(Comparator.comparingInt(City::getCityId))
                .collect(Collectors.toList());
    }
}
