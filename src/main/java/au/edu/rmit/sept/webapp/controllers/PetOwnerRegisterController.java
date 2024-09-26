package au.edu.rmit.sept.webapp.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.UserService;

@Controller
@SessionAttributes({ "user", "address", "petOwner" })
public class PetOwnerRegisterController {
    @Autowired
    private UserService userService;

    // Show the pet owner registration page with the option to delete the user if it
    // exists in session already (e.g. if the user wants to start over with
    // registration)
    @GetMapping("/petOwnerRegister")
    public String showPetOwnerRegister(
            @RequestParam(value = "deleteUser", required = false) Boolean deleteUser,
            Model model) {

        if (deleteUser != null && deleteUser) {
            // Retrieve user from session
            User user = (User) model.getAttribute("user");

            if (user != null) {
                // Delete the user
                userService.deleteUserByUserID(user.getId());

                // Remove the user from session
                model.asMap().remove("user");
            }
        }

        return "petOwnerRegister"; // Corresponds to petOwnerRegister.html
    }

    @PostMapping("/petOwnerRegister")
    public String registerPetOwner(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dob") LocalDate dob,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phone,
            @RequestParam("streetAddress") String street,
            @RequestParam("suburb") String suburb,
            @RequestParam("state") String state,
            @RequestParam("postcode") String postcode,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        // Create a new user
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(dob);
        user.setGender(gender);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        user.setPassword(password);

        // Save the user to get the ID
        userService.createUser(user);

        // Retrieve the saved user's id
        User savedUser = userService.getUserByEmail(email);

        // Create a new address
        Address address = new Address();
        address.setStreet(street);
        address.setSuburb(suburb);
        address.setState(state);
        address.setPostcode(postcode);
        address.setUserID(savedUser.getId()); // Set the user ID for the address to the user ID of the new user created

        // Create a new pet owner
        PetOwner petOwner = new PetOwner();
        petOwner.setUserID(savedUser.getId()); // Set the user ID for the pet owner to the user ID of the new user
                                               // created

        // Store user, address, and petOwner in session
        model.addAttribute("user", savedUser);
        model.addAttribute("address", address);
        model.addAttribute("petOwner", petOwner);

        // Redirect to the pet registration page
        return "redirect:/petRegister"; // Redirect to the pet registration page
    }
}
