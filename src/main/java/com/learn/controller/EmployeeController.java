package com.learn.controller;

import com.learn.common.ApiResponse;
import com.learn.common.ApiResponseUtil;
import com.learn.common.PageResponse;
import com.learn.dto.request.EmployeeRegistrationRequestDTO;
import com.learn.dto.request.EmployeeUpdateRequestDTO;
import com.learn.dto.response.EmployeeResponseDTO;
import com.learn.dto.response.EmployeeSummaryDTO;
import com.learn.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController
{
    private final EmployeeService employeeService;

    /**
     For Registering the employee
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> registerEmployee(
            @Valid @RequestBody EmployeeRegistrationRequestDTO request){

        EmployeeResponseDTO dto = employeeService.registerEmployee(request);
        return  ResponseEntity.status(201)
                .body(ApiResponseUtil.success("Employee Registered Successfully",dto));
   }

    /**
     For fetching all the employee
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeSummaryDTO>>> getAllEmployees(){
        List<EmployeeSummaryDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(ApiResponseUtil.success("Employees fetched Successfully", employees));
    }

    /**
     For fetching all the employee through pagination
     */
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<PageResponse<EmployeeSummaryDTO>>> getALlEmployeesPaginated(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size,
            @RequestParam (defaultValue = "firstName") String sortBy,
            @RequestParam (defaultValue = "asc") String sortDir) {
        PageResponse<EmployeeSummaryDTO> employees = employeeService.getAllEmployeesPaginated(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponseUtil.success("Employees fetched successfully",employees));
    }

    /**
     For fetching the each employee with specific id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable Long id){
        EmployeeResponseDTO dto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponseUtil.success("Employee Fetched Successfully",dto));
   }

    /**
     For updating the each employee with specific id
     */
   @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>>updateEmployeeById(
            @PathVariable Long id , @RequestBody EmployeeUpdateRequestDTO request){
        EmployeeResponseDTO dto = employeeService.updateEmployee(id,request);
        return ResponseEntity.ok(ApiResponseUtil.success("Employee Details Updated Successfully",dto));
   }

    /**
     For deleting/removing the each employee with specific id
     */
   @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>deleteEmployeeById(
            @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponseUtil.success("Employee deleted Successfully",null));
   }
}
