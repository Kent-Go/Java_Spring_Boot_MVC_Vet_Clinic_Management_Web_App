package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleAppointmentConfirmationController {

    @GetMapping("appointment/new/confirmation")
    public String confirmAppointment(Model model) {
        return "appointmentConfirmation"; // Return the view name
    }
}
