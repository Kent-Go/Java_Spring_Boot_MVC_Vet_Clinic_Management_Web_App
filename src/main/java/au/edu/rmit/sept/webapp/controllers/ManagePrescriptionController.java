package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ManagePrescriptionController {

    @GetMapping("/managePrescription")
    public String showManagePrescriptionPage(Model model) {
        // Dummy data for existing prescriptions
        List<PrescribedMedication> prescriptions = new ArrayList<>();

        // Create medicine objects
        Medicine frusemide = new Medicine(1, "Frusemide", "20mg", "$10");
        Medicine paracetamol = new Medicine(2, "Paracetamol", "500mg", "$5");

        // Create prescribed medication and link it with the medicine
        PrescribedMedication prescription1 = new PrescribedMedication(1, 2, 14, "Feed during breakfast and dinner", 1, 1, 1);
        prescription1.setMedicine(frusemide);
        prescriptions.add(prescription1);

        // Add dummy prescription to the list
        model.addAttribute("prescriptions", prescriptions);

        // Add available medicines
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(frusemide);
        medicines.add(paracetamol);
        model.addAttribute("medicines", medicines);

        return "managePrescription";
    }

}
