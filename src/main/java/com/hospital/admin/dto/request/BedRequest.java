package com.hospital.admin.dto.request;

import com.hospital.admin.enums.BedStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BedRequest {

    @NotBlank(message = "Bed number is required")
    private String bedNumber;

    @NotNull(message = "Status is required")
    private BedStatus status;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    private Long patientId;
    private String notes;
}
