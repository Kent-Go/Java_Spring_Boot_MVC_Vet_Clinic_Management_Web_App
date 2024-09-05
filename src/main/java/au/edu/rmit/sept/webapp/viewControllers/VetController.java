package au.edu.rmit.sept.webapp.viewControllers;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Base64;
// import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetDTO;
import au.edu.rmit.sept.webapp.repositories.VetRepository;

@Controller
public class VetController {
    @Autowired
    private VetRepository vetRepository;

    @GetMapping("/vet_example")
    public String displayVets(Model model) {
        // Get all the vets from the database
        List<Vet> vets = vetRepository.findAll();
        // model.addAttribute("vets", vets);

        // return "vet_example"; // Refers to the Thymeleaf template welcome.html

        // Convert profile pictures to Base64 strings
        List<VetDTO> vetDTOs = vets.stream().map(vet -> {
            VetDTO vetDTO = new VetDTO();
            vetDTO.setId(vet.getId());
            vetDTO.setTitle(vet.getTitle());
            vetDTO.setLanguagesSpoken(vet.getLanguagesSpoken());
            vetDTO.setSelfDescription(vet.getSelfDescription());
            vetDTO.setUserID(vet.getUserID());
            vetDTO.setProfilePicture(Base64.getEncoder().encodeToString(vet.getProfilePicture()));
            return vetDTO;
        }).collect(Collectors.toList());

        model.addAttribute("vets", vetDTOs);

        return "vet_example"; // Refers to the Thymeleaf template vet_example.html
    }
}
