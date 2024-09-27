package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;

@Controller
public class CancelAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PetService petService;

    @Autowired
    private PetOwnerService petOwnerService;

    @GetMapping("/appointment/cancel")
    public String cancelAppointment(
        @RequestParam("appointmentId") int appointmentId, 
        RedirectAttributes redirectAttributes) {
        int petOwnerId = petService.getPetByPetID(appointmentService.getAppointmentByAppointmentID(appointmentId).getPetID()).getPetOwnerID();
        int userId = petOwnerService.getPetOwnerByPetOwnerID(petOwnerId).getUserID();
    
        appointmentService.cancelAppointment(appointmentId);
        
        // Optionally, add a success message
        redirectAttributes.addFlashAttribute("message", "Appointment cancelled successfully.");

        // Redirect back to the pet owner welcome page page
        return "redirect:/petOwnerWelcome?userId=" + userId + "&petOwnerId=" + petOwnerId;
    }
}
