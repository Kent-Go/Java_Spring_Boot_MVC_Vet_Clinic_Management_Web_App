package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import au.edu.rmit.sept.webapp.services.PrescribedMedicationService;

@Controller
public class RefillPrescriptionController {

    @Autowired
    private PrescribedMedicationService prescriptionService;

    @GetMapping("/refillPrescription")
    public String showRefillPrescriptionPage(@RequestParam("prescriptionId") int prescriptionId, Model model) {
        
        PrescribedMedication selectedPrescription = prescriptionService.getPrescribedMedicationByID(prescriptionId);
        

        // Add the selected prescription to the model if found
        if (selectedPrescription != null) {
            model.addAttribute("selectedPrescription", selectedPrescription);
        } else {
            model.addAttribute("errorMessage", "Prescription not found");
        }

        return "refillPrescription";
    }
}
