package au.edu.rmit.sept.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.edu.rmit.sept.webapp.services.UserService;

@Controller
public class ResetPasswordController {
    @Autowired
    private UserService userService;

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(Model model) {
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            RedirectAttributes redirectAttributes) {

        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "resetPassword";
        }

        // Check if email exists in the database
        // If email does not exist, return an error message
        if (userService.getUserByEmail(email) == null) {
            redirectAttributes.addFlashAttribute("error", "Email does not exist.");
            return "resetPassword";
        }

        // Update the password
        userService.updatePassword(email, newPassword);
        redirectAttributes.addFlashAttribute("success", "Password updated successfully.");

        return "redirect:/login";
    }
}
