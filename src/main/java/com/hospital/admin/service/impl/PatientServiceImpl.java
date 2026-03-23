package com.hospital.admin.service.impl;

import com.hospital.admin.dto.request.PatientRequest;
import com.hospital.admin.dto.response.PatientResponse;
import com.hospital.admin.entity.Patient;
import com.hospital.admin.exception.DuplicateResourceException;
import com.hospital.admin.exception.ResourceNotFoundException;
import com.hospital.admin.repository.PatientRepository;
import com.hospital.admin.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponse createPatient(PatientRequest request) {
        if (request.getEmail() != null && patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        Patient patient = Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(request.getAddress())
                .bloodGroup(request.getBloodGroup())
                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactPhone(request.getEmergencyContactPhone())
                .medicalHistory(request.getMedicalHistory())
                .allergies(request.getAllergies())
                .patientCode(generatePatientCode())
                .build();

        return mapToResponse(patientRepository.save(patient));
    }

    @Override
    public PatientResponse getPatientById(Long id) {
        return mapToResponse(findById(id));
    }

    @Override
    public PatientResponse getPatientByCode(String code) {
        Patient patient = patientRepository.findByPatientCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "code", code));
        return mapToResponse(patient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientResponse> searchPatientsByName(String name) {
        return patientRepository.searchByName(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = findById(id);

        if (request.getEmail() != null
                && !request.getEmail().equals(patient.getEmail())
                && patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setAllergies(request.getAllergies());

        return mapToResponse(patientRepository.save(patient));
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.delete(findById(id));
    }

    @Override
    public void togglePatientStatus(Long id) {
        Patient patient = findById(id);
        patient.setIsActive(!Boolean.TRUE.equals(patient.getIsActive()));
        patientRepository.save(patient);
    }

    private String generatePatientCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = patientRepository.count() + 1;
        return "PAT-" + datePart + "-" + String.format("%04d", count);
    }

    private Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
    }

    private PatientResponse mapToResponse(Patient p) {
        PatientResponse response = new PatientResponse();
        response.setId(p.getId());
        response.setPatientCode(p.getPatientCode());
        response.setFirstName(p.getFirstName());
        response.setLastName(p.getLastName());
        response.setEmail(p.getEmail());
        response.setPhone(p.getPhone());
        response.setDateOfBirth(p.getDateOfBirth());
        response.setGender(p.getGender());
        response.setAddress(p.getAddress());
        response.setBloodGroup(p.getBloodGroup());
        response.setEmergencyContactName(p.getEmergencyContactName());
        response.setEmergencyContactPhone(p.getEmergencyContactPhone());
        response.setMedicalHistory(p.getMedicalHistory());
        response.setAllergies(p.getAllergies());
        response.setIsActive(p.getIsActive());
        response.setCreatedAt(p.getCreatedAt());
        return response;
    }
}
