package com.hjr.test;

import com.hjr.been.Student;
import com.hjr.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void testFindAllStudent() {
        List<Student> studentList = studentService.findAllStudent();

        System.out.println(studentList);
    }
}
