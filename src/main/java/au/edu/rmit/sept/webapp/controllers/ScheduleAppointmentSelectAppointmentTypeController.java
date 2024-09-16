package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.services.AppointmentTypeService;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScheduleAppointmentSelectAppointmentTypeController {

    @Autowired
    private AppointmentTypeService appointmentTypeService;

    @GetMapping("/appointment/new/select_appointment_type")
    public String showAppointmentTypes(Model model) {
        // List of available appointment types using a Map to represent each type
        Collection<AppointmentType> appointmentTypes = appointmentTypeService.getAllAppointmentType();
       
        // Add the appointment types to the model
        model.addAttribute("appointmentTypes", appointmentTypes);

        // Return the Thymeleaf view (appointmentSelectService.html)
        return "appointmentSelectAppointmentType";
    }
}
