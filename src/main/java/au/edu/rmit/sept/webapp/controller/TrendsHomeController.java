package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TrendsHomeController {

    @GetMapping("/trendsHome")
    public String showTrendsHome(Model model) {
        return "trendsHome"; // Corresponds to petOwnerWelcome.html
    }

    @PostMapping("/trendsHome")
    public String homeTrends() {
        // Handle the form submission, save the pet owner details
        return "redirect:/trendsHome"; // Redirect to the same page or to a success page
    }
}
