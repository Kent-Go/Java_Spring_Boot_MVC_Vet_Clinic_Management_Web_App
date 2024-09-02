package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        System.out.println("Processing login for email: " + email);

        // Simulated user authentication logic
        // This would be replaced with actual logic that checks the database or session
        if (authenticateUser(email, password)) {
            // Determine user profile type
            String userProfileType = getUserProfileType(email);

            if ("VETERINARIAN".equals(userProfileType)) {
                return "redirect:/vetDashboard";
            } else if ("USER".equals(userProfileType)) {
                return "redirect:/userDashboard";
            } else {
                model.addAttribute("error", "Unknown user profile.");
                return "login";
            }
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

    // Mock method to authenticate a user
    private boolean authenticateUser(String email, String password) {
        // Replace this with actual logic to check against your database
        String storedEmail = "vet@example.com"; // Simulate stored email
        String storedPassword = "password"; // Simulate stored password

        return storedEmail.equals(email) && storedPassword.equals(password);
    }

    // Mock method to get the user profile type
    private String getUserProfileType(String email) {
        // Replace this with logic to retrieve the user profile type
        if ("vet@example.com".equals(email)) {
            return "VETERINARIAN";
        } else if ("user@example.com".equals(email)) {
            return "USER";
        }
        return null;
    }
    
    @GetMapping("/userDashboard")
    public String showUserDashboardPage(Model model) {
        return "userDashboard";  // this should point to your user dashboard HTML/Thymeleaf template
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "register";
    }
}
