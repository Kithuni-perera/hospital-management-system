package com.hospital.admin.dto.request;

import com.hospital.admin.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    private String qualification;
    private Integer experienceYears;
    private String licenseNumber;
    private Gender gender;
    private Double consultationFee;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
