package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FAQPageController {

    @GetMapping("/faqPage")
    public String showFAQ(Model model) {
        return "faqPage";
    }

    @PostMapping("/faqPage")
    public String FAQPage() {
        // Handle the form submission, save the pet owner details
        return "redirect:/faqPage"; // Redirect to the same page or to a success page
    }
}