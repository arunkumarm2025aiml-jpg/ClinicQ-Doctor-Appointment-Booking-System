package com.ClinicQ.Booking.Service;


import com.ClinicQ.Booking.Entity.Doctor;
import com.ClinicQ.Booking.Repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {

        Doctor existingDoctor = getDoctorById(id);

        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());

        return doctorRepository.save(existingDoctor);
    }

    public void deleteDoctor(Long id) {

        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found");
        }

        doctorRepository.deleteById(id);
    }
}
