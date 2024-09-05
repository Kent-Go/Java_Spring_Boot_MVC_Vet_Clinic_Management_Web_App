package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VetDashboardController {

    @GetMapping("/vetDashboard")
    public String showVetDashboard(Model model) {
        return "vetDashboard"; // Corresponds to vetDashboard.html
    }
}
