package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.PaymentDetails;
import au.edu.rmit.sept.webapp.repositories.PaymentDetailsRepository;

import java.util.List;

@RestController
@RequestMapping("/api/paymentDetails")
public class PaymentDetailsRestController {
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @GetMapping
    public List<PaymentDetails> getPaymentDetails() {
        return paymentDetailsRepository.findAll();
    }
}
