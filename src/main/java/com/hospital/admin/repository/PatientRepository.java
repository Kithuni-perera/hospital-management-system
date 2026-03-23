package com.hospital.admin.repository;

import com.hospital.admin.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPatientCode(String patientCode);
    boolean existsByEmail(String email);
    boolean existsByPatientCode(String patientCode);
    List<Patient> findByIsActiveTrue();

    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> searchByName(String name);

    @Query("SELECT p FROM Patient p WHERE p.phone = :phone")
    Optional<Patient> findByPhone(String phone);
}
