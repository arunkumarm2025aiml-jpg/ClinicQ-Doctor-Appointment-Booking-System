package com.ClinicQ.Booking.Repository;


import com.ClinicQ.Booking.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientPatientId(Long patientId);

    List<Appointment> findBySlotDoctorDoctorId(Long doctorId);

    List<Appointment> findBySlotDoctorDoctorIdAndSlotDate(
            Long doctorId,
            LocalDate date);
}