package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Controller
public class PetListController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private PetService petService;

    @GetMapping("/petList")
    public String displayPets(@RequestParam("petOwnerId") int petOwnerId, Model model) {
        // Get all the pets based on the petOwnerId
        Collection<Pet> pets = petService.getPetsByPetOwnerID(petOwnerId);

        // Get the pet owner based on the petOwnerId
        PetOwner petOwner = petOwnerService.getPetOwnerByPetOwnerID(petOwnerId);

        // Get the user based on the petOwnerId
        User user = userService.getUserByUserID(petOwner.getUserID());

        // Set the petOwner to pets and the user to the petOwner
        for (Pet pet : pets) {
            pet.setPetOwner(petOwner);
            pet.getPetOwner().setUser(user);
        }

        // Add the pets to the model
        model.addAttribute("pets", pets);
        model.addAttribute("petOwner", petOwner);
        return "petList";
    }
}
