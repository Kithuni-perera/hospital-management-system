package com.hospital.admin.repository;

import com.hospital.admin.entity.Bed;
import com.hospital.admin.enums.BedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);
    List<Bed> findByStatus(BedStatus status);
    List<Bed> findByPatientId(Long patientId);
    boolean existsByBedNumberAndRoomId(String bedNumber, Long roomId);

    @Query("SELECT COUNT(b) FROM Bed b WHERE b.status = :status")
    Long countByStatus(BedStatus status);

    @Query("SELECT b FROM Bed b WHERE b.status = 'AVAILABLE'")
    List<Bed> findAllAvailableBeds();
}
