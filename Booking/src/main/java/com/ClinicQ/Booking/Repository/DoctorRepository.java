package com.ClinicQ.Booking.Repository;


import com.ClinicQ.Booking.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}