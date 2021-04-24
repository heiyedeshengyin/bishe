package com.hjr.service;

import com.hjr.been.Class;
import com.hjr.mapper.ClassMapper;
import com.hjr.util.ClassRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassService {

    private static final String CLASS_MAP_REDIS_KEY = "class_map_by_class_id";

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private ClassRedisUtil classRedisUtil;

    public List<Class> findAllClass() {
        if (classRedisUtil.hasKey(CLASS_MAP_REDIS_KEY)) {
            log.info("Find Class in Redis");
            return classRedisUtil.getClassList(CLASS_MAP_REDIS_KEY);
        }
        else {
            List<Class> classList = classMapper.findAllClass();
            classRedisUtil.setClassListById(CLASS_MAP_REDIS_KEY, classList, Duration.ofDays(7));
            log.info("Find Class in Database");
            return classList;
        }
    }

    public Class findClassById(Integer classId) {
        if (classRedisUtil.hasKey(CLASS_MAP_REDIS_KEY)) {
            log.info("Find Class in Redis");
            return classMapper.findClassById(classId);
        }
        else {
            List<Class> classList = classMapper.findAllClass();
            classRedisUtil.setClassListById(CLASS_MAP_REDIS_KEY, classList, Duration.ofDays(7));
            List<Class> classCollect = classList.stream()
                    .filter(clazz -> clazz.getClassId().equals(classId))
                    .collect(Collectors.toList());

            log.info("Find Class in Database");
            return classCollect.isEmpty() ? null : classCollect.get(0);
        }
    }

    public List<Class> findClassByCollegeId(Integer collegeId) {
        List<Class> classList = null;

        if (classRedisUtil.hasKey(CLASS_MAP_REDIS_KEY)) {
            classList = classRedisUtil.getClassList(CLASS_MAP_REDIS_KEY);
            log.info("Find Class in Redis");
        }
        else {
            classList = classMapper.findAllClass();
            classRedisUtil.setClassListById(CLASS_MAP_REDIS_KEY, classList, Duration.ofDays(7));
            log.info("Find Class in Database");
        }

        return classList.stream()
                .filter(clazz -> clazz.getClassCollegeId().equals(collegeId))
                .collect(Collectors.toList());
    }
}
