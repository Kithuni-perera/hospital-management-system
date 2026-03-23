package com.hospital.admin.controller;

import com.hospital.admin.dto.request.BedRequest;
import com.hospital.admin.dto.request.RoomRequest;
import com.hospital.admin.dto.response.ApiResponse;
import com.hospital.admin.dto.response.BedResponse;
import com.hospital.admin.dto.response.RoomResponse;
import com.hospital.admin.enums.BedStatus;
import com.hospital.admin.service.BedManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bed-management")
@RequiredArgsConstructor
@Tag(name = "Bed & Room Management", description = "Manage rooms and bed assignments")
public class BedManagementController {

    private final BedManagementService bedManagementService;

    // ─── ROOMS ───

    @PostMapping("/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create room")
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Room created", bedManagementService.createRoom(request)));
    }

    @GetMapping("/rooms")
    @Operation(summary = "Get all rooms")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAllRooms() {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getAllRooms()));
    }

    @GetMapping("/rooms/{id}")
    @Operation(summary = "Get room by ID")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getRoomById(id)));
    }

    @GetMapping("/rooms/department/{departmentId}")
    @Operation(summary = "Get rooms by department")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getRoomsByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getRoomsByDepartment(departmentId)));
    }

    @PutMapping("/rooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update room")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
            @PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Room updated", bedManagementService.updateRoom(id, request)));
    }

    @DeleteMapping("/rooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete room")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long id) {
        bedManagementService.deleteRoom(id);
        return ResponseEntity.ok(ApiResponse.success("Room deleted", null));
    }

    // ─── BEDS ───

    @PostMapping("/beds")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    @Operation(summary = "Create bed")
    public ResponseEntity<ApiResponse<BedResponse>> createBed(@Valid @RequestBody BedRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Bed created", bedManagementService.createBed(request)));
    }

    @GetMapping("/beds")
    @Operation(summary = "Get all beds")
    public ResponseEntity<ApiResponse<List<BedResponse>>> getAllBeds() {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getAllBeds()));
    }

    @GetMapping("/beds/{id}")
    @Operation(summary = "Get bed by ID")
    public ResponseEntity<ApiResponse<BedResponse>> getBedById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getBedById(id)));
    }

    @GetMapping("/beds/room/{roomId}")
    @Operation(summary = "Get beds by room")
    public ResponseEntity<ApiResponse<List<BedResponse>>> getBedsByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getBedsByRoom(roomId)));
    }

    @GetMapping("/beds/available")
    @Operation(summary = "Get all available beds")
    public ResponseEntity<ApiResponse<List<BedResponse>>> getAvailableBeds() {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getAvailableBeds()));
    }

    @GetMapping("/beds/status/{status}")
    @Operation(summary = "Get beds by status")
    public ResponseEntity<ApiResponse<List<BedResponse>>> getBedsByStatus(@PathVariable BedStatus status) {
        return ResponseEntity.ok(ApiResponse.success(bedManagementService.getBedsByStatus(status)));
    }

    @PatchMapping("/beds/{bedId}/assign/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE', 'RECEPTIONIST')")
    @Operation(summary = "Assign bed to patient")
    public ResponseEntity<ApiResponse<BedResponse>> assignBed(
            @PathVariable Long bedId, @PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success("Bed assigned", bedManagementService.assignBedToPatient(bedId, patientId)));
    }

    @PatchMapping("/beds/{bedId}/release")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE', 'RECEPTIONIST')")
    @Operation(summary = "Release bed")
    public ResponseEntity<ApiResponse<BedResponse>> releaseBed(@PathVariable Long bedId) {
        return ResponseEntity.ok(ApiResponse.success("Bed released", bedManagementService.releaseBed(bedId)));
    }

    @PutMapping("/beds/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    @Operation(summary = "Update bed")
    public ResponseEntity<ApiResponse<BedResponse>> updateBed(
            @PathVariable Long id, @Valid @RequestBody BedRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Bed updated", bedManagementService.updateBed(id, request)));
    }

    @DeleteMapping("/beds/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete bed")
    public ResponseEntity<ApiResponse<Void>> deleteBed(@PathVariable Long id) {
        bedManagementService.deleteBed(id);
        return ResponseEntity.ok(ApiResponse.success("Bed deleted", null));
    }
}
