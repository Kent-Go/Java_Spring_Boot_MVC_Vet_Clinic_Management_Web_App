package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
public class PetListController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private PetService petService;

    // Display the pets based on the petOwnerId
    @GetMapping("/petList")
    public String displayPets(@RequestParam("petOwnerId") int petOwnerId, Model model) {
        // Get all the pets based on the petOwnerId
        Collection<Pet> pets = petService.getPetsByPetOwnerID(petOwnerId);

        // Get the pet owner based on the petOwnerId
        PetOwner petOwner = petOwnerService.getPetOwnerByPetOwnerID(petOwnerId);
        // Handle null case for petOwner
        if (petOwner == null) {
            // Optionally, you could add an error message or return a different view
            model.addAttribute("errorMessage", "Pet Owner not found.");
            return "errorPage";  // Make sure to have an error page for this case
        }

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

    // Delete a pet based on the petId
    @PostMapping("/pets/delete")
    public String deletePet(@RequestParam("petId") int petId, @RequestParam("petOwnerId") int petOwnerId,
            RedirectAttributes redirectAttributes) {
        // Call the service to delete the pet
        petService.deletePetById(petId);

        // Optionally, add a success message
        redirectAttributes.addFlashAttribute("message", "Pet deleted successfully.");

        // Redirect back to the pet list page
        return "redirect:/petList?petOwnerId=" + petOwnerId;
    }
}
