package com.hjr.test;

import com.hjr.been.Class;
import com.hjr.service.ClassService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("ClassService Test")
public class ClassServiceTest {

    @Autowired
    private ClassService classService;

    @Test
    @DisplayName("ClassService findClassById Test")
    public void testFindClassById() {
        Class class5 = classService.findClassById(5);
        System.out.println(class5);
    }

    @Test
    @DisplayName("ClassService findAllClass Test")
    public void testFindAllClass() {
        List<Class> allClass = classService.findAllClass();
        for (Class aClass : allClass) {
            System.out.println(aClass);
        }
    }
}
