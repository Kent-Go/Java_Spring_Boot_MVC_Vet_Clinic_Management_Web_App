package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VeterinarianRegisterController {

    @GetMapping("/veterinarianRegister")
    public String showVeterinarianRegister(Model model) {
        return "veterinarianRegister"; // Corresponds to veterinarianRegister.html
    }

    @PostMapping("/veterinarianRegister")
    public String registerVeterinarian(/* Add form parameters here */) {
        // Handle the form submission, save the veterinarian details
        return "redirect:/veterinarianRegister"; // Redirect to the same page or to a success page
    }
}
