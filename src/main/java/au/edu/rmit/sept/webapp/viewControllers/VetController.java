package au.edu.rmit.sept.webapp.viewControllers;

import java.util.Collection;
import java.util.stream.Collectors;

import java.util.Base64;
// import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetDTO;
import au.edu.rmit.sept.webapp.services.VetService;

@Controller
public class VetController {
    @Autowired
    private VetService vetService;

    @GetMapping("/vet_example")
    public String displayPage() {
        return "vet_example";
    }

    @GetMapping("/vets")
    @ResponseBody
    public Collection<VetDTO> getAllVets() {
        // Get all the vets from the database
        Collection<Vet> vets = vetService.getAllVets();
        // model.addAttribute("vets", vets);

        // return "vet_example"; // Refers to the Thymeleaf template welcome.html

        // Convert profile pictures to Base64 strings
        Collection<VetDTO> vetDTOs = vets.stream().map(vet -> {
            VetDTO vetDTO = new VetDTO();
            vetDTO.setId(vet.getId());
            vetDTO.setTitle(vet.getTitle());
            vetDTO.setLanguagesSpoken(vet.getLanguagesSpoken());
            vetDTO.setSelfDescription(vet.getSelfDescription());
            vetDTO.setUserID(vet.getUserID());
            vetDTO.setProfilePicture(Base64.getEncoder().encodeToString(vet.getProfilePicture()));
            return vetDTO;
        }).collect(Collectors.toList());

        return vetDTOs;

        // model.addAttribute("vets", vetDTOs);

        // return "vet_example"; // Refers to the Thymeleaf template vet_example.html
    }

    @GetMapping("/vets/{userID}")
    @ResponseBody
    public Vet getVetbyUserID(@PathVariable int userID) {
        return vetService.getVetByUserID(userID);
    }

    @PostMapping("/vets/new")
    public String registerVet(@ModelAttribute Vet vet, Model model) {
        vetService.createVet(vet);
        return "redirect:/vet_example";
    }
}