package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

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
        PetOwner petOwner = petOwnerService.getPetOwnerByUserID(petOwnerId);
        User user = userService.getUserByUserID(petOwner.getUserID());
        Address address = addressService.getAddressByUserID(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("address", address);
        model.addAttribute("petOwner", petOwner);

        return "petOwnerProfile";
    }

    @PostMapping("/petOwnerProfile")
    public String updateProfile(@RequestParam Map<String, String> formData,
                                @RequestParam("profilePicture") MultipartFile profilePicture,
                                Model model) throws IOException {
        // Handle form data updates
        if (!profilePicture.isEmpty()) {
            byte[] profilePictureBytes = profilePicture.getBytes();
            // Logic to save the profile picture
        }
        // Logic to update the profile data

        return "redirect:/petOwnerProfile";
    }
    /* you can try
@PostMapping("/petOwnerProfile")
public String updateProfile(@RequestBody Map<String, String> formData, @RequestParam("petOwnerId") int petOwnerId, Model model) {
    // Fetch pet owner and user by ID
    PetOwner petOwner = petOwnerService.getPetOwnerById(petOwnerId);
    User user = userService.getUserByUserID(petOwner.getUserID());

    // Update user information
    user.setFirstName(formData.get("firstName"));
    user.setLastName(formData.get("lastName"));
    user.setBirthDate(LocalDate.parse(formData.get("birthDate")));
    user.setGender(formData.get("gender"));
    user.setPhoneNumber(formData.get("phone"));
    user.setEmail(formData.get("email"));

    // Update address (assuming address service exists)
    Address address = addressService.getAddressByUserID(user.getId());
    address.setStreet(formData.get("street"));
    address.setSuburb(formData.get("suburb"));
    address.setState(formData.get("state"));
    address.setPostcode(formData.get("postcode"));

    // Save updated user and address
    userService.save(user);
    addressService.save(address);

    // Reload updated profile data into the model
    model.addAttribute("user", user);
    model.addAttribute("address", address);

    return "petOwnerProfile";
}
*/

}
