package com.hospital.admin.service;

import com.hospital.admin.dto.request.BillingRequest;
import com.hospital.admin.dto.response.BillingResponse;
import com.hospital.admin.enums.BillingStatus;

import java.time.LocalDate;
import java.util.List;

public interface BillingService {
    BillingResponse createBilling(BillingRequest request);
    BillingResponse getBillingById(Long id);
    BillingResponse getBillingByInvoiceNumber(String invoiceNumber);
    List<BillingResponse> getAllBillings();
    List<BillingResponse> getBillingsByPatient(Long patientId);
    List<BillingResponse> getBillingsByStatus(BillingStatus status);
    List<BillingResponse> getBillingsByDateRange(LocalDate startDate, LocalDate endDate);
    BillingResponse updateBilling(Long id, BillingRequest request);
    BillingResponse updateBillingStatus(Long id, BillingStatus status);
    void deleteBilling(Long id);
}
