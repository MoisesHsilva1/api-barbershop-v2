package com.moisas.barbershop.modules.appointment.repository;

import com.moisas.barbershop.modules.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {
}
