package com.hjr.service;

import com.hjr.been.College;
import com.hjr.mapper.CollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    public List<College> findAllCollege() {
        return collegeMapper.findAllCollege();
    }
}
