package com.moisas.barbershop.unit.appointment.controller;

import com.moisas.barbershop.modules.appointment.controller.AppointmentController;
import com.moisas.barbershop.modules.appointment.dto.AppointmentDTO;
import com.moisas.barbershop.modules.appointment.dto.CreateAppointmentDTO;
import com.moisas.barbershop.modules.appointment.service.CreateAppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private CreateAppointmentService createAppointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @Test
    void shouldReturnCreatedWhenAppointmentIsSuccessfullyCreated() {
        CreateAppointmentDTO createDto = new CreateAppointmentDTO();
        AppointmentDTO expectedDto = new AppointmentDTO();
        
        given(createAppointmentService.execute(createDto)).willReturn(expectedDto);

        ResponseEntity<AppointmentDTO> response = appointmentController.create(createDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull().isEqualTo(expectedDto);
    }

    @Test
    void shouldReturnNotFoundWhenAppointmentCreationReturnsNull() {
        CreateAppointmentDTO createDto = new CreateAppointmentDTO();
        
        given(createAppointmentService.execute(createDto)).willReturn(null);

        ResponseEntity<AppointmentDTO> response = appointmentController.create(createDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
