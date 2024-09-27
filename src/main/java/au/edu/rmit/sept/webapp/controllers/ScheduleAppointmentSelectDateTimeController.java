package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleAppointmentSelectDateTimeController {

    @GetMapping("/appointment/new/select_date_time")
    public String displayScheduleAppointmentDateTime(@RequestParam("vetId") int vetId, @RequestParam("appointmentTypeId") int appointmentTypeId, Model model) {
        // You can add any necessary attributes here if needed
        return "appointmentSelectDateTime";  // Return the name of the Thymeleaf template (appointmentSelectDateTime.html)
    }

    @GetMapping("/appointment/reschedule/select_date_time")
    public String displayRescheduleAppointmentDateTime(
        @RequestParam("appointmentId") int appointmentId,
        @RequestParam("vetId") int vetId, 
        @RequestParam("appointmentTypeDuration") int appointmentTypeDuration,
        
        Model model) {
        // You can add any necessary attributes here if needed
        return "rescheduleAppointmentSelectDateTime";  // Return the name of the Thymeleaf template (rescheduleAppointmentSelectDateTime.html)
    }
}
