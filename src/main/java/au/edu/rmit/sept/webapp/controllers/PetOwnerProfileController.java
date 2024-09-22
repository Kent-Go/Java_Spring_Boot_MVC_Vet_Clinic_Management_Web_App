package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AddressService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class PetOwnerProfileController {

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/petOwnerProfile")
    public String showPetOwnerProfile(@RequestParam("petOwnerId") int petOwnerId, Model model) {
        // Check for invalid parameter values (e.g., negative ID)
        if (petOwnerId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid PetOwner ID");
        }

        // Only call service if the ID is valid
        PetOwner petOwner = petOwnerService.getPetOwnerByUserID(petOwnerId);

        // If PetOwner is not found, return 404
        if (petOwner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PetOwner not found");
        }

        User user = userService.getUserByUserID(petOwner.getUserID());
        Address address = addressService.getAddressByUserID(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("address", address);
        model.addAttribute("petOwner", petOwner);

        return "petOwnerProfile";
    }



    @PostMapping("/petOwnerProfile")
    public String updatePetOwnerProfile(
            @RequestParam("edit-userId") int userId,
            @RequestParam("edit-petOwnerId") int petOwnerId,
            @RequestParam("edit-firstname") String firstname,
            @RequestParam("edit-lastname") String lastname,
            @RequestParam("edit-dob") String birthDate,
            @RequestParam("gender") String gender,
            @RequestParam("edit-phone") String phoneNumber,
            @RequestParam("edit-email") String email,
            @RequestParam("edit-password") String password,
            @RequestParam("edit-streetAddress") String street,
            @RequestParam("edit-suburb") String suburb,
            @RequestParam("edit-state") String state,
            @RequestParam("edit-postcode") String postcode,
            Model model) throws IOException {

        // Fetch the User and Address from the database
        User user = userService.getUserByUserID(userId);
        Address address = addressService.getAddressByUserID(userId);

        // Update the User data
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setBirthDate(LocalDate.parse(birthDate));
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);

        // Update the Address data
        address.setStreet(street);
        address.setSuburb(suburb);
        address.setState(state);
        address.setPostcode(postcode);

        // Save the updated User
        userService.createUser(user);

        // Save the updated Address
        addressService.createAddress(address);

        return "redirect:/petOwnerProfile?petOwnerId=" + petOwnerId;
    }
}
