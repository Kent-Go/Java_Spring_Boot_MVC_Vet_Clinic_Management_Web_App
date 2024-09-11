package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/schedule")
    public String showRegistrationPage(Model model) {
        return "schedule"; // Corresponds to schedule.html
    }
}
