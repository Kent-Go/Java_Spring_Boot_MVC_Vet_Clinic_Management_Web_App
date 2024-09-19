package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleAppointmentSelectDateTimeController {

    @GetMapping("/appointment/new/select_date_time")
    public String vetSchedule(@RequestParam("vetId") int vetId, @RequestParam("appointmentTypeId") int appointmentTypeId, Model model) {
        // You can add any necessary attributes here if needed
        return "appointmentSelectDateTime";  // Return the name of the Thymeleaf template (newAppointment.html)
    }
}
