package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.repositories.PetRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/pet")
public class PetRestController {
    @Autowired
    private PetRepository petRepository;

    @GetMapping("/{PetID}")
    public Optional<Pet> getPetByPetId(@PathVariable("PetID") int petID) {
        return petRepository.findById(petID);
    }
}
