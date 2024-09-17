package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VideosController {

    @GetMapping("/videos")
    public String showVideos(Model model) {
        return "videos"; 
    }

    @PostMapping("/videos")
    public String videosPage() {
        // Handle the form submission, save the pet owner details
        return "redirect:/videos"; // Redirect to the same page or to a success page
    }
}
