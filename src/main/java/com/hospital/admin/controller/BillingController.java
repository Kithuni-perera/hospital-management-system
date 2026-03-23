package com.hospital.admin.controller;

import com.hospital.admin.dto.request.BillingRequest;
import com.hospital.admin.dto.response.ApiResponse;
import com.hospital.admin.dto.response.BillingResponse;
import com.hospital.admin.enums.BillingStatus;
import com.hospital.admin.service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
@Tag(name = "Billing & Invoicing", description = "Manage patient billing and invoices")
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Create billing invoice")
    public ResponseEntity<ApiResponse<BillingResponse>> create(@Valid @RequestBody BillingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Invoice created", billingService.createBilling(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Get all billings")
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(billingService.getAllBillings()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get billing by ID")
    public ResponseEntity<ApiResponse<BillingResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getBillingById(id)));
    }

    @GetMapping("/invoice/{invoiceNumber}")
    @Operation(summary = "Get billing by invoice number")
    public ResponseEntity<ApiResponse<BillingResponse>> getByInvoiceNumber(@PathVariable String invoiceNumber) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getBillingByInvoiceNumber(invoiceNumber)));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get billings by patient")
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getBillingsByPatient(patientId)));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get billings by status")
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getByStatus(@PathVariable BillingStatus status) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getBillingsByStatus(status)));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get billings by date range")
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(billingService.getBillingsByDateRange(startDate, endDate)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Update billing")
    public ResponseEntity<ApiResponse<BillingResponse>> update(
            @PathVariable Long id, @Valid @RequestBody BillingRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Billing updated", billingService.updateBilling(id, request)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Update billing status")
    public ResponseEntity<ApiResponse<BillingResponse>> updateStatus(
            @PathVariable Long id, @RequestParam BillingStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", billingService.updateBillingStatus(id, status)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete billing")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        billingService.deleteBilling(id);
        return ResponseEntity.ok(ApiResponse.success("Billing deleted", null));
    }
}
