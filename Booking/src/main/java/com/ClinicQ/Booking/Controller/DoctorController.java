package com.ClinicQ.Booking.Controller;



import com.ClinicQ.Booking.Entity.Doctor;
import com.ClinicQ.Booking.Service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {

        return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return "Doctor deleted successfully";
    }
}