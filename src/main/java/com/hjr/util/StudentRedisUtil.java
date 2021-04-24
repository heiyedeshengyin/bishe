package com.hjr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjr.been.Student;
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
public class StudentRedisUtil extends RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void setStudentListByLoginName(String key, List<Student> studentList, Duration duration) {
        setStudentListByLoginName(key, studentList);
        redisTemplate.expire(key, duration);
    }

    public void setStudentListByLoginName(String key, List<Student> studentList) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> studentMapByLoginName = studentList.stream()
                .collect(Collectors.toMap(Student::getStudentLoginName, new Function<Student, String>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public String apply(Student student) {
                        return objectMapper.writeValueAsString(student);
                    }
                }));

        hashOperations.putAll(key, studentMapByLoginName);
    }

    public Student getStudentByLoginName(String key, String studentLoginName) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String studentJson = hashOperations.get(key, studentLoginName);
        Student student = null;
        try {
            student = objectMapper.readValue(studentJson, Student.class);
        } catch (JsonProcessingException e) {
            log.warn("Can not read student object from Redis! key: " + key, e);
        }

        return student;
    }

    public List<Student> getStudentList(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        List<String> studentJsonList = hashOperations.values(key);

        return studentJsonList.stream()
                .map(new Function<String, Student>() {
                    @Override
                    @SneakyThrows(JsonProcessingException.class)
                    public Student apply(String json) {
                        return objectMapper.readValue(json, Student.class);
                    }
                })
                .collect(Collectors.toList());
    }
}
