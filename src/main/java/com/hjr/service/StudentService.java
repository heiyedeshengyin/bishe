package com.hjr.service;

import com.hjr.been.Student;
import com.hjr.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findAllStudent() {
        return studentMapper.findAllStudent();
    }

    public Student findStudentByLoginName(String loginName) {
        return studentMapper.findStudentByLoginName(loginName);
    }

    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }
}
