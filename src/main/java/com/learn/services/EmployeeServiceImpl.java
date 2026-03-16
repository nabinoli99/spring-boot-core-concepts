package com.learn.services;

import com.learn.dto.request.EmployeeRegistrationRequestDTO;
import com.learn.dto.request.EmployeeUpdateRequestDTO;
import com.learn.dto.response.EmployeeResponseDTO;
import com.learn.dto.response.EmployeeSummaryDTO;
import com.learn.entity.Employee;
import com.learn.exception.ConflictException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDTO registerEmployee(EmployeeRegistrationRequestDTO request) {

        if(employeeRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ConflictException("Email already Registered: "+request.getEmail());
        }

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        employee.setActive(true);

        Employee saved = employeeRepository.save(employee);
        return mapToResponseDTO(saved);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));
        return mapToResponseDTO(employee);
    }

    @Override
    public List<EmployeeSummaryDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateRequestDTO request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));

        if(request.getFirstName()!=null) {
            employee.setFirstName(request.getFirstName());
        }

        if(request.getLastName()!=null){
            employee.setLastName(request.getLastName());
        }

        if(request.getEmail()!=null){
            employee.setEmail(request.getEmail());
        }

        if(request.getDepartment()!=null){
            employee.setDepartment(request.getDepartment());
        }

        if(request.getSalary()!=0)
        {
            employee.setSalary(request.getSalary());
        }

        Employee updated = employeeRepository.save(employee);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id: "+id));

        employeeRepository.delete(employee);
    }

    private EmployeeResponseDTO mapToResponseDTO (Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFirstName() + " "+ employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setSalary(employee.getSalary());
        dto.setActive(employee.isActive());
        return dto;
    }

    private EmployeeSummaryDTO mapToSummaryDTO (Employee employee){
        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFirstName() + " "+ employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        return dto;
    }
}
