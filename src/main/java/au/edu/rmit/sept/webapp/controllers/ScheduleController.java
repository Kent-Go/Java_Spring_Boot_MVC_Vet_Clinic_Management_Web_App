package au.edu.rmit.sept.webapp.controllers;

import java.util.Locale;
import java.util.Collection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

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
    public String showSchedulePage(@RequestParam("vetId") int vetId,
            @RequestParam(value = "weekStart", required = false) String weekStart,
            Model model) {
        // Determine the week start date
        LocalDate startDate = (weekStart != null) ? LocalDate.parse(weekStart)
                : LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate endDate = startDate.plusDays(6);

        // Fetch appointments for the week
        Collection<Appointment> appointments = appointmentService.getAppointmentsByVetAndWeek(vetId, startDate,
                endDate);

        // Format the startDate to a string in YYYY-MM-DD format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedWeekStart = startDate.format(formatter);

        // Format the day of the week based on the date
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E", Locale.ENGLISH);

        // Store the day of the week in each appointment
        for (Appointment appointment : appointments) {
            // Assuming appointment.getDate() returns a LocalDate
            String dayOfWeek = appointment.getDate().format(dayFormatter);
            appointment.setDayOfWeek(dayOfWeek); // Store this in the Appointment class
        }

        // Add attributes to the model
        model.addAttribute("appointments", appointments);
        model.addAttribute("weekStart", formattedWeekStart);
        model.addAttribute("vetId", vetId);

        return "schedule"; // Return the schedule.html template
    }
}
