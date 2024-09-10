package au.edu.rmit.sept.webapp.controller;

import java.util.Map;
import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        // Load user profile data from the database or other sources
        return "profile"; // Returns the profile.html view
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam Map<String, String> formData,
            @RequestParam("profilePicture") MultipartFile profilePicture,
            Model model) throws IOException {
        // Process the updated form data and save it to the database
        // Example: userService.updateUserProfile(formData);

        // Handle profile picture upload
        if (!profilePicture.isEmpty()) {
            byte[] profilePictureBytes = profilePicture.getBytes();
            // Save the profile picture to the database or file system
        }

        // Reload the updated profile data into the model
        model.addAttribute("profileData", formData);

        return "profile"; // Reloads the profile page with the updated data
    }
}
