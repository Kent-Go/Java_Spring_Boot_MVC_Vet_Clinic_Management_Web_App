package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Date;

@Controller
public class VetDashboardController {

    @GetMapping("/vetDashboard")
    public String showVetDashboard(Model model) {
        model.addAttribute("today", new Date());
        return "vetDashboard"; // Corresponds to vetDashboard.html
    }
}
