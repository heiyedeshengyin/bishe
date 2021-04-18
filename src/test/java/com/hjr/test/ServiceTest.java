package com.hjr.test;

import com.hjr.been.Checked;
import com.hjr.been.Student;
import com.hjr.service.CheckedService;
import com.hjr.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CheckedService checkedService;

    @Test
    public void testFindAllStudent() {
        List<Student> studentList = studentService.findAllStudent();

        System.out.println(studentList);
    }

    @Test
    public void testFindStudentByLoginName() {
        Student student = studentService.findStudentByLoginName("test4");

        System.out.println(student);
    }

    @Test
    public void testInsertChecked() {
        Checked checked = new Checked();
        checked.setCheckedId(0);
        checked.setCheckedTime(LocalDateTime.now());
        checked.setCheckedTemperature("23.4");
        checked.setIsCheckedDelete(false);
        checked.setCheckedStudentId(2);

        checkedService.insertChecked(checked);
    }
}
