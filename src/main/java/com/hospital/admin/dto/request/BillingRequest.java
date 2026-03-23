package com.hospital.admin.dto.request;

import com.hospital.admin.enums.BillingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BillingRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long appointmentId;
    private Double consultationFee = 0.0;
    private Double roomCharges = 0.0;
    private Double medicineCharges = 0.0;
    private Double testCharges = 0.0;
    private Double otherCharges = 0.0;
    private Double discount = 0.0;
    private Double paidAmount = 0.0;
    private BillingStatus status = BillingStatus.PENDING;

    @NotNull(message = "Billing date is required")
    private LocalDate billingDate;

    private LocalDate dueDate;
    private String paymentMethod;
    private String notes;
}
