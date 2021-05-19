package com.hjr.been;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Checked {
    private Integer checkedId;

    private LocalDateTime checkedTime;
    private Boolean checkedIsFever;
    private Boolean checkedIsContact;
    private String checkedTemperature;
    private Integer checkedDistrictId;

    private Boolean isCheckedDelete;
    private Integer checkedStudentId;
}
