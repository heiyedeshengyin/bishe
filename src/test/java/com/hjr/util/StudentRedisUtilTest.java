package com.hjr.util;

import com.hjr.been.Student;
import com.hjr.mapper.StudentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
@DisplayName("StudentRedisUtil Test")
public class StudentRedisUtilTest {

    @Autowired
    private StudentRedisUtil studentRedisUtil;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    @DisplayName("StudentRedisUtil setStudentListByLoginName Test")
    public void testSetStudentListByLoginName() {
        List<Student> allStudent = studentMapper.findAllStudent();
        studentRedisUtil.setStudentListByLoginName("student_list_test", allStudent, Duration.ofMinutes(1));
    }

    @Test
    @DisplayName("StudentRedisUtil getStudentBuLoginName Test")
    public void testGetStudentByLoginName() {
        Student student = studentRedisUtil.getStudentByLoginName("student_list_test", "hjr2333");
        System.out.println(student);
    }

    @Test
    @DisplayName("StudentRedisUtil getStudentList Test")
    public void testGetStudentList() {
        List<Student> studentList = studentRedisUtil.getStudentList("student_list_test");
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    @Test
    @DisplayName("RedisUtil hasKey Test")
    public void testHasKey() {
        System.out.println(studentRedisUtil.hasKey("student_list_test"));
    }
}
