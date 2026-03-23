package com.hospital.admin.service;

import com.hospital.admin.dto.request.AppointmentRequest;
import com.hospital.admin.dto.response.AppointmentResponse;
import com.hospital.admin.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);
    AppointmentResponse getAppointmentById(Long id);
    AppointmentResponse getAppointmentByNumber(String number);
    List<AppointmentResponse> getAllAppointments();
    List<AppointmentResponse> getAppointmentsByPatient(Long patientId);
    List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId);
    List<AppointmentResponse> getAppointmentsByDate(LocalDate date);
    List<AppointmentResponse> getAppointmentsByStatus(AppointmentStatus status);
    AppointmentResponse updateAppointment(Long id, AppointmentRequest request);
    AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatus status);
    void deleteAppointment(Long id);
}
