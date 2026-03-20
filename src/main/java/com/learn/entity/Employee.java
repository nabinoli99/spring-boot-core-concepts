package com.learn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true , nullable = false)
    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private double salary;
    private boolean active;

}
