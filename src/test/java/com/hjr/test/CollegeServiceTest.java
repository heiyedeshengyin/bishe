package com.hjr.test;

import com.hjr.been.College;
import com.hjr.service.CollegeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("CollegeService Test")
public class CollegeServiceTest {

    @Autowired
    private CollegeService collegeService;

    @Test
    @DisplayName("CollegeService findAllCollege Test")
    public void testFindAllCollege() {
        List<College> allCollege = collegeService.findAllCollege();
        for (College college : allCollege) {
            System.out.println(college);
        }
    }

    @Test
    @DisplayName("CollegeService findCollegeById Test")
    public void testFindCollegeById() {
        College collegeById = collegeService.findCollegeById(7);
        System.out.println(collegeById);
    }
}
