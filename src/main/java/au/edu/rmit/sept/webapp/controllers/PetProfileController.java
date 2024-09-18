package au.edu.rmit.sept.webapp.controllers;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.SurgeryHistory;
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;

import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.SurgeryHistoryService;
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
    private SurgeryHistoryService surgeryHistoryService;

    @Autowired
    private ImmunisationHistoryService immunisationHistoryService;

    @Autowired
    private PrescribedMedicationService prescribedMedicationService;

    // Display the pet profile page
    @GetMapping("/petProfile")
    public String showPetProfile(@RequestParam("petId") int petId,
            @RequestParam(value = "editMode", defaultValue = "false") boolean editMode, Model model) {
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
            prescribedMedications
                    .addAll(prescribedMedicationService.getPrescribedMedicationByAppointmentID(appointment.getId()));
        });

        // For each prescribed medication, get the medicine
        prescribedMedications.forEach(prescribedMedication -> {
            prescribedMedication.setMedicine(prescribedMedication.getMedicine());
        });

        // Get the list of immunisation history of the pet
        List<ImmunisationHistory> immunisationHistories = immunisationHistoryService
                .getImmunisationHistoryByPetID(petId);

        // Get the list of surgery history of the pet
        List<SurgeryHistory> surgeryHistories = surgeryHistoryService.getSurgeryHistoryByPetID(petId);

        // Add the pet to the model
        model.addAttribute("pet", pet);
        model.addAttribute("address", address);
        model.addAttribute("surgeryHistories", surgeryHistories);
        model.addAttribute("immunisationHistories", immunisationHistories);
        model.addAttribute("prescribedMedications", prescribedMedications);
        model.addAttribute("editMode", editMode); // Add the editMode flag to the model

        return "petProfile"; // This refers to the petProfile.html page
    }

    @PostMapping("/updatePetProfile")
    public String updatePetProfile(@RequestParam("petId") int petId,
            @RequestParam("name") String name,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("species") String species,
            @RequestParam("breed") String breed,
            @RequestParam("gender") String gender,
            @RequestParam("weight") float weight) {

        // Fetch the pet by ID and update the details
        Pet pet = petService.getPetByPetID(petId);

        String regex = "^[a-zA-Z0-9 ]*$";

        // Validate the input before updating the pet profile
        if (name == null || name.isEmpty() || birthDate == null || birthDate.isEmpty() || species == null
                || species.isEmpty() || breed == null || breed.isEmpty() || gender == null || gender.isEmpty()
                || weight <= 0 || !name.matches(regex) || !species.matches(regex) || !breed.matches(regex)) {
            return "redirect:/petProfile?petId=" + petId + "&editMode=true";
        }

        pet.setName(name);
        pet.setBirthDate(LocalDate.parse(birthDate));
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setGender(gender);
        pet.setWeight(weight);

        // Save the updated pet profile
        petService.updatePet(pet);

        // Redirect to view mode after saving
        return "redirect:/petProfile?petId=" + petId;
    }
}
