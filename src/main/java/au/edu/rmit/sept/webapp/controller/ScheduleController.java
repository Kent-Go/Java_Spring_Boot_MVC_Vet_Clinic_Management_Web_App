package au.edu.rmit.sept.webapp.controller;

import java.util.Locale;
import java.util.Collection;
import java.time.format.DateTimeFormatter;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.services.AppointmentService;

@Controller
public class ScheduleController {
    @Autowired
    private AppointmentService appointmentService;

    // Get the appointments of the specific vet when the page is loaded
    @GetMapping("/schedule")
    public String showSchedulePage(@RequestParam("vetId") int vetId, Model model) {
        Collection<Appointment> appointments = appointmentService.getAppointmentByVetID(vetId);

        // Format the day of the week based on the date
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E", Locale.ENGLISH);

        // Store the day of the week in each appointment
        for (Appointment appointment : appointments) {
            // Assuming appointment.getDate() returns a LocalDate
            String dayOfWeek = appointment.getDate().format(dayFormatter);
            appointment.setDayOfWeek(dayOfWeek); // Store this in the Appointment class
        }
        model.addAttribute("appointments", appointments);
        return "schedule"; // Return the schedule.html template
    }
}
