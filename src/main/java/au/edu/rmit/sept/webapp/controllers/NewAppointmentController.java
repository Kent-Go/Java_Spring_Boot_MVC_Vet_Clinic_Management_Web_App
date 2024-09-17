package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewAppointmentController {

    @GetMapping("/appointment/time")
    public String vetSchedule(Model model) {
        // You can add any necessary attributes here if needed
        return "newAppointment";  // Return the name of the Thymeleaf template (newAppointment.html)
    }
}
