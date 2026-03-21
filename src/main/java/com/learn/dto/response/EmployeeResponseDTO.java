package com.learn.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String departmentName;
    private double salary;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
