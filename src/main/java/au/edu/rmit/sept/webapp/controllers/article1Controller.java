package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class article1Controller {

    @GetMapping("/article1")
    public String showArticles(Model model) {
        return "article1";
    }

    @PostMapping("/article1")
    public String articlesPage() {
        // Handle the form submission, save the pet owner details
        return "redirect:/article1"; // Redirect to the same page or to a success page
    }
}


