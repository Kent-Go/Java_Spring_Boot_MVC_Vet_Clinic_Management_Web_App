package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Vet;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleAppointmentSelectVeterinarianController {

    // Hardcoded list of veterinarians
    private List<Vet> getVets() {
        List<Vet> vets = new ArrayList<>();

        // Adding hardcoded veterinarians
        Vet vet1 = new Vet("Dr Jude Bellingham", "English, Spanish", "Experienced in treating small animals", 101);
        vet1.setId(1);
        vets.add(vet1);

        Vet vet2 = new Vet("Dr Julian Alvarez", "English, French", "Specialized in surgeries", 102);
        vet2.setId(2);
        vets.add(vet2);

        Vet vet3 = new Vet("Dr Felix Gavi", "English, German", "Focuses on dental care for pets", 103);
        vet3.setId(3);
        vets.add(vet3);

        return vets;
    }

    @GetMapping("/appointment/new/select_veterinarian")
    public String selectVet(Model model) {
        List<Vet> vets = getVets();
        model.addAttribute("vets", vets);
        return "vetList";  // This should match your Thymeleaf template name for vet list
    }

    @PostMapping("/appointment/selectVet")
    public String selectVetForAppointment(@RequestParam("vetId") int vetId, Model model) {
        // Logic to handle creating the appointment with the selected veterinarian
        Vet selectedVet = getVets().stream().filter(vet -> vet.getId() == vetId).findFirst().orElse(null);
        if (selectedVet != null) {
            model.addAttribute("selectedVetName", selectedVet.getTitle());
        }
        return "appointmentConfirmation";  // This should match your confirmation view template
    }
}
