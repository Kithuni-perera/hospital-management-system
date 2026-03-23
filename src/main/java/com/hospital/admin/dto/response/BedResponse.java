package com.hospital.admin.dto.response;

import com.hospital.admin.enums.BedStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BedResponse {
    private Long id;
    private String bedNumber;
    private BedStatus status;
    private Long roomId;
    private String roomNumber;
    private Long patientId;
    private String patientName;
    private String notes;
    private LocalDateTime createdAt;
}
