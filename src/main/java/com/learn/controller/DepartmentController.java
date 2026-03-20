package com.learn.controller;

import com.learn.common.ApiResponse;
import com.learn.common.ApiResponseUtil;
import com.learn.dto.response.EmployeeResponseDTO;
import com.learn.entity.Department;
import com.learn.exception.ResourceNotFoundException;
import com.learn.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> createDepartment(
            @RequestBody Department department) {
        Department saved = departmentRepository.save(department);
        return ResponseEntity.status(201)
                .body(ApiResponseUtil.success("Department Created Successfully",saved));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllDepartments(){
        return ResponseEntity.ok(
                ApiResponseUtil.success("Departments Fetched Successfully",departmentRepository.findAll()));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<ApiResponse<?>> getEmployeesByDepartment(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        List<EmployeeResponseDTO> employees = department.getEmployees()
                .stream()
                .map(employee -> {
                    EmployeeResponseDTO dto = new EmployeeResponseDTO();
                    dto.setId(employee.getId());
                    dto.setFullName(employee.getFirstName() + " " + employee.getLastName());
                    dto.setEmail(employee.getEmail());
                    dto.setDepartmentName(department.getName());
                    dto.setSalary(employee.getSalary());
                    dto.setActive(employee.isActive());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseUtil.success("Employees fetched successfully", employees));
    }
}
