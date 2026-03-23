package com.hospital.admin.service.impl;

import com.hospital.admin.dto.response.DashboardResponse;
import com.hospital.admin.enums.AppointmentStatus;
import com.hospital.admin.enums.BedStatus;
import com.hospital.admin.enums.BillingStatus;
import com.hospital.admin.repository.*;
import com.hospital.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;
    private final BedRepository bedRepository;
    private final BillingRepository billingRepository;

    @Override
    public DashboardResponse getDashboardStats() {
        LocalDate today = LocalDate.now();

        // Revenue
        Double totalRevenue = billingRepository.sumTotalAmountByDateRange(
                LocalDate.of(2000, 1, 1), today);
        Double pendingRevenue = billingRepository.sumPaidAmountByDateRange(
                LocalDate.of(2000, 1, 1), today);

        return DashboardResponse.builder()
                // Core counts
                .totalPatients(patientRepository.count())
                .totalDoctors(doctorRepository.count())
                .totalDepartments(departmentRepository.count())
                .totalAppointments(appointmentRepository.count())
                // Appointment stats
                .scheduledAppointments(appointmentRepository.countByStatus(AppointmentStatus.SCHEDULED))
                .completedAppointments(appointmentRepository.countByStatus(AppointmentStatus.COMPLETED))
                .cancelledAppointments(appointmentRepository.countByStatus(AppointmentStatus.CANCELLED))
                .todayAppointments(appointmentRepository.countByDate(today))
                // Bed stats
                .totalBeds(bedRepository.count())
                .availableBeds(bedRepository.countByStatus(BedStatus.AVAILABLE))
                .occupiedBeds(bedRepository.countByStatus(BedStatus.OCCUPIED))
                .maintenanceBeds(bedRepository.countByStatus(BedStatus.MAINTENANCE))
                // Billing stats
                .totalRevenue(totalRevenue != null ? totalRevenue : 0.0)
                .pendingRevenue(pendingRevenue != null ? pendingRevenue : 0.0)
                .pendingBills(billingRepository.countByStatus(BillingStatus.PENDING))
                .paidBills(billingRepository.countByStatus(BillingStatus.PAID))
                .build();
    }

    @Override
    public Double getTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        Double revenue = billingRepository.sumTotalAmountByDateRange(startDate, endDate);
        return revenue != null ? revenue : 0.0;
    }
}
