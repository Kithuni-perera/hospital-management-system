package com.hospital.admin.dto.response;

import com.hospital.admin.enums.Gender;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DoctorResponse {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private String qualification;
    private Integer experienceYears;
    private String licenseNumber;
    private Gender gender;
    private Double consultationFee;
    private Long departmentId;
    private String departmentName;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
