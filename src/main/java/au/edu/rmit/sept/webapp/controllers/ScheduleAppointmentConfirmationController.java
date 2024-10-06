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
import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.ClinicAppointmentTypePriceService;
import au.edu.rmit.sept.webapp.services.ReminderScheduler;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private ClinicAppointmentTypePriceService clinicAppointmentTypePriceService;

    @Autowired
    private ReminderScheduler reminderScheduler;

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

        // get clinic name
        String clinicName = clinicService.getClinicByClinicID(vet.getClinicID()).getName();

        // get clinic appointment type price
        double price = clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(vet.getClinicID(), selectedAppointmentTypeId).getPrice();

        model.addAttribute("clinicName", clinicName);
        model.addAttribute("appointmentTypeName", appointmentTypeName);
        model.addAttribute("price", price);
        model.addAttribute("petInfo", petInfo);
        model.addAttribute("appointmentDate", appointmentDate);
        model.addAttribute("appointmentTime", appointmentTime);
        model.addAttribute("vetTitleName", vetTitleName);

        return "appointmentConfirmation"; // Return the view name
    }

    @GetMapping("appointment/reschedule/confirmation")
    public String displayRescheduleAppointment(
        @RequestParam("appointmentId") int appointmentId,
        @RequestParam("selectedAppointmentDate") LocalDate selectedAppointmentDate,
        @RequestParam("selectedAppointmentTime") LocalTime selectedAppointmentTime,
        @RequestParam("selectedAppointmentTypeDuration") int selectedAppointmentTypeDuration,
        Model model) {

        // get Appointment object by appointment id
        Appointment appointment = appointmentService.getAppointmentByAppointmentID(appointmentId);
        // get Vet object
        Vet vet = vetService.getVetByVetID(appointment.getVetID());
        // get user object
        User user = userService.getUserByUserID(vet.getUserID());
        // get Pet object
        Pet pet = petService.getPetByPetID(appointment.getPetID());

        // get Clinic name
        String clinicName = clinicService.getClinicByClinicID(vet.getClinicID()).getName();
        
        // get appointment type name
        String appointmentTypeName = appointmentTypeService.getAppointmentTypeByAppointmentTypeID(appointment.getAppointmentTypeID()).getName();
        
        // get clinic appointment type price
        double price = clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(vet.getClinicID(), appointment.getAppointmentTypeID()).getPrice();

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

        // get pet info
        String petInfo = pet.getName() + " (" + pet.getGender() + " - " + pet.getBreed() + ")";

        // get vet title and name
        String vetTitleName = vet.getTitle() + " " + user.getFirstName() + " " + user.getLastName();


        model.addAttribute("clinicName", clinicName);
        model.addAttribute("appointmentTypeName", appointmentTypeName);
        model.addAttribute("price", price);
        model.addAttribute("petInfo", petInfo);
        model.addAttribute("appointmentDate", appointmentDate);
        model.addAttribute("appointmentTime", appointmentTime);
        model.addAttribute("vetTitleName", vetTitleName);

        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("selectedAppointmentDate", selectedAppointmentDate);
        model.addAttribute("selectedAppointmentTime", selectedAppointmentTime);
        model.addAttribute("selectedAppointmentTypeDuration", selectedAppointmentTypeDuration);

        return "rescheduleAppointmentConfirmation";
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

        // create new appointment entity
        Appointment appointment = new Appointment(selectedAppointmentDate, selectedAppointmentTime, selectedAppointmentEndTime, selectedVetId, selectedPetId, selectedAppointmentTypeId);
        // save into database
        appointmentService.createAppointment(appointment);

        return "redirect:/petOwnerWelcome?userId=" + userId + "&petOwnerId=" + petOwnerId;
    }

    @PostMapping("appointment/reschedule/confirmation")
    public String confirmRescheduleAppointment(
            @RequestParam("appointmentId") int appointmentId,
            @RequestParam("selectedAppointmentDate") LocalDate selectedAppointmentDate,
            @RequestParam("selectedAppointmentTime") LocalTime selectedAppointmentTime,
            @RequestParam("selectedAppointmentTypeDuration") int selectedAppointmentTypeDuration,
            @RequestParam("userId") int userId,
            @RequestParam("petOwnerId") int petOwnerId,
            Model model) {

        // Retrieve appointment by appointment id
        Appointment appointment = appointmentService.getAppointmentByAppointmentID(appointmentId);

        // Cancel the previously scheduled email
        boolean isCancelled = reminderScheduler.cancelScheduledEmail(appointmentId);
        if (isCancelled) {
            System.out.println("Previous email reminder canceled for appointment ID: " + appointmentId);
        }

        // Get appointment end time by adding selectedAppointmentTypeDuration
        LocalTime selectedAppointmentEndTime = selectedAppointmentTime.plusMinutes(selectedAppointmentTypeDuration);

        // Update the appointment to new date, start time, and end time
        appointment.setDate(selectedAppointmentDate);
        appointment.setStartTime(selectedAppointmentTime);
        appointment.setEndTime(selectedAppointmentEndTime);

        // Save the updated appointment in database
        appointmentService.createAppointment(appointment);

        // Schedule email notification 24 hours before the appointment
        scheduleAppointmentReminder(appointment, userId, petOwnerId);

        return "redirect:/petOwnerWelcome?userId=" + userId + "&petOwnerId=" + petOwnerId;
    }


    private void scheduleAppointmentReminder(Appointment appointment, int userId, int petOwnerId) {
        // Get the appointment date and time
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getDate(), appointment.getStartTime());

        // Calculate 24 hours before the appointment
        LocalDateTime reminderDateTime = appointmentDateTime.minusHours(24);

        // Calculate delay in seconds
        LocalDateTime now = LocalDateTime.now();
        long delayInSeconds = reminderDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() - now.atZone(ZoneId.systemDefault()).toEpochSecond();

        // Get Vet object by vetID
        Vet vet = vetService.getVetByVetID(appointment.getVetID());

        // Get User object associated with the vet to retrieve the vet's name
        User vetUser = userService.getUserByUserID(vet.getUserID());
        String vetName = vet.getTitle() + " " + vetUser.getFirstName() + " " + vetUser.getLastName();

        // Get Clinic name
        String clinicName = clinicService.getClinicByClinicID(vet.getClinicID()).getName();

        // Get Appointment type name
        String appointmentTypeName = appointmentTypeService.getAppointmentTypeByAppointmentTypeID(appointment.getAppointmentTypeID()).getName();

        // Get Pet's name
        Pet pet = petService.getPetByPetID(appointment.getPetID());
        String petName = pet.getName();

        // Get PetOwner's User email (the user scheduling the appointment)
        User petOwner = userService.getUserByUserID(userId);  // Assuming `userId` is the pet owner's ID
        String email = petOwner.getEmail();  // Get the pet owner's email address

        if (delayInSeconds > 0) {
            // Build the email content with appointment details
            String subject = "Appointment Reminder";
            String text = "This is a reminder for your upcoming appointment for your pet, " + petName + "."
                    + "\n\nAppointment Type: " + appointmentTypeName
                    + "\nAppointment Date: " + appointment.getDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                    + "\nAppointment Time: " + appointment.getStartTime().format(DateTimeFormatter.ofPattern("h:mm a"))
                    + "\nVeterinarian: " + vetName
                    + "\nClinic: " + clinicName;

            // Schedule the email and check if it was sent successfully
            // Replace getAppointmentID() with getId() or the correct method name
            boolean isEmailScheduled = reminderScheduler.scheduleEmail(email, subject, text, delayInSeconds, appointment.getId());

            if (isEmailScheduled) {
                System.out.println("Email successfully scheduled for 24 hours before the appointment.");
            } else {
                System.out.println("Failed to schedule the email.");
            }
        } else {
            // If the appointment is within the next 24 hours, send the email immediately
            String subject = "Appointment Reminder";
            String text = "Your appointment for your pet, " + petName + ", is in less than 24 hours."
                    + "\n\nAppointment Type: " + appointmentTypeName
                    + "\nAppointment Date: " + appointment.getDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                    + "\nAppointment Time: " + appointment.getStartTime().format(DateTimeFormatter.ofPattern("h:mm a"))
                    + "\nVeterinarian: " + vetName
                    + "\nClinic: " + clinicName;

            // Replace getAppointmentID() with getId() or the correct method name
            boolean isEmailScheduled = reminderScheduler.scheduleEmail(email, subject, text, delayInSeconds, appointment.getId());
            // Send immediately
            if (isEmailScheduled) {
                System.out.println("Email sent successfully for immediate delivery.");
            } else {
                System.out.println("Failed to send the email.");
            }
        }
    }
}
