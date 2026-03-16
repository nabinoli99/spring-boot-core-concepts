package com.learn.security;

import com.learn.entity.Employee;
import com.learn.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Student not found: "+email));

        return User.builder()
                .username(employee.getEmail())
                .password(employee.getPassword())
                .roles("STUDENT")
                .build();
    }
}