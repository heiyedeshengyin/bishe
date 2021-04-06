package com.hjr.service;

import com.hjr.been.Checked;
import com.hjr.mapper.CheckedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckedService {

    @Autowired
    private CheckedMapper checkedMapper;

    public List<Checked> findAllChecked() {
        return checkedMapper.findAllChecked();
    }
}
