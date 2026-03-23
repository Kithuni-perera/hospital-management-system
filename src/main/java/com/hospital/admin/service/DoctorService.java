package com.hospital.admin.service;

import com.hospital.admin.dto.request.DoctorRequest;
import com.hospital.admin.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorRequest request);
    DoctorResponse getDoctorById(Long id);
    List<DoctorResponse> getAllDoctors();
    List<DoctorResponse> getDoctorsByDepartment(Long departmentId);
    List<DoctorResponse> getDoctorsBySpecialization(String specialization);
    DoctorResponse updateDoctor(Long id, DoctorRequest request);
    void deleteDoctor(Long id);
    void toggleDoctorStatus(Long id);
}
