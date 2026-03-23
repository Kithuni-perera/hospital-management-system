package com.hospital.admin.entity;

import com.hospital.admin.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "billings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Billing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", unique = true, nullable = false)
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(name = "consultation_fee")
    private Double consultationFee;

    @Column(name = "room_charges")
    private Double roomCharges;

    @Column(name = "medicine_charges")
    private Double medicineCharges;

    @Column(name = "test_charges")
    private Double testCharges;

    @Column(name = "other_charges")
    private Double otherCharges;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "due_amount")
    private Double dueAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BillingStatus status;

    @Column(name = "billing_date", nullable = false)
    private LocalDate billingDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_method")
    private String paymentMethod; // CASH, CARD, INSURANCE, ONLINE

    @Column(name = "notes", columnDefinition = "NVARCHAR(500)")
    private String notes;
}
