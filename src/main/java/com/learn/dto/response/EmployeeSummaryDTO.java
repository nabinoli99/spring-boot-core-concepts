package com.learn.dto.response;

import lombok.Data;

@Data
public class EmployeeSummaryDTO {
    private Long id;
    private String fullName;
    private String email;
    private String departmentName;
}
