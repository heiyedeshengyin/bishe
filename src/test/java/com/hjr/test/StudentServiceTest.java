package com.hjr.test;

import com.hjr.been.Student;
import com.hjr.service.StudentService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("StudentService Test")
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("StudentService findAllStudnet Test")
    public void testFindAllStudent() {
        List<Student> studentList = studentService.findAllStudent();

        System.out.println(studentList);
    }

    @Test
    @DisplayName("StudentService findStudentByLoginName Test")
    public void testFindStudentByLoginName() {
        Student student = studentService.findStudentByLoginName("test3");

        System.out.println(student);
    }

    @Test
    @DisplayName("StudentService updateStudent Test")
    @Disabled
    public void testUpdateStudent() {
        Student student = studentService.findStudentByLoginName("test3");
        student.setStudentWechat("ddwefv");

        studentService.updateStudent(student);
    }
}
