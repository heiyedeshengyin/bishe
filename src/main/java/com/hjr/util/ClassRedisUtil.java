package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.Class;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClassRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setClassListById(String key, List<Class> classList, Duration duration) {
        setClassListById(key, classList);
        redisTemplate.expire(key, duration);
    }

    public void setClassListById(String key, List<Class> classList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> classMapById = classList.stream()
                .collect(Collectors.toMap(clazz -> clazz.getClassId().toString(), new Function<Class, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(Class clazz) {
                        return objectMapper.writeValueAsString(clazz);
                    }
                }));

        hashOperations.putAll(key, classMapById);
    }

    public Class getClassById(String key, Integer classId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String classJson = hashOperations.get(key, classId.toString());
        Class clazz = null;
        try {
            clazz = objectMapper.readValue(classJson, Class.class);
        } catch (JsonProcessingException e) {
            log.warn("Can not read class object from Redis! Key: " + key, e);
        }

        return clazz;
    }

    public List<Class> getClassList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> classJsonList = hashOperations.values(key);

        return classJsonList.stream()
                .map(new Function<String, Class>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public Class apply(String json) {
                        return objectMapper.readValue(json, Class.class);
                    }
                }).collect(Collectors.toList());
    }
}
