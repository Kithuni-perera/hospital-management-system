package com.hospital.admin.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DepartmentResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String phone;
    private Integer totalDoctors;
    private Integer totalRooms;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
