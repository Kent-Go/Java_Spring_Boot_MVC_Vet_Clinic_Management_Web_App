package au.edu.rmit.sept.webapp.viewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import au.edu.rmit.sept.webapp.repositories.UserRepository;
import au.edu.rmit.sept.webapp.models.User;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        // Get all the users from the database
        // The findAll() method is provided by the UserController class
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        // Set the message to be displayed on the welcome page
        model.addAttribute("message", "Welcome to the site!");
        return "welcome"; // Refers to the Thymeleaf template welcome.html
    }
}
