package com.learn.services;

import com.learn.common.PageResponse;
import com.learn.dto.request.EmployeeRegistrationRequestDTO;
import com.learn.dto.request.EmployeeUpdateRequestDTO;
import com.learn.dto.response.EmployeeResponseDTO;
import com.learn.dto.response.EmployeeSummaryDTO;
import com.learn.entity.Department;
import com.learn.entity.Employee;
import com.learn.exception.ConflictException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.repository.DepartmentRepository;
import com.learn.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeResponseDTO registerEmployee(EmployeeRegistrationRequestDTO request) {
        log.info("Registering employee with email: {}",request.getEmail());

        if(employeeRepository.findByEmail(request.getEmail()).isPresent()){
            log.info("Email already registered: {}",request.getEmail());
            throw new ConflictException("Email already Registered: "+request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> {
                    log.error("Department not found with id: {}", request.getDepartmentId());
                    return new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId());
                });

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setDepartment(department);
        employee.setSalary(request.getSalary());
        employee.setActive(true);

        Employee saved = employeeRepository.save(employee);
        log.info("Employee registered successfully with id: {}",saved.getId());
        return mapToResponseDTO(saved);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
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

        if(request.getDepartmentId()!=null){
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(()-> new ResourceNotFoundException("Department not found with id: "+request.getDepartmentId()));
                    employee.setDepartment(department);
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
        log.info("Deleting employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
        employeeRepository.delete(employee);
        log.info("Employee deleted successfully with id: {}", id);
    }

    private EmployeeResponseDTO mapToResponseDTO (Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFirstName() + " "+ employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentName(employee.getDepartment().getName());
        dto.setSalary(employee.getSalary());
        dto.setActive(employee.isActive());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        return dto;
    }

    private EmployeeSummaryDTO mapToSummaryDTO (Employee employee){
        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFirstName() + " "+ employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentName(employee.getDepartment() != null
                ? employee.getDepartment().getName()
                : "No Department");
        return dto;
    }

    @Override
    public PageResponse<EmployeeSummaryDTO> getAllEmployeesPaginated(int page, int size, String sortBy, String sortDir) {
       Sort sort = sortDir.equalsIgnoreCase("asc")
               ? Sort.by(sortBy).ascending()
               : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page ,size,sort);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        List<EmployeeSummaryDTO> content = employeePage.getContent()
                .stream()
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());

        return PageResponse.<EmployeeSummaryDTO>builder()
                .content(content)
                .pageNumber(employeePage.getNumber())
                .pageSize(employeePage.getSize())
                .totalElements(employeePage.getTotalElements())
                .totalPages(employeePage.getTotalPages())
                .isLastPage(employeePage.isLast())
                .build();


    }

    @Override
    public List<EmployeeSummaryDTO> getByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName)
                .stream()
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeSummaryDTO> getEmployeesWithSalaryGreaterThan(double salary) {
        return employeeRepository.findBySalaryGreaterThan(salary)
                .stream()
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeSummaryDTO> searchByName(String keyword) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());
    }
}
