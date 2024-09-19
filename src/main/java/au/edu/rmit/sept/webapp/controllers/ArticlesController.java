package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticlesController {

    @GetMapping("/articlesPage")
    public String showArticles(Model model) {
        return "articlesPage";
    }

    @PostMapping("/articlesPage")
    public String articlesPage() {
        // Handle the form submission, save the pet owner details
        return "redirect:/articlesPage"; // Redirect to the same page or to a success page
    }
}
