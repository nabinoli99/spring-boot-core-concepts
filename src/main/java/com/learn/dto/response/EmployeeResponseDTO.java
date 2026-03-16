package com.learn.dto.response;

import lombok.Data;

@Data
public class EmployeeResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String department;
    private double salary;
    private boolean active;

}
