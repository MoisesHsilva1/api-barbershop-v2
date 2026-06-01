package com.moisas.barbershop.modules.appointment.controller;

import com.moisas.barbershop.modules.appointment.dto.AppointmentDTO;
import com.moisas.barbershop.modules.appointment.dto.CreateAppointmentDTO;
import com.moisas.barbershop.modules.appointment.service.CreateAppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final CreateAppointmentService createAppointmentService;

    @PostMapping()
    public ResponseEntity<AppointmentDTO> create(@RequestBody @Valid CreateAppointmentDTO appointment) {
        AppointmentDTO result = createAppointmentService.execute(appointment);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
