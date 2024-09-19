package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class PetListController {

    private List<Pet> pets = new CopyOnWriteArrayList<>(getStaticPets());

    @PostMapping("/deletePet")
    public String deletePet(@RequestParam("petId") int petId) {
        // Remove the pet with the given petId
        pets.removeIf(pet -> pet.getId() == petId);
        return "redirect:/petList";  // Redirect back to the updated pet list
    }

    @GetMapping("/petList")
    public String displayPets(Model model) {
        model.addAttribute("pets", pets);
        return "petList";
    }

    // Create static pets with unique petId but shared petOwnerID
    private List<Pet> getStaticPets() {
        List<Pet> pets = new CopyOnWriteArrayList<>();
        int petOwnerID = 101;  // Use a constant petOwnerID for all pets

        pets.add(new Pet("Johnny", LocalDate.of(2020, 1, 5), "Dog", "Chihuahua", "Male", 3.0f, petOwnerID));
        pets.add(new Pet("Donny", LocalDate.of(2019, 4, 20), "Cat", "Persian", "Male", 5.5f, petOwnerID));
        pets.add(new Pet("Billy", LocalDate.of(2021, 7, 11), "Bird", "Parrot", "Female", 0.8f, petOwnerID));
        pets.add(new Pet("Snowy", LocalDate.of(2018, 3, 15), "Rabbit", "Lionhead", "Female", 2.1f, petOwnerID));
        pets.add(new Pet("Chomper", LocalDate.of(2021, 6, 18), "Hamster", "Chinese", "Male", 0.1f, petOwnerID));
        pets.add(new Pet("Nemo", LocalDate.of(2022, 9, 1), "Fish", "Guppy", "Female", 0.05f, petOwnerID));
        pets.add(new Pet("Autumn", LocalDate.of(2017, 11, 25), "Horse", "Mustang", "Male", 500.0f, petOwnerID));
        pets.add(new Pet("Raphael", LocalDate.of(2015, 5, 30), "Turtle", "Box", "Male", 1.8f, petOwnerID));
        pets.add(new Pet("Bonnie", LocalDate.of(2022, 2, 9), "Bird", "Hamburgh", "Female", 0.9f, petOwnerID));

        // Assign unique IDs to pets (you could use IDs starting from 1 or from a higher value if needed)
        for (int i = 0; i < pets.size(); i++) {
            pets.get(i).setId(i + 1);  // Unique petId starting from 1
        }

        return pets;
    }
}
