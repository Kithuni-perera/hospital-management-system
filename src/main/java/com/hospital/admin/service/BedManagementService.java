package com.hospital.admin.service;

import com.hospital.admin.dto.request.BedRequest;
import com.hospital.admin.dto.request.RoomRequest;
import com.hospital.admin.dto.response.BedResponse;
import com.hospital.admin.dto.response.RoomResponse;
import com.hospital.admin.enums.BedStatus;

import java.util.List;

public interface BedManagementService {
    // Room operations
    RoomResponse createRoom(RoomRequest request);
    RoomResponse getRoomById(Long id);
    List<RoomResponse> getAllRooms();
    List<RoomResponse> getRoomsByDepartment(Long departmentId);
    RoomResponse updateRoom(Long id, RoomRequest request);
    void deleteRoom(Long id);

    // Bed operations
    BedResponse createBed(BedRequest request);
    BedResponse getBedById(Long id);
    List<BedResponse> getAllBeds();
    List<BedResponse> getBedsByRoom(Long roomId);
    List<BedResponse> getBedsByStatus(BedStatus status);
    List<BedResponse> getAvailableBeds();
    BedResponse updateBed(Long id, BedRequest request);
    BedResponse assignBedToPatient(Long bedId, Long patientId);
    BedResponse releaseBed(Long bedId);
    void deleteBed(Long id);
}
