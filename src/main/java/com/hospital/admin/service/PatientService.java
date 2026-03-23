package com.hospital.admin.service;

import com.hospital.admin.dto.request.PatientRequest;
import com.hospital.admin.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse createPatient(PatientRequest request);
    PatientResponse getPatientById(Long id);
    PatientResponse getPatientByCode(String code);
    List<PatientResponse> getAllPatients();
    List<PatientResponse> searchPatientsByName(String name);
    PatientResponse updatePatient(Long id, PatientRequest request);
    void deletePatient(Long id);
    void togglePatientStatus(Long id);
}
