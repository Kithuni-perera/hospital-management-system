package com.hospital.admin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    // Counts
    private Long totalPatients;
    private Long totalDoctors;
    private Long totalDepartments;
    private Long totalAppointments;

    // Appointment stats
    private Long scheduledAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;
    private Long todayAppointments;

    // Bed stats
    private Long totalBeds;
    private Long availableBeds;
    private Long occupiedBeds;
    private Long maintenanceBeds;

    // Billing stats
    private Double totalRevenue;
    private Double pendingRevenue;
    private Long pendingBills;
    private Long paidBills;
}
