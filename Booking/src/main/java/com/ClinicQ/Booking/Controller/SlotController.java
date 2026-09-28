package com.ClinicQ.Booking.Controller;



import com.ClinicQ.Booking.Entity.Slot;
import com.ClinicQ.Booking.Service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/doctor/{doctorId}")
    public Slot createSlot(
            @PathVariable Long doctorId,
            @RequestBody Slot slot) {

        return slotService.createSlot(doctorId, slot);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Slot> getDoctorSlots(
            @PathVariable Long doctorId) {

        return slotService.getDoctorSlots(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public List<Slot> getDoctorSlotsByDate(
            @PathVariable Long doctorId,
            @PathVariable LocalDate date) {

        return slotService.getDoctorSlotsByDate(doctorId, date);
    }

    @DeleteMapping("/{id}")
    public String deleteSlot(@PathVariable Long id) {

        slotService.deleteSlot(id);

        return "Slot deleted successfully";
    }
}
