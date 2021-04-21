package com.hjr.test;

import com.hjr.been.Checked;
import com.hjr.service.CheckedService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
