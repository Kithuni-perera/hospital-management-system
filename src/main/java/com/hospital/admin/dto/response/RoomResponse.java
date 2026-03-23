package com.hospital.admin.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private String roomType;
    private Integer floor;
    private Integer capacity;
    private Double pricePerDay;
    private Long departmentId;
    private String departmentName;
    private Integer totalBeds;
    private Integer availableBeds;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
