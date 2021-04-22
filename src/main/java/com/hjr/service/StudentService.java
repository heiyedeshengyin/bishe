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

    public List<Student> findStudentByClassId(Integer classId) {
        return studentMapper.findStudentByClassId(classId);
    }

    public Student findStudentByLoginName(String loginName) {
        return studentMapper.findStudentByLoginName(loginName);
    }

    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    public void insertIntoStudent(Student student) {
        studentMapper.insertIntoStudent(student.getStudentLoginName(), student.getStudentPassword(), student.getStudentName(),
                student.getStudentPhone(), student.getStudentWechat(), student.getStudentQQ(), student.getStudentBirthday(),
                student.getStudentHeight(), student.getStudentWeight(), student.getStudentGender(), student.getIsStudentDelete(),
                student.getStudentClassId());
    }
}
