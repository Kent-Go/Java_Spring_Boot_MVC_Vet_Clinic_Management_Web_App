package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    @GetMapping("/userRegister")
    public String showRegistrationPage(Model model) {
        model.addAttribute("selected", "");
        return "register";
    }
}
