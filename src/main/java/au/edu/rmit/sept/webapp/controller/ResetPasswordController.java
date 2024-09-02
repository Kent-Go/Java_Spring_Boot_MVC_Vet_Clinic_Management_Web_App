package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(Model model) {
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model) {

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "resetPassword";
        }

        // Verify email and phone number
        boolean isVerified = verifyEmailAndPhone(email, phone);

        if (!isVerified) {
            model.addAttribute("error", "Email or phone number does not match our records.");
            return "resetPassword";
        }

        // Here you would typically look up the user by their email address, validate the request, and then update their password.
        boolean isReset = resetPasswordForUser(email, newPassword);

        if (isReset) {
            // Redirect to login page after successful reset
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Failed to reset password. Please try again.");
            return "resetPassword";
        }
    }

    private boolean verifyEmailAndPhone(String email, String phone) {
        // Replace this with actual logic to verify the email and phone number against your database
        return "user@example.com".equals(email) && "1234567890".equals(phone);
    }

    private boolean resetPasswordForUser(String email, String newPassword) {
        // Implement the actual password reset logic here, interacting with your database or user service
        // For now, we'll simulate success.
        return true; // Simulate a successful password reset
    }
}
