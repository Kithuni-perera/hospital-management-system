package com.hospital.admin.service.impl;

import com.hospital.admin.dto.request.BedRequest;
import com.hospital.admin.dto.request.RoomRequest;
import com.hospital.admin.dto.response.BedResponse;
import com.hospital.admin.dto.response.RoomResponse;
import com.hospital.admin.entity.Bed;
import com.hospital.admin.entity.Department;
import com.hospital.admin.entity.Patient;
import com.hospital.admin.entity.Room;
import com.hospital.admin.enums.BedStatus;
import com.hospital.admin.exception.DuplicateResourceException;
import com.hospital.admin.exception.ResourceNotFoundException;
import com.hospital.admin.repository.BedRepository;
import com.hospital.admin.repository.DepartmentRepository;
import com.hospital.admin.repository.PatientRepository;
import com.hospital.admin.repository.RoomRepository;
import com.hospital.admin.service.BedManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BedManagementServiceImpl implements BedManagementService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;

    // ───────────── ROOM OPERATIONS ─────────────

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.existsByRoomNumber(request.getRoomNumber())) {
            throw new DuplicateResourceException("Room number already exists: " + request.getRoomNumber());
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        Room room = Room.builder()
                .roomNumber(request.getRoomNumber())
                .roomType(request.getRoomType())
                .floor(request.getFloor())
                .capacity(request.getCapacity())
                .pricePerDay(request.getPricePerDay())
                .department(department)
                .build();

        return mapRoomToResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        return mapRoomToResponse(findRoomById(id));
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::mapRoomToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getRoomsByDepartment(Long departmentId) {
        return roomRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapRoomToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomRequest request) {
        Room room = findRoomById(id);

        if (!room.getRoomNumber().equals(request.getRoomNumber())
                && roomRepository.existsByRoomNumber(request.getRoomNumber())) {
            throw new DuplicateResourceException("Room number already exists: " + request.getRoomNumber());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setFloor(request.getFloor());
        room.setCapacity(request.getCapacity());
        room.setPricePerDay(request.getPricePerDay());
        room.setDepartment(department);

        return mapRoomToResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.delete(findRoomById(id));
    }

    // ───────────── BED OPERATIONS ─────────────

    @Override
    public BedResponse createBed(BedRequest request) {
        Room room = findRoomById(request.getRoomId());

        if (bedRepository.existsByBedNumberAndRoomId(request.getBedNumber(), request.getRoomId())) {
            throw new DuplicateResourceException("Bed number already exists in this room: " + request.getBedNumber());
        }

        Patient patient = null;
        if (request.getPatientId() != null) {
            patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));
        }

        Bed bed = Bed.builder()
                .bedNumber(request.getBedNumber())
                .status(request.getStatus())
                .room(room)
                .patient(patient)
                .notes(request.getNotes())
                .build();

        return mapBedToResponse(bedRepository.save(bed));
    }

    @Override
    public BedResponse getBedById(Long id) {
        return mapBedToResponse(findBedById(id));
    }

    @Override
    public List<BedResponse> getAllBeds() {
        return bedRepository.findAll().stream()
                .map(this::mapBedToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BedResponse> getBedsByRoom(Long roomId) {
        return bedRepository.findByRoomId(roomId).stream()
                .map(this::mapBedToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BedResponse> getBedsByStatus(BedStatus status) {
        return bedRepository.findByStatus(status).stream()
                .map(this::mapBedToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BedResponse> getAvailableBeds() {
        return bedRepository.findAllAvailableBeds().stream()
                .map(this::mapBedToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BedResponse updateBed(Long id, BedRequest request) {
        Bed bed = findBedById(id);
        Room room = findRoomById(request.getRoomId());

        bed.setBedNumber(request.getBedNumber());
        bed.setStatus(request.getStatus());
        bed.setRoom(room);
        bed.setNotes(request.getNotes());

        return mapBedToResponse(bedRepository.save(bed));
    }

    @Override
    public BedResponse assignBedToPatient(Long bedId, Long patientId) {
        Bed bed = findBedById(bedId);
        if (bed.getStatus() != BedStatus.AVAILABLE) {
            throw new IllegalArgumentException("Bed is not available. Current status: " + bed.getStatus());
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));

        bed.setPatient(patient);
        bed.setStatus(BedStatus.OCCUPIED);
        return mapBedToResponse(bedRepository.save(bed));
    }

    @Override
    public BedResponse releaseBed(Long bedId) {
        Bed bed = findBedById(bedId);
        bed.setPatient(null);
        bed.setStatus(BedStatus.AVAILABLE);
        return mapBedToResponse(bedRepository.save(bed));
    }

    @Override
    public void deleteBed(Long id) {
        bedRepository.delete(findBedById(id));
    }

    // ───────────── HELPERS ─────────────

    private Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
    }

    private Bed findBedById(Long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bed", "id", id));
    }

    private RoomResponse mapRoomToResponse(Room r) {
        RoomResponse response = new RoomResponse();
        response.setId(r.getId());
        response.setRoomNumber(r.getRoomNumber());
        response.setRoomType(r.getRoomType());
        response.setFloor(r.getFloor());
        response.setCapacity(r.getCapacity());
        response.setPricePerDay(r.getPricePerDay());
        response.setDepartmentId(r.getDepartment().getId());
        response.setDepartmentName(r.getDepartment().getName());
        response.setIsActive(r.getIsActive());
        response.setCreatedAt(r.getCreatedAt());
        if (r.getBeds() != null) {
            response.setTotalBeds(r.getBeds().size());
            long available = r.getBeds().stream()
                    .filter(b -> b.getStatus() == BedStatus.AVAILABLE).count();
            response.setAvailableBeds((int) available);
        }
        return response;
    }

    private BedResponse mapBedToResponse(Bed b) {
        BedResponse response = new BedResponse();
        response.setId(b.getId());
        response.setBedNumber(b.getBedNumber());
        response.setStatus(b.getStatus());
        response.setRoomId(b.getRoom().getId());
        response.setRoomNumber(b.getRoom().getRoomNumber());
        response.setNotes(b.getNotes());
        response.setCreatedAt(b.getCreatedAt());
        if (b.getPatient() != null) {
            response.setPatientId(b.getPatient().getId());
            response.setPatientName(b.getPatient().getFirstName() + " " + b.getPatient().getLastName());
        }
        return response;
    }
}
