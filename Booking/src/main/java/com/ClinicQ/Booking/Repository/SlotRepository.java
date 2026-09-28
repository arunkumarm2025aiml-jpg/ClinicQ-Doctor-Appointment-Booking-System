package com.ClinicQ.Booking.Repository;


import com.ClinicQ.Booking.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findByDoctorDoctorId(Long doctorId);

    List<Slot> findByDoctorDoctorIdAndDate(
            Long doctorId,
            LocalDate date);

    List<Slot> findByDate(LocalDate date);
}