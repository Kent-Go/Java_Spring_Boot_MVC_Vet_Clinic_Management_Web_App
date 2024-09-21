package au.edu.rmit.sept.webapp.controllers;

import java.util.Map;
import java.util.List;
import java.io.IOException;

import org.springframework.ui.Model;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Qualification;

import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.QualificationService;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


@Controller
public class VetProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private VetService vetService;

    @Autowired
    private QualificationService qualificationService;

    @GetMapping("/vetProfile")
    public String showProfilePage(@RequestParam("vetId") int vetId, Model model) {
        // Check for invalid parameter values (e.g., negative ID or zero)
        if (vetId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Vet ID");
        }

        // Get the vet by vetID
        Vet vet = vetService.getVetByVetID(vetId);

        // Check if vet is null, return 404 if not found
        if (vet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found");
        }

        // Get the user associated with the vet
        User user = userService.getUserByUserID(vet.getUserID());

        // Get the address of the vet
        Address address = addressService.getAddressByUserID(user.getId());

        // Get the qualifications of the vet
        List<Qualification> qualifications = qualificationService.getQualificationsByVetID(vetId);

        // Add attributes to the model
        model.addAttribute("user", user);
        model.addAttribute("address", address);
        model.addAttribute("vet", vet);
        model.addAttribute("qualifications", qualifications);

        return "vetProfile"; // Returns the vetProfile.html view
    }



    @PostMapping("/vetProfile")
    public String updateProfile(@RequestParam Map<String, String> formData,
                                @RequestParam("profilePicture") MultipartFile profilePicture,
                                Model model) throws IOException {
        // Handle form validation (check for required fields, etc.)
        if (formData.isEmpty() || profilePicture.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields or profile picture");
        }

        // Handle profile picture upload
        if (!profilePicture.isEmpty()) {
            byte[] profilePictureBytes = profilePicture.getBytes();
            // Save the profile picture to the database or file system
        }

        // Reload the updated profile data into the model
        model.addAttribute("profileData", formData);

        return "vetProfile"; // Reloads the profile page with the updated data
    }
}
