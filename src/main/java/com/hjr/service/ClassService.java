package com.hjr.service;

import com.hjr.been.Class;
import com.hjr.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassMapper classMapper;

    public List<Class> findAllClass() {
        return classMapper.findAllClass();
    }
}
