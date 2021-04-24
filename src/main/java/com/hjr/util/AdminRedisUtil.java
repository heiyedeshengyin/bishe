package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.Admin;
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
public class AdminRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setAdminListByLoginName(String key, List<Admin> adminList, Duration duration) {
        setAdminListByLoginName(key, adminList);
        redisTemplate.expire(key, duration);
    }

    public void setAdminListByLoginName(String key, List<Admin> adminList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> adminMapByLoginName = adminList.stream()
                .collect(Collectors.toMap(Admin::getAdminLoginName, new Function<Admin, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(Admin admin) {
                        return objectMapper.writeValueAsString(admin);
                    }
                }));

        hashOperations.putAll(key, adminMapByLoginName);
    }

    public Admin getAdminByLoginName(String key, String adminLoginName) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String adminJson = hashOperations.get(key, adminLoginName);
        Admin admin = null;

        if (adminJson != null && !adminJson.isEmpty()) {
            try {
                admin = objectMapper.readValue(adminJson, Admin.class);
            } catch (JsonProcessingException e) {
                log.warn("Can not read admin object from Redis! Key: " + key, e);
            }
        }

        return admin;
    }

    public List<Admin> getAdminList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> adminJsonList = hashOperations.values(key);

        return adminJsonList.stream()
                .map(new Function<String, Admin>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public Admin apply(String json) {
                        return objectMapper.readValue(json, Admin.class);

                    }
                }).collect(Collectors.toList());
    }
}
