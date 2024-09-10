package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.QualificationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class VeterinarianRegisterController {

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

            @RequestParam("qualification_name") String name,
            @RequestParam("university") String university,
            @RequestParam("countryQualification") String country,
            @RequestParam("yearAwarded") String year,

            Model model)
        {
        // Handle the form submission, save the veterinarian details

        // Create user entitiy
        User user = new User( firstName, lastName, LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE), gender, phoneNumber, email, password);
        userService.createUser(user);

        // Get the user_id by email
        int user_id = userService.getUserByEmail(email).getId();

        // Create address entity
        Address address = new Address(street, suburb, state, postcode, user_id);
        addressService.createAddress(address);

        // Create vet entity
        Vet vet = new Vet( title, languagesSpoken, selfDescription, user_id);
        vetService.createVet(vet);

        // Get the vet_id by user_id
        int vet_id = vetService.getVetByUserID(user_id).getId();

        // Create qualification entity
        Qualification qualification = new Qualification( name, university, country, Integer.parseInt(year), vet_id);
        qualificationService.createQualification(qualification);

        return "redirect:/profile"; // Redirect to the same page or to a success page
    }
}
