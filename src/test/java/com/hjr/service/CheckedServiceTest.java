package com.hjr.service;

import com.hjr.been.Checked;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@DisplayName("CheckedService Test")
public class CheckedServiceTest {

    @Autowired
    private CheckedService checkedService;

    @Test
    @DisplayName("CheckedService findCheckedByStudentId Test")
    public void testFindCheckedByStudentId() {
        List<Checked> checkedList = checkedService.findCheckedByStudentId(2);
        for (Checked checked : checkedList) {
            System.out.println(checked);
        }
    }

    @Test
    @DisplayName("CheckedService findCheckedByStudentIdAndCheckedTime Test")
    public void testFindCheckedByStudentIdAndCheckedTime() {
        List<Checked> checkedList = checkedService.findCheckedByStudentIdAndCheckedTime("2021-04-20", 2);
        for (Checked checked : checkedList) {
            System.out.println(checked);
        }
    }

    @Test
    @DisplayName("CheckedService findLastCheckedByStudentId Test")
    public void testFindLastCheckedByStudentId() {
        Checked checked = checkedService.findLastCheckedByStudentId(2);
        System.out.println(checked);
    }

    @Test
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime of = LocalDateTime.of(2021, 5, 3, 13, 24, 59);
        Duration between = Duration.between(of, now);
        System.out.println(between.toDays());
    }

    @Test
    @DisplayName("CheckedService isCheckedThisDay Test")
    public void testIsCheckedThisDay() {
        boolean checkedThisDay = checkedService.isCheckedThisDay(LocalDate.of(2021, 4, 20), 1);
        System.out.println(checkedThisDay);
    }

    @Test
    @DisplayName("CheckedService insertChecked Test")
    @Disabled
    public void testInsertChecked() {
        Checked checked = new Checked();
        checked.setCheckedId(0);
        checked.setCheckedTime(LocalDateTime.now());
        checked.setCheckedTemperature("36.8");
        checked.setIsCheckedDelete(false);
        checked.setCheckedStudentId(2);

        checkedService.insertChecked(checked);
    }
}
