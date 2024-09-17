package au.edu.rmit.sept.webapp.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;

import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.PrescribedMedicationService;

@Controller
public class PetProfileController {
    @Autowired
    private PetService petService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PrescribedMedicationService prescribedMedicationService;

    // Display the pet profile page
    @GetMapping("/petProfile")
    public String showPetProfile(@RequestParam("petId") int petId, Model model) {
        // Get the pet by petId and the pet owner by the petOwnerId that is associated
        // with the pet
        Pet pet = petService.getPetByPetID(petId);

        // Associate the pet with the pet owner
        pet.setPetOwner(petOwnerService.getPetOwnerByPetOwnerID(pet.getPetOwnerID()));

        // Get the appointments of the pet
        Collection<Appointment> appointments = appointmentService.getAppointmentByPetID(petId);

        // Associate the appointments with the pet
        appointments.forEach(appointment -> {
            appointment.setPet(pet);
        });

        // For each appointment, get the prescribed medications and add them to the list
        List<PrescribedMedication> prescribedMedications = new ArrayList<>();
        appointments.forEach(appointment -> {
            prescribedMedications.addAll(prescribedMedicationService.getPrescribedMedicationByAppointmentID(appointment.getId()));
        });

        // For each prescribed medication, get the medicine
        prescribedMedications.forEach(prescribedMedication -> {
            prescribedMedication.setMedicine(prescribedMedication.getMedicine());
        });

        // Add the pet to the model
        model.addAttribute("pet", pet);
        model.addAttribute("prescribedMedications", prescribedMedications);

        return "petProfile"; // This refers to the petProfile.html page
    }
}
