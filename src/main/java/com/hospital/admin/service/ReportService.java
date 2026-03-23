package com.hospital.admin.service;

import com.hospital.admin.dto.response.DashboardResponse;

import java.time.LocalDate;

public interface ReportService {
    DashboardResponse getDashboardStats();
    Double getTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate);
}
