package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetOwnerWelcomeController {

    @GetMapping("/petOwnerWelcome")
    public String showPetOwnerWelcome(Model model) {
        return "petOwnerWelcome"; // Corresponds to petOwnerWelcome.html
    }

    @PostMapping("/petOwnerWelcome")
    public String welcomePetOwner() {
        // Handle the form submission, save the pet owner details
        return "redirect:/petOwnerWelcome"; // Redirect to the same page or to a success page
    }

}
