package com.moisas.barbershop.modules.appointment.service;

import com.moisas.barbershop.modules.appointment.dto.AppointmentDTO;
import com.moisas.barbershop.modules.appointment.dto.CreateAppointmentDTO;
import com.moisas.barbershop.modules.appointment.entity.AppointmentEntity;
import com.moisas.barbershop.modules.appointment.mapper.AppointmentMapper;
import com.moisas.barbershop.modules.appointment.repository.AppointmentRepository;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ProductRepository servicesRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentDTO execute(@NonNull CreateAppointmentDTO dto) {
        Optional<ProductEntity> service = servicesRepository.findById(dto.getServiceId());
        
        if (service.isEmpty()) {
            return null;
        }

        AppointmentEntity appointmentEntity = appointmentMapper.toEntity(dto);

        appointmentEntity.setServiceId(service.get());

        return appointmentMapper.toDTO(appointmentRepository.save(appointmentEntity));
    }
}
