package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import au.edu.rmit.sept.webapp.models.Medicine;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PrescriptionListController {

    @GetMapping("/prescriptions")
    public String showPrescriptionList(Model model) {
        // Create hardcoded data
        List<PrescribedMedication> prescriptions = new ArrayList<>();

        // Medicine 1
        Medicine medicine1 = new Medicine();
        medicine1.setId(1);
        medicine1.setName("Frusemide");
        medicine1.setQuantity("20mg");
        medicine1.setPrice("$10.00");

        // Prescription 1
        PrescribedMedication prescription1 = new PrescribedMedication(1, 1, 14, "Feed one tablet daily during breakfast for 2 weeks.", medicine1.getId(), 1, 0);
        prescription1.setMedicine(medicine1);
        prescriptions.add(prescription1);

        // Medicine 2
        Medicine medicine2 = new Medicine();
        medicine2.setId(2);
        medicine2.setName("Medicine 2");
        medicine2.setQuantity("20mg");
        medicine2.setPrice("$15.00");

        // Prescription 2
        PrescribedMedication prescription2 = new PrescribedMedication(2, 2, 7, "Feed one tablet twice daily during breakfast and dinner for 1 week.", medicine2.getId(), 2, 0);
        prescription2.setMedicine(medicine2);
        prescriptions.add(prescription2);

        // Medicine 3
        Medicine medicine3 = new Medicine();
        medicine3.setId(3);
        medicine3.setName("Medicine 3");
        medicine3.setQuantity("50mg");
        medicine3.setPrice("$20.00");

        // Prescription 3
        PrescribedMedication prescription3 = new PrescribedMedication(1, 1, 10, "Feed one tablet daily during lunch for 10 days.", medicine3.getId(), 3, 0);
        prescription3.setMedicine(medicine3);
        prescriptions.add(prescription3);

        // Add prescriptions to the model
        model.addAttribute("prescriptions", prescriptions);

        return "prescriptionList";
    }
    @GetMapping("/refillPrescription")
    public String refillPrescription() {
        // Logic to handle the prescription refill
        System.out.println("Refill prescription request received");

        // Redirect to some confirmation page or back to the prescription list
        return "redirect:/refillPrescription"; // Redirect back to the list after handling
    }
}
