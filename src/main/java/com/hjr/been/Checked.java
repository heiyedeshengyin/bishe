package com.hjr.been;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Checked {
    private Integer checkedId;

    private LocalDateTime checkedTime;
    private String checkedTemperature;

    private Boolean isCheckedDelete;
    private Integer checkedStudentId;
}
