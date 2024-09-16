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
public class PetAppointmentController {

    @Autowired
    private PetService petService;

    // Mock service to get the list of pets
    private List<Pet> getPets() {
        List<Pet> pets = new ArrayList<>();

        // Creating pets using the default constructor and setters
        Pet johnny = new Pet();
        johnny.setName("Johnny");
        johnny.setSpecies("dog");
        johnny.setBreed("Chihuahua");
        johnny.setGender("Male");
        johnny.setWeight(3.5f);
        johnny.setBirthDate(LocalDate.of(2018, 5, 14));  // Example date
        johnny.setId(1); // Setting unique ID
        pets.add(johnny);

        Pet donny = new Pet();
        donny.setName("Donny");
        donny.setSpecies("cat");
        donny.setBreed("Persian");
        donny.setGender("Male");
        donny.setWeight(4.2f);
        donny.setBirthDate(LocalDate.of(2017, 7, 23));
        donny.setId(2);
        pets.add(donny);

        Pet billy = new Pet();
        billy.setName("Billy");
        billy.setSpecies("bird");
        billy.setBreed("Parrot");
        billy.setGender("Female");
        billy.setWeight(1.2f);
        billy.setBirthDate(LocalDate.of(2020, 10, 11));
        billy.setId(3);
        pets.add(billy);

        return pets;
    }

    @GetMapping("/appointment/new")
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
