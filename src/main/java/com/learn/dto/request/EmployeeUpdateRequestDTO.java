package com.learn.dto.request;

import lombok.Data;

@Data
public class EmployeeUpdateRequestDTO {
    private String firstName;

    private String lastName;

    private String email;

    private String department;

    private double salary;
}
