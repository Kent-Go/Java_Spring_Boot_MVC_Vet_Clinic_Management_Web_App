package au.edu.rmit.sept.webapp.controllers;

import java.util.Locale;
import java.time.LocalDate;
import java.util.Collection;
import java.time.temporal.WeekFields;
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
    public String showSchedulePage(@RequestParam("vetId") int vetId,
            @RequestParam(value = "weekStart", required = false) String weekStart,
            Model model) {
        // Determine the start date of the week, default to current week's Monday if not
        // provided
        LocalDate startDate = (weekStart != null) ? LocalDate.parse(weekStart)
                : LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate endDate = startDate.plusDays(6);

        // Fetch appointments for the week
        Collection<Appointment> appointments = appointmentService.getAppointmentsByVetAndWeek(vetId, startDate,
                endDate);

        // Calculate the day of the week for each appointment and set it to appointment
        appointments.forEach(appointment -> {
            String dayOfWeek = appointment.getDate().getDayOfWeek()
                    .getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH);
            appointment.setDayOfWeek(dayOfWeek);
        });

        // Format the week start and end dates for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedWeekStart = startDate.format(formatter);
        String formattedWeekEnd = endDate.format(formatter);

        // Add attributes to the model
        model.addAttribute("appointments", appointments);
        model.addAttribute("weekStart", formattedWeekStart);
        model.addAttribute("weekEnd", formattedWeekEnd);
        model.addAttribute("vetId", vetId);

        return "schedule"; // Return the schedule.html template
    }
}
