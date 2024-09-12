package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppointmentTypeController {

    @GetMapping("/appointment/type")
    public String showAppointmentTypes(Model model) {
        // List of available appointment types using a Map to represent each type
        List<Map<String, Object>> appointmentTypes = Arrays.asList(
                createAppointmentType(1, "General Clinical Consultation", 30),
                createAppointmentType(2, "Physical Examination", 45),
                createAppointmentType(3, "Dental Care", 30),
                createAppointmentType(4, "Surgery", 60),
                createAppointmentType(5, "Diet and Nutrition", 40)
        );

        // Add the appointment types to the model
        model.addAttribute("appointmentTypes", appointmentTypes);

        // Return the Thymeleaf view (appointmentType.html)
        return "appointmentType";
    }

    private Map<String, Object> createAppointmentType(int id, String name, int duration) {
        Map<String, Object> type = new HashMap<>();
        type.put("id", id);
        type.put("name", name);
        type.put("duration", duration);
        return type;
    }
}
