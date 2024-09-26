package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.models.User;

import au.edu.rmit.sept.webapp.services.VetAppointmentTypeOfferedService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;

import java.util.ArrayList;

import java.util.Collection;

@Controller
public class ScheduleAppointmentSelectVeterinarianController {

    @Autowired
    private VetAppointmentTypeOfferedService vetAppointmentTypeOfferedService;

    @Autowired
    private VetService vetService;

    @Autowired
    private UserService userService;

    @GetMapping("/appointment/new/select_veterinarian")
    public String showVet(
        @RequestParam("appointmentTypeId") int appointmentTypeId,
        @RequestParam("clinicId") int clinicId,
        Model model) {

        Collection<Vet> vetsInClinic = vetService.getVetsByClinicID(clinicId);

        Collection<Vet> vets = new ArrayList<>();

        // iterate each vet from clinic
        for (Vet vetInClinic : vetsInClinic) {
            // check if vet offer the appointment type
            if (vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByVetIDAndAppointmentTypeID(vetInClinic.getId(), appointmentTypeId)!= null) {
                // get user entity for the vet
                User user = userService.getUserByUserID(vetInClinic.getUserID());
                vetInClinic.setUser(user);

                // add vet into vets collection
                vets.add(vetInClinic);
            }   
        }

        model.addAttribute("vets", vets);
        return "appointmentSelectVeterinarian";  // This should match your Thymeleaf template name for vet list
    }
}
