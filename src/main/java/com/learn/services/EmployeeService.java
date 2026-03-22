package com.learn.services;

import com.learn.common.PageResponse;
import com.learn.dto.request.EmployeeRegistrationRequestDTO;
import com.learn.dto.request.EmployeeUpdateRequestDTO;
import com.learn.dto.response.EmployeeResponseDTO;
import com.learn.dto.response.EmployeeSummaryDTO;
import com.learn.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO registerEmployee(EmployeeRegistrationRequestDTO request);
    EmployeeResponseDTO getEmployeeById(Long id);
    List<EmployeeSummaryDTO>getAllEmployees();
    EmployeeResponseDTO updateEmployee (Long id , EmployeeUpdateRequestDTO request);
    void deleteEmployee(Long id);
    PageResponse<EmployeeSummaryDTO> getAllEmployeesPaginated(int page , int size , String sortBy, String sortDir );
    List<EmployeeSummaryDTO> getByDepartmentName(String departmentName);
    List<EmployeeSummaryDTO> getEmployeesWithSalaryGreaterThan(double salary);
    List<EmployeeSummaryDTO> searchByName(String keyword);

}
