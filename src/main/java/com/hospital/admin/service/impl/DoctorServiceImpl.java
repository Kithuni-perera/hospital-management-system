package com.hospital.admin.service.impl;

import com.hospital.admin.dto.request.DoctorRequest;
import com.hospital.admin.dto.response.DoctorResponse;
import com.hospital.admin.entity.Department;
import com.hospital.admin.entity.Doctor;
import com.hospital.admin.entity.User;
import com.hospital.admin.exception.DuplicateResourceException;
import com.hospital.admin.exception.ResourceNotFoundException;
import com.hospital.admin.repository.DepartmentRepository;
import com.hospital.admin.repository.DoctorRepository;
import com.hospital.admin.repository.UserRepository;
import com.hospital.admin.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        if (request.getLicenseNumber() != null && doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateResourceException("License number already registered: " + request.getLicenseNumber());
        }

        Doctor doctor = Doctor.builder()
                .user(user)
                .specialization(request.getSpecialization())
                .qualification(request.getQualification())
                .experienceYears(request.getExperienceYears())
                .licenseNumber(request.getLicenseNumber())
                .gender(request.getGender())
                .consultationFee(request.getConsultationFee())
                .department(department)
                .build();

        return mapToResponse(doctorRepository.save(doctor));
    }

    @Override
    public DoctorResponse getDoctorById(Long id) {
        return mapToResponse(findById(id));
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponse> getDoctorsByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponse> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = findById(id);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        if (request.getLicenseNumber() != null
                && !request.getLicenseNumber().equals(doctor.getLicenseNumber())
                && doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateResourceException("License number already registered: " + request.getLicenseNumber());
        }

        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setGender(request.getGender());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setDepartment(department);

        return mapToResponse(doctorRepository.save(doctor));
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.delete(findById(id));
    }

    @Override
    public void toggleDoctorStatus(Long id) {
        Doctor doctor = findById(id);
        doctor.setIsActive(!Boolean.TRUE.equals(doctor.getIsActive()));
        doctorRepository.save(doctor);
    }

    private Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
    }

    private DoctorResponse mapToResponse(Doctor d) {
        DoctorResponse response = new DoctorResponse();
        response.setId(d.getId());
        response.setUserId(d.getUser().getId());
        response.setFirstName(d.getUser().getFirstName());
        response.setLastName(d.getUser().getLastName());
        response.setEmail(d.getUser().getEmail());
        response.setPhone(d.getUser().getPhone());
        response.setSpecialization(d.getSpecialization());
        response.setQualification(d.getQualification());
        response.setExperienceYears(d.getExperienceYears());
        response.setLicenseNumber(d.getLicenseNumber());
        response.setGender(d.getGender());
        response.setConsultationFee(d.getConsultationFee());
        response.setDepartmentId(d.getDepartment().getId());
        response.setDepartmentName(d.getDepartment().getName());
        response.setIsActive(d.getIsActive());
        response.setCreatedAt(d.getCreatedAt());
        return response;
    }
}
