package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetOwnerRegisterController {

    @GetMapping("/petOwnerRegister")
    public String showPetOwnerRegister(Model model) {
        return "petOwnerRegister"; // Corresponds to petOwnerRegister.html
    }

    @PostMapping("/petOwnerRegister")
    public String registerPetOwner() {
        // Handle the form submission, save the pet owner details
        return "redirect:/petOwnerRegister"; // Redirect to the same page or to a success page
    }
}
