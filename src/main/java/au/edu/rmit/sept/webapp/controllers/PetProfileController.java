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
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;

import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.ImmunisationHistoryService;
import au.edu.rmit.sept.webapp.services.PrescribedMedicationService;

@Controller
public class PetProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ImmunisationHistoryService immunisationHistoryService;

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

        // Associate the pet owner with the user
        pet.getPetOwner().setUser(userService.getUserByUserID(pet.getPetOwner().getUserID()));

        // Associate the user with the address
        Address address = addressService.getAddressByUserID(pet.getPetOwner().getUserID());
        address.setUser(pet.getPetOwner().getUser());

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

        // Get the list of immunisation history of the pet
        List<ImmunisationHistory> immunisationHistories = immunisationHistoryService.getImmunisationHistoryByPetID(petId);

        // Add the pet to the model
        model.addAttribute("pet", pet);
        model.addAttribute("address", address);
        model.addAttribute("immunisationHistories", immunisationHistories);
        model.addAttribute("prescribedMedications", prescribedMedications);

        return "petProfile"; // This refers to the petProfile.html page
    }
}
