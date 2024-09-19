package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetOwnerDashboardController {

    @GetMapping("/petOwnerDashboard")
    public String showPetOwnerWelcome(Model model) {
        return "petOwnerDashboard"; // Corresponds to petOwnerWelcome.html
    }

    @PostMapping("/petOwnerDashboard")
    public String dashboardPetOwner() {
        // Handle the form submission, save the pet owner details
        return "redirect:/petOwnerDashboard"; // Redirect to the same page or to a success page
    }

}
