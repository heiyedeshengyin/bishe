package com.hjr.service;

import com.hjr.been.Checked;
import com.hjr.mapper.CheckedMapper;
import com.hjr.util.CheckedRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class CheckedService {

    private static final String CHECKED_MAP_REDIS_KEY_PREFIX = "checked_map_by_student_id_";

    @Autowired
    private CheckedMapper checkedMapper;

    @Autowired
    private CheckedRedisUtil checkedRedisUtil;

    public List<Checked> findAllChecked() {
        return checkedMapper.findAllChecked();
    }

    public List<Checked> findCheckedByStudentId(Integer studentId) {
        if (checkedRedisUtil.hasKey(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString())) {
            log.info("Find Checked in Redis, studentId: " + studentId.toString());
            return checkedRedisUtil.getCheckedList(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString());
        }
        else {
            List<Checked> checkedList = checkedMapper.findCheckedByStudentId(studentId);

            if (checkedList != null && !checkedList.isEmpty()) {
                checkedRedisUtil.setCheckedListById(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString(), checkedList, Duration.ofDays(7));
            }
            log.info("Find Checked in Database, studentId: " + studentId.toString());
            return checkedList;
        }
    }

    public void insertChecked(Checked checked) {
        checkedMapper.insertChecked(checked);
        checkedRedisUtil.deleteKey(CHECKED_MAP_REDIS_KEY_PREFIX + checked.getCheckedStudentId().toString());
    }
}
