package com.hospital.admin.controller;

import com.hospital.admin.dto.response.ApiResponse;
import com.hospital.admin.dto.response.DashboardResponse;
import com.hospital.admin.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Reports & Analytics", description = "Dashboard statistics and reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get dashboard statistics",
               description = "Returns summary counts for patients, doctors, appointments, beds, and billing")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats fetched", reportService.getDashboardStats()));
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get total revenue by date range")
    public ResponseEntity<ApiResponse<Double>> getRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Double revenue = reportService.getTotalRevenueByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Revenue fetched", revenue));
    }
}
