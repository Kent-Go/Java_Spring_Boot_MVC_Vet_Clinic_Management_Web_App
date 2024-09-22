package au.edu.rmit.sept.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.util.ArrayList;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.PetService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@SessionAttributes({ "user", "address", "petOwner" })
public class PetRegisterController {
    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PetService petService;

    @GetMapping("/petRegister")
    public String showPetRegister(Model model) {
        return "petRegister"; // Corresponds to petRegister.html
    }

    @PostMapping("/petRegister")
    @ResponseBody
    public ArrayList<Pet> registerPet(
            @RequestParam("petName") String[] petName,
            @RequestParam("dob") LocalDate[] dob,
            @RequestParam("gender") String[] gender,
            @RequestParam("petType") String[] petType,
            @RequestParam("breed") String[] breed,
            @RequestParam("color") String[] color,
            @RequestParam("weight") float[] weight,
            //@RequestParam("profilePicture") IDK profilePicture,
            @RequestParam("allergies") String[] allergies,
            @RequestParam("existingCondition") String[] condition,
            @RequestParam("petOwnerId") int petOwnerID,
            SessionStatus sessionStatus,
            Model model) {

        // Retrieve user, address, and petOwner from session
        User user = (User) model.getAttribute("user");
        PetOwner petOwner = (PetOwner) model.getAttribute("petOwner");
        Address address = (Address) model.getAttribute("address");
        ArrayList<Pet> addedPets = new ArrayList<Pet>();
        
        if ((user != null && petOwner != null && address != null) || petOwnerID >= 0) {

            PetOwner owner;

            //petOwnerID is -1 if this request comes through a new account,
            //so we need to save the address and petOwner to the database.
            if(petOwnerID < 0){
                addressService.createAddress(address);
                owner = petOwnerService.createPetOwner(petOwner);
            }
            else {
                //Just retrieve the petOwner if it's an existing account.
                owner = petOwnerService.getPetOwnerByPetOwnerID(petOwnerID);
            }

            for(int i = 0; i < petName.length; i++){
                // Assuming there is a service to handle pets
                Pet pet = new Pet();

                pet.setName(petName[i]);
                pet.setBirthDate(dob[i]);
                pet.setGender(gender[i]);
                pet.setSpecies(petType[i]);
                pet.setBreed(breed[i]);
                pet.setWeight(weight[i]);
                pet.setPetOwnerID(owner.getId());
                pet.setAllergies(allergies[i]);
                pet.setExistingConditions(condition[i]);
                
                addedPets.add(pet);
                petService.createPet(pet); // Add your code to save pet
            }

            // Clear session
            sessionStatus.setComplete();
        }

        return addedPets;
    }
}
