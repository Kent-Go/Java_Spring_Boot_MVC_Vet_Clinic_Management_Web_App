package au.edu.rmit.sept.webapp.viewControllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.services.PetService;

@Controller
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping("/pets")
    @ResponseBody
    public Collection<Pet> getAllPets() {
        Collection<Pet> pets = petService.getAllPets();
        return pets;
    }

    @GetMapping("/pets/{petID}")
    @ResponseBody
    public Pet getPetbyPetID(@PathVariable int petID) {
        return petService.getPetByPetID(petID);
    }

    @GetMapping("/pets/owner/{ownerID}")
    @ResponseBody
    public Collection<Pet> getPetbyPetOwnerID(@PathVariable int ownerID) {
        return petService.getPetsByPetOwnerID(ownerID);
    }

    @PostMapping("/pets/new")
    public String registerVet(@ModelAttribute Pet pet, Model model) {
        petService.createPet(pet);
        return "redirect:/vet_example";
    }
}