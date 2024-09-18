package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ScheduleAppointmentConfirmationController {

    @Autowired
    private AppointmentTypeService appointmentTypeService;

    @Autowired
    private PetService petService;

    @Autowired
    private VetService vetService;

    @Autowired
    private UserService userService;

    @GetMapping("appointment/new/confirmation")
    public String confirmAppointment(
        @RequestParam("selectedAppointmentTypeId") int selectedAppointmentTypeId,
        @RequestParam("selectedPetId") int selectedPetId,
        @RequestParam("selectedVetId") int selectedVetId,
        @RequestParam("selectedAppointmentDate") LocalDate selectedAppointmentDate,
        @RequestParam("selectedAppointmentTime") LocalTime selectedAppointmentTime,
        @RequestParam("selectedAppointmentTypeDuration") int selectedAppointmentTypeDuration,
        Model model) {
        
        // get appointment type name
        String appointmentTypeName = appointmentTypeService.getAppointmentTypeByAppointmentTypeID(selectedAppointmentTypeId).getName();

        // get pet info
        Pet pet = petService.getPetByPetID(selectedPetId);
        String petInfo = pet.getName() + " (" + pet.getGender() + " - " + pet.getBreed() + ")";

        // convert date into d MMMM yyyy
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String appointmentDate = selectedAppointmentDate.format(dateFormatter);

        // convert time into 12 hour format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        String appointmentTime = selectedAppointmentTime.format(timeFormatter);

        // get vet title and name
        Vet vet = vetService.getVetByVetID(selectedVetId);
        User user = userService.getUserByUserID(vet.getUserID());
        String vetTitleName = vet.getTitle() + " " + user.getFirstName() + " " + user.getLastName();

        model.addAttribute("appointmentTypeName", appointmentTypeName);
        model.addAttribute("petInfo", petInfo);
        model.addAttribute("appointmentDate", appointmentDate);
        model.addAttribute("appointmentTime", appointmentTime);
        model.addAttribute("vetTitleName", vetTitleName);

        return "appointmentConfirmation"; // Return the view name
    }
}
