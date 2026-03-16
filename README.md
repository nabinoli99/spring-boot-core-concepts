# 🚀 Spring Boot Core Concepts

> A hands-on learning journey mastering the core building blocks of production-ready Spring Boot backends.
> Built concept by concept, committed piece by piece.

---

## 👋 What is this repo?

This is a continuation of my Spring Boot learning journey.
After mastering DTOs, Mapping, ApiResponse, Security, and Pagination — this repo goes deeper into the core concepts every real backend developer must know.

Every day = one concept learned.
Every commit = one step forward.

---

## 📚 Concepts Covered

### ✅ Day 1 — Custom Exceptions

**The problem with `RuntimeException`:**
Throwing `RuntimeException` everywhere is bad practice. It always returns HTTP 500 regardless of what actually went wrong. A missing resource should return 404. A duplicate email should return 409. A bad request should return 400.

**Custom exceptions give you:**

| Exception | HTTP Status | When to use |
|---|---|---|
| `ResourceNotFoundException` | 404 Not Found | Entity doesn't exist in DB |
| `BadRequestException` | 400 Bad Request | Invalid input or logic error |
| `ConflictException` | 409 Conflict | Duplicate data (email already exists) |

**What I built:**

| Class | Type | Purpose |
|---|---|---|
| `ResourceNotFoundException` | Exception | Thrown when entity not found |
| `BadRequestException` | Exception | Thrown when request is invalid |
| `ConflictException` | Exception | Thrown when duplicate data exists |
| `GlobalExceptionHandler` | `@RestControllerAdvice` | Catches all exceptions, returns proper `ApiResponse` |
| `ApiResponse<T>` | Common | Standard response envelope |
| `ApiResponseUtil` | Common | Shortcut builder for success/error responses |

**Key decisions made:**
- Each exception extends `RuntimeException` — no need to declare in method signatures
- `GlobalExceptionHandler` catches each type separately — different status codes for different errors
- Fallback `Exception.class` handler catches anything unexpected — always returns 500
- `error()` in `ApiResponseUtil` never takes data — errors have no payload

---

## 🗂️ Project Structure

```
src/
└── main/
    └── java/
        └── com/learn/
            ├── common/
            │   ├── ApiResponse.java
            │   └── ApiResponseUtil.java
            ├── entity/
            │   └── Employee.java
            ├── dto/
            │   ├── request/
            │   │   ├── EmployeeRegistrationRequestDTO.java
            │   │   └── EmployeeUpdateRequestDTO.java
            │   └── response/
            │       ├── EmployeeResponseDTO.java
            │       └── EmployeeSummaryDTO.java
            └── exception/
                ├── ResourceNotFoundException.java
                ├── BadRequestException.java
                ├── ConflictException.java
                └── GlobalExceptionHandler.java
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

## ⚙️ Setup & Run Locally

**1. Clone the repo**
```
git clone https://github.com/nabinoli99/springboot-core-concepts.git
cd springboot-core-concepts
```

**2. Create PostgreSQL database**
```sql
CREATE DATABASE springboot_core_db;
```

**3. Update application.properties**
```
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot_core_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**4. Run**
```
./mvnw spring-boot:run
```

---

## 📅 Roadmap

- [x] Day 1 — Custom Exceptions + GlobalExceptionHandler
- [ ] Day 2 — Input Validation (`@Valid`)
- [ ] Day 3 — JPA Relationships (`@OneToMany`, `@ManyToOne`)
- [ ] Day 4 — Custom Queries (JPQL)
- [ ] Day 5 — Auditing (`createdAt`, `updatedAt`)
- [ ] Day 6 — Logging

---

## 💡 What I Learned

```
❌ Before:  throw new RuntimeException("Not found") → always 500
✅ After:   throw new ResourceNotFoundException("Not found") → proper 404

❌ Before:  Inconsistent error responses
✅ After:   Every error wrapped in ApiResponse with correct HTTP status

❌ Before:  One generic exception for everything
✅ After:   Specific exception per scenario — readable, maintainable
```

---

> 🎯 Goal: Become a production-ready backend developer.
> 📍 Current: Board exams + learning sessions.
> 💪 The grind is real. The commits don't lie.
