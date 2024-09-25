package au.edu.rmit.sept.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.QualificationService;
import au.edu.rmit.sept.webapp.services.ClinicService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Controller
public class VeterinarianRegisterController {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private VetService vetService;

    @Autowired
    private QualificationService qualificationService;

    @GetMapping("/veterinarianRegister")
    public String showVeterinarianRegister(Model model) {
        Collection<Clinic> clinics = clinicService.getAllClinics();

        // Add attributes to the model
        model.addAttribute("clinics", clinics);

        return "veterinarianRegister"; // Corresponds to veterinarianRegister.html
    }

    @PostMapping("/veterinarianRegister")
    public String registerVeterinarian(
            @RequestParam("firstname") String firstName,
            @RequestParam("lastname") String lastName,
            @RequestParam("dob") String birthDate,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("password") String password,

            @RequestParam("streetAddress") String street,
            @RequestParam("suburb") String suburb,
            @RequestParam("state") String state,
            @RequestParam("postcode") String postcode,

            @RequestParam("title") String title,
            @RequestParam("language") String languagesSpoken,
            @RequestParam("description") String selfDescription,
            @RequestParam("clinic_id") int clinicID,

            @RequestParam("qualification_name") String name,
            @RequestParam("university") String university,
            @RequestParam("countryQualification") String country,
            @RequestParam("yearAwarded") String year,

            Model model) {

        // Validate required fields
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("First name and last name are required");
        }

        // Validate year awarded is not in the future
        int awardedYear = Integer.parseInt(year);
        int currentYear = LocalDate.now().getYear();
        if (awardedYear > currentYear) {
            throw new IllegalArgumentException("Year awarded cannot be in the future");
        } else if (awardedYear < 1900) {  // Example minimum year validation
            throw new IllegalArgumentException("Year awarded cannot be in the past beyond a certain threshold");
        }

        // Create user entity
        User user = new User(firstName, lastName, LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE), gender,
                phoneNumber, email, password);
        userService.createUser(user);

        // Get the user_id by email and handle null case
        User existingUser = userService.getUserByEmail(email);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with email " + email + " does not exist.");
        }

        // Get the user_id by email
        int userID = existingUser.getId();

        // Create address entity
        Address address = new Address(street, suburb, state, postcode, userID);
        addressService.createAddress(address);

        // Create vet entity
        Vet vet = new Vet(title, languagesSpoken, selfDescription, userID, clinicID);
        vetService.createVet(vet);

        // Get the vet_id by user_id
        int vetID = vetService.getVetByUserID(userID).getId();

        // Create qualification entity
        Qualification qualification = new Qualification(name, university, country, awardedYear, vetID);
        qualificationService.createQualification(qualification);

        return "redirect:/profile"; // Redirect to the same page or to a success page
    }

}
