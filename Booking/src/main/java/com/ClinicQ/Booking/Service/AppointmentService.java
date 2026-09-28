package com.ClinicQ.Booking.Service;



import com.ClinicQ.Booking.Entity.Appointment;
import com.ClinicQ.Booking.Entity.Patient;
import com.ClinicQ.Booking.Entity.Slot;
import com.ClinicQ.Booking.Repository.AppointmentRepository;
import com.ClinicQ.Booking.Repository.PatientRepository;
import com.ClinicQ.Booking.Repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final SlotRepository slotRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            SlotRepository slotRepository,
            PatientRepository patientRepository) {

        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment bookAppointment(Long slotId, Long patientId) {

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (!slot.isAvailable()) {
            throw new RuntimeException("Slot is already booked");
        }

        Appointment appointment = new Appointment();

        appointment.setSlot(slot);
        appointment.setPatient(patient);
        appointment.setStatus("BOOKED");
        appointment.setBookingDate(LocalDateTime.now());

        slot.setAvailable(false);

        slotRepository.save(slot);

        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("CANCELLED");

        Slot slot = appointment.getSlot();

        slot.setAvailable(true);

        slotRepository.save(slot);

        appointmentRepository.save(appointment);
    }

    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findBySlotDoctorDoctorId(doctorId);
    }

    public List<Appointment> getTodayAppointments(Long doctorId) {

        return appointmentRepository.findBySlotDoctorDoctorIdAndSlotDate(
                doctorId,
                LocalDate.now()
        );
    }

    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientPatientId(patientId);
    }
}