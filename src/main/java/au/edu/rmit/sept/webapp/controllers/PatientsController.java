package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientsController {

    // Mock method to retrieve a list of pets
    private List<Pet> getPets() {
        List<Pet> pets = new ArrayList<>();

        Pet johnny = new Pet("Johnny", LocalDate.of(2018, 5, 14), "Dog", "Chihuahua", "Male", 3.5f, 1);
        Pet donny = new Pet("Donny", LocalDate.of(2017, 7, 23), "Cat", "Persian", "Male", 4.2f, 2);
        Pet billy = new Pet("Billy", LocalDate.of(2020, 10, 11), "Bird", "Parrot", "Female", 1.2f, 3);

        pets.add(johnny);
        pets.add(donny);
        pets.add(billy);

        return pets;
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<Pet> pets = getPets();
        model.addAttribute("pets", pets);
        return "patients";
    }
}
