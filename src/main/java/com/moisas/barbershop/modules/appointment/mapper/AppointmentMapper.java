package com.moisas.barbershop.modules.appointment.mapper;

import com.moisas.barbershop.modules.appointment.dto.AppointmentDTO;
import com.moisas.barbershop.modules.appointment.dto.CreateAppointmentDTO;
import com.moisas.barbershop.modules.appointment.entity.AppointmentEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    public AppointmentEntity toEntity(@NonNull CreateAppointmentDTO dto) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setDate(dto.getDate());

        return entity;
    }

    public AppointmentDTO toDTO(AppointmentEntity entity) {
        if (entity == null) {
            return null;
        }

        return AppointmentDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .serviceId(entity.getServiceId() != null ? entity.getServiceId().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
