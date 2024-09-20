package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String showCheckoutPage(@RequestParam("prescriptionId") Long prescriptionId, Model model) {

        // Dummy data for prescription details
        model.addAttribute("prescriptionName", "Frusemide 20mg");
        model.addAttribute("quantity", 1);
        model.addAttribute("price", 20);

        model.addAttribute("additionalItemName", "Medicine 2 20mg");
        model.addAttribute("additionalQuantity", 2);
        model.addAttribute("additionalPrice", 30);

        // Dummy payment method
        model.addAttribute("paymentMethod", "MasterCard ending in **34");

        // Dummy shipping address
        String shippingAddress = "123 Main St, Melbourne, VIC 3000, Australia";
        model.addAttribute("shippingAddress", shippingAddress);

        // Total price calculation
        model.addAttribute("totalPrice", 50);

        return "checkout";
    }
}
