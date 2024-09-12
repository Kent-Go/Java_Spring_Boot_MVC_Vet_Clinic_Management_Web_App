package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.VetService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private VetService vetService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        // Get the user by email
        User user = userService.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Store the user ID in session or pass it as a query parameter
            String redirectUrl = "/petOwnerWelcome?userId=" + user.getId();
            
            // If the user is a vet, redirect to vet dashboard
            if (vetService.getVetByUserID(user.getId()) != null) {
                Vet vet = vetService.getVetByUserID(user.getId());
                redirectUrl = "/vetDashboard?userId=" + user.getId() + "&vetId=" + vet.getId();
            }
            
            return "redirect:" + redirectUrl;
        } else {
            // If authentication fails, show error on login page
            model.addAttribute("error", "Email and/or password is incorrect!");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "register";
    }
}
