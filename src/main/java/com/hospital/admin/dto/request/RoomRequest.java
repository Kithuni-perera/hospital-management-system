package com.hospital.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequest {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    private String roomType;

    private Integer floor;
    private Integer capacity;
    private Double pricePerDay;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
