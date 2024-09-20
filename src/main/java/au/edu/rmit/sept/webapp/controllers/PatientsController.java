package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.AppointmentService;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collection;

@Controller
public class PatientsController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PetService petService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patients")
    public String getPatients(@RequestParam("vetId") int vetId, Model model) {
        // Get all the appointments of the vet
        Collection<Appointment> appointments = appointmentService.getAppointmentByVetID(vetId);

        // For every appointment, get the petID 
        List<Integer> petIDs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            petIDs.add(appointment.getPetID()); 
        }

        // Remove duplicates
        petIDs = new ArrayList<>(new HashSet<>(petIDs));

        // Get the pets
        List<Pet> pets = new ArrayList<>();
        for (int petID : petIDs) {
            pets.add(petService.getPetByPetID(petID));
        }

        // Get the pet owner of each pet
        for (Pet pet : pets) {
            pet.setPetOwner(petOwnerService.getPetOwnerByPetOwnerID(pet.getPetOwnerID()));
        }

        // Get the user of each pet owner
        for (Pet pet : pets) {
            pet.getPetOwner().setUser(userService.getUserByUserID(pet.getPetOwner().getUserID()));
        }

        // Get current week start date for weekly schedule page from navbar
        LocalDate startDate = LocalDate.now()
                .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));

        // Return appointments that contain the pet object, the vet ID, and the week start date for navigating to the weekly schedule page
        model.addAttribute("pets", pets);
        model.addAttribute("vetId", vetId);
        model.addAttribute("weekStart", startDate);

        return "patients";
    }
}
