package com.ClinicQ.Booking.Repository;



import com.ClinicQ.Booking.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}