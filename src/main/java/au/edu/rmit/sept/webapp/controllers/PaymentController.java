package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    @GetMapping("/payment")
    public String showPaymentPage(@RequestParam("prescriptionId") Long prescriptionId, Model model) {
        // Add the prescriptionId to the model so that the view can access it
        model.addAttribute("prescriptionId", prescriptionId);
        return "payment";
    }

    // Update this mapping to avoid conflict with the checkout controller
    @GetMapping("/processPayment")
    public String processPayment(@RequestParam("prescriptionId") Long prescriptionId, Model model) {
        // Add the prescriptionId to the model so that it can be used in the process payment step
        model.addAttribute("prescriptionId", prescriptionId);
        return "confirmPayment";
    }
}
