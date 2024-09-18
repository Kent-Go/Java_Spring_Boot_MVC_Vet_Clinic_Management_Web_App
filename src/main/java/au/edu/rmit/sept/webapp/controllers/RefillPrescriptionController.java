package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RefillPrescriptionController {

    @GetMapping("/refillPrescription")
    public String showRefillPrescriptionPage(@RequestParam("prescriptionId") int prescriptionId, Model model) {
        // Dummy data for prescriptions
        List<PrescribedMedication> prescriptions = new ArrayList<>();

        // Medicine 1 - Frusemide
        Medicine medicine1 = new Medicine();
        medicine1.setId(1);
        medicine1.setName("Frusemide");
        medicine1.setQuantity("20mg");
        medicine1.setPrice("$20");
        PrescribedMedication prescription1 = new PrescribedMedication(1, 1, 14, "Feed one tablet daily during breakfast for 2 weeks.", medicine1.getId(), 1, 0);
        prescription1.setMedicine(medicine1);
        prescriptions.add(prescription1);

        // Medicine 2 - Medicine 2
        Medicine medicine2 = new Medicine();
        medicine2.setId(2);
        medicine2.setName("Medicine 2");
        medicine2.setQuantity("20mg");
        medicine2.setPrice("$30");
        PrescribedMedication prescription2 = new PrescribedMedication(2, 2, 7, "Feed one tablet twice daily during breakfast and dinner for 1 week.", medicine2.getId(), 2, 0);
        prescription2.setMedicine(medicine2);
        prescriptions.add(prescription2);

        // Find the prescription that matches the prescriptionId
        PrescribedMedication selectedPrescription = prescriptions.stream()
                .filter(p -> p.getId() == prescriptionId)
                .findFirst()
                .orElse(null);

        // Add the selected prescription to the model if found
        if (selectedPrescription != null) {
            model.addAttribute("selectedPrescription", selectedPrescription);
        } else {
            model.addAttribute("errorMessage", "Prescription not found");
        }

        return "refillPrescription";
    }
}
