package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetProfileController {

    // Display the pet profile page
    @GetMapping("/petProfile")
    public String showPetProfile(Model model) {
        // The pet data will be loaded from the browser's localStorage, no need to pass data here
        return "petProfile";  // This refers to the petProfile.html page
    }

    // This method could be expanded in case you want to add server-side functionality
    // for saving or retrieving pet data from a database.
}
