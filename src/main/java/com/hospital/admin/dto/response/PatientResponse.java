package com.hospital.admin.dto.response;

import com.hospital.admin.enums.Gender;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientResponse {
    private Long id;
    private String patientCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String address;
    private String bloodGroup;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String medicalHistory;
    private String allergies;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
