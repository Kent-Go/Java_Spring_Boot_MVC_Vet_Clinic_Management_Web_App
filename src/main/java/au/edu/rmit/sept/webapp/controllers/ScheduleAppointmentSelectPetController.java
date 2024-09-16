package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.services.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class ScheduleAppointmentSelectPetController {

    @Autowired
    private PetService petService;

    @GetMapping("/appointment/new/select_pet")
    public String displayPet(@RequestParam("petOwnerId") int petOwnerId, Model model) {
        Collection<Pet> pets = petService.getPetsByPetOwnerID(petOwnerId);
        model.addAttribute("pets", pets);
        return "appointmentSelectPet";  // This should match your Thymeleaf template name
    }

    @PostMapping("/appointment/create")
    public String createAppointment(@RequestParam("petId") int petId, Model model) {
        // Logic to handle creating the appointment with the selected pet
        Pet selectedPet = getPets().stream().filter(pet -> pet.getId() == petId).findFirst().orElse(null);
        if (selectedPet != null) {
            model.addAttribute("selectedPetName", selectedPet.getName());
        }
        return "appointmentConfirmation";  // This should match your confirmation view template
    }
}
