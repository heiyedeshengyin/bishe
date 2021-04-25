package com.hjr.test.util;

import com.hjr.been.Class;
import com.hjr.mapper.ClassMapper;
import com.hjr.util.ClassRedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
@DisplayName("ClassRedisUtil Test")
public class ClassRedisUtilTest {

    @Autowired
    private ClassRedisUtil classRedisUtil;

    @Autowired
    private ClassMapper classMapper;

    @Test
    @DisplayName("ClassRedisUtil setClassListById Test")
    public void testSetClassListById() {
        List<Class> classList = classMapper.findAllClass();
        classRedisUtil.setClassListById("class_list_test", classList, Duration.ofMinutes(3));
    }

    @Test
    @DisplayName("ClassRedisUtil getClassList Test")
    public void testGetClassList() {
        List<Class> classList = classRedisUtil.getClassList("class_list_test");
        for (Class clazz : classList) {
            System.out.println(clazz);
        }
    }
}
