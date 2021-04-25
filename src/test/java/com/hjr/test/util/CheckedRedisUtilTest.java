package com.hjr.test.util;

import com.hjr.been.Checked;
import com.hjr.mapper.CheckedMapper;
import com.hjr.util.CheckedRedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
@DisplayName("CheckedRedisUtil Test")
public class CheckedRedisUtilTest {

    @Autowired
    private CheckedRedisUtil checkedRedisUtil;

    @Autowired
    private CheckedMapper checkedMapper;

    @Test
    @DisplayName("CheckedRedisUtil setCheckedListById Test")
    public void testSetCheckedListById() {
        List<Checked> checkedList = checkedMapper.findAllChecked();
        checkedRedisUtil.setCheckedListById("checked_list_test", checkedList, Duration.ofMinutes(3));
    }

    @Test
    @DisplayName("CheckedRedisUtil getCheckedList Test")
    public void testGetCheckedList() {
        List<Checked> checkedList = checkedRedisUtil.getCheckedList("checked_list_test");
        for (Checked checked : checkedList) {
            System.out.println(checked);
        }
    }
}
