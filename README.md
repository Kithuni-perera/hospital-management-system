# 🏥 Hospital Management System - Admin Panel

A full-featured Spring Boot REST API for managing hospital operations including doctors, patients, appointments, billing, and more.

---

## 🛠️ Tech Stack

| Technology         | Purpose                        |
|--------------------|--------------------------------|
| Java 17            | Programming Language           |
| Spring Boot 3.2.0  | Backend Framework              |
| Maven              | Build Tool                     |
| MS SQL Server      | Database                       |
| Spring Security    | Authentication & Authorization |
| JWT (jjwt 0.11.5)  | Token-based Auth               |
| Swagger/OpenAPI 3  | API Documentation              |
| Lombok             | Boilerplate Reduction          |
| JPA / Hibernate    | ORM                            |

---

## 📦 Project Structure

```
src/main/java/com/hospital/admin/
├── config/             # Security, Swagger, DataInitializer
├── controller/         # REST API Controllers
├── dto/
│   ├── request/        # Request DTOs (input)
│   └── response/       # Response DTOs (output)
├── entity/             # JPA Entities
├── enums/              # Role, Status Enums
├── exception/          # Custom Exceptions + Global Handler
├── repository/         # Spring Data JPA Repositories
├── security/           # JWT Utils + Auth Filter
└── service/
    ├── (interfaces)    # Service interfaces
    └── impl/           # Service implementations
```

---

## ⚙️ Setup Instructions

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- Microsoft SQL Server (local or remote)

### 2. Create Database
```sql
CREATE DATABASE HospitalManagementDB;
```

### 3. Configure `application.properties`
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=HospitalManagementDB;encrypt=true;trustServerCertificate=true
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 4. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔐 Default Admin Credentials

On first startup, a default admin user is created automatically:

| Field    | Value              |
|----------|--------------------|
| Email    | admin@hospital.com |
| Password | Admin@1234         |

> ⚠️ Change this password immediately after first login.

---

## 📄 API Documentation (Swagger)

After starting the application, visit:

```
http://localhost:8080/api/swagger-ui.html
```

---

## 🔑 Authentication Flow

```
1. POST /api/auth/login  →  { email, password }
2. Response: { accessToken: "Bearer eyJ..." }
3. Add header to all requests: Authorization: Bearer <token>
```

---

## 📡 API Endpoints Summary

### 🔐 Auth
| Method | Endpoint         | Description       |
|--------|------------------|-------------------|
| POST   | /auth/login      | Login & get JWT   |

### 👤 User Management (ADMIN only)
| Method | Endpoint                        | Description          |
|--------|---------------------------------|----------------------|
| POST   | /admin/users                    | Create user          |
| GET    | /admin/users                    | Get all users        |
| GET    | /admin/users/{id}               | Get user by ID       |
| GET    | /admin/users/role/{role}        | Get users by role    |
| PUT    | /admin/users/{id}               | Update user          |
| PATCH  | /admin/users/{id}/toggle-status | Toggle active status |
| DELETE | /admin/users/{id}               | Delete user          |

### 🏢 Department Management
| Method | Endpoint                              | Description             |
|--------|---------------------------------------|-------------------------|
| POST   | /admin/departments                    | Create department       |
| GET    | /admin/departments                    | Get all departments     |
| GET    | /admin/departments/{id}               | Get department by ID    |
| PUT    | /admin/departments/{id}               | Update department       |
| PATCH  | /admin/departments/{id}/toggle-status | Toggle status           |
| DELETE | /admin/departments/{id}               | Delete department       |

### 👨‍⚕️ Doctor Management
| Method | Endpoint                                         | Description               |
|--------|--------------------------------------------------|---------------------------|
| POST   | /admin/doctors                                   | Create doctor profile     |
| GET    | /admin/doctors                                   | Get all doctors           |
| GET    | /admin/doctors/{id}                              | Get doctor by ID          |
| GET    | /admin/doctors/department/{departmentId}         | Get by department         |
| GET    | /admin/doctors/specialization/{specialization}   | Get by specialization     |
| PUT    | /admin/doctors/{id}                              | Update doctor             |
| PATCH  | /admin/doctors/{id}/toggle-status                | Toggle active status      |
| DELETE | /admin/doctors/{id}                              | Delete doctor             |

