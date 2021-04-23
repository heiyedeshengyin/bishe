package com.hjr.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public Boolean setExpire(String key, Duration duration) {
        return redisTemplate.expire(key, duration);
    }
}
