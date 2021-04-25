package com.hjr.service;

import com.hjr.been.Student;
import com.hjr.mapper.StudentMapper;
import com.hjr.util.StudentRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    private static final String STUDENT_MAP_REDIS_KEY = "student_map_by_login_name";

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentRedisUtil studentRedisUtil;

    public List<Student> findAllStudent() {
        if (studentRedisUtil.hasKey(STUDENT_MAP_REDIS_KEY)) {
            log.info("Find Student in Redis");
            return studentRedisUtil.getStudentList(STUDENT_MAP_REDIS_KEY);
        }
        else {
            List<Student> studentList = studentMapper.findAllStudent();
            studentRedisUtil.setStudentListByLoginName(STUDENT_MAP_REDIS_KEY, studentList, Duration.ofDays(7));
            log.info("Find Student in Database");
            return studentList;
        }
    }

    public List<Student> findStudentByClassId(Integer classId) {
        List<Student> studentList = null;

        if (studentRedisUtil.hasKey(STUDENT_MAP_REDIS_KEY)) {
            studentList = studentRedisUtil.getStudentList(STUDENT_MAP_REDIS_KEY);
            log.info("Find Student in Redis");
        }
        else {
            studentList = studentMapper.findAllStudent();
            studentRedisUtil.setStudentListByLoginName(STUDENT_MAP_REDIS_KEY, studentList, Duration.ofDays(7));
            log.info("Find Student in Database");
        }

        return studentList.stream()
                .filter(student -> student.getStudentClassId().equals(classId))
                .collect(Collectors.toList());
    }

    public Integer findIdByLoginName(String loginName) {
        if (studentRedisUtil.hasKey(STUDENT_MAP_REDIS_KEY)) {
            Student student = studentRedisUtil.getStudentByLoginName(STUDENT_MAP_REDIS_KEY, loginName);
            log.info("Find Student in Redis");
            return student.getStudentId();
        }
        else {
            List<Student> studentList = studentMapper.findAllStudent();
            studentRedisUtil.setStudentListByLoginName(STUDENT_MAP_REDIS_KEY, studentList, Duration.ofDays(7));
            List<Student> studentCollect = studentList.stream()
                    .filter(student -> student.getStudentLoginName().equals(loginName))
                    .collect(Collectors.toList());

            log.info("Find Student in Database");
            return studentCollect.isEmpty() ? null : studentCollect.get(0).getStudentId();
        }
    }

    public Student findStudentByLoginName(String loginName) {
        if (studentRedisUtil.hasKey(STUDENT_MAP_REDIS_KEY)) {
            log.info("Find Student in Redis");
            return studentRedisUtil.getStudentByLoginName(STUDENT_MAP_REDIS_KEY, loginName);
        }
        else {
            List<Student> studentList = studentMapper.findAllStudent();
            studentRedisUtil.setStudentListByLoginName(STUDENT_MAP_REDIS_KEY, studentList, Duration.ofDays(7));
            List<Student> studentCollect = studentList.stream()
                    .filter(student -> student.getStudentLoginName().equals(loginName))
                    .collect(Collectors.toList());

            log.info("Find Student in Database");
            return studentCollect.isEmpty() ? null : studentCollect.get(0);
        }
    }

    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
        studentRedisUtil.deleteKey(STUDENT_MAP_REDIS_KEY);
    }

    public void insertIntoStudent(Student student) {
        studentMapper.insertIntoStudent(student.getStudentLoginName(), student.getStudentPassword(), student.getStudentName(),
                student.getStudentPhone(), student.getStudentWechat(), student.getStudentQQ(), student.getStudentBirthday(),
                student.getStudentHeight(), student.getStudentWeight(), student.getStudentGender(), student.getIsStudentDelete(),
                student.getStudentClassId());
        studentRedisUtil.deleteKey(STUDENT_MAP_REDIS_KEY);
    }
}
