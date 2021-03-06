package com.hjr.service;

import com.hjr.been.Student;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    @Test
    @DisplayName("StudentService findIdByLoginName Test")
    public void testFindIdByLoginName() {
        Integer id = studentService.findIdByLoginName("test2");

        System.out.println(id);
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
        student.setStudentWechat("ddwdwev");

        studentService.updateStudent(student);
    }

    @Test
    @DisplayName("StudentService insertIntoStudent Test")
    @Disabled
    public void testInsertIntoStudent() {
        Student student = new Student();
        student.setStudentLoginName("qwertyu");
        student.setStudentPassword("zxcvbnm");
        student.setStudentName("hehe");
        student.setStudentPhone("14533562674");
        student.setStudentWechat("p3n5bf");
        student.setStudentQQ("1263477694");
        student.setStudentBirthday(LocalDate.of(2001, 3, 18));
        student.setStudentHeight("176");
        student.setStudentWeight("55.2");
        student.setStudentGender(2);
        student.setIsStudentDelete(false);
        student.setStudentClassId(5);

        studentService.insertIntoStudent(student);
    }
}