### 🧑‍🤝‍🧑 Patient Management
| Method | Endpoint                          | Description             |
|--------|-----------------------------------|-------------------------|
| POST   | /patients                         | Register patient        |
| GET    | /patients                         | Get all patients        |
| GET    | /patients/{id}                    | Get patient by ID       |
| GET    | /patients/code/{code}             | Get by patient code     |
| GET    | /patients/search?name=            | Search by name          |
| PUT    | /patients/{id}                    | Update patient          |
| PATCH  | /patients/{id}/toggle-status      | Toggle status           |
| DELETE | /patients/{id}                    | Delete patient          |

### 📅 Appointment Management
| Method | Endpoint                            | Description              |
|--------|-------------------------------------|--------------------------|
| POST   | /appointments                       | Create appointment       |
| GET    | /appointments                       | Get all appointments     |
| GET    | /appointments/{id}                  | Get by ID                |
| GET    | /appointments/patient/{patientId}   | Get by patient           |
| GET    | /appointments/doctor/{doctorId}     | Get by doctor            |
| GET    | /appointments/date/{date}           | Get by date              |
| GET    | /appointments/status/{status}       | Get by status            |
| PUT    | /appointments/{id}                  | Update appointment       |
| PATCH  | /appointments/{id}/status?status=   | Update status            |
| DELETE | /appointments/{id}                  | Delete appointment       |

### 🛏️ Bed & Room Management
| Method | Endpoint                                        | Description           |
|--------|-------------------------------------------------|-----------------------|
| POST   | /admin/bed-management/rooms                     | Create room           |
| GET    | /admin/bed-management/rooms                     | Get all rooms         |
| GET    | /admin/bed-management/rooms/{id}                | Get room by ID        |
| GET    | /admin/bed-management/rooms/department/{id}     | Get rooms by dept     |
| PUT    | /admin/bed-management/rooms/{id}                | Update room           |
| DELETE | /admin/bed-management/rooms/{id}                | Delete room           |
| POST   | /admin/bed-management/beds                      | Create bed            |
| GET    | /admin/bed-management/beds                      | Get all beds          |
| GET    | /admin/bed-management/beds/available            | Get available beds    |
| GET    | /admin/bed-management/beds/status/{status}      | Get beds by status    |
| PATCH  | /admin/bed-management/beds/{bedId}/assign/{pid} | Assign bed to patient |
| PATCH  | /admin/bed-management/beds/{bedId}/release      | Release bed           |
| DELETE | /admin/bed-management/beds/{id}                 | Delete bed            |

### 💰 Billing & Invoicing
| Method | Endpoint                              | Description           |
|--------|---------------------------------------|-----------------------|
| POST   | /billing                              | Create invoice        |
| GET    | /billing                              | Get all billings      |
| GET    | /billing/{id}                         | Get by ID             |
| GET    | /billing/invoice/{invoiceNumber}      | Get by invoice no.    |
| GET    | /billing/patient/{patientId}          | Get by patient        |
| GET    | /billing/status/{status}              | Get by status         |
| GET    | /billing/date-range?start=&end=       | Get by date range     |
| PUT    | /billing/{id}                         | Update billing        |
| PATCH  | /billing/{id}/status?status=          | Update status         |
| DELETE | /billing/{id}                         | Delete billing        |

### 📊 Reports & Analytics (ADMIN only)
| Method | Endpoint                                | Description                |
|--------|-----------------------------------------|----------------------------|
| GET    | /reports/dashboard                      | Full dashboard stats       |
| GET    | /reports/revenue?startDate=&endDate=    | Revenue by date range      |

---

## 👥 Role Permissions

| Role         | Access Level                                                   |
|--------------|----------------------------------------------------------------|
| ADMIN        | Full access to all endpoints                                   |
| DOCTOR       | View patients, appointments; update appointment status         |
| NURSE        | View patients, appointments; manage beds                       |
| RECEPTIONIST | Register patients, create/manage appointments, create invoices |

---

## 📝 Auto-Generated Codes

| Entity      | Format Example            |
|-------------|---------------------------|
| Patient     | PAT-20240315-0001         |
| Appointment | APT-20240315-0001         |
| Invoice     | INV-20240315-0001         |

---

## 🚀 Running in Production

```bash
mvn clean package -DskipTests
java -jar target/hospital-management-1.0.0.jar
```
