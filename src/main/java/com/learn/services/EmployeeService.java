package com.learn.services;

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
}
