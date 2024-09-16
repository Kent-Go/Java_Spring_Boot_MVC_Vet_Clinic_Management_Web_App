package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.models.User;

import au.edu.rmit.sept.webapp.services.VetAppointmentTypeOfferedService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;

import java.util.Collection;

@Controller
public class ScheduleAppointmentSelectVeterinarianController {

    @Autowired
    private VetAppointmentTypeOfferedService vetAppointmentTypeOfferedService;

    @Autowired
    private VetService vetService;

    @Autowired
    private UserService userService;

    @GetMapping("/appointment/new/select_veterinarian")
    public String showVet(@RequestParam("appointmentTypeId") int appointmentTypeId, Model model) {
        Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentTypeId);

        for (VetAppointmentTypeOffered vetAppointmentTypeOffered : vetAppointmentTypeOffereds) {
            Vet vet = vetService.getVetByVetID(vetAppointmentTypeOffered.getVetID());
            User user = userService.getUserByUserID(vet.getUserID());

            // store the User into Vet entity
            vet.setUser(user);
            // store the Vet into VetAppointmentTypeOffered entity
            vetAppointmentTypeOffered.setVet(vet);
        }

        model.addAttribute("vetAppointmentTypeOffereds", vetAppointmentTypeOffereds);
        return "appointmentSelectVeterinarian";  // This should match your Thymeleaf template name for vet list
    }

    // @PostMapping("/appointment/selectVet")
    // public String selectVetForAppointment(@RequestParam("vetId") int vetId, Model model) {
    //     // Logic to handle creating the appointment with the selected veterinarian
    //     Vet selectedVet = getVets().stream().filter(vet -> vet.getId() == vetId).findFirst().orElse(null);
    //     if (selectedVet != null) {
    //         model.addAttribute("selectedVetName", selectedVet.getTitle());
    //     }
    //     return "appointmentConfirmation";  // This should match your confirmation view template
    // }
}
