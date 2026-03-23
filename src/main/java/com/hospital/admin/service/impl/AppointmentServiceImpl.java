package com.hospital.admin.service.impl;

import com.hospital.admin.dto.request.AppointmentRequest;
import com.hospital.admin.dto.response.AppointmentResponse;
import com.hospital.admin.entity.Appointment;
import com.hospital.admin.entity.Doctor;
import com.hospital.admin.entity.Patient;
import com.hospital.admin.enums.AppointmentStatus;
import com.hospital.admin.exception.ResourceNotFoundException;
import com.hospital.admin.repository.AppointmentRepository;
import com.hospital.admin.repository.DoctorRepository;
import com.hospital.admin.repository.PatientRepository;
import com.hospital.admin.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", request.getDoctorId()));

        Appointment appointment = Appointment.builder()
                .appointmentNumber(generateAppointmentNumber())
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .status(request.getStatus() != null ? request.getStatus() : AppointmentStatus.SCHEDULED)
                .reason(request.getReason())
                .notes(request.getNotes())
                .followUpDate(request.getFollowUpDate())
                .build();

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        return mapToResponse(findById(id));
    }

    @Override
    public AppointmentResponse getAppointmentByNumber(String number) {
        Appointment appointment = appointmentRepository.findByAppointmentNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "number", number));
        return mapToResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = findById(id);

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", request.getDoctorId()));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(request.getStatus());
        appointment.setReason(request.getReason());
        appointment.setNotes(request.getNotes());
        appointment.setFollowUpDate(request.getFollowUpDate());

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatus status) {
        Appointment appointment = findById(id);
        appointment.setStatus(status);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.delete(findById(id));
    }

    private String generateAppointmentNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = appointmentRepository.count() + 1;
        return "APT-" + datePart + "-" + String.format("%04d", count);
    }

    private Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
    }

    private AppointmentResponse mapToResponse(Appointment a) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(a.getId());
        response.setAppointmentNumber(a.getAppointmentNumber());
        response.setPatientId(a.getPatient().getId());
        response.setPatientName(a.getPatient().getFirstName() + " " + a.getPatient().getLastName());
        response.setPatientCode(a.getPatient().getPatientCode());
        response.setDoctorId(a.getDoctor().getId());
        response.setDoctorName(a.getDoctor().getUser().getFirstName() + " " + a.getDoctor().getUser().getLastName());
        response.setDoctorSpecialization(a.getDoctor().getSpecialization());
        response.setAppointmentDate(a.getAppointmentDate());
        response.setAppointmentTime(a.getAppointmentTime());
        response.setStatus(a.getStatus());
        response.setReason(a.getReason());
        response.setNotes(a.getNotes());
        response.setFollowUpDate(a.getFollowUpDate());
        response.setCreatedAt(a.getCreatedAt());
        return response;
    }
}
