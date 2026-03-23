package com.hospital.admin.dto.response;

import com.hospital.admin.enums.BillingStatus;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BillingResponse {
    private Long id;
    private String invoiceNumber;
    private Long patientId;
    private String patientName;
    private String patientCode;
    private Long appointmentId;
    private String appointmentNumber;
    private Double consultationFee;
    private Double roomCharges;
    private Double medicineCharges;
    private Double testCharges;
    private Double otherCharges;
    private Double discount;
    private Double totalAmount;
    private Double paidAmount;
    private Double dueAmount;
    private BillingStatus status;
    private LocalDate billingDate;
    private LocalDate dueDate;
    private String paymentMethod;
    private String notes;
    private LocalDateTime createdAt;
}
