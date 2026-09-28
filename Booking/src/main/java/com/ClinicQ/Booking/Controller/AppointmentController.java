package com.ClinicQ.Booking.Controller;



import com.ClinicQ.Booking.Entity.Appointment;
import com.ClinicQ.Booking.Service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public Appointment bookAppointment(
            @RequestParam Long slotId,
            @RequestParam Long patientId) {

        return appointmentService.bookAppointment(slotId, patientId);
    }

    @PutMapping("/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id) {

        appointmentService.cancelAppointment(id);

        return "Appointment cancelled successfully";
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getDoctorAppointments(
            @PathVariable Long doctorId) {

        return appointmentService.getDoctorAppointments(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/today")
    public List<Appointment> getTodayAppointments(
            @PathVariable Long doctorId) {

        return appointmentService.getTodayAppointments(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getPatientAppointments(
            @PathVariable Long patientId) {

        return appointmentService.getPatientAppointments(patientId);
    }
}
