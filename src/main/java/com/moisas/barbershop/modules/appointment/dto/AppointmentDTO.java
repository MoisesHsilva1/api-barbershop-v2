package com.moisas.barbershop.modules.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private String id;
    private LocalDateTime date;
    private String serviceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
