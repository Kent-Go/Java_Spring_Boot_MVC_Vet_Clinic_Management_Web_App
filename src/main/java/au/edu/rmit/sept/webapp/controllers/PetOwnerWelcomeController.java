package au.edu.rmit.sept.webapp.controllers;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.Appointment;

import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.ClinicAddressService;


@Controller
public class PetOwnerWelcomeController {
    @Autowired
    private PetService petService;

    @Autowired
    private VetService vetService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentTypeService appointmentTypeService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private ClinicAddressService clinicAddressService;

    @GetMapping("/petOwnerWelcome")
    public String showPetOwnerWelcome(@RequestParam("userId") Integer userId, Model model) {
        // Default or handle missing parameters as needed
        if (userId == null) {
            // Handle or provide default values
        }

        // Get user details
        model.addAttribute("user", userService.getUserByUserID(userId));

        // Get pet owner details
        PetOwner petOwner = petOwnerService.getPetOwnerByUserID(userId);
        petOwner.setUser(userService.getUserByUserID(userId));

        // Get all the pets of the pet owner
        Collection<Pet> pets = petService.getPetsByPetOwnerID(petOwner.getId());

        // For each pet, get the upcoming appointments and set the appointment type
        Collection<Appointment> upcomingAppointments = null;
        for (Pet pet : pets) {
            Collection<Appointment> petAppointments = appointmentService.getAppointmentsByPetIDAndDateAfter(pet.getId(), LocalDate.now());

            // Get the appointment type and vet based on the appointmentTypeID in each appointment
            for (Appointment appointment : petAppointments) {
                appointment.setAppointmentType(appointmentTypeService.getAppointmentTypeByAppointmentTypeID(appointment.getAppointmentTypeID()));
                appointment.setVet(vetService.getVetByVetID(appointment.getVetID()));

                // Set the vet user to appointment based on the vet's user ID
                appointment.getVet().setUser(userService.getUserByUserID(appointment.getVet().getUserID()));

                // set the clinic entity to vet
                int clinicId = appointment.getVet().getClinicID();
                appointment.getVet().setClinic(clinicService.getClinicByClinicID(clinicId));
            }

            if (upcomingAppointments == null) {
                upcomingAppointments = petAppointments;
            } else {
                upcomingAppointments.addAll(petAppointments);
            }
        }

        model.addAttribute("petOwner", petOwner);
        model.addAttribute("pets", pets);
        model.addAttribute("upcomingAppointments", upcomingAppointments);

        return "petOwnerWelcome";
    }

    @PostMapping("/petOwnerWelcome")
    public String welcomePetOwner() {
        // Handle the form submission, save the pet owner details
        return "redirect:/petOwnerWelcome"; // Redirect to the same page or to a success page
    }

}
