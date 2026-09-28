package com.ClinicQ.Booking.Service;



import com.ClinicQ.Booking.Entity.Doctor;
import com.ClinicQ.Booking.Entity.Slot;
import com.ClinicQ.Booking.Repository.DoctorRepository;
import com.ClinicQ.Booking.Repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;

    public SlotService(SlotRepository slotRepository,
                       DoctorRepository doctorRepository) {

        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
    }

    public Slot createSlot(Long doctorId, Slot slot) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        slot.setDoctor(doctor);
        slot.setAvailable(true);

        return slotRepository.save(slot);
    }

    public List<Slot> getDoctorSlots(Long doctorId) {
        return slotRepository.findByDoctorDoctorId(doctorId);
    }

    public List<Slot> getDoctorSlotsByDate(Long doctorId, LocalDate date) {
        return slotRepository.findByDoctorDoctorIdAndDate(doctorId, date);
    }

    public void deleteSlot(Long id) {

        if (!slotRepository.existsById(id)) {
            throw new RuntimeException("Slot not found");
        }

        slotRepository.deleteById(id);
    }
}
