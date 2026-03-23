package com.hospital.admin.repository;

import com.hospital.admin.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    List<Doctor> findByDepartmentId(Long departmentId);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByIsActiveTrue();

    @Query("SELECT d FROM Doctor d WHERE d.isActive = true AND d.department.id = :departmentId")
    List<Doctor> findActiveDoctorsByDepartment(Long departmentId);
}
