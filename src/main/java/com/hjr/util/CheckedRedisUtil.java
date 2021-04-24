package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.Checked;
import lombok.SneakyThrows;
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
public class CheckedRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setCheckedListById(String key, List<Checked> checkedList, Duration duration) {
        setCheckedListById(key, checkedList);
        redisTemplate.expire(key, duration);
    }

    public void setCheckedListById(String key, List<Checked> checkedList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> checkedMapById = checkedList.stream()
                .collect(Collectors.toMap(checked -> checked.getCheckedId().toString(), new Function<Checked, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(Checked checked) {
                        return objectMapper.writeValueAsString(checked);
                    }
                }));

        hashOperations.putAll(key, checkedMapById);
    }

    public List<Checked> getCheckedList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> checkedJsonList = hashOperations.values(key);

        return checkedJsonList.stream()
                .map(new Function<String, Checked>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public Checked apply(String json) {
                        return objectMapper.readValue(json, Checked.class);
                    }
                }).collect(Collectors.toList());
    }
}
