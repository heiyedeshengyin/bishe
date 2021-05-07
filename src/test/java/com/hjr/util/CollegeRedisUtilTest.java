package com.hjr.util;

import com.hjr.been.College;
import com.hjr.mapper.CollegeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
@DisplayName("CollegeRedisUtil Test")
public class CollegeRedisUtilTest {

    @Autowired
    private CollegeRedisUtil collegeRedisUtil;

    @Autowired
    private CollegeMapper collegeMapper;

    @Test
    @DisplayName("CollegeRedisUtil setCollegeListById Test")
    public void testSetCollegeListById() {
        List<College> collegeList = collegeMapper.findAllCollege();
        collegeRedisUtil.setCollegeListById("college_list_test", collegeList, Duration.ofMinutes(3));
    }

    @Test
    @DisplayName("CollegeRedisUtil getCollegeList Test")
    public void testGetCollegeList() {
        List<College> collegeList = collegeRedisUtil.getCollegeList("college_list_test");
        for (College college : collegeList) {
            System.out.println(college);
        }
    }
}
