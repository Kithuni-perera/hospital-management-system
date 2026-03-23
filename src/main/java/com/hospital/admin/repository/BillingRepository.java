package com.hospital.admin.repository;

import com.hospital.admin.entity.Billing;
import com.hospital.admin.enums.BillingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByInvoiceNumber(String invoiceNumber);
    List<Billing> findByPatientId(Long patientId);
    List<Billing> findByStatus(BillingStatus status);
    List<Billing> findByBillingDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(b.totalAmount) FROM Billing b WHERE b.billingDate BETWEEN :startDate AND :endDate")
    Double sumTotalAmountByDateRange(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(b.paidAmount) FROM Billing b WHERE b.billingDate BETWEEN :startDate AND :endDate")
    Double sumPaidAmountByDateRange(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(b) FROM Billing b WHERE b.status = :status")
    Long countByStatus(BillingStatus status);
}
