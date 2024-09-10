package au.edu.rmit.sept.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@SessionAttributes({ "user", "address", "petOwner" })
public class PetRegisterController {
    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/petRegister")
    public String showPetRegister(Model model) {
        return "petRegister"; // Corresponds to petRegister.html
    }

    @PostMapping("/petRegister")
    public String registerPet(
            SessionStatus sessionStatus,
            Model model) {

        // Retrieve user, address, and petOwner from session
        User user = (User) model.getAttribute("user");
        PetOwner petOwner = (PetOwner) model.getAttribute("petOwner");
        Address address = (Address) model.getAttribute("address");

        if (user != null && petOwner != null && address != null) {
            // Save address and petOwner to database
            addressService.createAddress(address);
            petOwnerService.createPetOwner(petOwner);

            // Assuming there is a service to handle pets
            // petService.createPet(pet); // Add your code to save pet

            // Clear session
            sessionStatus.setComplete();

            // Redirect to a success page or profile
            return "redirect:/profile";
        } else {
            // Redirect to an error page or homepage
            return "redirect:/error";
        }
    }
}
