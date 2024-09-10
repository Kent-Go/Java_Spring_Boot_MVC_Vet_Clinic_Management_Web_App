package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetRegisterController {

    @GetMapping("/petRegister")
    public String showPetRegister(Model model) {
        return "petRegister"; // Corresponds to petRegister.html
    }

    @PostMapping("/petRegister")
    public String registerPet() {
        // Handle the form submission, save the pet details
        return "redirect:/petRegister"; // Redirect to the same page or to a success page
    }
}
