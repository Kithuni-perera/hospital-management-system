package com.hospital.admin.service.impl;

import com.hospital.admin.dto.request.BillingRequest;
import com.hospital.admin.dto.response.BillingResponse;
import com.hospital.admin.entity.Appointment;
import com.hospital.admin.entity.Billing;
import com.hospital.admin.entity.Patient;
import com.hospital.admin.enums.BillingStatus;
import com.hospital.admin.exception.ResourceNotFoundException;
import com.hospital.admin.repository.AppointmentRepository;
import com.hospital.admin.repository.BillingRepository;
import com.hospital.admin.repository.PatientRepository;
import com.hospital.admin.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public BillingResponse createBilling(BillingRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        Appointment appointment = null;
        if (request.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", request.getAppointmentId()));
        }

        double total = calculateTotal(request);
        double due = total - (request.getPaidAmount() != null ? request.getPaidAmount() : 0.0);

        Billing billing = Billing.builder()
                .invoiceNumber(generateInvoiceNumber())
                .patient(patient)
                .appointment(appointment)
                .consultationFee(request.getConsultationFee())
                .roomCharges(request.getRoomCharges())
                .medicineCharges(request.getMedicineCharges())
                .testCharges(request.getTestCharges())
                .otherCharges(request.getOtherCharges())
                .discount(request.getDiscount())
                .totalAmount(total)
                .paidAmount(request.getPaidAmount())
                .dueAmount(due)
                .status(request.getStatus() != null ? request.getStatus() : BillingStatus.PENDING)
                .billingDate(request.getBillingDate())
                .dueDate(request.getDueDate())
                .paymentMethod(request.getPaymentMethod())
                .notes(request.getNotes())
                .build();

        return mapToResponse(billingRepository.save(billing));
    }

    @Override
    public BillingResponse getBillingById(Long id) {
        return mapToResponse(findById(id));
    }

    @Override
    public BillingResponse getBillingByInvoiceNumber(String invoiceNumber) {
        Billing billing = billingRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Billing", "invoiceNumber", invoiceNumber));
        return mapToResponse(billing);
    }

    @Override
    public List<BillingResponse> getAllBillings() {
        return billingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingResponse> getBillingsByPatient(Long patientId) {
        return billingRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingResponse> getBillingsByStatus(BillingStatus status) {
        return billingRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingResponse> getBillingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return billingRepository.findByBillingDateBetween(startDate, endDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BillingResponse updateBilling(Long id, BillingRequest request) {
        Billing billing = findById(id);

        double total = calculateTotal(request);
        double due = total - (request.getPaidAmount() != null ? request.getPaidAmount() : 0.0);

        billing.setConsultationFee(request.getConsultationFee());
        billing.setRoomCharges(request.getRoomCharges());
        billing.setMedicineCharges(request.getMedicineCharges());
        billing.setTestCharges(request.getTestCharges());
        billing.setOtherCharges(request.getOtherCharges());
        billing.setDiscount(request.getDiscount());
        billing.setTotalAmount(total);
        billing.setPaidAmount(request.getPaidAmount());
        billing.setDueAmount(due);
        billing.setStatus(request.getStatus());
        billing.setBillingDate(request.getBillingDate());
        billing.setDueDate(request.getDueDate());
        billing.setPaymentMethod(request.getPaymentMethod());
        billing.setNotes(request.getNotes());

        return mapToResponse(billingRepository.save(billing));
    }

    @Override
    public BillingResponse updateBillingStatus(Long id, BillingStatus status) {
        Billing billing = findById(id);
        billing.setStatus(status);
        return mapToResponse(billingRepository.save(billing));
    }

    @Override
    public void deleteBilling(Long id) {
        billingRepository.delete(findById(id));
    }

    private double calculateTotal(BillingRequest r) {
        double subtotal = (r.getConsultationFee() != null ? r.getConsultationFee() : 0)
                + (r.getRoomCharges() != null ? r.getRoomCharges() : 0)
                + (r.getMedicineCharges() != null ? r.getMedicineCharges() : 0)
                + (r.getTestCharges() != null ? r.getTestCharges() : 0)
                + (r.getOtherCharges() != null ? r.getOtherCharges() : 0);
        double discount = r.getDiscount() != null ? r.getDiscount() : 0;
        return subtotal - discount;
    }

    private String generateInvoiceNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = billingRepository.count() + 1;
        return "INV-" + datePart + "-" + String.format("%04d", count);
    }

    private Billing findById(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billing", "id", id));
    }

    private BillingResponse mapToResponse(Billing b) {
        BillingResponse response = new BillingResponse();
        response.setId(b.getId());
        response.setInvoiceNumber(b.getInvoiceNumber());
        response.setPatientId(b.getPatient().getId());
        response.setPatientName(b.getPatient().getFirstName() + " " + b.getPatient().getLastName());
        response.setPatientCode(b.getPatient().getPatientCode());
        if (b.getAppointment() != null) {
            response.setAppointmentId(b.getAppointment().getId());
            response.setAppointmentNumber(b.getAppointment().getAppointmentNumber());
        }
        response.setConsultationFee(b.getConsultationFee());
        response.setRoomCharges(b.getRoomCharges());
        response.setMedicineCharges(b.getMedicineCharges());
        response.setTestCharges(b.getTestCharges());
        response.setOtherCharges(b.getOtherCharges());
        response.setDiscount(b.getDiscount());
        response.setTotalAmount(b.getTotalAmount());
        response.setPaidAmount(b.getPaidAmount());
        response.setDueAmount(b.getDueAmount());
        response.setStatus(b.getStatus());
        response.setBillingDate(b.getBillingDate());
        response.setDueDate(b.getDueDate());
        response.setPaymentMethod(b.getPaymentMethod());
        response.setNotes(b.getNotes());
        response.setCreatedAt(b.getCreatedAt());
        return response;
    }
}
