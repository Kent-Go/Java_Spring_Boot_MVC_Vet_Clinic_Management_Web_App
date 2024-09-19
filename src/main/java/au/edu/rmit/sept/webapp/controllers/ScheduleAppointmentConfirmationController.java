package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AppointmentService;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

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

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("appointment/new/confirmation")
    public String displayAppointment(
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
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
        .appendPattern("h:mm ")
        .appendText(ChronoField.AMPM_OF_DAY, TextStyle.SHORT)
        .toFormatter(Locale.ENGLISH)
        .withLocale(Locale.ENGLISH);

        String appointmentTime = selectedAppointmentTime.format(timeFormatter).toUpperCase();

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

    @PostMapping("appointment/new/confirmation")
    public String confirmAppointment(
        @RequestParam("selectedAppointmentTypeId") int selectedAppointmentTypeId,
        @RequestParam("selectedPetId") int selectedPetId,
        @RequestParam("selectedVetId") int selectedVetId,
        @RequestParam("selectedAppointmentDate") LocalDate selectedAppointmentDate,
        @RequestParam("selectedAppointmentTime") LocalTime selectedAppointmentTime,
        @RequestParam("selectedAppointmentTypeDuration") int selectedAppointmentTypeDuration,
        @RequestParam("userId") int userId,
        @RequestParam("petOwnerId") int petOwnerId,
        Model model) {

        // get appointment end time by adding selectedAppointmentTypeDuration
        LocalTime selectedAppointmentEndTime = selectedAppointmentTime.plusMinutes(selectedAppointmentTypeDuration);

        Appointment appointment = new Appointment(selectedAppointmentDate, selectedAppointmentTime, selectedAppointmentEndTime, selectedVetId, selectedPetId, selectedAppointmentTypeId);
        appointmentService.createAppointment(appointment);

        return "redirect:/petOwnerWelcome?userId=" + userId + "&petOwnerId=" + petOwnerId;
    }
}
