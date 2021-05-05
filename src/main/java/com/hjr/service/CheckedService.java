package com.hjr.service;

import com.hjr.been.Checked;
import com.hjr.mapper.CheckedMapper;
import com.hjr.util.CheckedRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class CheckedService {

    private static final String CHECKED_MAP_REDIS_KEY_PREFIX = "checked_map_by_student_id_";
    private static final String LAST_CHECKED_REDIS_KEY_PREFIX = "last_checked_by_student_id_";

    @Autowired
    private CheckedMapper checkedMapper;

    @Autowired
    private CheckedRedisUtil checkedRedisUtil;

    public List<Checked> findAllChecked() {
        return checkedMapper.findAllChecked();
    }

    public List<Checked> findCheckedByStudentId(Integer studentId) {
        if (checkedRedisUtil.hasKey(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString())) {
            log.info("Find Checked List in Redis, studentId: " + studentId.toString());
            return checkedRedisUtil.getCheckedList(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString());
        }
        else {
            List<Checked> checkedList = checkedMapper.findCheckedByStudentId(studentId);

            if (checkedList != null && !checkedList.isEmpty()) {
                checkedRedisUtil.setCheckedListById(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString(), checkedList, Duration.ofDays(1));
                log.info("Find Checked List in Database, studentId: " + studentId.toString());
            }

            return checkedList;
        }
    }

    public List<Checked> findCheckedByStudentIdAndCheckedTime(String checkedDate, Integer studentId) {
        return checkedMapper.findCheckedByStudentIdAndCheckedTime(checkedDate, studentId);
    }

    public Checked findLastCheckedByStudentId(Integer studentId) {
        if (checkedRedisUtil.hasKey(LAST_CHECKED_REDIS_KEY_PREFIX + studentId.toString())) {
            log.info("Find Last Checked in Redis, studentId: " + studentId.toString());
            return checkedRedisUtil.getChecked(LAST_CHECKED_REDIS_KEY_PREFIX + studentId.toString());
        }
        else {
            Checked checked = checkedMapper.findLastCheckedByStudentId(studentId);
            if (checked != null) {
                checkedRedisUtil.setChecked(LAST_CHECKED_REDIS_KEY_PREFIX + studentId.toString(), checked, Duration.ofDays(1));
                log.info("Find Last Checked in Database, studentId: " + studentId.toString());
            }
            return checked;
        }
    }

    public boolean isCheckedThisDay(LocalDate date, Integer studentId) {
        if (checkedRedisUtil.hasKey(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString())) {
            List<Checked> checkedList = checkedRedisUtil.getCheckedList(CHECKED_MAP_REDIS_KEY_PREFIX + studentId.toString());
            for (Checked checked : checkedList) {
                if (checked.getCheckedTime().toLocalDate().equals(date)) {
                    return true;
                }
            }
            return false;
        }
        else {
            String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
            List<Checked> checkedList = checkedMapper.findCheckedByStudentIdAndCheckedTime(dateString, studentId);

            return checkedList != null && checkedList.size() == 1;
        }
    }

    public void insertChecked(Checked checked) {
        checkedMapper.insertChecked(checked);
        checkedRedisUtil.deleteKey(CHECKED_MAP_REDIS_KEY_PREFIX + checked.getCheckedStudentId().toString());
        checkedRedisUtil.deleteKey(LAST_CHECKED_REDIS_KEY_PREFIX + checked.getCheckedStudentId().toString());
    }
}
