# 🚀 Spring Boot Core Concepts

> A hands-on learning journey mastering the core building blocks of production-ready Spring Boot backends.
> Built concept by concept, committed piece by piece.

---

## 👋 What is this repo?

This is a continuation of my Spring Boot learning journey.
After mastering DTOs, Mapping, ApiResponse, Security, and Pagination — this repo goes deeper into the core concepts every real backend developer must know.

Every day = one concept learned.
Every commit = one step forward.

## 🗂️ Project Structure

```
springboot-core-concepts/
└── src/
    └── main/
        └── java/
            └── com/learn/
                ├── common/
                │   ├── ApiResponse.java
                │   └── ApiResponseUtil.java
                ├── dto/
                │   ├── request/
                │   │   ├── EmployeeRegistrationRequestDTO.java
                │   │   └── EmployeeUpdateRequestDTO.java
                │   └── response/
                │       ├── EmployeeResponseDTO.java
                │       └── EmployeeSummaryDTO.java
                ├── entity/
                │   └── Employee.java
                ├── exception/
                │   ├── BadRequestException.java
                │   ├── ConflictException.java
                │   ├── GlobalExceptionHandler.java
                │   └── ResourceNotFoundException.java
                ├── repository/
                │   └── EmployeeRepository.java
                ├── service/
                │   ├── impl/
                │   │   └── EmployeeServiceImpl.java
                │   └── EmployeeService.java
                └── SpringbootCoreConceptsApplication.java
```

---

## 🛠️ Tech Stack

| Tech | Version | Why |
|---|---|---|
| Java | 17 | LTS version, industry standard |
| Spring Boot | 3.2.x | Modern, production-ready framework |
| PostgreSQL | 14+ | Real database, not in-memory |
| Lombok | Latest | Removes boilerplate getters/setters |
| Hibernate Validator | Latest | DTO validation annotations |
| Spring Security | Latest | Authentication and authorization |

---

## 📅 Roadmap

- [x] Day 1 — Custom Exceptions + GlobalExceptionHandler
- [ ] Day 2 — Input Validation (`@Valid`)
- [ ] Day 3 — JPA Relationships (`@OneToMany`, `@ManyToOne`)
- [ ] Day 4 — Custom Queries (JPQL)
- [ ] Day 5 — Auditing (`createdAt`, `updatedAt`)
- [ ] Day 6 — Logging

---

> 🎯 Goal: Become a production-ready backend developer.
> 📍 Current: Board exams + learning sessions.
> 💪 The grind is real. The commits don't lie.
