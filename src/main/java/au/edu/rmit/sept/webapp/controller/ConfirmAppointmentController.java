package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmAppointmentController {

    @GetMapping("/appointment/confirm")
    public String confirmAppointment(
            @RequestParam("petName") String petName,
            @RequestParam("appointmentTypeName") String appointmentTypeName,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("vetName") String vetName,
            Model model) {

        // Add the selected values to the model
        model.addAttribute("petName", petName);
        model.addAttribute("appointmentTypeName", appointmentTypeName);
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("vetName", vetName);

        return "confirmAppointment"; // Return the view name
    }
}
