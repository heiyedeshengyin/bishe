package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.College;
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
public class CollegeRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setCollegeListById(String key, List<College> collegeList, Duration duration) {
        setCollegeListById(key, collegeList);
        redisTemplate.expire(key, duration);
    }

    public void setCollegeListById(String key, List<College> collegeList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> collegeMapById = collegeList.stream()
                .collect(Collectors.toMap(college -> college.getCollegeId().toString(), new Function<College, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(College college) {
                        return objectMapper.writeValueAsString(college);
                    }
                }));

        hashOperations.putAll(key, collegeMapById);
    }

    public College getCollegeById(String key, Integer collegeId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String collegeJson = hashOperations.get(key, collegeId.toString());
        College college = null;

        if (collegeJson != null && !collegeJson.isEmpty()) {
            try {
                college = objectMapper.readValue(collegeJson, College.class);
            } catch (JsonProcessingException e) {
                log.warn("Can not read college object from Redis! Key: " + key, e);
            }
        }

        return college;
    }

    public List<College> getCollegeList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> collegeJsonList = hashOperations.values(key);

        return collegeJsonList.stream()
                .map(new Function<String, College>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public College apply(String json) {
                        return objectMapper.readValue(json, College.class);
                    }
                })
                .sorted(Comparator.comparingInt(College::getCollegeId))
                .collect(Collectors.toList());
    }
}
