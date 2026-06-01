package com.moisas.barbershop.unit.appointment.service;

import com.moisas.barbershop.modules.appointment.dto.AppointmentDTO;
import com.moisas.barbershop.modules.appointment.dto.CreateAppointmentDTO;
import com.moisas.barbershop.modules.appointment.entity.AppointmentEntity;
import com.moisas.barbershop.modules.appointment.mapper.AppointmentMapper;
import com.moisas.barbershop.modules.appointment.repository.AppointmentRepository;
import com.moisas.barbershop.modules.appointment.service.CreateAppointmentService;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CreateAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ProductRepository servicesRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private CreateAppointmentService createAppointmentService;

    @Test
    void shouldCreateAppointmentSuccessfully() {
        CreateAppointmentDTO createDto = new CreateAppointmentDTO();
        createDto.setServiceId("service-123");
        
        ProductEntity serviceEntity = new ProductEntity();
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        AppointmentEntity savedEntity = new AppointmentEntity();
        AppointmentDTO expectedDto = new AppointmentDTO();

        given(servicesRepository.findById("service-123")).willReturn(Optional.of(serviceEntity));
        given(appointmentMapper.toEntity(createDto)).willReturn(appointmentEntity);
        given(appointmentRepository.save(appointmentEntity)).willReturn(savedEntity);
        given(appointmentMapper.toDTO(savedEntity)).willReturn(expectedDto);

        AppointmentDTO result = createAppointmentService.execute(createDto);

        assertThat(result).isNotNull().isEqualTo(expectedDto);
        assertThat(appointmentEntity.getServiceId()).isEqualTo(serviceEntity);
        verify(appointmentMapper).toEntity(createDto);
        verify(appointmentRepository).save(appointmentEntity);
    }

    @Test
    void shouldReturnNullWhenServiceNotFound() {
        CreateAppointmentDTO createDto = new CreateAppointmentDTO();
        createDto.setServiceId("service-123");

        given(servicesRepository.findById("service-123")).willReturn(Optional.empty());

        AppointmentDTO result = createAppointmentService.execute(createDto);

        assertThat(result).isNull();
        verifyNoInteractions(appointmentMapper);
        verifyNoInteractions(appointmentRepository);
    }
}
