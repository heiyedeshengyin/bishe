package com.hjr.service;

import com.hjr.been.College;
import com.hjr.mapper.CollegeMapper;
import com.hjr.util.CollegeRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollegeService {

    private static final String COLLEGE_MAP_REDIS_KEY = "college_map_by_college_id";

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private CollegeRedisUtil collegeRedisUtil;

    public List<College> findAllCollege() {
        if (collegeRedisUtil.hasKey(COLLEGE_MAP_REDIS_KEY)) {
            log.info("Find College in Redis");
            return collegeRedisUtil.getCollegeList(COLLEGE_MAP_REDIS_KEY);
        }
        else {
            List<College> collegeList = collegeMapper.findAllCollege();
            collegeRedisUtil.setCollegeListById(COLLEGE_MAP_REDIS_KEY, collegeList, Duration.ofDays(7));
            log.info("Find College in Database");
            return collegeList;
        }
    }

    public College findCollegeById(Integer collegeId) {
        if (collegeRedisUtil.hasKey(COLLEGE_MAP_REDIS_KEY)) {
            log.info("Find College in Redis");
            return collegeMapper.findCollegeById(collegeId);
        }
        else {
            List<College> collegeList = collegeMapper.findAllCollege();
            collegeRedisUtil.setCollegeListById(COLLEGE_MAP_REDIS_KEY, collegeList, Duration.ofDays(7));
            List<College> collegeCollect = collegeList.stream()
                    .filter(college -> college.getCollegeId().equals(collegeId))
                    .collect(Collectors.toList());

            log.info("Find College in Database");
            return collegeCollect.isEmpty() ? null : collegeCollect.get(0);
        }
    }
}
